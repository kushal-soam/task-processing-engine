package com.sapient.java.taskprocessor.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Config.Builder;

/**
 * @author kuspalsi
 *
 */
public class ConfigReader {

	
	public List<Config> buildConfigFromCsv() throws IOException, FileNotFoundException, URISyntaxException {
		List<Config> configForbatches = new ArrayList<Config>();
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
