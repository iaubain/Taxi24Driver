/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.logic;

import biz.galaxy.commons.config.CommonErrorCodeConfig;
import biz.galaxy.commons.models.ErrorModel;
import biz.galaxy.commons.models.ErrorsListModel;
import biz.galaxy.commons.utilities.ErrorGeneralException;
import biz.galaxy.commons.utilities.serializer.UtilSerializer;
import com.driver.config.SystemConfig;
import com.driver.entities.Driver;
import com.driver.entities.Location;
import com.driver.facades.CommonQueries;
import com.driver.facades.GenericDao;
import com.driver.models.Coordinates;
import com.driver.models.GenCoordinateModel;
import com.driver.utilities.CoordinatesCalculations;
import com.driver.utilities.Log;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class DriverProcessor {
    @Autowired
            GenericDao dao;
    @Autowired
            CommonQueries cQuery;
    
    public List<Driver> createDrivers(String body)throws ErrorGeneralException, Exception{
        List<Driver> drivers;
        List<Driver> myDrivers = new ArrayList<>();
        //Serialize the body
        try {
            drivers = new UtilSerializer(Driver.class).serializeList(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(drivers == null || drivers.isEmpty()){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            for(Driver drv : drivers){
                if(cQuery.isDriverCreated(drv.getDriverNames()))
                    throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Driver already exist. "+drv.toString()))));
                drv.prepare();
                drv.validateOb();
                myDrivers.add(drv);
            }
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            return dao.save(myDrivers);
        }  catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public List<Driver> editDrivers(String body)throws ErrorGeneralException, Exception{
        List<Driver> drivers;
        List<Driver> myDrivers = new ArrayList<>();
        //Serialize the body
        try {
            drivers = new UtilSerializer(Driver.class).serializeList(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(drivers == null || drivers.isEmpty()){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            for(Driver drv : drivers){
                drv.validateOb();
                Driver checkDriver = dao.find(Driver.class, drv.getId());
                if(checkDriver == null)
                    throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Driver not found. "+drv.toString()))));
                myDrivers.add(drv);
            }
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            return dao.update(myDrivers);
        }  catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public List<Location> createDriversCoordinates(String body)throws ErrorGeneralException, Exception{
        List<Location> locations;
        List<Location> myLocations = new ArrayList<>();
        //Serialize the body
        try {
            locations = new UtilSerializer(Location.class).serializeList(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(locations == null || locations.isEmpty()){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            for(Location loc : locations){
                loc.prepare();
                loc.validateOb();
                Driver checkDriver = dao.findById(loc.getDriverId(), Driver.class);
                if(checkDriver == null)
                    throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Driver not found. "+loc.toString()))));
                myLocations.add(loc);
            }
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            return dao.save(myLocations);
        }  catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public Coordinates genCoordinate(String body)throws ErrorGeneralException, Exception{
        GenCoordinateModel genCo;
        //Serialize the body
        try {
            genCo = new UtilSerializer(GenCoordinateModel.class).serialize(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        if(genCo == null){
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
        }
        try {
            return CoordinatesCalculations.getRandomLocation(genCo.getCenterCoordinate().getLatitude(), genCo.getCenterCoordinate().getLongitude(), genCo.getRadius());
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public List<Driver> getDriverInRadius(String body)throws ErrorGeneralException, Exception{
        GenCoordinateModel mlocationPoint;
        //Serialize the body
        try {
            mlocationPoint = new UtilSerializer(GenCoordinateModel.class).serialize(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(mlocationPoint == null){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            List<Driver> driverInRange = new ArrayList<>();
            List<Driver> availableDrivers = cQuery.availableDrivers();
            for(Driver drv : availableDrivers){
                Location loc = cQuery.driverLocation(drv.getId());
                if(loc == null)
                    continue;
                if(CoordinatesCalculations.isInCircleArea(new Coordinates(loc.getLatitude(), loc.getLongitude()), mlocationPoint.getCenterCoordinate(), mlocationPoint.getRadius())){
                    drv.setLocation(loc);
                    driverInRange.add(drv);
                }
            }
            return driverInRange;
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
    
    public List<Driver> getCloserDriver(String body)throws ErrorGeneralException, Exception{
        Driver mDriver;
        //Serialize the body
        try {
            mDriver = new UtilSerializer(Driver.class).serialize(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        //prepare the body payload
        try {
            if(mDriver == null){
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Serialization produced null results."))));
            }
            Driver checkDriver = dao.find(Driver.class, mDriver.getId());
            if(checkDriver == null)
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Driver not found. "+mDriver.toString()))));
            Location mDriverLocation = cQuery.driverLocation(mDriver.getId());
            if(mDriverLocation == null)
                throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "Driver location not found. "+mDriver.toString()))));
            Coordinates dCoordinates = new Coordinates(mDriverLocation.getLatitude(), mDriverLocation.getLongitude());
            
            List<Driver> distancedDrivers = new ArrayList<>();
            List<Driver> allDrivers = dao.findAll(Driver.class);
            for(Driver drv : allDrivers){
                if(drv.getId().equals(mDriver.getId()))
                    continue;
                
                Location loc = cQuery.driverLocation(drv.getId());
                if(loc == null)
                    continue;
                
                drv.setDistance(CoordinatesCalculations.getDistanceBetweenTwoPoints(dCoordinates, new Coordinates(loc.getLatitude(), loc.getLongitude())));
                distancedDrivers.add(drv);
            }
            List<Driver> closerDrivers = new ArrayList<>();
            if(!distancedDrivers.isEmpty())
                Collections.sort(distancedDrivers);
            else
                return closerDrivers;
            
            if(distancedDrivers.size() <= 1)
                closerDrivers.add(distancedDrivers.get(0));
            else{
                for(int i = 0; i < distancedDrivers.size() - 1; i++){
                    closerDrivers.add(distancedDrivers.get(i));
                    if(i == 2)
                        break;
                }
            }
            return closerDrivers;
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
}
