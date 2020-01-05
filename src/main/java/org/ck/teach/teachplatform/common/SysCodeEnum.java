package org.ck.teach.teachplatform.common;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/27 10:09
 * @description
 */
public enum SysCodeEnum implements CommonCode {

    SUCCESS_EXECUTE("0000","执行成功"),
    PARAM_EXCEPTION("9998","参数异常"),
    REPEAT_PEOPLE("9996","请勿重复请求,请耐心等待"),
    LOGIN_TIMEOUT("0001","登录超时"),
    EXCEPTION("9997","自定义异常消息提示"),
    FAIL_EXECUTE("9999","服务执行失败");

    private String code;
    private String message;

    private SysCodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
