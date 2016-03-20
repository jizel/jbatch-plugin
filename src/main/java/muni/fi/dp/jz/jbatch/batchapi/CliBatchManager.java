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
import javax.ejb.Stateless;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

/**
 *
 * @author Zorz
 */
@Stateless
public class CliBatchManager {

    public CliBatchManager() {
    }        
    
    public String startJobCli() {
	String runJob = "/deployment=restReader.war/subsystem=batch-jberet:start-job(job-xml-name=restReader)";
//        TODO - if runCommand == null throw exception and return null;
        return runCommand(runJob);
	}        
    
    public String getDeploymentInfo(){
       String getInfo = "deployment-info";
       return runCommand(getInfo);
    }
    
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
