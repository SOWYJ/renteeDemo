package com.example.userservices.controller;


import com.example.moviegateway.token.JwtTokenUtils;
import com.example.userservices.domain.Users;
import com.example.userservices.dto.LoginDto;
import com.example.userservices.http.HttpResult;
import com.example.userservices.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public HttpResult login(@RequestBody LoginDto loginDto){
        /**
         * 拿到用户名和密码后，先去数据库验证是否匹配，如果匹配成功，则颁发token
         */
        if (usersService.getUserName(loginDto.getUsername()) == 0){
            System.out.println("用户名不存在，请注册！");
        }else {
            String password = usersService.getPassword(loginDto.getUsername());
            if (loginDto.getPassword().equals(password) ){
                String token = JwtTokenUtils.generateToken(loginDto.getUsername());
                //返回token给前端
                return HttpResult.ok(token);
            }else {
                System.out.println("密码错误，请重新输入！");
            }
        }
        return null;
    }

    @PostMapping("/enroll")
    public HttpResult saveUser(@RequestBody Users users){
        if (usersService.getUserName(users.getUserName()) !=null ){
            return HttpResult.ok("该用户名已存在");
        }else {
            users.setUserId(UUID.randomUUID().toString().replace("-",""));
            usersService.save(users);
            return HttpResult.ok("注册成功");
        }
    }
}
