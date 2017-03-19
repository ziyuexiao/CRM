package com.kaishengit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wgs on 2017/3/19.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)

public class NotFoundException extends RuntimeException {
}
