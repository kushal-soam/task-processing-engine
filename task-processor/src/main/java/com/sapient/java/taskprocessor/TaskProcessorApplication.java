package com.sapient.java.taskprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Config.Builder;
import com.sapient.java.taskprocessor.model.Task;
import com.sapient.java.taskprocessor.simulator.TaskGenerator;
import com.sapient.java.taskprocessor.simulator.TaskGeneratorImpl;

/**
 * @author kuspalsi
 *
 */
@SpringBootApplication
public class TaskProcessorApplication {
	private static final Logger logger = LoggerFactory.getLogger(TaskProcessorApplication.class);

	public static void main(String[] args) throws IOException, URISyntaxException {

		SpringApplication.run(TaskProcessorApplication.class, args);
		TaskProcessorApplication TaskProcessorApplication = new TaskProcessorApplication();
		List<Config> configs = TaskProcessorApplication.buildConfigFromCsv();
		logger.info("Config is {}", configs);
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

	private List<Config> buildConfigFromCsv() throws IOException, FileNotFoundException, URISyntaxException {
		List<Config> configForbatches = new ArrayList<Config>();
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String filePath = rootPath + "samplefile.csv";
		ClassLoader classLoader = getClass().getClassLoader();
		String path1 = classLoader.getResource("samplefile.csv").getPath();
		InputStream in = this.getClass().getResourceAsStream("/samplefile.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		try (CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
			for (CSVRecord record : csvParser) {
				Integer frequency = Integer.parseInt(record.get(0));
				Integer mintaskCount = Integer.parseInt(record.get(1));
				Integer maxTaskCount = Integer.parseInt(record.get(2));
				Integer minTaskRunTime = Integer.parseInt(record.get(3));
				Integer maxTaskRunTime = Integer.parseInt(record.get(4));
				String taskDistibution = record.get(5);
				Builder builder = new Builder();
				builder.addFrequency(frequency);
				builder.addMaxRunTime(maxTaskRunTime);
				builder.addMaxSize(maxTaskCount);
				builder.addMinRunTime(minTaskRunTime);
				builder.addMinSize(mintaskCount);
				builder.addTypeDistribution(taskDistibution);
				configForbatches.add(builder.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Read proeperty file for different configuration */
		return configForbatches;

	}

}
