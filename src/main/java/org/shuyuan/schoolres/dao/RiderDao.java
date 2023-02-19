package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Rider;
import org.springframework.data.repository.CrudRepository;

public interface RiderDao extends CrudRepository<Rider, Integer>
{
    Rider findRiderByOpenId(String appId);

    Rider findRiderByName(String name);
}
