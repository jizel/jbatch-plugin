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
package muni.fi.dp.jz.jbatchmanager;

import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;

/**
 *
 * @author Zorz
 */
public class CliBatchManager {

    public CliBatchManager() {
    }
    
    public void startJobCli() {
		final CommandContext ctx ;
	    try {
	        ctx = org.jboss.as.cli.CommandContextFactory.getInstance().newCommandContext();
	        
	        try {
	            // connect to the server controller
	            ctx.connectController();

	            // execute commands and operations
	            ctx.handle("/deployment=restReader.war/subsystem=batch-jberet:start-job(job-xml-name=restReader)");
	        } catch (CommandLineException e) {
	        	System.out.println("Exception when submitting command to server:" + e.toString());
	        }
	        
	    } catch (CliInitializationException e) {
	        System.out.println("Exception when creating the ctx:" + e.toString());
	    }
	}
}
