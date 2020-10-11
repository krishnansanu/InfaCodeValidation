package com.wow.dev.infanodes;

import java.util.Map;

public class SessionTaskInstance {
	private Map<String, String> map;
	private String taskName;
	private String taskType;
	private String FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN;
	private String FAIL_PARENT_IF_INSTANCE_FAILS;
	
	public SessionTaskInstance(Map<String,String> map) {
		this.map=map;
		this.taskName=map.get("TASKINSTANCE.TASKNAME");
		this.taskType=map.get("TASKINSTANCE.TASKTYPE");
		this.FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN=map.get("TASKINSTANCE.FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN");
		this.FAIL_PARENT_IF_INSTANCE_FAILS=map.get("TASKINSTANCE.FAIL_PARENT_IF_INSTANCE_FAILS");
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public String getFAIL_PARENT_IF_INSTANCE_DID_NOT_RUN() {
		return FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN;
	}

	public String getFAIL_PARENT_IF_INSTANCE_FAILS() {
		return FAIL_PARENT_IF_INSTANCE_FAILS;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "["+taskName+","+taskType+","+FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN+","+FAIL_PARENT_IF_INSTANCE_FAILS+"]";
	}
	
}
