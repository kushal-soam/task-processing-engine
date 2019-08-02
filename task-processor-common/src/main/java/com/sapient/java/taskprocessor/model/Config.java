package com.sapient.java.taskprocessor.model;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

/**
 * @author kuspalsi
 *
 */
public class Config {
	/* frequncy of tasks submissions */
	private final Integer frequency;
	
	/* min size of task in a batch */
	private final Integer minBatchSize;
	
	/* max size of task in a batch */
	private final Integer maxBatchSize;
	
	/* min runtime of task in a batch */
	private final Integer minRuntime;
	
	/* max runtime of task in the batch */
	private final Integer maxRunTime;
	
	/* distribution of task for batch */
	private final Map<String, Integer> typeDistribution;

	public static class ConfigBuilder {

		private Integer frequency;
		private Integer minBatchSize;
		private Integer maxBatchSize;
		private Integer minRuntime;
		private Integer maxRunTime;
		private Map<String, Integer> typeDistribution;

		public ConfigBuilder addFrequency(Integer frequency) {
			this.frequency = frequency;
			return this;
		}

		public ConfigBuilder addMinBatchSize(Integer minSize) {
			this.minBatchSize = minSize;
			return this;
		}

		public ConfigBuilder addMaxBatchSize(Integer maxSize) {
			this.maxBatchSize = maxSize;
			return this;
		}

		public ConfigBuilder addMinRunTime(Integer minRuntime) {
			this.minRuntime = minRuntime;
			return this;
		}

		public ConfigBuilder addMaxRunTime(Integer maxRunTime) {
			this.maxRunTime = maxRunTime;
			return this;
		}

		
		public ConfigBuilder addTypeDistribution(String typeDistributionData) {
			StringTokenizer stringTokenizer = new StringTokenizer(typeDistributionData, "|");
			Map<String, Integer> taskDistributionMap = new HashMap<String, Integer>();
			while (stringTokenizer.hasMoreTokens()) {
				String taskDistibutionToken = stringTokenizer.nextToken();
				String[] mapping = taskDistibutionToken.split(":");
				taskDistributionMap.put(StringUtils.trimWhitespace(mapping[0]), Integer.parseInt(StringUtils.trimWhitespace(mapping[1])));
			}
			this.typeDistribution = taskDistributionMap;
			return this;
		}

		public Config build() {
			return new Config(this);
		}
	}

	/**
	 * @param builder
	 */
	private Config(ConfigBuilder builder) {
		this.frequency = builder.frequency;
		this.maxRunTime = builder.maxRunTime;
		this.maxBatchSize = builder.maxBatchSize;
		this.minRuntime = builder.minRuntime;
		this.minBatchSize = builder.minBatchSize;
		this.typeDistribution = builder.typeDistribution;
	}
	
	
	/**
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}


	/**
	 * @return the minSize
	 */
	public Integer getMinSize() {
		return minBatchSize;
	}


	/**
	 * @return the maxSize
	 */
	public Integer getMaxSize() {
		return maxBatchSize;
	}


	/**
	 * @return the minRuntime
	 */
	public Integer getMinRuntime() {
		return minRuntime;
	}


	/**
	 * @return the maxRunTime
	 */
	public Integer getMaxRunTime() {
		return maxRunTime;
	}


	/**
	 * @return the typeDistribution
	 */
	public Map<String, Integer> getTypeDistribution() {
		return typeDistribution;
	}


	@Override
	public String toString() {
		return "BatchModel [frequency=" + frequency + ", minBatchSize=" + minBatchSize + ", maxBatchSize=" + maxBatchSize + ", minRuntime="
				+ minRuntime + ", maxRunTime=" + maxRunTime + ", typeDistribution=" + typeDistribution + "]";
	}


}
