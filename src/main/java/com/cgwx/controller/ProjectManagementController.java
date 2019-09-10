package com.cgwx.controller;


import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import com.cgwx.service.IProjectManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
public class ProjectManagementController {

    @Autowired
    IProjectManagementService iProjectManagementService;

    @RequestMapping(value = "/getProjectList")
    @CrossOrigin()
    @ResponseBody
    public Result getProjectList(String projectName) {

        //用户暂时未加
        return ResultUtil.success(iProjectManagementService.getProjectList(projectName));
    }

    @RequestMapping(value = "/addProject")
    @CrossOrigin()
    @ResponseBody
    public Result addProject(String projectName) {

        return ResultUtil.success(iProjectManagementService.addProject(projectName));
    }

    @RequestMapping(value = "/deleteProject")
    @CrossOrigin()
    @ResponseBody
    public Result deleteProject(int projectId) {

        return ResultUtil.success(iProjectManagementService.deleteProject(projectId));
    }

    @RequestMapping(value = "/renameProject")
    @CrossOrigin()
    @ResponseBody
    public Result renameProject(int projectId,String name) {

        return ResultUtil.success(iProjectManagementService.renameProject(projectId,name));
    }

    @RequestMapping(value = "/addData")//待完成
    @CrossOrigin()
    @ResponseBody
    public Result addData(int projectId,String productId,String singleId) {

        return ResultUtil.success();
    }

    @RequestMapping(value = "/getDataList")
    @CrossOrigin()
    @ResponseBody
    public Result getDataList(int projectId) {

        return ResultUtil.success(iProjectManagementService.getProjectDataDtoList(projectId));
    }

}
