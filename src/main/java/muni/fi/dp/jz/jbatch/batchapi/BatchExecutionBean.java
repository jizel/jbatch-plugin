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

    public long submitJob() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobPropertis = new Properties();
        long executionId = jobOperator.start("first-batch-job", jobPropertis);
        return executionId;
    }
    public long submitJob(String jobName) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobPropertis = new Properties();
        long executionId = jobOperator.start(jobName, jobPropertis);
        return executionId;
    }
    
    //TODO use jobExecution dto instead of id as param??
    public JobExecution getJobExecution(long executionId) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        return jobExecution;
    }
 
    //TODO use jobExecution dto instead of id as param
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
    //TODO change JobInstance to own implementation
    public List<JobInstance> getJobInstances(String jobName) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getJobInstances(jobName, 0, 50);
    }
    public List<JobExecution> getJobExecutions(JobInstance instance) {
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getJobExecutions(instance);
    }
    public List<StepExecution> getStepExecutions(long jobExecutionId){
    	JobOperator jobOperator = BatchRuntime.getJobOperator();
    	return jobOperator.getStepExecutions(jobExecutionId);
    }
    
}
