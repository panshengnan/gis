package com.cgwx.dao;

import com.cgwx.data.entity.GisProductStoreLinkInfo;
import java.util.List;

public interface GisProductStoreLinkInfoMapper {
    int insert(GisProductStoreLinkInfo record);

    List<GisProductStoreLinkInfo> selectAll();
}