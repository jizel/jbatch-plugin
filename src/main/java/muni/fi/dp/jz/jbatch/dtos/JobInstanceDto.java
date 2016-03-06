package muni.fi.dp.jz.jbatch.dtos;

import java.util.Objects;


public class JobInstanceDto {
	private String jobName;
//	private String parameters;
//	private String startTime;
	private Long jobInstanceId;
//        private long[] jobInstanceExecutionsIds;
        private Long jobNameHash;   
        
        
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public Long getJobInstanceId() {
		return jobInstanceId;
	}
	public void setJobInstanceId(Long jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}	

//    public long[] getJobInstanceExecutionsIds() {
//        return jobInstanceExecutionsIds;
//    }
//
//    public void setJobInstanceExecutionsIds(long[] jobInstanceExecutionsIds) {
//        this.jobInstanceExecutionsIds = jobInstanceExecutionsIds;
//    }

    public Long getJobNameHash() {
        return jobNameHash;
    }

        
    public void setJobNameHash(String jobName) {
        Long hash = new Long(7);
        hash = 17 * hash + Objects.hashCode(jobName);
        this.jobNameHash = hash;
    }   
    
	
    
	
}
