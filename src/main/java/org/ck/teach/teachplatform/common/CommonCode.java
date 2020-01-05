package org.ck.teach.teachplatform.common;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/9/27 9:52
 * @description
 *
 * 0000 成功
 * 9xxx
 * 1xxx 用户模块异常
 *
 *
 */
public interface CommonCode {

    /***
     *状态码
     * @return 状态码
     */
   String getCode();

    /***
     *信息
     * @return 信息
     */
   String getMessage();

}
