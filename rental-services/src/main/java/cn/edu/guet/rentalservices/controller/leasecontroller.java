package cn.edu.guet.rentalservices.controller;

import cn.edu.guet.rentalservices.domain.Lease;
import cn.edu.guet.rentalservices.http.HttpResult;
import cn.edu.guet.rentalservices.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class leasecontroller {
    @Autowired
    private LeaseService leaseService;

    @RequestMapping("/pay")
    @ResponseBody
    public HttpResult pay(String licenseId) {

        int lease = leaseService.pay(licenseId);
        return HttpResult.ok(lease);
    }

    @RequestMapping("/getAlllease")
    @ResponseBody
    public List<Lease> getAllLease() {
        return leaseService.getAlllease();
    }
    @PostMapping("/saveLease")
    public HttpResult saveLease(@RequestBody Lease lease) {
        System.out.println(lease);
        leaseService.saveLease(lease);
       return HttpResult.ok("返回成功");
    }
    @RequestMapping("/deleteLease")
    public void deleteLease( String id) {
        leaseService.deleteLease(id);
    }
    @RequestMapping("/updateLease")
    public HttpResult updateLease(@RequestBody Lease lease) {
        leaseService.updateLease(lease);
        return HttpResult.ok();
    }
     @RequestMapping("/state")
    public HttpResult state(@RequestBody Lease lease) {
        leaseService.state(lease);
        return HttpResult.ok();
    }
}