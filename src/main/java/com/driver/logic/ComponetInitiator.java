/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.logic;

import com.driver.config.SystemConfig;
import com.driver.entities.Driver;
import com.driver.entities.Location;
import com.driver.facades.CommonQueries;
import com.driver.facades.GenericDao;
import com.driver.models.Coordinates;
import com.driver.utilities.CoordinatesCalculations;
import com.driver.utilities.Log;
import com.driver.utilities.UtilInitiator;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class ComponetInitiator {
    private final double latitude = -1.920686111;
    private final double longitude = 30.064422777;
    
    @Autowired
            GenericDao dao;
    @Autowired
            CommonQueries cQuery;
    @PostConstruct
    public void init() {
        //Initiate component
        try {
            Thread t = new Thread(new UtilInitiator());
            t.start();
        } catch (Exception e) {
            Log.e(getClass(), SystemConfig.SYSTEM_ID[1]+"_"+e.getMessage());
        }
        //create default values
        for(int i = 1; i <= 12; i++){
            try {
                if(cQuery.isDriverCreated("dr"+i))
                    continue;
                Driver drv = new Driver();
                drv.setDriverNames("dr"+i);
                drv.setId("12"+i);
                drv.prepare();
                dao.save(drv);
                
                Coordinates coord = CoordinatesCalculations.getRandomLocation(latitude, longitude, (i*200));
                Location loc = new Location();
                loc.setLatitude(coord.getLatitude());
                loc.setLongitude(coord.getLongitude());
                loc.setDriverId(drv.getId());
                loc.prepare();
                dao.save(loc);
            } catch (Exception e) {
                Log.e(getClass(), SystemConfig.SYSTEM_ID[1]+"_"+e.getMessage());
            }
        }
        
    }
}