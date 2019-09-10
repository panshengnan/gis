package com.cgwx.controller;

import com.cgwx.service.IPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Blaze on 2018/5/11.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/POI")


public class POIController {

    @Autowired
    IPOIService poiService;

    @GetMapping("/search")
    @CrossOrigin(methods = RequestMethod.GET)
    public String getPOIList(String name) throws IOException {

        System.out.println("收到请求！");
        String poiDtoList =   poiService.getPOIListByKeyword(name);
        return poiDtoList;

    }

}
