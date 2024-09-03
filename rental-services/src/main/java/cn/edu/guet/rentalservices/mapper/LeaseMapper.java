package cn.edu.guet.rentalservices.mapper;

import cn.edu.guet.rentalservices.domain.Lease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author HP
* @description 针对表【lease】的数据库操作Mapper
* @createDate 2024-08-26 16:22:26
* @Entity cn.edu.guet.rentalservices.domain.Lease
*/
@Mapper
public interface LeaseMapper extends BaseMapper<Lease> {
    int pay(String licenseId);
    List<Lease> getAlllease();
    void saveLease(Lease lease);
    void deleteLease(String id);
    void updateLease(Lease lease);
    void state(Lease lease);
}




