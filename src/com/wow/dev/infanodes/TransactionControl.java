package com.wow.dev.infanodes;

import java.util.Map;

public class TransactionControl extends Transformation{
	
	private String transformationName;
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String filterConditionValidation;
	
	public TransactionControl(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,2,"TC");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validateFilterConditionExpression(Map<String, String> validationList, int i) {
		String updateStrategyExpression=map.get("TRANSFORMATION_TABLEATTRIBUTE.Transaction Control Condition");
		if (updateStrategyExpression.isEmpty()) {
			validationList.put(i+"_"+transformationName+"Transaction Control Condition Check","Transaction Control ["+transformationName+"] Condition is empty");
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		super.trace(transformationType, transformationName);
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		filterConditionValidation=validateFilterConditionExpression(validationList,i)?"PASS":"FAIL";
	}

	public String getTransformationName() {
		return transformationName;
	}

	public String getTransformationNameValidation() {
		return transformationNameValidation;
	}

	public String getTracingLevelValidation() {
		return tracingLevelValidation;
	}

	public String getFilterConditionValidation() {
		return filterConditionValidation;
	}
	
	
	


}
