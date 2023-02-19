package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Host;
import org.springframework.data.repository.CrudRepository;

public interface HostDao extends CrudRepository<Host, Integer>
{
    Host findHostByName(String name);
}
