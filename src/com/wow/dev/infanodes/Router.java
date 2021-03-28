package com.wow.dev.infanodes;

import java.util.Map;

public class Router extends Transformation{

	private String transformationName;
	
	public Router(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"RTR");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("ROUTER_NAME_VALIDATION",validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("ROUTER_TRACING_LEVEL_VALIDATION",validatetracingLevel(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}
	
}
