package com.cgwx.dao;

import com.cgwx.data.entity.GisUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisUserInfoMapper {
    int insert(GisUserInfo record);

    List<GisUserInfo> selectAll();
    @Select("SELECT password\n" +
            "FROM gis_user_info\n" +
            "WHERE upper(user_name) = upper(#{account})"
    )
    String selectPasswdByAccount(@Param("account") String account);



    @Select("SELECT user_id\n" +
            "FROM gis_user_info\n" +
            "WHERE upper(user_name) = upper(#{account})"
    )
    String selectIdByAccount(@Param("account") String account);




    @Select("SELECT count(user_id)\n" +
            "FROM gis_user_info\n" +
            "WHERE upper(user_name) = upper(#{account})"
    )
    int selectCountIdByAccount(@Param("account") String account);

    @Select("SELECT count(user_id)\n" +
            "FROM gis_user_info\n" +
            "WHERE upper(user_id) = upper(#{userId})"
    )
    int selectCountIdByUserId(@Param("userId") String userId);

    @Select("update gis_user_info set password = #{passwd}\n" +
            "WHERE upper(user_name) = upper(#{account})"
    )
    void insertPasswd(@Param("account") String account,@Param("passwd") String passwd);

    @Select("SELECT user_name\n" +
            "FROM gis_user_info\n" +
            "WHERE user_id = #{userId}"
    )
    String selectUserNameByUserId(@Param("userId") String userId);

    @Select("SELECT user_name\n" +
            "FROM gis_user_info\n" +
            "WHERE 1=1 order by user_name collate \"C\" "
    )
    List<String> selectAllUsers();
}