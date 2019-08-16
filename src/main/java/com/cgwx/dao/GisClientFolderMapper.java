package com.cgwx.dao;

import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.entity.GisClientFolder;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisClientFolderMapper {
    int insert(GisClientFolder record);

    List<GisClientFolder> selectAll();
    @Select({"SELECT folder_id\n" +
            " FROM gis_Client_folder\n" +
            "WHERE client_id = #{clientId} "+
            "order by folder_id desc limit 1"
    })
    int getMaxFolderId(@Param("clientId") int clientId);

    @Select({"SELECT *\n" +
            " FROM gis_Client_folder\n" +
            "WHERE client_id = #{clientId} "
    })
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
    List<GisClientFolder> getFoldersByclientId(@Param("clientId") int clientId);

    @Update({"UPDATE gis_Client_folder \n" +
            " set parent_id = #{newParentId} \n" +
            "WHERE folder_id = #{folderId} and client_id = #{clientId} "
    })
    Boolean modifyFolderParentIdByFolderId(@Param("clientId") int clientId,@Param("folderId") int folderId, @Param("newParentId") int newParentId);

    @Delete({"DELETE \n" +
            " FROM gis_Client_folder\n" +
            "WHERE folder_id = #{folderId} and client_id = #{clientId} "
    })
    Boolean deleteFolderByFolderId(@Param("folderId") int folderId,@Param("clientId") int clientId);

    @Update({"UPDATE gis_Client_folder \n" +
            " set folder_name = #{newName} \n" +
            "WHERE folder_id = #{folderId} and client_id = #{clientId} "
    })
    Boolean UpdateFolderNameByFolderId(@Param("folderId") int folderId, @Param("newName") String newName,@Param("clientId") int clientId);

}