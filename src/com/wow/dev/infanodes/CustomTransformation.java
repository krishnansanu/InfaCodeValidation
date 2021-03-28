package com.wow.dev.infanodes;

import java.util.Map;

public class CustomTransformation extends Transformation{
	
	private String transformationName;
	
	public CustomTransformation(Map<String, String> map,String transformationType) {
		super(map,transformationType);
	}
	
	public String extractTransformationName() {
		return map.get("TRANSFORMATION.NAME");
	}
	
	public String extractTemplateName() {
		return map.get("TRANSFORMATION.TEMPLATENAME");
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
		transformationName=extractTransformationName();
		String templateName=extractTemplateName();
		super.trace(templateName, transformationName);
		transformationValidationResults.put("CUSTOM_TRANSFORMATION_TEMPLATE_VALIDATION", templateName);
		transformationValidationResults.put("CUSTOM_TRANSFORMATION_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("CUSTOM_TRANSFORMATION_PORT_NAME_VALIDATION", validatePortName(validationList,i)?"PASS":"WARNING");
	}
	
	public String getTransformationName() {
		return transformationName;
	}

}
