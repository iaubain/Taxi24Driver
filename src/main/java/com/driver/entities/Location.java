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
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aubain
 */
@Entity
@Table(name = "Location",
        indexes = {@Index(name = "idx_1", columnList = "status"),
            @Index(name = "idx_2", columnList = "creationTime"),
            @Index(name = "idx_3", columnList = "driverId"),
            @Index(name = "idx_4", columnList = "latitude"),
            @Index(name = "idx_5", columnList = "longitude")})
public class Location extends BaseEntity implements Serializable, UtilModel {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "driverId", length = 255)
    private String driverId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private Double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private Double longitude;

    public Location() {
        super();
    }

    @Override
    public void validateOb() throws ErrorGeneralException {
        ErrorsListModel errors = new ErrorsListModel();
        if(this == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Null object"));
        }
        
        if(driverId == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Field: driverId shouldn't be null"));
        }
        if(latitude == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Field: latitude shouldn't be null"));
        }
        if(longitude == null){
            errors.addError(new ErrorModel(SystemConfig.SYSTEM_ID[0]+CommonErrorCodeConfig.VALIDATION_ERROR[0], CommonErrorCodeConfig.VALIDATION_ERROR[1], "Field: longitude shouldn't be null"));
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
}
