package cn.edu.guet.rentalservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.guet.rentalservices.domain.Lease;
import cn.edu.guet.rentalservices.service.LeaseService;
import cn.edu.guet.rentalservices.mapper.LeaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【lease】的数据库操作Service实现
* @createDate 2024-08-26 16:22:26
*/
@Service
public class LeaseServiceImpl  implements LeaseService{
    @Autowired
    private LeaseMapper leaseMapper;


    @Override
    public int pay(String licenseId) {
       return leaseMapper.pay(licenseId);
    }
}




