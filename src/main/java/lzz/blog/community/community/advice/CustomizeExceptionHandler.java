package lzz.blog.community.community.advice;

import com.alibaba.fastjson.JSON;
import lzz.blog.community.community.dto.ResultDTO;
import lzz.blog.community.community.exception.CustomizeErrorCode;
import lzz.blog.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handler(HttpServletRequest request, Throwable ex,
                   HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            //返回json
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = (ResultDTO) ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return null;
        } else {
            //错误页面
            ModelAndView modelAndView = new ModelAndView();
            if (ex instanceof CustomizeException) {
                modelAndView.addObject("errMsg", ex.getMessage());
            } else {
                modelAndView.addObject("errMsg", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
