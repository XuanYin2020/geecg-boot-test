package org.jeecg.modules.demo.login.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.login.model.loginCEModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/CE")
@Api(tags="CE用户登录")
@Slf4j
public class LoginCEController {

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody loginCEModel sysLoginModel){
        Result<JSONObject> result = new Result<JSONObject>();
        log.info("sysLoginModel:",sysLoginModel.toString());
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        log.info("username:",username);
        log.info("password:",password);
        //TODO:根据username查询password
        log.info("password In database:",username);


        return result;
    }
}