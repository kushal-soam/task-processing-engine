package com.sapient.java.taskprocessor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sapient.java.taskprocessor.executer.TaskExecuter;
import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Task;
import com.sapient.java.taskprocessor.simulator.TaskGenerator;
import com.sapient.java.taskprocessor.simulator.TaskGeneratorImpl;
import com.sapient.java.taskprocessor.util.ConfigReader;

/**
 * @author kuspalsi
 *
 */
@SpringBootApplication
public class TaskProcessorApplication {
	private static final Logger logger = LoggerFactory.getLogger(TaskProcessorApplication.class);

	public static void main(String[] args) throws IOException, URISyntaxException {

		SpringApplication.run(TaskProcessorApplication.class, args);
		ConfigReader configReader = new ConfigReader();
		List<Config> configs = configReader.buildConfigFromCsv();
		logger.info("Config is {}", configs);
		generateBatchAndSubmit(configs);

	}

	private static void generateBatchAndSubmit(List<Config> configs) {
		TaskGenerator taskGenerator = new TaskGeneratorImpl();
		TaskExecuter taskExecuter = TaskExecuter.getInstance();
		configs.forEach(config -> {
			logger.info("Generating batch where batch Config is {}", config);
			List<Task> tasks = taskGenerator.generateTaskBundle(config);
			logger.info("Submitting batch where batch Config is {}", config);
			taskExecuter.submitBatch(tasks);
			try {
				logger.info("Going to sleep for {} seconds", config.getFrequency());
				TimeUnit.SECONDS.sleep(config.getFrequency());
			} catch (InterruptedException e) {
				logger.info("Exception occurred whiling delaying on the basis of frequency {}", config);
			}
		});
		if (taskExecuter.isIdle()) {
			taskExecuter.shutDown();
		}
	}



}
