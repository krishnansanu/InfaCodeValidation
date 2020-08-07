package com.wow.dev.controller;

import java.util.Map;

import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.Workflow;

public class OnPremValidation {
	
	private Workflow[] workflows;
	private Session[] sessions;
	private Mapping mappings[];
	private Map<String,String> validationList;
	private String repositoryName;
	private String workflowName;
	private String folderName;
	
	public OnPremValidation(String repositoryName,String workflowName,String folderName,Map<String,String> validationList) {
		this.validationList=validationList;
		this.repositoryName=repositoryName;
		this.folderName=folderName;
		this.workflowName=workflowName;
	}
	
	public void validateWorkflow() {

		// Reading XML and extracting NodeDetails from XML file.
		ExtractXMLDetails xmlDetails = new ExtractXMLDetails(workflowName+".xml");
		
		
		// Creation of Workflow Object for Validation
		Map<String, String> workflowObject[]=xmlDetails.extractDetailsToMap("WORKFLOW");
		workflows=new Workflow[workflowObject.length];
		for(int i=0;i<workflowObject.length;i++) {
			workflows[i] = new Workflow(workflowObject[i], folderName);
			workflows[i].validate(validationList);
		}
		
		// Creation of Session Object for Validation
		Map<String, String> sessionObject[]=xmlDetails.extractDetailsToMap("SESSION");
		sessions= new Session[sessionObject.length];
		for(int i=0;i<sessionObject.length;i++) {
			sessions[i] = new Session(sessionObject[i], folderName);
			sessions[i].validate(validationList);
		}
		
		Map<String, String> mappingObject[]=xmlDetails.extractDetailsToMap("MAPPING");
		mappings=new Mapping[mappingObject.length];
		for(int i=0;i<mappings.length;i++) {
			mappings[i]=new Mapping(mappingObject[i], folderName);
			mappings[i].validate(validationList);
		}
	}

	public Workflow[] getWorkflows() {
		return workflows;
	}

	public Session[] getSessions() {
		return sessions;
	}

	public Mapping[] getMappings() {
		return mappings;
	}

	public Map<String, String> getValidationList() {
		return validationList;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public String getFolderName() {
		return folderName;
	}
	
}
