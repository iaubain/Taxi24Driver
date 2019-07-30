/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.driver.models;

import com.driver.facades.BaseEntity;
import java.util.Date;

/**
 *
 * @author Aubain
 */
public class CommandModel extends BaseEntity{
    private String command;
    private String commandDescr;
    private String request;
    private String response;
    private String requestHeader;
    private String method;
    private String uri;
    private String systemId;
    private boolean exported;
    private ComponentModel system;

    public CommandModel() {
        super();
    }

    public CommandModel(String command, DateRange creationTimeRange, Integer pageNumber, Integer pageSize, Boolean isDesc) {
        super(creationTimeRange, pageNumber, pageSize, isDesc);
        this.command = command;
    }

    public CommandModel(String command, String commandDescr, String request, String response, String requestHeader, String method, String uri, String systemId, boolean exported, ComponentModel system, String status, Date creationTime) {
        super(status, creationTime);
        this.command = command;
        this.commandDescr = commandDescr;
        this.request = request;
        this.response = response;
        this.requestHeader = requestHeader;
        this.method = method;
        this.uri = uri;
        this.systemId = systemId;
        this.exported = exported;
        this.system = system;
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandDescr() {
        return commandDescr;
    }

    public void setCommandDescr(String commandDescr) {
        this.commandDescr = commandDescr;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public boolean isExported() {
        return exported;
    }

    public void setExported(boolean exported) {
        this.exported = exported;
    }

    public ComponentModel getSystem() {
        return system;
    }

    public void setSystem(ComponentModel system) {
        this.system = system;
    }
    
}
