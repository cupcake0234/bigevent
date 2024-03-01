package com.sptj.Interceptors;

import com.sptj.pojo.Result;
import com.sptj.pojo.User;
import com.sptj.utils.JwtUtil;
import com.sptj.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //
//        System.out.println("拦截器里1");
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claims = JwtUtil.parseToken(token);
            //
//            System.out.println("拦截器里2");
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);

            if(redisToken==null){
                throw new RuntimeException();
            }
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        }catch (Exception e){
            //
            response.setStatus(401);
            //不,中断
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//      请求完成后清除，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
