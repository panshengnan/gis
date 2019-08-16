package com.cgwx.dao;

import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.entity.GisClientFile;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisClientFileMapper {
    int insert(GisClientFile record);

    List<ClientFileInfo> selectAll();

    @Select({"SELECT logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url\n" +
            " FROM gis_Client_file\n" +
            "WHERE client_id = #{clientId} and  folder_id = #{folderId}"
    })
    @Results({@Result(
            column = "logic_id",
            property = "logicId"
    ), @Result(
            column = "product_name",
            property = "productName"
    ), @Result(
            column = "geo",
            property = "geoJson"
    ), @Result(
            column = "thumb_url",
            property = "thumbUrl"
    ),@Result(
            column = "download_url",
            property = "downloadUrl"
    )})
    List<ClientFileInfo> getProductListByFolderId(@Param("clientId") int clientId,@Param("folderId") int folderId);

    @Select({"SELECT logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url\n" +
            " FROM gis_Client_file\n" +
            "WHERE client_id = #{clientId} and  product_type = #{Filetype}"
    })
    @Results({@Result(
            column = "logic_id",
            property = "logicId"
    ), @Result(
            column = "product_name",
            property = "productName"
    ), @Result(
            column = "geo",
            property = "geoJson"
    ), @Result(
            column = "thumb_url",
            property = "thumbUrl"
    ),@Result(
            column = "download_url",
            property = "downloadUrl"
    )})
    List<ClientFileInfo> getProductListByTppe(@Param("clientId") int clientId,@Param("Filetype") String Filetype);

    @Select({"SELECT logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url\n" +
            " FROM gis_Client_file\n" +
            "WHERE client_id = #{clientId} and  product_class = #{Fileclass}"
    })
    @Results({@Result(
            column = "logic_id",
            property = "logicId"
    ), @Result(
            column = "product_name",
            property = "productName"
    ), @Result(
            column = "geo",
            property = "geoJson"
    ), @Result(
            column = "thumb_url",
            property = "thumbUrl"
    ),@Result(
            column = "download_url",
            property = "downloadUrl"
    )})
    List<ClientFileInfo> getProductListByClass(@Param("clientId") int clientId,@Param("Fileclass") String Fileclass);



    @Update({"UPDATE gis_Client_file \n" +
            " set folder_id = #{newfolderId} \n" +
            "WHERE logic_id = #{productId}   "
    })
    Boolean modifyFolderByProductId(@Param("productId") int productId,@Param("newfolderId") int newfolderId);

    @Update({"UPDATE gis_Client_file \n" +
            " set folder_id = #{newfolderId} \n" +
            "WHERE folder_id = #{productId} and client_id = #{clientId} "
    })
    Boolean modifyFolderByFolderAndClient(@Param("clientId") int clientId,@Param("folderId") int folderId,@Param("newfolderId") int newfolderId);

}