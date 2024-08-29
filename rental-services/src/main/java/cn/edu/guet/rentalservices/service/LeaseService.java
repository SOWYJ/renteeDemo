package cn.edu.guet.rentalservices.service;



import cn.edu.guet.rentalservices.domain.Lease;

import java.util.List;

/**
* @author HP
* @description 针对表【lease】的数据库操作Service
* @createDate 2024-08-26 16:22:26
*/
public interface LeaseService  {
  int pay(String licenseId);

  List<Lease> getAlllease();
void saveLease(Lease lease);
 void deleteLease(String licenseId);
}
