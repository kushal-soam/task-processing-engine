package com.sapient.java.taskprocessor.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.sapient.java.taskprocessor.model.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigReader.class, loader = AnnotationConfigContextLoader.class)
public class ConfigReaderTest {

	@Test
	public void testDataNotNull() {
		List<Config> list = null;
		File file = new File("temp.csv");
		try {
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/samplefile.csv"));
			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(isr, outputStream);
			String path = file.getAbsoluteFile().getAbsolutePath();
			ConfigReader configReader = new ConfigReader();
			list = configReader.buildConfigFromCsv(path);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!Objects.isNull(file) && file.exists()) {
				try {
					file.delete();
				} catch (Exception e) {
				}
			}
		}

		assertTrue(list != null);
	}

	@Test
	public void testDataSize() {
		List<Config> list = null;
		File file = new File("temp.csv");
		try {
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/samplefile.csv"));
			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(isr, outputStream);
			ConfigReader configReader = new ConfigReader();
			list = configReader.buildConfigFromCsv(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 finally {
				if (!Objects.isNull(file) && file.exists()) {
					try {
						file.delete();
					} catch (Exception e) {
					}
				}
			}

		assertEquals(5, list.size());
	}

}
