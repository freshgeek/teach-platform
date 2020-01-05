package org.ck.teach.teachplatform.entity.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/23 15:51
 * @description
 */
@Data
public class UserLoginForm {

    @NotNull(message = "手机号码不能为空")
    private String phone;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String code;

}
