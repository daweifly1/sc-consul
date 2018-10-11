package com.xiaojun.core.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.xiaojun.core.beans.ResultMessage;
import com.xiaojun.core.util.BeanValidatorUtils;
import feign.Response;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Controller 内异常 : {}", request.toString(), ex);
        String message = "请求异常";
        List result = new ArrayList();
        if (ex instanceof BindException){
            List<ObjectError> errors = ((BindException) ex).getBindingResult().getAllErrors();
            errors.forEach(x -> result.add(x.getDefaultMessage()));
        }else if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> errors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            errors.forEach(x -> result.add(x.getDefaultMessage()));
        }else if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException m = (MissingServletRequestParameterException) ex;
            result.add("缺少参数:"+ m.getParameterName());
        }else if (ex instanceof HttpRequestMethodNotSupportedException) {
            message = ex.getMessage();
        }else if (ex instanceof HttpMessageNotReadableException) {
            message = ex.getMessage();
        } else {
            result.add(ex.toString());
        }
        return new ResponseEntity<>(ResultMessage.error(status.value(), message,result), status);
    }

    /**
     * 参数非法异常.
     *
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultMessage illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常={}", e.getMessage(), e);
        return ResultMessage.error(e.getMessage());
    }

    /**
     * 参数验证异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultMessage handleServiceException(ConstraintViolationException e) {
        logger.error("参数验证失败", e);
        List errorMessages = BeanValidatorUtils.extractMessage(e);
        return ResultMessage.error(ResultMessage.FAILL,"参数验证失败",errorMessages);
    }

    /**
     * 上传失败
     * @param t
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public ResultMessage handleMultipartException(Throwable t){
        logger.error("上传失败", t);
        return ResultMessage.error(1,"上传失败",t);
    }

    /**
     * 数据库校验异常
     * @param t
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultMessage handleDuplicateKeyException(Throwable t){
        String message = "数据库校验异常";
        String data = t.getMessage();
        Throwable e = t.getCause();
        if (e != null)
            data = e.getMessage();
        logger.error(message, t);
        return ResultMessage.error(1,message,data);
    }


    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultMessage invalidRequestException(InvalidRequestException e) {
        log.error("非正常请求异常={}", e.getMessage(), e);
        return ResultMessage.error(e.getMessage());
    }

    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultMessage retryableException(RetryableException e) {
        log.error("feign重试异常={}", e.getMessage(), e);
        return ResultMessage.error("feign重试超时");
    }


    @ExceptionHandler(HystrixBadRequestException.class)
    public ResponseEntity<Object> hystrixBadRequestException(HystrixBadRequestException e) {
        log.error(" hystrix异常={}", e.getMessage(), e);
        if (e.getCause() instanceof WrapException) {
            WrapException ex = (WrapException)e.getCause();
            Response response = (Response)ex.getReponse();
           return new ResponseEntity<>(ResultMessage.error(response.status(), ex.getMessage()), HttpStatus.valueOf(response.status()));
        }
        return new ResponseEntity<>(ResultMessage.error(e.getMessage()), HttpStatus.OK);
    }

//    /**
//     * gate没有这个异常
//     */
//    @ExceptionHandler(UncategorizedSQLException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResultMessage exception(UncategorizedSQLException e) {
//        log.error("SQL异常信息 ex = {}", e.toString(), e);
//        return ResultMessage.error("SQL 语法错误");
//    }

    /**
     * 全局异常.
     *
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultMessage exception(Exception e) {
        log.error("保存全局异常信息 ex = {}", e.toString(), e);
        return ResultMessage.error("系统发生异常："+e.getClass());
    }
}