package com.cgwx.service;

import com.cgwx.data.dto.ProjectDataDto;
import com.cgwx.data.dto.ProjectDto;
import com.cgwx.data.entity.GisMyProjectDataInfo;
import com.cgwx.data.entity.GisMyProjectInfo;

import java.util.List;

public interface IProjectManagementService {

    List<ProjectDto> getProjectList(String projectName);
    int addProject(String projectName);
    int deleteProject(int projectId);
    int renameProject(int projectId,String name);
    List<ProjectDataDto> getProjectDataDtoList(int projectId);
    int addData(int projectId,String productId,String singleId);
}
