package com.netboy.quartz.stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataWorkContext {
	 /**
     * 计划任务map
     */
	private static Map<String, JobDO> jobMap = new HashMap<String, JobDO>();

/*    static {
        TaskJob job = new TaskJob();
        job.setJobId("10001");
        job.setJobName("coupon-buyer-vsearch");
        job.setJobGroup("optIndex");
        job.setJobStatus("1");
        job.setCronExpression("0/10 * * * * ?");
        job.setDesc("索引优化调度");
        
        addJob(job);
    }*/

    /**
     * 添加任务
     *
     * @param scheduleJob
     */
    public static void addJob(JobDO scheduleJob) {
        jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
    }

    /**
     * 获取任务
     *
     * @param jobId
     * @return
     */
    public static JobDO getJob(String jobId) {
        return jobMap.get(jobId);
    }

    /**
     * 获取所有任务
     *
     * @return
     */
    public static List getAllJob() {
        List jobList = new ArrayList(jobMap.size());
        for (Map.Entry entry : jobMap.entrySet()) {
            jobList.add(entry.getValue());
        }
        return jobList;
    }

}
