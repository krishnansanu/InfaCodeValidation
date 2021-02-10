package com.wow.dev.infanodes;

import java.util.Map;

public class CustomTransformation extends Transformation{
	
	private String transformationName;
	private String tracingLevelValidation;
	private String portNameValidation;
	private String templateName;
	
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
		templateName=extractTemplateName();
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		portNameValidation=validatePortName(validationList,i)?"PASS":"WARNING";
	}
	
	
	public String getTransformationName() {
		return transformationName;
	}
	
	
	public String getTemplateName() {
		return templateName;
	}

	public String getTracingLevelValidation() {
		return tracingLevelValidation;
	}

	public String getPortnameValidation() {
		return portNameValidation;
	}
}
