package com.sapient.java.taskprocessor.simulator;

import java.util.List;

import com.sapient.java.taskprocessor.model.Config;
import com.sapient.java.taskprocessor.model.Task;

public interface TaskGenerator {
	

	List<Task> generateTaskBundle(Config config);

}
