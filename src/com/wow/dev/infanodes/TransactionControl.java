package com.wow.dev.infanodes;

import java.util.Map;

public class TransactionControl extends Transformation{
	
	private String transformationName;
	
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
		transformationValidationResults.put("TRANSACTION_CONTROL_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("TRANSACTION_CONTROL_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("TRANSACTION_CONTROL_CONDITION_VALIDATION", validateFilterConditionExpression(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
