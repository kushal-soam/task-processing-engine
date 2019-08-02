package com.sapient.java.taskprocessor.executer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.java.taskprocessor.model.Task;

/**
 * @author kuspalsi
 *
 */
public class TaskExecuter {
	private static final Logger logger = LoggerFactory.getLogger(TaskExecuter.class);
	public static int corePoolSize = 1;
	public static int maximumPoolSize = 8;
	public static long keepAliveTime = 0L;

	private static TaskExecuter taskExecuter = null;

	private BlockingQueue<Runnable> priorityQueue;
	private ExecutorService priorityTaskPoolExecutor;

	private TaskExecuter() {
		priorityQueue = new PriorityBlockingQueue<Runnable>();
		priorityTaskPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
				TimeUnit.SECONDS, priorityQueue);
	}

	public static TaskExecuter getInstance() {
		if (taskExecuter == null) {
			taskExecuter = new TaskExecuter();
		}
		return taskExecuter;
	}

	public void submitBatch(List<Task> tasks) {

		logger.info("priorityQueue elements are: {}, Size is: {}", priorityQueue, priorityQueue.size());

		tasks.forEach(task -> {
			priorityTaskPoolExecutor.execute(task);
		});

	}

	public void shutDown() {
		priorityTaskPoolExecutor.shutdown();
	}
}
