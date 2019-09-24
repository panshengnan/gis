package com.cgwx.dao;

import com.cgwx.data.dto.ClientData;
import com.cgwx.data.dto.ClientFileInfo;
import com.cgwx.data.entity.GisClientFile;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisClientFileMapper {
    int insert(GisClientFile record);

    List<ClientFileInfo> selectAll();

    @Select({"SELECT product_id\n" +
            " FROM gis_Client_file\n" +
            "WHERE client_id = #{clientId} and  logic_id = #{logicId}"
    })
    String getProductIdbylogicid(@Param("clientId") long clientId,@Param("logicId") int logicId);

    @Select({"SELECT product_id,logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url,layer_name\n" +
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
    ), @Result(
            column = "layer_name",
            property = "layerName"
    ), @Result(
            column = "product_id",
            property = "productId"
    )})
    List<ClientFileInfo> getProductListByFolderId(@Param("clientId") long clientId,@Param("folderId") int folderId);

    @Select({"SELECT product_id,logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url,layer_name\n" +
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
    ), @Result(
            column = "layer_name",
            property = "layerName"
    ), @Result(
            column = "product_id",
            property = "productId"
    )})
    List<ClientFileInfo> getProductListByType(@Param("clientId") long clientId,@Param("Filetype") String Filetype);

    @Select({"SELECT product_id,logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url,layer_name\n" +
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
    ), @Result(
            column = "layer_name",
            property = "layerName"
    ), @Result(
            column = "product_id",
            property = "productId"
    )})
    List<ClientFileInfo> getProductListByClass(@Param("clientId") long clientId,@Param("Fileclass") String Fileclass);



    @Update({"UPDATE gis_Client_file \n" +
            " set folder_id = #{newfolderId} \n" +
            "WHERE logic_id = #{productId}   "
    })
    Boolean modifyFolderByProductId(@Param("productId") int productId,@Param("newfolderId") int newfolderId);

    @Update({"UPDATE gis_Client_file \n" +
            " set folder_id = #{newfolderId} \n" +
            "WHERE folder_id = #{productId} and client_id = #{clientId} "
    })
    Boolean modifyFolderByFolderAndClient(@Param("clientId") long clientId,@Param("folderId") int folderId,@Param("newfolderId") int newfolderId);

    @Select({"update gis_Client_file\n" +
            "  set image_geo = st_geomfromgeojson(#{geoJson})\n"
            + " where product_id = #{productId} "
    })
    void updateClientFileImgGeo(@Param("productId") String productId,@Param("geoJson") String geoJson);

    @Select({"SELECT product_id,product_name,thumb_url,layer_name \n" +
            " FROM gis_Client_file as A  \n" +
            "WHERE  client_id = #{clientId} and product_name like CONCAT('%',#{description},'%') and product_class != '标准产品' "
    })
    @Results({@Result(
            column = "product_id",
            property = "productId"
    ), @Result(
            column = "product_name",
            property = "productName"
    ), @Result(
            column = "thumb_url",
            property = "thumbnailUrl"
    ), @Result(
            column = "layer_name",
            property = "layerName"
    )
    })
    List<ClientData> getClientDataList(@Param("clientId") long clientId,@Param("description")String description);

    @Select({"SELECT count(*)\n FROM gis_Client_file\nWHERE product_id = #{productId}and client_id = #{clientId}"})
    int getproductId(@Param("productId") String productId,@Param("clientId") long clientId);

    @Select({"SELECT product_id,logic_id,product_name,st_asgeojson(image_geo) as geo,thumb_url,download_url,layer_name,product_type\n" +
            " FROM gis_Client_file\n" +
            "WHERE client_id = 0 or client_id is null "
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
    ), @Result(
            column = "layer_name",
            property = "layerName"
    ), @Result(
            column = "product_id",
            property = "productId"
    ), @Result(
            column = "product_type",
            property = "productType"
    )})
    List<ClientFileInfo> getExampleStandardProduct();

}