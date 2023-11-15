package org.jeecg.modules.demo.login.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="登录", description="登录")
public class loginCEModel {
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
//    @ApiModelProperty(value = "验证码")
//    private String captcha;
//    @ApiModelProperty(value = "验证码key")
//    private String checkKey;

}
