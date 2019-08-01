package com.sapient.java.taskprocessor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kuspalsi
 *
 */
public class Task implements Runnable, Comparable<Task> {
	private static final Logger logger = LoggerFactory.getLogger(Task.class);
	String id;
	String name;
	Integer priority;
	String type;
	Integer taskDuration;
	String groupId;

	/**
	 * @return the taskDuration
	 */
	public Integer getTaskDuration() {
		return taskDuration;
	}

	/**
	 * @param taskDuration the taskDuration to set
	 */
	public void setTaskDuration(Integer taskDuration) {
		this.taskDuration = taskDuration;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void run() {
		logger.info("Thread {} execution start with sleep time: {}", this.toString(), this.getTaskDuration());
		try {
			Thread.sleep((this.getTaskDuration() * 1000));
		} catch (InterruptedException e) {
			logger.error("Exception occured while executing thread {}, Error occured is {}", this.toString(),
					e.getMessage());
			e.printStackTrace();
		}
		logger.info("Thread {} execution completed with sleep time: {}", this.toString(), this.getTaskDuration());

	}

	@Override
	public int compareTo(Task task) {

		return this.getPriority() > task.getPriority() ? 1 : (this.getPriority() == task.getPriority() ? 0 : -1);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", priority=" + priority + ", type=" + type + ", taskDuration="
				+ taskDuration + ", groupId=" + groupId + "]";
	}

}
