package com.sptj.controller;

import com.sptj.pojo.Result;
import com.sptj.pojo.User;
import com.sptj.service.UserService;
import com.sptj.utils.JwtUtil;
import com.sptj.utils.Md5Util;
import com.sptj.utils.ThreadLocalUtil;
import com.sun.source.tree.BreakTree;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        User u = userService.findByUserName(username);
            //
        if(u==null){
                //
                //
            userService.register(username,password);
            return Result.success();
        }else{
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> Login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //
        User loginUser = userService.findByUserName(username);
        //
        if(loginUser  == null){
            return Result.error("用户名错误");
        }
        //
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());


            String token = JwtUtil.genToken(claims);


            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,15, TimeUnit.MINUTES);


            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
        //
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
//
//        User user = userService.findByUserName(username);
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader(name = "Authorization") String token){
        //
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }
        //
        //
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if(!loginUser.getPassword().equals(oldPwd)){
            return Result.error("原密码错误");
        }

        if(!rePwd.equals(newPwd)){
            return Result.error("两次密码不同");
        }
        //
        userService.updatePwd(newPwd);

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
}
