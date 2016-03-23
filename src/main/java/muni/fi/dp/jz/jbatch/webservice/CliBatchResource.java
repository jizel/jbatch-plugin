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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.service.CliService;
import org.apache.log4j.Logger;

/**
 *
 * @author Zorz
 */
@Stateless
@Path("cli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CliBatchResource {
    
    @EJB
    private CliService cliService;
    private static final Logger LOG = Logger.getLogger( JobResource.class.getName() );
    
    @GET
    @Path("start/{deployment}/{jobName}")
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName){
        String resp = cliService.startJobCli(deploymentName, jobName);
        LOG.info("Job " + jobName + " started via cli! Server response returned.\n");
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();        
    }            
    
    @GET        
    @Path("deployments")
    public Response getDeploymentInfo(){
       String resp = cliService.getDeploymentInfo();        
        LOG.info("\nDeployments from server requested\n");                
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }
    
    @GET        
    @Path("batchDepl")
    public Response getBatchDeployments(){
       String resp = cliService.getBatchDeploymentsWithJobs();        
        LOG.info("\nBatch deployments only requested from server\n");                
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }
    
    @GET        
    @Path("deploymentJobs/{deployment}")
    public Response getBatchDeployments(@PathParam("deployment") String deployment){
       String deploymentJobs = cliService.getJobsFromDeployment(deployment);
//       TODO: Take just the job part from resp??
        LOG.info("\nAll possible jobs for deployment" + deployment + "returned via rest\n");                
        return Response.ok(deploymentJobs, MediaType.APPLICATION_JSON).build();
    }
}
