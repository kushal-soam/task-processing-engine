package com.sapient.java.taskprocessor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Config.ConfigBuilder;

/**
 * @author kuspalsi
 *
 */
public class ConfigReader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

	/**
	 * @return
	 * @throws Exception
	 */
	public List<Config> buildConfigFromCsv(String path) throws Exception {
		List<Config> configForbatches = new ArrayList<Config>();
		File file = null;
		if (Objects.isNull(path)) {
			InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/samplefile.csv"));
			file = new File("temp.csv");
			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(isr, outputStream);
//			file = new File(this.getClass().getResource("/samplefile.csv").getFile());
//			ClassPathResource cl = new ClassPathResource("samplefile.csv");
			// file = cl.getFile();
		} else {
			file = new File(path);
		}
		try (FileReader filereader = new FileReader(file);
				BufferedReader br = new BufferedReader(filereader);
				CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());) {

			csvParser.forEach(record -> {

				Integer frequency = Integer.parseInt(StringUtils.trimWhitespace(record.get(0)));
				Integer mintaskCount = Integer.parseInt(StringUtils.trimWhitespace(record.get(1)));
				Integer maxTaskCount = Integer.parseInt(StringUtils.trimWhitespace(record.get(2)));
				Integer minTaskRunTime = Integer.parseInt(StringUtils.trimWhitespace(record.get(3)));
				Integer maxTaskRunTime = Integer.parseInt(StringUtils.trimWhitespace(record.get(4)));
				String taskDistibution = StringUtils.trimWhitespace(record.get(5));

				/* build configuration from input file */
				ConfigBuilder builder = new ConfigBuilder();
				builder.addFrequency(frequency);
				builder.addMaxRunTime(maxTaskRunTime);
				builder.addMaxBatchSize(maxTaskCount);
				builder.addMinRunTime(minTaskRunTime);
				builder.addMinBatchSize(mintaskCount);
				builder.addTypeDistribution(taskDistibution);
				configForbatches.add(builder.build());
			});
		} catch (Exception e) {
			logger.error("Config loading failed {}, {}", e.getMessage(), e.getCause());
			throw new Exception("Loading config failed from input file.");
		} finally {
			if (file.exists()) {
				try {
					file.delete();
				} catch (Exception e) {
				}
			}
		}

		return configForbatches;

	}

}
