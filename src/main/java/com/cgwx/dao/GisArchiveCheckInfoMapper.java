package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveCheckInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisArchiveCheckInfoMapper {
    int insert(GisArchiveCheckInfo record);

    List<GisArchiveCheckInfo> selectAll();
    @Select({"SELECT file_name\nFROM gis_archive_check_info\nWHERE product_id = #{tempId} "})
    String selectFileNameById(@Param("tempId") String var1);

    @Select({"SELECT temporary_path\nFROM gis_archive_check_info\nWHERE product_id = #{tempId} "})
    String selectTemporaryPathById(@Param("tempId") String var1);
}