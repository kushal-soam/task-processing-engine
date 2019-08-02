package com.sapient.java.taskprocessor.simulator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Task;
import com.sapient.java.taskprocessor.util.ConfigReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TaskGeneratorImpl.class, loader = AnnotationConfigContextLoader.class)
public class ConfigReaderTest {

	@Test
	public void testDataNotNull() {
		String path = "C:\\Users\\kuspalsi\\Documents\\workspace-sts-3.9.9.RELEASE\\task-processing-engine\\task-processor\\src\\main\\resources\\samplefile.csv";
		TaskGenerator taskGenerator = new TaskGeneratorImpl();
		ConfigReader configReader = new ConfigReader();
		List<Config> configs = null;
		try {
			configs = configReader.buildConfigFromCsv(path);
			assertNotNull(configs);
			List<Task> tasks = taskGenerator.generateTaskBundle(configs.stream().findFirst().get());
			assertNotNull(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(configs != null);
	}

}
