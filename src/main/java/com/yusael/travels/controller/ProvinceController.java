package com.yusael.travels.controller;

import com.yusael.travels.entity.Province;
import com.yusael.travels.entity.Result;
import com.yusael.travels.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    /**
     * 修改省份信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Province province) {
        Result result = new Result();
        try {
            provinceService.update(province);
            result.setMsg("修改省份信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("修改省份信息失败!");
        }
        return result;
    }

    /**
     * 查询一个省份信息
     */
    @GetMapping("/findOne")
    public Province findOne(String id) {
        return provinceService.findOne(id);
    }

    /**
     * 删除省份
     */
    @GetMapping("/delete")
    public Result delete(String id) {
        Result result = new Result();
        try {
            provinceService.delete(id);
            result.setMsg("删除省份信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("删除省份信息失败!");
        }
        return result;
    }

    /**
     * 添加省份
     */
    @PostMapping("/save")
    public Result save(@RequestBody Province province) {
        Result result = new Result();
        try {
            provinceService.save(province);
            result.setMsg("保存省份信息成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg("保存省份信息失败!");
        }
        return result;
    }

    /**
     * 分页查询数据
     */
    @GetMapping("/findByPage")
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        page = page==null ? 1 : page;
        rows = rows==null ? 4 : rows;
        System.out.println(page + " : " + rows);
        HashMap<String, Object> map = new HashMap<>();

        // 分页查询出当前页面显示的数据
        List<Province> provinces = provinceService.findByPage(page, rows);

        // 查询总数据条数, 用于计算总页数
        Integer totals = provinceService.findTotals();
        // 计算总页数
        // 如果总数据条数可以整除每一页数据个数, 说明结果正好为总页数
        // 如果总数据条数无法整除每一页数据个数, 说明总页数需要结果 + 1
        Integer totalPage = totals % rows == 0 ? totals / rows : totals / rows + 1;

        map.put("provinces", provinces);
        map.put("totals", totals);
        map.put("totalPage", totalPage);
        map.put("page", page);

        map.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
        return map;
    }

}
