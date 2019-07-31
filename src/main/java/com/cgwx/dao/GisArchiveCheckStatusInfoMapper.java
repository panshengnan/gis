package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveCheckStatusInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisArchiveCheckStatusInfoMapper {
    int insert(GisArchiveCheckStatusInfo record);

    List<GisArchiveCheckStatusInfo> selectAll();
}