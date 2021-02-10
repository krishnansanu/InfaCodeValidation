package com.wow.dev.infanodes;

import java.util.Map;

public class UpdateStrategy extends Transformation{
	
	private String transformationName;
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String updateStrategyExpressionValidation;
	

	public UpdateStrategy(Map<String, String> map, String transformationType) {
		super(map,transformationType);
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,4,"UPD_");
		
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
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		updateStrategyExpressionValidation=validateUpdateStrageyExpression(validationList,i)?"PASS":"FAIL";
		
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

	public String getUpdateStrategyExpressionValidation() {
		return updateStrategyExpressionValidation;
	}
	
	

}
