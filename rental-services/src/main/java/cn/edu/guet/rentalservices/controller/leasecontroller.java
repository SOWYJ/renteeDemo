package cn.edu.guet.rentalservices.controller;

import cn.edu.guet.rentalservices.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class leasecontroller {
    @Autowired
    private LeaseService leaseService;
    @RequestMapping("/pay")
    public void pay(String licenseId){
         leaseService.pay(licenseId);
    }
}
