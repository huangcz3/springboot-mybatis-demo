package com.neo.exception;



import com.neo.util.response.Result;
import com.neo.util.response.ResultEnum;
import com.neo.util.response.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 *
 * @author Huangcz
 * @date 2017/6/15
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e) {

        logger.error("【系统异常】{}", e);

        if (e instanceof NoHandlerFoundException) {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR);
        } else if (e instanceof BusinessException) {
            return ResultUtil.error((BusinessException) e);
        } else {
            return ResultUtil.error(ResultEnum.NO_VALID_DATA);
        }
    }
}
