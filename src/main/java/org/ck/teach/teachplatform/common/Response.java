package org.ck.teach.teachplatform.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chen.chao
 */
@Data
public class Response implements Serializable {

    private Long current;
    private Long pages;
    private Long size;

    private Long total;
    private Object body;
    private String code;
    private String msg;

    public Response() {
        this.code = SysCodeEnum.SUCCESS_EXECUTE.getCode();
        this.msg = SysCodeEnum.SUCCESS_EXECUTE.getMessage();
    }

    public static Response success() {
        return new Response();
    }

    public static Response exception(String msg) {
        Response response = new Response();
        response.setCode(SysCodeEnum.EXCEPTION.getCode());
        response.setMsg(msg);
        return response;
    }

    public static Response exception() {
        return exception(SysCodeEnum.FAIL_EXECUTE.getMessage());
    }

    public Response(Object body) {
        this();
        this.body = body;
    }


    public Response(CommonCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    public Response(Object body, Long total) {
        this();
        this.body = body;
        this.total = total;
    }

    public static Response parse(IPage page) {
        Response response = new Response();
        response.setTotal(page.getTotal());
        response.setBody(page.getRecords());
        response.setPages(page.getPages());
        response.setCurrent(page.getCurrent());
        response.setSize(page.getSize());
        return response;
    }

    public static Response of(CommonCode commonCode) {
        return new Response(commonCode);
    }

    public static <T> Response success(T loginSessionUser) {
        return new Response(loginSessionUser);
    }

    public String status() {
        return String.format("{code:%s,msg:%s}}", this.code, this.msg);
    }
}