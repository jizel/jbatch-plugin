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

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;
import muni.fi.dp.jz.jbatch.dtos.JobExecutionDto;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;

/**
 *
 * @author Zorz
 */
@Stateless
public interface JobService {

    public long submitJob(String jobName);

    public JobExecution getJobExecution(long executionId);

    public long restartJob(long executionId);

    public Set<String> getJobNames();

    public int getJobInstanceCount(String jobName);

    public List<Long> getRunningExecutions(String jobName);

    public List<JobInstanceDto> getJobInstances(String jobName);

    public List<JobExecution> getJobExecutions(JobInstance instance);
    
    public List<JobExecutionDto> getJobExecutions(Long instanceId);

    public List<StepExecution> getStepExecutions(long jobExecutionId);
    
    public Map<String,List<JobInstanceDto>> getAllJobInstances();
    
    public List<JobInstanceDto> getAllInstances();
}
