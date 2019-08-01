package com.sapient.java.taskprocessor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kuspalsi
 *
 */
public class ConfigReader {

	public String parseInput() throws FileNotFoundException, IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("samplefile.csv").toURI());

		Stream<String> lines = Files.lines(path);
		String data = lines.collect(Collectors.joining("\n"));
		System.out.println(data);
		lines.close();
		return data;

	}

}
