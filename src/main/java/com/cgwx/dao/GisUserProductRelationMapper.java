package com.cgwx.dao;

import com.cgwx.data.entity.GisUserProductRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisUserProductRelationMapper {
    int insert(GisUserProductRelation record);

    List<GisUserProductRelation> selectAll();
}