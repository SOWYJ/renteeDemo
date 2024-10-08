package com.example.userservices.controller;


import com.example.moviegateway.token.JwtTokenUtils;

import com.example.userservices.domain.Cars;
import com.example.userservices.domain.Users;
import com.example.userservices.dto.LoginDto;

import com.example.userservices.http.HttpResult;

import com.example.userservices.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
            return HttpResult.error(401, "用户名不存在，请注册！");
        }else {
            String password = usersService.getPassword(loginDto.getUsername());
            if (loginDto.getPassword().equals(password) ){
                String token = JwtTokenUtils.generateToken(loginDto.getUsername());
                //返回token给前端
                return HttpResult.ok(200,token);
            }else {
                return HttpResult.error(401, "密码错误，请重新输入！");
            }
        }
    }

    @PostMapping("/enroll")
    public HttpResult saveUser(@RequestBody Users users){
        System.out.println("ddddddddddddddd"+users);
        if (usersService.getUserName(users.getUserName()) !=0 ){
            return HttpResult.ok(500,"该用户名已存在");
        }else {
            users.setUserId(UUID.randomUUID().toString().replace("-",""));
            System.out.println(users);
            usersService.save(users);
            return HttpResult.ok("注册成功");
        }
    }

    @PostMapping("/verify")
    public HttpResult reviseUser(@RequestBody Users users) {
        System.out.println("RRRRRRRRRRRRRR" + users);
        String password = usersService.getPassword(users.getUserName());
        if (usersService.getUserName(users.getUserName()) == 0 || !users.getPassword().equals(password) ) {
            return HttpResult.error(500, "用户名或密码错误");
        }else {
            usersService.updateUser(users.getUserId(), users.getUserName());
            System.out.println("yyyyyyyyyyyy");
            return HttpResult.ok(200,"修改成功");
        }
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping ("/getAllCarts")
    public List<Cars> getAllCars() {

        ServiceInstance serviceInstance = loadBalancerClient.choose("vehicle");
        String url = String.format("http://%s:%s/vehicle/getAllCarts", serviceInstance.getHost(), serviceInstance.getPort());

        System.out.println("Request URL: " + url);

        // 使用 RestTemplate 发送 GET 请求
        List<Cars> carsList = restTemplate.getForObject(url, List.class);

        return carsList;
    }

    @RequestMapping("/test")
    public String test() {
        return "1111";
    }

}
