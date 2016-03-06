package muni.fi.dp.jz.jbatch.dtos;

import java.sql.Timestamp;
import java.util.Properties;
import javax.batch.runtime.BatchStatus;


public class JobExecutionDto {
	private long jobExecutionId;
	private String jobName;
//        private JobInstanceDto jobInstance;
//	private String parameters;
	private Timestamp createTime;
	private Timestamp startTime;
        private Timestamp endTime;
        private Timestamp lastUpdatedTime;
	private BatchStatus batchStatus;
	private String exitStatus;
//	private boolean stop = false;
	private Properties jobParameters;

    public long getJobExecutionId() {
        return jobExecutionId;
    }

    public String getJobName() {
        return jobName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public String getExitStatus() {
        return exitStatus;
    }

    public Properties getJobParameters() {
        return jobParameters;
    }

    public void setJobExecutionId(long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public void setExitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
    }

    public void setJobParameters(Properties jobParameters) {
        this.jobParameters = jobParameters;
    }
	
	
	
}
