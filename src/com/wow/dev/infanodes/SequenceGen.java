package com.wow.dev.infanodes;

import java.util.Map;

public class SequenceGen extends Transformation{
	
	private String transformationName;

	public SequenceGen(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"SEQ");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("SEQUENCE_GEN_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("SEQUENCE_GEN_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
