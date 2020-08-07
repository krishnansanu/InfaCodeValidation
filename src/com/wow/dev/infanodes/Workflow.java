package com.wow.dev.infanodes;

import java.util.Map;

public class Workflow{
	
	// Workflow Properties Attributes
	private Map<String, String> map;
	private String workflowName;
	private String isValid;
	private String integrationService;
	private String WORKFLOW_BACKWARD_COMPATIBLE;
	private String logName;
	private String logDirectory;
	private String folderName;
	
	// Workflow Validation Attributes
	private String workflowNameValidation;
	private String workflowIsValidValidation;
	private String workflowIntegrationServiceValidation;
	private String WORKFLOW_BACKWARD_COMPATIBLEValidaiton;
	private String workflowLogNameValidation;
	private String workflowlogDirectoryValidation;
	
	
	public Workflow(Map<String,String> map,String folderName) {
		this.map=map;
		this.folderName=folderName;
	}
	
	public boolean validateWorkflowName(Map<String,String> validationList) {
		workflowName=map.get("WORKFLOW.NAME");
		
		if(!workflowName.substring(0, 4).contentEquals("wkf_")) {
			validationList.put("WORKFLOW.NAME","Invalid Start of Workflow Name ["+ workflowName +"]. Workflow Name Should Start with 'wkf_'");
			return false;
		}
		return true;
	}
	
	public boolean isWorkflowValid(Map<String,String> validationList) {
		isValid=map.get("WORKFLOW.ISVALID");
		System.out.println("Validating Workflow isValid Option. [isValid=" + isValid + "]");
		if(!isValid.equals("YES")) {
			validationList.put("WORKFLOW.ISVALID","Workflow [" + map.get("WORKFLOW.NAME") + "] is not valid. Please validate the Workflow to find out the issue");
			return false;
		}
		return true;
	}
	
	public boolean validateIntegrationService(Map<String,String> validationList) {
		integrationService=map.get("WORKFLOW.SERVERNAME");
		if(integrationService.contains("IS_ENT")) {
			validationList.put("WORKFLOW.INTEGRATIONSERVERNAME","Workflow [" + map.get("WORKFLOW.NAME") + "] Integration is selected as IS_ENT* Please Verify if there is a special requirement to run the workflow in ENT Integration Service");
			return false;
		}
		return true;
	}
	
	
	public boolean validateWorkflowBackwardCompatible(Map<String,String> validationList) {
		WORKFLOW_BACKWARD_COMPATIBLE=map.get("WORKFLOW_ATTRIBUTE.Write Backward Compatible Workflow Log File");
		System.out.println("Validating Workflow Backward Compatible Option. [Backward Compatible=" + WORKFLOW_BACKWARD_COMPATIBLE + "]");
		if(!WORKFLOW_BACKWARD_COMPATIBLE.equals("YES")) {
			validationList.put("WORKFLOW_ATTRIBUTE.Write Backward Compatible Workflow Log File","Backward Compatible is not enabled in the Workflow - [" + map.get("WORKFLOW.NAME") + "]");
			return false;
		}		
		return true;
	}
	
	public boolean validateWorkflowLog(Map<String,String> validationList) {
		logName=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Name");
		System.out.println("Validating Workflow log Name. [Workflow Log Name=" + logName + "]");
		if(!logName.equals(map.get("WORKFLOW.NAME")+".log")) {
			validationList.put("WORKFLOW_ATTRIBUTE.Workflow Log File Name","Workflow log name should be same as Workflow name [" + map.get("Workflow.NAME") + "].");
			return false;
		}
		return true;
	}
	
	public boolean validateWorkflowLogDirectory(Map<String,String> validationList) {
		String logDirectory=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Directory");
		logDirectory=logDirectory.replace("$PMWorkflowLogDir", "/infadata/Logs");
		System.out.println("Validating workflow log directory. [workflow log directory=" + logDirectory + "]");
		if(!logDirectory.equals("/infadata/Logs/" + folderName + "/WkfLogs/")) {
			validationList.put("WORKFLOW_ATTRIBUTE.Workflow Log File Directory", "Workflow [" + map.get("WORKFLOW.NAME") + "] Logs are not pointing to Project Folder. Workflow Logs should be written under /infadata/Logs/" + folderName + "/WkfLogs/");
			return false;
		}
		return true;
	}
	
	public void validate(Map<String,String> validationList) {
		workflowNameValidation=validateWorkflowName(validationList)?"PASS":"FAIL";
		workflowIsValidValidation=isWorkflowValid(validationList)?"PASS":"FAIL";
		workflowIntegrationServiceValidation=validateIntegrationService(validationList)?"PASS":"WARNING";
		WORKFLOW_BACKWARD_COMPATIBLEValidaiton=validateWorkflowBackwardCompatible(validationList)?"PASS":"FAIL";
		workflowLogNameValidation=validateWorkflowLog(validationList)?"PASS":"FAIL";
		workflowlogDirectoryValidation=validateWorkflowLogDirectory(validationList)?"PASS":"WARNING";
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public String getIsValid() {
		return isValid;
	}

	public String getIntegrationService() {
		return integrationService;
	}

	public String getWORKFLOW_BACKWARD_COMPATIBLE() {
		return WORKFLOW_BACKWARD_COMPATIBLE;
	}

	public String getLogName() {
		return logName;
	}

	public String getLogDirectory() {
		return logDirectory;
	}

	public String getWorkflowNameValidation() {
		return workflowNameValidation;
	}

	public String getWorkflowIsValidValidation() {
		return workflowIsValidValidation;
	}

	public String getWorkflowIntegrationServiceValidation() {
		return workflowIntegrationServiceValidation;
	}

	public String getWORKFLOW_BACKWARD_COMPATIBLEValidaiton() {
		return WORKFLOW_BACKWARD_COMPATIBLEValidaiton;
	}

	public String getWorkflowLogNameValidation() {
		return workflowLogNameValidation;
	}

	public String getWorkflowlogDirectoryValidation() {
		return workflowlogDirectoryValidation;
	}
	
	
	
	
	
}
