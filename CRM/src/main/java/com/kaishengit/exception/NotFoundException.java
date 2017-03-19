package com.kaishengit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wgs on 2017/3/19.
 */
@ResponseStatus(HttpStatus.FOUND)
public class NotFoundException extends RuntimeException {
}
