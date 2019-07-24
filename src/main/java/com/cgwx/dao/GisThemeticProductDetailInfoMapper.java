package com.cgwx.dao;

import com.cgwx.data.entity.GisThemeticProductDetailInfo;
import java.util.List;

public interface GisThemeticProductDetailInfoMapper {
    int insert(GisThemeticProductDetailInfo record);

    List<GisThemeticProductDetailInfo> selectAll();
}