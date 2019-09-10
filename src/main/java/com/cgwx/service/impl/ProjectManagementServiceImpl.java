package com.cgwx.service.impl;

import com.cgwx.dao.*;
import com.cgwx.data.dto.AttributeInfoDto;
import com.cgwx.data.dto.ProjectDataDto;
import com.cgwx.data.dto.ProjectDto;
import com.cgwx.data.entity.GisMyProjectDataInfo;
import com.cgwx.data.entity.GisMyProjectInfo;
import com.cgwx.data.entity.GisProductShpAttributeInfo;
import com.cgwx.service.IProductArchiveService;
import com.cgwx.service.IProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectManagementServiceImpl implements IProjectManagementService{

    @Autowired
    GisMyProjectInfoMapper gisMyProjectInfoMapper;

    @Autowired
    IProductArchiveService iProductArchiveService;

    @Autowired
    GisMyProjectDataInfoMapper gisMyProjectDataInfoMapper;

    @Autowired
    GisProductShpAttributeDetailInfoMapper gisProductShpAttributeDetailInfoMapper;

    @Autowired
    GisProductShpAttributeInfoMapper gisProductShpAttributeInfoMapper;

    @Autowired
    GisProductInfoMapper gisProductInfoMapper;

    @Value("${productStoreLinkHead}")
    private String productStoreLinkHead;

   @Override
   public List<ProjectDto> getProjectList(String projectName){

        List<ProjectDto> projectDtoList = gisMyProjectInfoMapper.getProjectList(projectName);
        for(ProjectDto projectDto:projectDtoList){
            projectDto.setThumbUrl(productStoreLinkHead+projectDto.getThumbUrl());
            projectDto.setCreateTime(projectDto.getCreateTime().substring(0,projectDto.getCreateTime().lastIndexOf(".")));
        }
        return projectDtoList;
    }

    @Override
    public int addProject(String projectName){

       GisMyProjectInfo gisMyProjectInfo = new GisMyProjectInfo();
       gisMyProjectInfo.setJpg("项目默认缩略图.png");
       gisMyProjectInfo.setProjectName(projectName);
       String status = iProductArchiveService.getUUId();
       gisMyProjectInfo.setStatus(status);
       gisMyProjectInfoMapper.insert(gisMyProjectInfo);
       return gisMyProjectInfoMapper.getProjectId(status);
    }

    @Override
    public int deleteProject(int projectId){
       try{
           gisMyProjectInfoMapper.deleteProject(projectId);
       }
       catch (Exception e){return 0;}
       return 1;
    }

    @Override
    public int renameProject(int projectId,String name){
        try{
            gisMyProjectInfoMapper.renameProject(projectId,name);
        }
        catch (Exception e){return 0;}
        return 1;
    }

    @Override
    public List<ProjectDataDto> getProjectDataDtoList(int projectId){

        List<ProjectDataDto> list = gisMyProjectDataInfoMapper.getProjectDataDtoList(projectId);
        for(ProjectDataDto projectDataDto : list){
            if("true".equals(projectDataDto.getIsEdit())) {
                int attributeId = gisProductShpAttributeDetailInfoMapper.getAttributeNameId(projectDataDto.getDataId());
                String attributeName = gisProductShpAttributeInfoMapper.getAttributeName(attributeId);
                projectDataDto.setAttributeName(attributeName);
                List<AttributeInfoDto> attributeInfoDtoList = gisProductShpAttributeDetailInfoMapper.getAttributeInfoDtoList(projectDataDto.getDataId(), attributeId);
                projectDataDto.setAttributeInfoDtoList(attributeInfoDtoList);
            }
        }
        return list;
    }

    @Override
    public int addData(int projectId,String productId,String singleId){
        try{
            GisMyProjectDataInfo gisMyProjectDataInfo = new GisMyProjectDataInfo();
            gisMyProjectDataInfo.setProjectId(projectId);
            gisMyProjectDataInfo.setProductId(productId);
            gisMyProjectDataInfo.setSingleId(singleId);
            int productType = gisProductInfoMapper.getProductType(productId);
            switch (productType) {
                case 1://专题

                    break;
                case 2://正射
                    break;
                case 3://镶嵌
                    break;
                case 4://分幅
                    break;
                case 5://标准
                    gisMyProjectDataInfo.setDataName(productId);
                    break;
                default:
                    break;
            }
            gisMyProjectDataInfo.setStatus("UNSAVED");
            gisMyProjectDataInfoMapper.insert(gisMyProjectDataInfo);

        }
        catch (Exception e){return 0;}
        return 1;
    }
}
