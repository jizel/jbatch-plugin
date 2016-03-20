/*
 * Copyright 2016 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package muni.fi.dp.jz.jbatch.batchapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Zorz
 */
@Stateless
public class CliBatchManager {

    public CliBatchManager() {
    }        
    
    public String startJobCli(String deploymentName, String jobName) {
	String runJob = "/deployment=restReader.war/subsystem=batch-jberet:start-job(job-xml-name=restReader)";
        String startJobCli = "/deployment=" + deploymentName + "/subsystem=batch-jberet:start-job(job-xml-name=" + jobName + ")";
//        TODO - if runCommand == null throw exception and return null;
        return runCommand(startJobCli);
	}        
    
    public String getDeploymentInfo(){
       String getInfo = "deployment-info";
       String resp = runCommand(getInfo);
       JSONObject json = new JSONObject(resp);         
       JSONObject result = json.getJSONObject("result");
       return result.toString();
    }
    
    public String getBatchDeployments(){
        List<String> batchDeploymentsList = new ArrayList<>();
        String allDeployments = getDeploymentInfo();
        JSONObject json = new JSONObject(allDeployments);
//        String result = json.getJSONObject("result").get;
        Iterator<?> keys = json.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            JSONObject subsystems = json.getJSONObject(key).getJSONObject("subsystem");
            if(subsystems.has("batch-jberet")){
                batchDeploymentsList.add(key);
            }
        }
        JSONArray batchDeployments = new JSONArray(batchDeploymentsList);
        return batchDeployments.toString();
    }
    
    public String getJobsFromDeployment(String deployment){
        String getJobs = "/deployment=" + deployment + "/subsystem=batch-jberet:read-resource";
        return runCommand(getJobs);
    }
    
    //Help methods
    public String runCommand(String command){
        final CommandContext ctx ;
	    try {
	        ctx = CommandContextFactory.getInstance().newCommandContext();
	        
	        try {
	            // connect to the server controller
	            ctx.connectController();
	            // execute commands and operations                    
                    String response = executeCommand(ctx, ctx.buildRequest(command));
                    return response;                   
	        } catch (CommandLineException e) {
//                    CommandLineException not found with scope provided
	        	System.out.println("Exception when submitting command to server:" + e.toString());
	        }
	        
	    } catch (CliInitializationException e) {
//               CliInitializationException not found with scope provided
	        System.out.println("Exception when creating the ctx:" + e.toString());
	    }
            return null;
    }
    
    public static String executeCommand(CommandContext ctx,ModelNode modelNode) {   
           
         ModelControllerClient client = ctx.getModelControllerClient();
         if(client != null) {
            try {
                  ModelNode response = client.execute(modelNode);
                  System.out.println(response);
                  return (response.toJSONString(true));
            } catch (IOException e) {
                System.out.println("IOException thrown when executing command: " + e.toString());
            }
         } else {
              System.out.println("Connection Error! The ModelControllerClient is not available.");
        }
         return null;
    }    
    
}
