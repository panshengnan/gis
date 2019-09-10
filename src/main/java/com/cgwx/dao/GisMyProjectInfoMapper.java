package com.cgwx.dao;

import com.cgwx.data.dto.ProjectDto;
import com.cgwx.data.entity.GisMyProjectInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GisMyProjectInfoMapper {
    int insert(GisMyProjectInfo record);

    List<GisMyProjectInfo> selectAll();


    @Select({"<script>" +
            "SELECT * FROM gis_my_project_info WHERE 1=1"+
            "<if test='null!= projectName &amp; !\"\".equals(projectName)'>" +
            " and project_name like \'%${projectName}%\' "
            + "</if>"+
            "order by gmt_modified desc"+
            "</script>"
    })
    @Results({@Result(
            column = "project_name",
            property = "projectName"

    ), @Result(
            column = "id",
            property = "projectId"
    ), @Result(
            column = "gmt_created",
            property = "createTime"
    ), @Result(
            column = "jpg",
            property = "thumbUrl")
    })
    List<ProjectDto> getProjectList(@Param("projectName")String projectName);

    @Select({"<script>" +
            "SELECT id FROM gis_my_project_info WHERE status = #{status}"+
            "</script>"
    })
    int getProjectId(@Param("status")String status);

    @Select({"<script>" +
            "delete FROM gis_my_project_info WHERE id = #{id}"+
            "</script>"
    })
    void deleteProject(@Param("id")int id);


    @Select({"<script>" +
            "update gis_my_project_info set project_name = #{name} where id = #{projectId}"+
            "</script>"
    })
    void renameProject(@Param("projectId")int projectId,@Param("name")String name);
}