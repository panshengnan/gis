//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cgwx.dao;

import com.cgwx.data.entity.GisClientFolder;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GisClientFolderMapper {
    int insert(GisClientFolder record);

    List<GisClientFolder> selectAll();

    @Select({"SELECT folder_id\n FROM gis_Client_folder\nWHERE client_id = #{clientId} order by folder_id desc limit 1"})
    int getMaxFolderId(@Param("clientId") long clientId);

    @Select({"SELECT *\n FROM gis_Client_folder\nWHERE client_id = #{clientId} "})
    @Results({@Result(
            column = "folder_id",
            property = "folderId"
    ), @Result(
            column = "parent_id",
            property = "parentId"
    ), @Result(
            column = "folder_name",
            property = "folderName"
    )})
    List<GisClientFolder> getFoldersByclientId(@Param("clientId") long clientId);

    @Update({"UPDATE gis_Client_folder \n set parent_id = #{newParentId} \nWHERE folder_id = #{folderId} and client_id = #{clientId} "})
    Boolean modifyFolderParentIdByFolderId(@Param("clientId") long clientId, @Param("folderId") int folderId, @Param("newParentId") int newParentId);

    @Delete({"DELETE \n FROM gis_Client_folder\nWHERE folder_id = #{folderId} and client_id = #{clientId} "})
    Boolean deleteFolderByFolderId(@Param("folderId") int folderId, @Param("clientId") long clientId);

    @Update({"UPDATE gis_Client_folder \n set folder_name = #{newName} \nWHERE folder_id = #{folderId} and client_id = #{clientId} "})
    Boolean UpdateFolderNameByFolderId(@Param("folderId") int folderId, @Param("newName") String newName, @Param("clientId") long clientId);

    @Select({"SELECT count(*)\n FROM gis_Client_folder\nWHERE client_id = #{clientId}"})
    int getclientId(@Param("clientId") long clientId);
}
