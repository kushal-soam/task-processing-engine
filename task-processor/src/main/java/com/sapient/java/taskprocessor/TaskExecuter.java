package com.sapient.java.taskprocessor;

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

	private static TaskExecuter taskExecuter = null;

	private BlockingQueue<Runnable> priorityQueue;
	private ExecutorService priorityTaskPoolExecutor;

	private TaskExecuter() {
		priorityQueue = new PriorityBlockingQueue<Runnable>();
		priorityTaskPoolExecutor = new ThreadPoolExecutor(2, 8, 1000, TimeUnit.SECONDS, priorityQueue);
		// priorityTaskPoolExecutor = Executors.newFixedThreadPool(10);
	}

	public static TaskExecuter getInstance() {
		if (taskExecuter == null) {
			taskExecuter = new TaskExecuter();
		}
		return taskExecuter;
	}

	public void submitBatch(List<Task> tasks) {

		priorityQueue.addAll(tasks);
		logger.info("priorityQueue elements are: {}, Size is: {}", priorityQueue, priorityQueue.size());

		tasks.forEach(task-> {
			priorityTaskPoolExecutor.execute(task);
		});
//		while (!priorityQueue.isEmpty()) {
//			try {
//				priorityTaskPoolExecutor.submit(priorityQueue.take());
//			} catch (InterruptedException e) {
//				// exception needs special handling
//			}
//		}

	}

	public boolean isIdle() {
		return priorityQueue.isEmpty();
	}
	public void shutDown() {
		priorityTaskPoolExecutor.shutdown();
	}
}
