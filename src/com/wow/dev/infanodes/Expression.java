package com.wow.dev.infanodes;

import java.util.Map;

public class Expression extends Transformation{
	
	private String transformationName;
	
	public Expression(Map<String, String> map,String transformationType) {
		super(map,transformationType);
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"EXP");
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
		
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("EXPRESSION_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("EXPRESSION_TRACING_LEVEL_VALIDATION",validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("EXPRESSION_PORT_NAME_VALIDATION",validatePortName(validationList,i)?"PASS":"WARNING");
	}
	
	
	public String getTransformationName() {
		return transformationName;
	}

	public String getTransformationType() {
		return transformationType;
	}

}
