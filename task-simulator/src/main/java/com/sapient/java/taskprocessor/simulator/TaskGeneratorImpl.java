package com.sapient.java.taskprocessor.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Task;

public class TaskGeneratorImpl implements TaskGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);
	
	public static AtomicInteger batchNumber = new AtomicInteger(0);

	@Override
	public List<Task> generateTaskBundle(Config config) {
		List<Task> tasks = new ArrayList<Task>();
		int batchNo = batchNumber.incrementAndGet();
		logger.info("Generating task batch number {}", batchNo);
		/* Generate count for number of task for a range between min and max */
		int randomTaskCount = ThreadLocalRandom.current().nextInt(config.getMinSize(), config.getMaxSize());

		

		/* generate task for different task types with distribution */
		Map<String, Integer> distributedCountMap = new HashMap<>();
		for (Entry<String, Integer> taskDistribution : config.getTypeDistribution().entrySet()) {
			String taskType = taskDistribution.getKey();
			Integer distributionPercent = taskDistribution.getValue();
			Integer taskCountForType = Math.floorDiv(randomTaskCount*distributionPercent,100);
			distributedCountMap.put(taskType, taskCountForType);

		}
		
		/* after calculating task count for different task type */
		int count = 0;
		for (Entry<String, Integer> taskDistributionEntry : distributedCountMap.entrySet()) {
			/* random priority for task types */
			String taskType = taskDistributionEntry.getKey();
			Integer distributedCount = taskDistributionEntry.getValue();
			for(int i=0; i<distributedCount;i++) {
				
				int ramdomPriority = ThreadLocalRandom.current().nextInt(1, 10);
				Task task = new Task();
				task.setGroupId(batchNo+"");
				++count;
				task.setId(batchNo+"_"+count);
				task.setName(taskType+"_"+batchNo+"_"+count);
				task.setPriority(ramdomPriority);
				task.setType(taskType);
				/* generate runtime for each task within a range */
				task.setTaskDuration(ThreadLocalRandom.current().nextInt(config.getMinRuntime(), config.getMaxRunTime()));
				tasks.add(task);
			}
			
		}
		logger.info("Task generated for batch= {} is {}",batchNo, tasks.size());

		return tasks;
	}

}
