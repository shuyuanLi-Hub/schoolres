package org.shuyuan.schoolres.service.rider.impl;

import org.shuyuan.schoolres.dao.RiderDao;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.service.rider.RiderService;
import org.springframework.stereotype.Service;

@Service
public class RiderServiceImpl implements RiderService
{
    private RiderDao dao;

    public RiderServiceImpl(RiderDao dao)
    {
        this.dao = dao;
    }

    @Override
    public Rider findRiderByOpenId(String openId)
    {
        return dao.findRiderByOpenId(openId);
    }

    @Override
    public void save(Rider rider)
    {
        dao.save(rider);
    }

//    @Override
//    public boolean login(Rider rider)
//    {
//        if (dao.findRiderByOpenId(rider.getOpenId()) != null)
//        {
//            return true;
//        }
//
//        dao.save(rider);
//        return false;
//    }
//
//    @Override
//    public void save(Rider rider)
//    {
//        dao.save(rider);
//    }
}
