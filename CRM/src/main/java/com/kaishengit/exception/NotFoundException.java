package com.kaishengit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
<<<<<<< HEAD
 * Created by wgs on 2017/3/19.
 */
@ResponseStatus(HttpStatus.FOUND)
=======
 * 404异常
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
>>>>>>> 3058d6c90c979a537b4b2908e3893de1c2ba6059
public class NotFoundException extends RuntimeException {
}
