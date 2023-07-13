package com.talent.wxChat.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public void errorThrowable(Exception ex, HttpServletResponse httpServletResponse) throws IOException {
        log.error("发生未知异常！！！"+ex.getMessage(),ex);
        httpServletResponse.getWriter().println( "errorThrowable");
    }

    /**
     * 参数绑定异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageConversionException.class)
    public void httpMessageConversionExceptionHandler(HttpMessageConversionException ex,
                                                        HttpServletResponse httpServletResponse) throws IOException {
        log.error("参数绑定异常！！！"+ex.getMessage(),ex);
        httpServletResponse.getWriter().println( "httpMessageConversionExceptionHandler");
    }

    /**
     * 访问方式异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public void httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex,
                                                                HttpServletResponse httpServletResponse) throws IOException {
        log.error("访问方式异常！！！"+ex.getMessage(),ex);
        httpServletResponse.getWriter().println( "httpRequestMethodNotSupportedException");
    }




}
