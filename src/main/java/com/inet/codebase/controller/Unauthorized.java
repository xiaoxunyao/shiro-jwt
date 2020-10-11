package com.inet.codebase.controller;

import com.inet.codebase.utlis.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * unauthorized
 *
 * @author HCY
 * @since 2020/10/11
 */
@RestController
public class Unauthorized {
    @RequestMapping(path = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return new Result(message , "错误请求",104);
    }

}
