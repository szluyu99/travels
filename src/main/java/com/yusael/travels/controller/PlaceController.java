package com.yusael.travels.controller;

import com.yusael.travels.entity.Place;
import com.yusael.travels.entity.Result;
import com.yusael.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
@CrossOrigin
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Value("${upload.dir}")
    private String realPath;

    /**
     * 修改景点信息
     */
    @PostMapping("/update")
    public Result update(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();
        System.out.println(place);
        System.out.println(pic);
        try {
            // 对接收文件做 base64 编码
            String picpath = Base64Utils.encodeToString(pic.getBytes());
            place.setPicpath(picpath);
            // 处理文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + extension;
            pic.transferTo(new File(realPath, newFileName));
            // 修改景点信息
            placeService.update(place);
            result.setMsg("修改景点信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据 id 查询景点信息
     */
    @GetMapping("/findOne")
    public Place findOne(String id) {
        return placeService.findOne(id);
    }

    /**
     * 删除景点信息
     */
    @GetMapping("/delete")
    public Result delete(String id) {
        Result result = new Result();
        try {
            placeService.delete(id);
            result.setMsg("删除景点信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("删除景点信息失败!");
        }
        return result;
    }

    /**
     * 保存景点信息
     *
     * @param pic
     * @return
     */
    @PostMapping("/save")
    public Result save(MultipartFile pic, Place place) throws IOException {
        Result result = new Result();
        try {
            // 文件上传
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
            System.out.println(extension);
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + extension;
            System.out.println(newFileName);
            // base64编码处理(注意, 这一步必须放在 transferTo 操作前面!)
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            // 文件上传
            File file = new File(realPath);
            pic.transferTo(new File(file, newFileName));
            // 保存place对象
            placeService.save(place);
            result.setMsg("保存景点信息成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 根据省份id查询景点的方法
     */
    @GetMapping("/findAllPlace")
    public Map<String, Object> findAllPlace(Integer page, Integer rows, String provinceId) {
        HashMap<String, Object> map = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 4 : rows;
        // 景点集合
        List<Place> places = placeService.findByProvinceIdPage(page, rows, provinceId);

        // 处理分页
        Integer counts = placeService.findByProvinceIdCounts(provinceId); // 总页数
        Integer totalPage = counts % rows == 0 ? counts / rows : counts / rows + 1;

        map.put("places", places);
        map.put("page", page);
        map.put("counts", counts);
        map.put("totalPage", totalPage);

        return map;
    }

}
