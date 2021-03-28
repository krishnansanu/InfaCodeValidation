package com.wow.dev.infanodes;

import java.util.Map;

public class Normalizer extends Transformation{
	
	private String transformationName;
	
	public Normalizer(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public String extractTransformationName() {
		return map.get("TRANSFORMATION.NAME");
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationName=extractTransformationName();
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("NORMALIZER_TRACING_LEVEL_VALIDATION",validatetracingLevel(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
