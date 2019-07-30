/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.facades;

import biz.galaxy.commons.utilities.ErrorGeneralException;
import com.driver.config.StatusConfig;
import com.driver.entities.Driver;
import com.driver.entities.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.driver.utilities.QueryWrapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class CommonQueries{
    @Autowired
            GenericDao genericDao;
    public boolean isDriverCreated(String driverName)throws ErrorGeneralException, Exception{
        QueryWrapper queryWrapper = new QueryWrapper("select B from Driver B where B.driverNames = :name Order by B.creationTime DESC");
        queryWrapper.setParameter("name", driverName);
        queryWrapper.setPageSize(1);
        List<Driver> mDrvs = genericDao.findWithNamedQuery(queryWrapper);
        return !(mDrvs == null || mDrvs.isEmpty());
    }
    
    public List<Driver> availableDrivers()throws ErrorGeneralException, Exception{
        QueryWrapper queryWrapper = new QueryWrapper("select B from Driver B where B.status = :status Order by B.creationTime DESC");
        queryWrapper.setParameter("status", StatusConfig.ACTIVE);
        return genericDao.findWithNamedQuery(queryWrapper);
    }
    
    public Location driverLocation(String driverId)throws ErrorGeneralException, Exception{
        QueryWrapper queryWrapper = new QueryWrapper("select B from Location B where B.driverId = :driverId Order by B.creationTime DESC");
        queryWrapper.setParameter("driverId", driverId);
        queryWrapper.setPageSize(1);
        List<Location> lcs = genericDao.findWithNamedQuery(queryWrapper);
        return lcs != null && !lcs.isEmpty() ? lcs.get(0) : null;
    }
    public <T> List<T> filter(QueryWrapper queryWrapper)throws ErrorGeneralException, Exception{
        return genericDao.findWithNamedQuery(queryWrapper);
    }
}
