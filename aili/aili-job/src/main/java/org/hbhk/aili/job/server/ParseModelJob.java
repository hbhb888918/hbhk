package org.hbhk.aili.job.server;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ParseModelJob implements Job {

	private static final Logger logger = Logger.getLogger(ParseModelJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// 这里输进任务处理的内容
		System.out.println("这里输进任务处理的内容");
	}

}
