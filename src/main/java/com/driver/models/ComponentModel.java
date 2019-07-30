/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.driver.models;

import com.driver.facades.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Aubain
 */
public class ComponentModel extends BaseEntity{
    private String name;
    private List<CommandModel> commands;

    public ComponentModel() {
        super();
    }

    public ComponentModel(String name, List<CommandModel> commands, String status, Date creationTime) {
        super(status, creationTime);
        this.name = name;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommandModel> getCommands() {
        return commands;
    }

    public void setCommands(List<CommandModel> commands) {
        this.commands = commands;
    }
    
}
