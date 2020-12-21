package com.wow.dev.controller;

import java.util.Map;

import com.wow.dev.infanodes.Aggregator;
import com.wow.dev.infanodes.Expression;
import com.wow.dev.infanodes.Lookup;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.SessionTaskInstance;
import com.wow.dev.infanodes.Sorter;
import com.wow.dev.infanodes.Transformation;
import com.wow.dev.infanodes.Workflow;

public class OnPremValidation {
	
	private Workflow[] workflows;
	private Session[] sessions;
	private SessionTaskInstance[] taskInstances;
	private Mapping mappings[];
	private Transformation[] transformations;
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
			workflows[i].validate(validationList,i);
		}
		
		
		Map<String, String> sessionTaskInstancesObject[]=xmlDetails.extractDetailsToMap("TASKINSTANCE");
		taskInstances=new SessionTaskInstance[sessionTaskInstancesObject.length];
		for(int i=0;i<sessionTaskInstancesObject.length;i++) {
			taskInstances[i]=new SessionTaskInstance(sessionTaskInstancesObject[i]);
		}
		
		
		// Creation of Session Object for Validation
		Map<String, String> sessionObject[]=xmlDetails.extractDetailsToMap("SESSION");
		sessions= new Session[sessionObject.length];
		for(int i=0;i<sessionObject.length;i++) {
			sessions[i] = new Session(sessionObject[i], folderName,taskInstances);
			sessions[i].validate(validationList,i);
		}
		
		
		Map<String, String> mappingObject[]=xmlDetails.extractDetailsToMap("MAPPING");
		mappings=new Mapping[mappingObject.length];
		for(int i=0;i<mappings.length;i++) {
			mappings[i]=new Mapping(mappingObject[i], folderName);
			mappings[i].validate(validationList,i);
		}
		
		Map<String, String> transformationObject[]=xmlDetails.extractDetailsToMap("TRANSFORMATION");
		transformations=new Transformation[transformationObject.length];
		for(int i=0;i<transformations.length;i++) {
			Map<String, String> trans=transformationObject[i];			
			String transType=trans.get("TRANSFORMATION.TYPE");
			switch(transType){
				case "Expression": transformations[i]=new Expression(trans,"Expression");break;
				case "Lookup Procedure": transformations[i]=new Lookup(trans,"Lookup",folderName);break;
				case "Aggregator": transformations[i]=new Aggregator(trans,"Aggregator",folderName);break;
				case "Sorter": transformations[i]=new Sorter(trans,"Sorter",folderName);break;
			}
			
			if(transformations[i]!=null) transformations[i].validate(validationList, i);
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
	
	public Transformation[] getTransformation() {
		return transformations;
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
