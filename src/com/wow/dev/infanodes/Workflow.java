package com.wow.dev.infanodes;

import java.util.ArrayList;

public class Workflow  extends InfaXMLNodes{
	
	public Workflow() {
		super();
	}
	
	public void validateWorkflowName(ArrayList<String> errorList) {
		String workflowName=map.get("WORKFLOW.NAME");
		super.nullValidation("Workflow Name", workflowName, errorList);
		
		if(!workflowName.substring(0, 4).contentEquals("wkf_")) {
			errorList.add("Invalid Start of Workflow Name ["+ workflowName +"]. Workflow Name Should Start with 'wkf_'");
		}
	}
	
	public void isWorkflowValid(ArrayList<String> errorList) {
		String isValid=map.get("WORKFLOW.ISVALID");
		System.out.println("Validating Workflow isValid Option. [isValid=" + isValid + "]");
		if(!isValid.equals("YES")) {
			errorList.add("Workflow [" + map.get("WORKFLOW.NAME") + "] is not valid. Please validate the Workflow to find out the issue");
		}
	}
	
	public void validateIntegrationService(ArrayList<String> errorList) {
		String integrationService=map.get("WORKFLOW.SERVERNAME");
		if(integrationService.contains("IS_ENT")) {
			errorList.add("Workflow [" + map.get("WORKFLOW.NAME") + "] Integration is selected as IS_ENT* Please Verify if there is a special requirement to run the workflow in ENT Integration Service");
		}
	}
	
	
	public void validateWorkflowBackwardCompatible(ArrayList<String> errorList) {
		String WORKFLOW_BACKWARD_COMPATIBLE=map.get("WORKFLOW_ATTRIBUTE.Write Backward Compatible Workflow Log File");
		System.out.println("Validating Workflow Backward Compatible Option. [Backward Compatible=" + WORKFLOW_BACKWARD_COMPATIBLE + "]");
		if(!WORKFLOW_BACKWARD_COMPATIBLE.equals("YES")) {
			errorList.add("Backward Compatible is not enabled in the Workflow - [" + map.get("WORKFLOW.NAME") + "]");
		}		
	}
	
	public void validateWorkflowLog(ArrayList<String> errorList) {
		String logName=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Name");
		System.out.println("Validating Workflow log Name. [Workflow Log Name=" + logName + "]");
		if(!logName.equals(map.get("WORKFLOW.NAME")+".log")) {
			errorList.add("Workflow log name should be same as Workflow name [" + map.get("Workflow.NAME") + "].");
		}
	}
	
	public void validateWorkflowLogDirectory(ArrayList<String> errorList, String folderName) {
		String logDirectory=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Directory");
		logDirectory=logDirectory.replace("$PMWorkflowLogDir", "/infadata/Logs");
		System.out.println("Validating workflow log directory. [workflow log directory=" + logDirectory + "]");
		if(!logDirectory.equals("/infadata/Logs/" + folderName + "/WkfLogs/")) {
			errorList.add("Workflow [" + map.get("WORKFLOW.NAME") + "] Logs are not pointing to Project Folder. Workflow Logs should be written under /infadata/Logs/" + folderName + "/WkfLogs/");
		}
	}
	
	@Override
	public void validate(ArrayList<String> infoList,ArrayList<String> errorList, ArrayList<String> warningList,String folderName) {
		validateWorkflowName(errorList);
		isWorkflowValid(errorList);
		validateIntegrationService(errorList);
		validateWorkflowBackwardCompatible(errorList);
		validateWorkflowLog(errorList);
		validateWorkflowLogDirectory(errorList,folderName);
	}
	
	public String getWorkflowName() {
		return map.get("WORKFLOW.NAME");
	}
	
}
