package com.cgwx.dao;

import com.cgwx.data.entity.GisUserProductRelation;
import java.util.List;

public interface GisUserProductRelationMapper {
    int insert(GisUserProductRelation record);

    List<GisUserProductRelation> selectAll();
}