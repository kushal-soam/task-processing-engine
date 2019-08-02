package com.sapient.java.taskprocessor.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.util.ConfigReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigReader.class, loader = AnnotationConfigContextLoader.class)
public class ConfigReaderTest {

	@Test
	public void testDataNotNull() {
		File file = new File(this.getClass().getResource("/samplefile.csv").getFile());
		String path = file.getAbsoluteFile().getAbsolutePath();
		ConfigReader configReader = new ConfigReader();
		List<Config> list = null;
		try {
			list = configReader.buildConfigFromCsv(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(list != null);
	}

	@Test
	public void testDataSize() {
		File file = new File(this.getClass().getResource("/samplefile.csv").getFile());
		String path = file.getAbsoluteFile().getAbsolutePath();
		ConfigReader configReader = new ConfigReader();
		List<Config> list = null;
		try {
			list = configReader.buildConfigFromCsv(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(5, list.size());
	}

}
