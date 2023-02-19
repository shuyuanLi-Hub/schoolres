package org.shuyuan.schoolres.service.rider;

import org.shuyuan.schoolres.domain.Rider;

public interface RiderService
{
    Rider findRiderByOpenId(String openId);

//    boolean login(Rider rider);

    void save(Rider rider);
}
