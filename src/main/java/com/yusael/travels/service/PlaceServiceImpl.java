package com.yusael.travels.service;

import com.yusael.travels.dao.PlaceDAO;
import com.yusael.travels.dao.ProvinceDAO;
import com.yusael.travels.entity.Place;
import com.yusael.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public List<Place> findByProvinceIdPage(Integer page, Integer rows, String provinceId) {
        Integer start = (page - 1) * rows;
        return placeDAO.findByProvinceIdPage(start, rows, provinceId);
    }

    @Override
    public Integer findByProvinceIdCounts(String provinceId) {
        return placeDAO.findByProvinceIdCounts(provinceId);
    }

    @Override
    public void save(Place place) {
        // 保存景点信息后, 要更新对应省份的景点个数 +1
        placeDAO.save(place);
        // 查询原始省份信息
        Province province = provinceDAO.findOne(place.getProvinceid());
        // 更新省份信息的景点个数
        province.setPlacecounts(province.getPlacecounts() + 1);
        provinceDAO.update(province);
    }

    @Override
    public void delete(String id) {
        // 不能直接删除景点, 要先让省份中的景点个数 -1, 再删除景点
        Place place = placeDAO.findOne(id);
        Province province = provinceDAO.findOne(place.getProvinceid());
        province.setPlacecounts(province.getPlacecounts() - 1); // 让省份的景点个数 -1
        provinceDAO.update(province);
        // 删除景点信息
        placeDAO.delete(id);
    }

    @Override
    public Place findOne(String id) {
        return placeDAO.findOne(id);
    }

    @Override
    public void update(Place place) {
        placeDAO.update(place);
    }
}
