package com.wow.dev.infanodes;

import java.util.Map;

public class ApplicationSourceQualifier extends Transformation{
	
	private String transformationName;
	
	public ApplicationSourceQualifier(Map<String, String> map,String transformationType) {
		super(map,transformationType);
	}
	
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,2,"SQ");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public String validateSourceUserDefinedJoin() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Join Override");
	}
	
	public String validateSourceFilter() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Filter");
	}
	
	public String validateStaticFilter() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Static filter");
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		// TODO Auto-generated method stub
		transformationValidationResults.put("SOURCE_QUALIFIER_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("SOURCE_QUALIFIER_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("SOURCE_QUALIFIER_USER_DEFINED_JOINS_VALIDATION", (validateSourceUserDefinedJoin()!="")?"YES":"NO");
		transformationValidationResults.put("SOURCE_QUALIFIER_FILTER_VALIDATION", (validateSourceFilter()!="")?"YES":"NO");
		transformationValidationResults.put("SOURCE_QUALIFIER_STATIC_FILTER_VALIDATION", (validateStaticFilter()!="")?"YES":"NO");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
