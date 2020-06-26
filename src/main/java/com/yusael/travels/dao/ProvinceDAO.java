package com.yusael.travels.dao;

import com.yusael.travels.entity.Province;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProvinceDAO extends BaseDAO<Province, String> {
}
