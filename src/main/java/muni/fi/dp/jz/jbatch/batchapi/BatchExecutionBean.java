/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.batchapi;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;
/**
 *
 * @author Zorz
 * 
 * Service layer bean for batch job operations
 * Create interface??
 */

@Stateless
public class BatchExecutionBean {
    
    public long submitJob(String jobName) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobPropertis = new Properties();
        long executionId = jobOperator.start(jobName, jobPropertis);
        return executionId;
    }
        
    public JobExecution getJobExecution(Long executionId) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        return jobExecution;
    }
     
    public long restartJob(long executionId) {
        Properties jobProperties = new Properties();
        long newExecutionId = 
          BatchRuntime.getJobOperator().restart(executionId, jobProperties);
        return newExecutionId;
    }
    
    public Set<String> getJobNames(){
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Set<String> jobNames = jobOperator.getJobNames();
        return jobNames;
    }
    
    public int getJobInstanceCount(String jobName) {
       JobOperator jobOperator = BatchRuntime.getJobOperator();
       int jobInstanceCount = jobOperator.getJobInstanceCount(jobName);
       return jobInstanceCount;
    }
    
    public List<Long> getRunningExecutions(String jobName) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getRunningExecutions(jobName);
    }
    
    public List<JobInstance> getJobInstances(String jobName) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getJobInstances(jobName, 0, 100);
    }
    
    public List<JobInstance> getJobInstances(String jobName, int start, int count) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getJobInstances(jobName, start, count);
    }    
    public List<JobExecution> getJobExecutions(JobInstance instance) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getJobExecutions(instance);
    }
     
    public List<StepExecution> getStepExecutions(long jobExecutionId){
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getStepExecutions(jobExecutionId);
    }
    
    //Gets parent instance for job execution not instance by id!!!
    public JobInstance getJobInstance(Long executionId) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        JobInstance jobInstance = jobOperator.getJobInstance(executionId);
        return jobInstance;
    }
    
}
