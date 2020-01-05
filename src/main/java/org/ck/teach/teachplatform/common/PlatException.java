package org.ck.teach.teachplatform.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/24 9:17
 * @description
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PlatException extends RuntimeException{

    public PlatException() {
    }

    public PlatException(String message) {
        super(message);
    }

    public PlatException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatException(Throwable cause) {
        super(cause);
    }

    public PlatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
