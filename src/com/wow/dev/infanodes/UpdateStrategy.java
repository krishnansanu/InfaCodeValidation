package com.wow.dev.infanodes;

import java.util.Map;

public class UpdateStrategy extends Transformation{
	
	private String transformationName;

	public UpdateStrategy(Map<String, String> map, String transformationType) {
		super(map,transformationType);
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"UPD");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validateUpdateStrageyExpression(Map<String, String> validationList, int i) {
		String updateStrategyExpression=map.get("TRANSFORMATION_TABLEATTRIBUTE.Update Strategy Expression");
		if (updateStrategyExpression.isEmpty()) {
			validationList.put(i+"_"+transformationName+"Update Strategy Expression Condition Check","Update Strategy ["+transformationName+"]  Expression Condition is empty");
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("UPDATE_STRATEGY_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("UPDATE_STRATEGY_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("UPDATE_STRATEGY_EXPRESSION_VALIDATION", validateUpdateStrageyExpression(validationList,i)?"PASS":"FAIL");
		
	}
	

	public String getTransformationName() {
		return transformationName;
	}

}
