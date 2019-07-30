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
import com.driver.facades.CommonQueries;
import com.driver.facades.GenericDao;
import com.driver.utilities.Log;
import com.driver.utilities.QueryWrapper;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aubain
 */
@Component
public class FilterProcessor {
    @Autowired
            GenericDao dao;
    @Autowired
            CommonQueries cQuery;
    public List<Driver> filterDriver(String body)throws ErrorGeneralException, Exception{
        Driver filter;
        //Serialize token and the body
        try {
            filter = new UtilSerializer(Driver.class).serialize(body);
        } catch (ErrorGeneralException e) {
            throw e;
        }catch (Exception e) {
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
        try {
            if(filter == null)
                return dao.findAll(Driver.class);
            else{
                QueryWrapper queryWrapper = new QueryWrapper();
                StringBuilder query = new StringBuilder("select A from Driver A where A.id is not null ");
                if(filter.getId()!= null){
                    query.append("AND A.id = :id ");
                    queryWrapper.setParameter("id", filter.getId());
                }
                if(filter.getCreationTimeRange() != null && (filter.getCreationTimeRange().getStartDate() != null && filter.getCreationTimeRange().getEndDate() != null)){
                    query.append(" AND (A.creationTime >= :startDate AND A.creationTime <= :endDate) ");
                    queryWrapper.setParameter("startDate", filter.getCreationTimeRange().getStartDate()).setParameter("endDate", filter.getCreationTimeRange().getEndDate());
                }else if(filter.getCreationTime() != null){
                    query.append(" AND A.creationTime = :creationTime ");
                    queryWrapper.setParameter("creationTime", filter.getCreationTime());
                }
                if(filter.getStatus()!= null){
                    query.append("AND A.status = :status ");
                    queryWrapper.setParameter("status", filter.getStatus());
                }
                if(filter.getDriverNames()!= null){
                    query.append("AND A.driverNames = :driverNames ");
                    queryWrapper.setParameter("driverNames", filter.getDriverNames());
                }
                if(filter.getIsDesc() != null && filter.getIsDesc()){
                    query.append("Order by A.creationTime DESC ");
                }
                if(filter.getPageNumber() != null){
                    queryWrapper.setPageNumber(filter.getPageNumber());
                }
                if(filter.getPageSize()!= null){
                    queryWrapper.setPageNumber(filter.getPageSize());
                }
                queryWrapper.setQuery(query.toString());
                return cQuery.filter(queryWrapper);
            }
        } catch(ErrorGeneralException e){
            throw e;
        }catch (Exception e) {
            e.printStackTrace(out);
            Log.d(getClass(), e.getMessage());
            throw new ErrorGeneralException(new ErrorsListModel(Arrays.asList(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[0], CommonErrorCodeConfig.GENERAL_PROCESSING_ERROR[1], "There was an internal issue while proccessing your request."))));
        }
    }
}
