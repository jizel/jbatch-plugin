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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.dtos.JobExecutionDto;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;
import muni.fi.dp.jz.jbatch.dtos.StepExecutionDto;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;
import muni.fi.dp.jz.jbatch.service.JobService;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/**
 *
 * @author jzelezny
 */
@Stateless
@Path("jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {    
    
    @EJB
    private JobService jobService;    
    private static final Logger LOG = Logger.getLogger( JobResource.class.getName() );                
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("submit/{jobname}")
    public Response getJobExecution(@PathParam("jobname") String jobName){       
        try{
		long execId = jobService.submitJob(jobName);
                return Response.ok(execId, MediaType.APPLICATION_JSON).build();
            }catch(BatchExecutionException e){
            LOG.error("Exception when calling job service" + e);
            return Response.serverError().build();
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("names")
    public Response getJobNames() {
        try {
		List<String> jobNameList = new ArrayList<>(jobService.getJobNames());
                return Response.ok(jobNameList, MediaType.APPLICATION_JSON).build();
        }catch(BatchExecutionException e){
            LOG.error("Exception when calling job service" + e);
            return Response.serverError().build();
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jobexec/{execid}")
    public Response getJobExecution(@PathParam("execid") Long executionId){
        if(executionId == null){
            return Response.serverError().entity("Execution id is empty").build();
        }
        try{
		JobExecution jobExec =  jobService.getJobExecution(executionId);
                return Response.ok(jobExec.toString(), MediaType.APPLICATION_JSON).build();
            }catch(BatchExecutionException e){
            LOG.error("Exception when calling job service" + e);
            return Response.serverError().build();
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count/{jobname}")
    public Response getJobInstanceCount(@PathParam("jobname") String jobName){
         if(jobName == null){
            return Response.serverError().entity("Jobname is empty").build();
        }
        try{
		int instanceCount =  jobService.getJobInstanceCount(jobName);
                return Response.ok(instanceCount, MediaType.APPLICATION_JSON).build();
        }catch(EJBTransactionRolledbackException ex){
            LOG.error("Transaction rollback exception" + ex);
           return Response.serverError().build();
        }catch (BatchExecutionException e){
            LOG.error("Exception when calling job service" + e);
            return Response.serverError().build();
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("counts")
    public Response getJobCounts() {
        try{
		List<String> jobNameList = new ArrayList<>(jobService.getJobNames());
                Map<String, Integer> jobCounts = new HashMap<>();
                for (String job:jobNameList) jobCounts.put(job,jobService.getJobInstanceCount(job));
                return Response.ok(jobCounts, MediaType.APPLICATION_JSON).build();
         }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
	}
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inst/{jobname}")
    public Response getJobInstances(@PathParam("jobname") String jobName) {
        if(jobName == null){
            return Response.serverError().entity("Jobname is empty").build();
        }
        try {
            List<JobInstanceDto> jobInstances = jobService.getJobInstances(jobName);        
            return Response.ok(jobInstances, MediaType.APPLICATION_JSON).build();		                   
        }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inst/{jobname}/{instId}/executions")    
    public Response getJobExecutionsFromInstance(@PathParam("instId") Long instanceId, @PathParam("jobname") String jobName){
        if(jobName == null || instanceId == null){
            return Response.serverError().entity("Job name or instance id is empty").build();
        }
        try {
            List<JobExecutionDto> jobExecutions = jobService.getJobExecutions(jobName,instanceId);
            return Response.ok(jobExecutions, MediaType.APPLICATION_JSON).build();
        }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("inst/{jobname}/{instId}/executions/last")
    public Response getLastJobExecutionFromInstance(@PathParam("instId") Long instanceId, @PathParam("jobname") String jobName){
        if(jobName == null || instanceId == null){
            return Response.serverError().entity("Job name or instance id is empty").build();
        }
        try {
            List<JobExecutionDto> jobExecutions = jobService.getJobExecutions(jobName,instanceId);
            JobExecutionDto lastInstanceExecution = jobExecutions.get(jobExecutions.size()-1);
            return Response.ok(lastInstanceExecution.getJobExecutionId(), MediaType.APPLICATION_JSON).build();
        }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
    }
    
    @GET    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("steps/{execId}")
    public Response getStepExecutions(@PathParam("execId") Long executionId) {
        if(executionId == null){
            return Response.serverError().entity("Execution id is empty").build();
        }
        try {
            List<StepExecutionDto> stepExecutions = jobService.getStepExecutions(executionId);      
            return Response.ok(stepExecutions, MediaType.APPLICATION_JSON).build();		                   
         }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        } 
	}
    
    @GET
    @Path("restart/{execId}")
    public Response restartJob(@PathParam("execId") Long executionId){
        if(executionId == null){
            return Response.serverError().entity("Execution id is empty").build();
        }
        try {
            long id = jobService.restartJob(executionId);                
            LOG.info("\nJob with id: " + executionId + " restarted\n");        
            return Response.ok(id, MediaType.APPLICATION_JSON).build();
        }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
    }
    
    @GET
    @Path("stop/{execId}")
    public Response stopExecution(@PathParam("execId") Long executionId){
        if(executionId == null){
            return Response.serverError().entity("Execution id is empty").build();
        }
        try {
            jobService.stop(executionId);
            LOG.info("\nJob with id: " + executionId + " stopped\n"); 
            return Response.ok("Execution with id: " + executionId + " stopped", MediaType.APPLICATION_JSON).build();
         }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
    }
    
    @GET
    @Path("abandon/{execId}")
    public Response abandonExecution(@PathParam("execId") Long executionId){
        if(executionId == null){
            return Response.serverError().entity("Execution id is empty").build();
        }
        try {
            jobService.abandon(executionId);
            LOG.info("\nJob with id: " + executionId + " abandoned\n"); 
            return Response.ok("Execution with id: " + executionId + " abandoned", MediaType.APPLICATION_JSON).build();
         }catch (BatchExecutionException e){
         LOG.error("Exception when calling job service" + e);
         return Response.serverError().build();
        }
        }
        
}