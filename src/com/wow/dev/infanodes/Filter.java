package com.wow.dev.infanodes;

import java.util.Map;

public class Filter extends Transformation{
	
	private String transformationName;
	
	public Filter(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"FIL");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validateFilterConditionExpression(Map<String, String> validationList, int i) {
		String updateStrategyExpression=map.get("TRANSFORMATION_TABLEATTRIBUTE.Filter Condition");
		if (updateStrategyExpression.isEmpty()) {
			validationList.put(i+"_"+transformationName+"Filter Condition Check","Filter ["+transformationName+"] Filter Condition is empty");
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("FILTER_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("FILTER_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("FILTER_CONDITION_VALIDATION", validateFilterConditionExpression(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
