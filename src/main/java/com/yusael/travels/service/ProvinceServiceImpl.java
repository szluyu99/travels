package com.yusael.travels.service;

import com.yusael.travels.dao.ProvinceDAO;
import com.yusael.travels.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public List<Province> findByPage(Integer page, Integer rows) {
        // 传入的是当前页数, 以及页面显示的数量
        // 所以要根据这两个参数计算从mysql中查询数据要从第几行开始查几条
        int start = (page - 1) * rows; // 计算要查询的数据是从第几条数据开始的
        return provinceDAO.findByPage(start, rows);
    }

    @Override
    public Province findOne(String id) {
        return provinceDAO.findOne(id);
    }

    @Override
    public void update(Province province) {
        provinceDAO.update(province);
    }

    @Override
    public void delete(String id) {
        provinceDAO.delete(id);
    }

    @Override
    public void save(Province province) {
        province.setPlacecounts(0); //景点个数为零
        provinceDAO.save(province);
    }

    @Override
    public Integer findTotals() {
        return provinceDAO.findTotals();
    }
}
