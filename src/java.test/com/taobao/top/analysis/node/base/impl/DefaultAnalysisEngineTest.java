package com.taobao.top.analysis.node.base.impl;


import java.util.List;
import org.junit.Test;

import com.taobao.top.analysis.config.MasterConfig;
import com.taobao.top.analysis.job.Job;
import com.taobao.top.analysis.job.JobBuilder;
import com.taobao.top.analysis.job.JobTask;
import com.taobao.top.analysis.node.base.IInputAdaptor;


public class DefaultAnalysisEngineTest {

	@Test
	public void testDoAnalysis() throws Exception {
		DefaultAnalysisEngine defaultAnalysisEngine = new DefaultAnalysisEngine();
		IInputAdaptor fileInputAdaptor =  new FileInputAdaptor();
		IInputAdaptor httpInputAdaptor = new HttpInputAdaptor();
		FileOutputAdaptor fileOutAdaptor = new FileOutputAdaptor();
		MasterConfig masterConfig = new MasterConfig();
		
		DefaultReportExporter reportExporter = new DefaultReportExporter();
		reportExporter.setMasterConfig(masterConfig);
		reportExporter.init();
		
		fileOutAdaptor.setReportExporter(reportExporter);
		
		defaultAnalysisEngine.addInputAdaptor(fileInputAdaptor);
		defaultAnalysisEngine.addInputAdaptor(httpInputAdaptor);
		defaultAnalysisEngine.addOutputAdaptor(fileOutAdaptor);
		
		
		JobBuilder jobBuilder = new JobBuilder();
		List<Job> jobs = jobBuilder.build("analysis-job-config.properties");
		
		for(Job job : jobs)
		{
			List<JobTask> tasks = job.getJobTasks();
			
			for(JobTask jobtask : tasks)
				defaultAnalysisEngine.doAnalysis(jobtask);
		}

		reportExporter.destory();
		
	}

}
