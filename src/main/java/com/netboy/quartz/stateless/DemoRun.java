package com.netboy.quartz.stateless;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceProvider;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.netboy.quartz.job.JobTask;
import com.netboy.quartz.utils.JobUtils;
import com.netboy.zk.demo.discovery.ExampleServer;
import com.netboy.zk.demo.discovery.InstanceDetails;

public class DemoRun {
	public static void main(String[] args) throws SchedulerException {
		ApplicationContext springContext = new ClassPathXmlApplicationContext("conf.xml");
		// schedulerFactoryBean 由spring创建注入
		Scheduler scheduler = (StdScheduler) springContext.getBean("schedulerFactoryBean");

		// 这里获取任务信息数据
		List<JobDO> jobList = DataWorkContext.getAllJob();

		for (JobDO job : jobList) {

			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 不存在，创建一个
			if (null == trigger) {
				JobDetail jobDetail = JobBuilder.newJob(JobTask.class)
						.withIdentity(job.getJobName(), job.getJobGroup()).build();
				jobDetail.isDurable();
				jobDetail.requestsRecovery();
				jobDetail.getJobDataMap().put(JobUtils.SCHEDULE_JOB, job);

				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		}

		try {
			processCommands(scheduler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("scheduler will shut down");
		scheduler.shutdown();
		System.exit(0);

	}

	private static void processCommands(Scheduler scheduler) throws Exception
    {
        // More scaffolding that does a simple command line processor

        printHelp();
            BufferedReader          in = new BufferedReader(new InputStreamReader(System.in));
            boolean                 done = false;
            while ( !done )
            {
                System.out.print("> ");

                String      line = in.readLine();
                if ( line == null )
                {
                    break;
                }

                String      command = line.trim();
                String[]    parts = command.split("=");
                if ( parts.length == 0 )
                {
                    continue;
                }
                String      operation = parts[0];
                String      args[] = Arrays.copyOfRange(parts, 1, parts.length);

                if ( operation.equalsIgnoreCase("help") || operation.equalsIgnoreCase("?") )
                {
                    printHelp();
                }
                else if ( operation.equalsIgnoreCase("q") || operation.equalsIgnoreCase("quit") )
                {
                    done = true;
                }
                else if ( operation.equals("add") )
                {	
                	JobDO taskJob = new JobDO(args[1], args[0], args[2],args[3]);
                	JobUtils.addJob(scheduler, taskJob);
                }
                else if ( operation.equals("delete") )
                {
                	JobDO taskJob = new JobDO(args[1], args[0], "");
                	JobUtils.deleteJob(scheduler, taskJob);
                }
                else if ( operation.equals("list_all") )
                {
                	List<JobDO> list  = JobUtils.getAllJob(scheduler);
                	System.out.println("## list all job ");
                	for(JobDO taskJob : list){
                		System.out.println("group = " + taskJob.getJobGroup()+"--job = " + taskJob.getJobName()+"--cron = "+taskJob.getCronExpression());
                	}
                }
                else if ( operation.equals("list_run") )
                {
                	List<JobDO> list  = JobUtils.getRunningJob(scheduler);
                	System.out.println("## list all runing job ");
                	for(JobDO taskJob : list){
                		System.out.println("group = " + taskJob.getJobGroup()+"--job = " + taskJob.getJobName());
                	}
                }
                else if ( operation.equals("pause") )
                {
                	JobDO taskJob = new JobDO(args[1], args[0], "");
                    JobUtils.pauseJob(scheduler, taskJob);
                }
                else if ( operation.equals("resume") )
                {
                	JobDO taskJob = new JobDO(args[1], args[0]);
                    JobUtils.resumeJob(scheduler, taskJob);
                }
                else if ( operation.equals("updateCron") )
                {
                	JobDO taskJob = new JobDO(args[1], args[0],args[2]);
                    JobUtils.updateJobCron(scheduler, taskJob);
                }
                else if ( operation.equals("run_now") )
                {
                	JobDO taskJob = new JobDO(args[1], args[0]);
                    JobUtils.runAJobNow(scheduler, taskJob);
                }
                else if ( operation.equals("quit") )
                {
                	System.out.println("quit....");
                }
                else {
					printHelp();
				}
            }
    }

	private static void printHelp() {
		System.out.println("####################\n");
		System.out.println();
		System.out.println("add <groupName> <jobName> <cronDesc>		=> [add=coupon=coupon-all-seller-vsearch=0/10 * * * * ?=127.2.2.1:8001s]");
		System.out.println("delete <groupName> <jobName>				=> [delete=coupon=coupon-all-seller-vsearch]");
		System.out.println("list_all									=> [list_all]");
		System.out.println("list_run									=> [list_run]");
		System.out.println("pause <groupName> <jobName>					=> [pause=coupon=coupon-all-seller-vsearch]");
		System.out.println("resume <groupName> <jobName>				=> [resume=coupon=coupon-all-seller-vsearch]");
		System.out.println("updateCron <groupName> <jobName> <cronDesc> => [updateCron=coupon=coupon-all-seller-vsearch=0/5 * * * * ?]");
		System.out.println("quit: Quit the example");
		System.out.println();
		System.out.println("####################\n");
	}
}
