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
package muni.fi.dp.jz.jbatch.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.runtime.JobExecution;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.jobservice.JobService;

/**
 *
 * @author jzelezny
 */
@Stateless
@Path("jobs")
public class JobResource {    
    
    @EJB
    private JobService jobService;
//    private BatchExecutionBean batchExecutor;
    private static final Logger LOG = Logger.getLogger( JobResource.class.getName() );
        
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("names")
    public Response getJobNames() {
		List<String> jobNameList = new ArrayList<>(jobService.getJobNames());
                return Response.ok(jobNameList, MediaType.APPLICATION_JSON).build();		
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jobexec/{execid}")
    public Response getJobExecution(@PathParam("execid") long executionId){
        try{
		JobExecution jobExec =  jobService.getJobExecution(executionId);
                return Response.ok(jobExec.toString(), MediaType.APPLICATION_JSON).build();
//                TODO - don't catch the low level exception, do that in service or batchExec class'
        }catch(EJBTransactionRolledbackException ex){
//            TODO - what to do here? :-)
            LOG.log( Level.SEVERE, ex.toString(), ex );
            System.out.println("exceptiona");
           return null; 
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count/{jobname}")
    public Response getJobInstanceCount(@PathParam("jobname") String jobName){
        try{
		int instanceCount =  jobService.getJobInstanceCount(jobName);
                return Response.ok(instanceCount, MediaType.APPLICATION_JSON).build();
//                TODO - don't catch the low level exception, do that in service or batchExec class'
        }catch(EJBTransactionRolledbackException ex){
//            TODO - what to do here? :-)
            LOG.log( Level.SEVERE, ex.toString(), ex );
            System.out.println("exceptiona");
           return null; 
        }
	}
    
}
