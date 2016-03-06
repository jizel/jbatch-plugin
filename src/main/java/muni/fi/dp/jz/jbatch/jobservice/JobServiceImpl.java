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
package muni.fi.dp.jz.jbatch.jobservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import muni.fi.dp.jz.jbatch.batchapi.BatchExecutionBean;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;
import muni.fi.dp.jz.jbatch.util.JobInstanceToDto;

/**
 *
 * @author Zorz
 */
@Stateless
public class JobServiceImpl implements JobService{

    @EJB
    BatchExecutionBean batchExecutor;
    
//    TODO - catch exceptions
    
    @Override
    public long submitJob(String jobName) {
        return batchExecutor.submitJob(jobName);
    }

    @Override
    public JobExecution getJobExecution(long executionId) {
        return batchExecutor.getJobExecution(executionId);
    }

    @Override
    public long restartJob(long executionId) {
        return batchExecutor.restartJob(executionId);
    }

    @Override
    public Set<String> getJobNames() {
        return batchExecutor.getJobNames();
    }

    @Override
    public int getJobInstanceCount(String jobName) {
        return batchExecutor.getJobInstanceCount(jobName);
    }

    @Override
    public List<Long> getRunningExecutions(String jobName) {
        return batchExecutor.getRunningExecutions(jobName);
    }

    @Override
    public List<JobInstance> getJobInstances(String jobName) {
        return batchExecutor.getJobInstances(jobName);
    }

    @Override
    public List<JobExecution> getJobExecutions(JobInstance instance) {
        return batchExecutor.getJobExecutions(instance);
    }

    @Override
    public List<StepExecution> getStepExecutions(long jobExecutionId) {
        return batchExecutor.getStepExecutions(jobExecutionId);
    }

    @Override
    public Map<String,List<JobInstanceDto>> getAllJobInstances() {
        Set<String> allJobs = getJobNames();
        
        Map<String,List<JobInstanceDto>> allJobInstances = new HashMap<>();
        
        
        for(String job:allJobs){
            List<JobInstance> jobInstances = getJobInstances(job);
            List<JobInstanceDto> jobInstanceDtos = new ArrayList<>();
            for(JobInstance jobInstance:jobInstances){
                jobInstanceDtos.add(JobInstanceToDto.createDtoFromJobInstace(jobInstance));
            }
            allJobInstances.put(job, jobInstanceDtos);
        }
        System.out.println(allJobInstances);
        return allJobInstances;
    }

    @Override
    public List<JobInstanceDto> getAllInstances() {
       Set<String> allJobs = getJobNames();
       List<JobInstanceDto> jobInstanceDtos = new ArrayList<>();
        for(String job:allJobs){
            List<JobInstance> jobInstances = getJobInstances(job);            
            for(JobInstance jobInstance:jobInstances){
                jobInstanceDtos.add(JobInstanceToDto.createDtoFromJobInstace(jobInstance));
            }            
        }        
        return jobInstanceDtos;
    }
    
}
