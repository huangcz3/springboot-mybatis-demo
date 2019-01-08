package com.neo.util.response;


import com.neo.exception.BusinessException;

/**
 * @author RUOK
 * @date 2017/6/15
 */
public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    public static Result error(BusinessException e) {
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMsg(e.getMessage());
        return result;
    }
}
