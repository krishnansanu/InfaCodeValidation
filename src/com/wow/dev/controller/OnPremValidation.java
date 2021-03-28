package com.wow.dev.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;
import com.wow.dev.infanodes.Aggregator;
import com.wow.dev.infanodes.Expression;
import com.wow.dev.infanodes.Filter;
import com.wow.dev.infanodes.CustomTransformation;
import com.wow.dev.infanodes.Joiner;
import com.wow.dev.infanodes.Lookup;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Normalizer;
import com.wow.dev.infanodes.Router;
import com.wow.dev.infanodes.SequenceGen;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.SessionTaskInstance;
import com.wow.dev.infanodes.Sorter;
import com.wow.dev.infanodes.SourceQualifier;
import com.wow.dev.infanodes.StoredProcedure;
import com.wow.dev.infanodes.Target;
import com.wow.dev.infanodes.TransactionControl;
import com.wow.dev.infanodes.Transformation;
import com.wow.dev.infanodes.UpdateStrategy;
import com.wow.dev.infanodes.Workflow;

public class OnPremValidation {
	
	private Workflow[] workflows;
	private Session[] sessions;
	private SessionTaskInstance[] taskInstances;
	private Mapping mappings[];
	private Target targets[];
	private Transformation[] transformations;
	private Map<String,String> validationList;
	private String repositoryName;
	private String workflowName;
	private String folderName;
	
	
	// Validation of Results
	private int TOTAL_TEST_CASE_COUNT;
	private int PASS_CASE_COUNT;
	private int FAIL_CASE_COUNT;
	private int WARNING_CASE_COUNT;
	
	public OnPremValidation(String repositoryName,String workflowName,String folderName,Map<String,String> validationList) {
		this.validationList=validationList;
		this.repositoryName=repositoryName;
		this.folderName=folderName;
		this.workflowName=workflowName;
	}
	
	public void validateWorkflow() {

		// Reading XML and extracting NodeDetails from XML file.
		ExtractXMLDetails xmlDetails = new ExtractXMLDetails(workflowName+".xml");
		
		
		Map<String, String> workflowVariables[]=xmlDetails.extractDetailsToMap("WORKFLOWVARIABLE");
		
		
		// Creation of Workflow Object for Validation
		Map<String, String> workflowObject[]=xmlDetails.extractDetailsToMap("WORKFLOW");
		workflows=new Workflow[workflowObject.length];
		for(int i=0;i<workflowObject.length;i++) {
			workflows[i] = new Workflow(workflowObject[i], folderName,workflowVariables);
			workflows[i].validate(validationList,i);
			validateTestCase(workflows[i].getValidationResults());
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
			validateTestCase(sessions[i].getValidationResults());
		}
		
		
		Map<String, String> mappingObject[]=xmlDetails.extractDetailsToMap("MAPPING");
		mappings=new Mapping[mappingObject.length];
		for(int i=0;i<mappings.length;i++) {
			mappings[i]=new Mapping(mappingObject[i], folderName);
			mappings[i].validate(validationList,i);
			validateTestCase(mappings[i].getValidationResults());
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
				case "Source Qualifier": transformations[i]=new SourceQualifier(trans,"Source Qualifier");break;
				case "Update Strategy":transformations[i]=new UpdateStrategy(trans,"Update Strategy");break;
				case "Sequence":transformations[i]=new SequenceGen(trans,"Sequence");break;
				case "Filter":transformations[i]=new Filter(trans,"Filter");break;
				case "Joiner":transformations[i]=new Joiner(trans,"Joiner",folderName);break;
				case "Transaction Control":transformations[i]=new TransactionControl(trans,"Transaction Control");break;
				case "Custom Transformation":transformations[i]=new CustomTransformation(trans,"Custom Transformation");break;
				case "Stored Procedure":transformations[i]=new StoredProcedure(trans,"Stored Procedure");break;
				case "Normalizer":transformations[i]=new Normalizer(trans,"Normalizer");break;
				case "Router":transformations[i]=new Router(trans,"Router");break;
			}
			
			if(transformations[i]!=null) {
				transformations[i].validate(validationList, i);
				validateTestCase(transformations[i].getValidationResults());
			}
		}
		
		
		Map<String, String> instanceObject[]=xmlDetails.extractDetailsToMap("INSTANCE");
		
		Map<String, String> targetObject[]=xmlDetails.extractDetailsToMap("TARGET");
		targets=new Target[targetObject.length];
		for(int i=0;i<targets.length;i++) {
			targets[i]=new Target(targetObject[i], folderName,instanceObject);
			targets[i].validate(validationList,i);
			validateTestCase(targets[i].getValidationResults());
		}
	}
	
	
	
	public void validateTestCase(Map<String, String> validationResults) {
		
		Iterator<String> it=validationResults.values().iterator();
		
		while(it.hasNext()) {
			String value=it.next();
			if(value.equalsIgnoreCase("FAIL")) {
				FAIL_CASE_COUNT++;
			}else if(value.equalsIgnoreCase("WARNING")) {
				WARNING_CASE_COUNT++;
			}else {
				PASS_CASE_COUNT++;
			}
			
			TOTAL_TEST_CASE_COUNT++;
		}
	}
	
	public void createTestSummaryReport() {

		try {
			File report=new File("Informatica_code_review_test_summary_report.txt");
			if(!report.exists()) {report.createNewFile();}
			String testSummary=workflowName + " - [TOTAL TEST CASE : " + TOTAL_TEST_CASE_COUNT + ", PASS : " + PASS_CASE_COUNT + ", FAIL : " + FAIL_CASE_COUNT + ", WARNING : " + WARNING_CASE_COUNT + "]\n";
		    Files.write(Paths.get("Informatica_code_review_test_summary_report.txt"), testSummary.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			System.out.println(e.getMessage());
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
	
	public Target[] getTargets() {
		return targets;
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

	public int getTOTAL_TEST_CASE_COUNT() {
		return TOTAL_TEST_CASE_COUNT;
	}

	public int getPASS_CASE_COUNT() {
		return PASS_CASE_COUNT;
	}

	public int getFAIL_CASE_COUNT() {
		return FAIL_CASE_COUNT;
	}

	public int getWARNING_CASE_COUNT() {
		return WARNING_CASE_COUNT;
	}
	
	
}
