package cn.edu.guet.rentalservices.service.impl;

import cn.edu.guet.rentalservices.domain.Lease;
import cn.edu.guet.rentalservices.service.LeaseService;
import cn.edu.guet.rentalservices.mapper.LeaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【lease】的数据库操作Service实现
* @createDate 2024-08-26 16:22:26
*/
@Component
public class LeaseServiceImpl  implements LeaseService{
    @Autowired
    private LeaseMapper leaseMapper;

    @Override
    public int pay(String licenseId) {

        int lease = leaseMapper.pay(licenseId);
        System.out.println(lease);
        if(lease<=60){
            System.out.println("收费");
        }else{
            System.out.println("shoufe");
        }
        return lease;
    }
}




