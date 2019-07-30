/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.utilities;

import com.driver.config.CmdConfig;
import com.driver.config.HeaderConfig;
import com.driver.config.SystemConfig;
import com.driver.models.CommandModel;
import com.driver.models.ComponentModel;
import com.driver.remote.ExternalService;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;

/**
 *
 * @author Aubain
 */
public class UtilInitiator implements Runnable{
    @Override
    public void run() {
        List<ComponentModel> ComponentModels = new Handler().start();
        if(ComponentModels == null || ComponentModels.isEmpty()){
            Log.d(getClass(), "Error initiating, we have an empty ComponentModels entity");
            return;
        }
        try {
            Thread.sleep(1000*60);
        } catch (InterruptedException ex) {
            ex.printStackTrace(out);
            Log.d(getClass(), "Error initiating, Thread was interupted");
            return;
        }
        try {
            List<ComponentModel> results = ExternalService.INIT(ComponentModels);
            if(results != null && !results.isEmpty())
                Log.d(getClass(), SystemConfig.SYSTEM_ID[1]+" Successfully Initiated to the dispatcher.");
            else
                Log.d(getClass(), SystemConfig.SYSTEM_ID[1]+" Failed Initiation to the dispatcher.");
        } catch (Exception e) {
            Log.e(getClass(), SystemConfig.SYSTEM_ID[1]+"_"+e.getMessage());
        }
    }
    class Handler{
        public List<ComponentModel> start(){
            List<ComponentModel> mComponentModel = new ArrayList<>();
            ComponentModel ComponentModel = new ComponentModel();
            ComponentModel.setName(SystemConfig.SYSTEM_ID[1]);
            List<CommandModel> mCommandModel = new ArrayList<>();
            
            for(CmdConfig cmd : CmdConfig.values()){
                CommandModel cmdModel = new CommandModel();
                switch(cmd){
                    case INIT_DRIVER_MODULE:
                        cmdModel.setCommand(CmdConfig.INIT_DRIVER_MODULE.toString());
                        cmdModel.setCommandDescr("This CommandModel is used to initiate a ComponentModel.");
                        cmdModel.setExported(true);
                        cmdModel.setMethod("POST");
                        cmdModel.setUri("http://localhost:9002/Taxi24Driver/driver/process/fact");
                        cmdModel.setRequestHeader(HeaderConfig.CMD+":"+CmdConfig.INIT_DRIVER_MODULE.toString()+"; Content-Type:"+MediaType.APPLICATION_JSON);
                        cmdModel.setRequest("["+new ComponentModel().toString()+"]");
                        cmdModel.setResponse("["+new ComponentModel().toString()+"]");
                        
                        mCommandModel.add(cmdModel);
                        break;
                    default:
                        cmdModel.setCommand(cmd.toString());
                        cmdModel.setCommandDescr("This CommandModel is used to "+cmd.toString()+".");
                        cmdModel.setExported(true);
                        cmdModel.setMethod("POST");
                        cmdModel.setUri("http://localhost:9002/Taxi24Driver/driver/process/fact");
                        cmdModel.setRequestHeader(HeaderConfig.CMD+":"+cmd.toString()+"; Content-Type:"+MediaType.APPLICATION_JSON+"; "+HeaderConfig.AUTH_KEY+": YOUR_TOKEN_KEY");
                        cmdModel.setRequest("any");
                        cmdModel.setResponse("any");
                        
                        mCommandModel.add(cmdModel);
                }
            }
            
            ComponentModel.setCommands(mCommandModel);
            mComponentModel.add(ComponentModel);
            return mComponentModel;
        }
    }
}
