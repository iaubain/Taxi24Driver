/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.entities;

import biz.galaxy.commons.config.CommonErrorCodeConfig;
import biz.galaxy.commons.models.ErrorModel;
import biz.galaxy.commons.models.ErrorsListModel;
import biz.galaxy.commons.utilities.ErrorGeneralException;
import biz.galaxy.commons.utilities.IdGen;
import biz.galaxy.commons.utilities.UtilModel;
import com.driver.config.StatusConfig;
import com.driver.config.SystemConfig;
import com.driver.facades.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aubain
 */
@Entity
@Table(name = "Driver",
        indexes = {@Index(name = "idx_1", columnList = "status"),
            @Index(name = "idx_2", columnList = "creationTime"),
            @Index(name = "idx_3", columnList = "driverNames")})
public class Driver extends BaseEntity implements Serializable, UtilModel, Comparable<Driver> {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "driverNames", length = 120)
    private String driverNames;
    @Transient
    private Location location;
    @Transient
    @JsonIgnore
    private Double distance;
    
    public Driver() {
        super();
    }
    
    @Override
    public void validateOb() throws ErrorGeneralException {
        ErrorsListModel errors = new ErrorsListModel();
        if(this == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Null object"));
        }
        
        if(driverNames == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Field: driverNames shouldn't be null"));
        }
        
        if(!errors.getErrors().isEmpty()){
            throw new ErrorGeneralException(errors);
        }
    }
    
    @Override
    public void prepare() throws Exception {
        if(super.getId() == null)
            super.setId(IdGen.SIMPLE());
        super.setCreationTime(new Date());
        super.setStatus(StatusConfig.ACTIVE);
    }
    @Override
    public int compareTo(Driver o) {
        if(getDistance() == null || o.getDistance() == null)
            return 0;
        return getDistance().compareTo(o.getDistance());
    }
    public String getDriverNames() {
        return driverNames;
    }
    
    public void setDriverNames(String driverNames) {
        this.driverNames = driverNames;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Double getDistance() {
        return distance;
    }
    
    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
