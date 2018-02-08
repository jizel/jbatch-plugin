package muni.fi.dp.jz.jbatch.dtos;

public class JobInstanceDto {
    private String jobName;
    private long jobInstanceId;


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

}
