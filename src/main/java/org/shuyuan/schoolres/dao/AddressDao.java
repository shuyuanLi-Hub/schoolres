package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressDao extends CrudRepository<Address, Integer>
{
    @Query(value = "select address from Address address where address.detail = ?1 and address.user.id = ?2")
    Address findAddressByDetailAndUserId(String detail, Integer id);
}
