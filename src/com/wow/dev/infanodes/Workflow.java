package com.wow.dev.infanodes;

import java.util.Map;
import java.util.Set;

public class Workflow{
	
	private Map<String, String> map;
	private String workflowName;
	private String folderName;
	private String workflowNameValidation;
	private String workflowIsValidValidation;
	private String workflowIntegrationServiceValidation;
	private String WORKFLOW_BACKWARD_COMPATIBLEValidaiton;
	private String workflowLogNameValidation;
	private String workflowlogDirectoryValidation;
	private String workflowVariablesValidation;
	private Map<String, String> workflowVariables[];
	private String paramFileName;
	private String paramFileValidation;
	private String paramFileDirectoryValidation;
	
	
	
	public Workflow(Map<String,String> map,String folderName,Map<String,String> workflowVariables[]) {
		this.map=map;
		this.folderName=folderName;
		this.workflowVariables=workflowVariables;
	}
	
	public boolean validateWorkflowName(Map<String,String> validationList,int i) {
		workflowName=map.get("WORKFLOW.NAME");
		
		if(!workflowName.substring(0, 4).contentEquals("wkf_")) {
			validationList.put(i+"_WORKFLOW.NAME","Invalid Start of Workflow Name ["+ workflowName +"]. Workflow Name Should Start with 'wkf_'");
			return false;
		}
		return true;
	}
	
	public boolean isWorkflowValid(Map<String,String> validationList,int i) {
		String isValid=map.get("WORKFLOW.ISVALID");
		if(!isValid.equals("YES")) {
			validationList.put(i+"_WORKFLOW.ISVALID","Workflow [" + map.get("WORKFLOW.NAME") + "] is not valid. Please validate the Workflow to find out the issue");
			return false;
		}
		return true;
	}
	
	public boolean validateIntegrationService(Map<String,String> validationList,int i) {
		String integrationService=map.get("WORKFLOW.SERVERNAME");
		if(integrationService != null && integrationService.contains("IS_ENT")) {
			validationList.put(i+"_WORKFLOW.INTEGRATIONSERVERNAME","Workflow [" + map.get("WORKFLOW.NAME") + "] Integration is selected as IS_ENT* Please Verify if there is a special requirement to run the workflow in ENT Integration Service");
			return false;
		}
		return true;
	}
	
	
	public boolean validateWorkflowBackwardCompatible(Map<String,String> validationList,int i) {
		String WORKFLOW_BACKWARD_COMPATIBLE=map.get("WORKFLOW_ATTRIBUTE.Write Backward Compatible Workflow Log File");
		if(!WORKFLOW_BACKWARD_COMPATIBLE.equals("YES")) {
			validationList.put(i+"_WORKFLOW_ATTRIBUTE.Write Backward Compatible Workflow Log File","Backward Compatible is not enabled in the Workflow - [" + map.get("WORKFLOW.NAME") + "]");
			return false;
		}		
		return true;
	}
	
	public boolean validateWorkflowLog(Map<String,String> validationList,int i) {
		String logName=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Name");
		if(!logName.equals(map.get("WORKFLOW.NAME")+".log")) {
			validationList.put(i+"_WORKFLOW_ATTRIBUTE.Workflow Log File Name","Workflow log name should be same as Workflow name [" + map.get("Workflow.NAME") + "].");
			return false;
		}
		return true;
	}
	
	public boolean validateWorkflowLogDirectory(Map<String,String> validationList,int i) {
		String logDirectory=map.get("WORKFLOW_ATTRIBUTE.Workflow Log File Directory");
		if(!logDirectory.contains(folderName)) {
			validationList.put(i+"_WORKFLOW_ATTRIBUTE.Workflow Log File Directory", "Workflow [" + map.get("WORKFLOW.NAME") + "] Log directory ["+ logDirectory +"]is invalid.  ");
			return false;
		}
		return true;
	}
	
	public String validateWorkflowVariables() {
		String TMP_WKF_VAR="N/A";
		for(Map<String,String> wkfVariables:workflowVariables) {
			String WKFNAME=wkfVariables.get("WORKFLOWVARIABLE.NAME");
			if(wkfVariables.get("WORKFLOWVARIABLE.USERDEFINED").contentEquals("YES")) {
				if(TMP_WKF_VAR.contentEquals("N/A")) TMP_WKF_VAR=WKFNAME; else TMP_WKF_VAR+=", "+WKFNAME;
			}
		}
		return TMP_WKF_VAR;
	}
	
	
	
	
	public boolean validateWorkflowParameterFile() {
		this.paramFileName=map.get("WORKFLOW_ATTRIBUTE.Parameter Filename");
		return (paramFileName!="")?true:false;
	}
	
	public boolean validateWorkflowParameterFileDirectory(Map<String,String> validationList,int i) {
		
		if(!paramFileName.contains(folderName)) {
			validationList.put(i+"_WORKFLOW_ATTRIBUTE.Workflow Param File Directory", "Workflow [" + map.get("WORKFLOW.NAME") + "] Param File directory ["+ paramFileName +"] must be pointing to "+folderName+".  ");
			return false;
		}
		return true;
	}
	
	
	
	public void validate(Map<String,String> validationList,int i) {
		workflowNameValidation=validateWorkflowName(validationList,i)?"PASS":"FAIL";
		System.out.println("Validating Workflow - " + workflowName);
		workflowIsValidValidation=isWorkflowValid(validationList,i)?"PASS":"FAIL";
		workflowIntegrationServiceValidation=validateIntegrationService(validationList,i)?"PASS":"WARNING";
		WORKFLOW_BACKWARD_COMPATIBLEValidaiton=validateWorkflowBackwardCompatible(validationList,i)?"PASS":"FAIL";
		workflowLogNameValidation=validateWorkflowLog(validationList,i)?"PASS":"FAIL";
		workflowlogDirectoryValidation=validateWorkflowLogDirectory(validationList,i)?"PASS":"WARNING";
		workflowVariablesValidation=validateWorkflowVariables();
		paramFileValidation=validateWorkflowParameterFile()?"Detected":"Not Detected";
		paramFileDirectoryValidation=(paramFileName!="")?(validateWorkflowParameterFileDirectory(validationList,i)?"PASS":"WARNING"):"N/A";
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public String getWorkflowName() {
		return workflowName;
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

	public String getWorkflowVariablesValidation() {
		return workflowVariablesValidation;
	}

	public String getParamFileName() {
		return paramFileName;
	}

	public String getParamFileValidation() {
		return paramFileValidation;
	}

	public String getParamFileDirectoryValidation() {
		return paramFileDirectoryValidation;
	}
	
}
