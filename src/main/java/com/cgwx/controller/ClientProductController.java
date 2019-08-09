package com.cgwx.controller;

import com.cgwx.aop.result.Result;
import com.cgwx.aop.result.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
public class ClientProductController {

    @RequestMapping(value = "/getClientProductList")  //客户产品列表
    @CrossOrigin(methods = RequestMethod.GET)
    @ResponseBody
    public Result ThemeticProductDetail(@RequestParam(value = "clientId", required = true) int clientId,
                                        @RequestParam(value = "style", required = true) int style,
                                        @RequestParam(value = "json", required = true) String json) {

        return ResultUtil.success("");
    }
}
