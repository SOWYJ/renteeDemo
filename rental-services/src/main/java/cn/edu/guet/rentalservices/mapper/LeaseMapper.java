package cn.edu.guet.rentalservices.mapper;

import cn.edu.guet.rentalservices.domain.Lease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author HP
* @description 针对表【lease】的数据库操作Mapper
* @createDate 2024-08-23 17:08:23
* @Entity cn.edu.guet.rentalservices.domain.Lease
*/
@Mapper
public interface LeaseMapper extends BaseMapper<Lease> {

}




