package com.wow.dev.infanodes;

import java.util.Map;

public class Expression extends Transformation{
	
	private String transformationName;
	private String tracingLevel;
	private String complexLogic;
	private String hardCodedValues;

	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String portNameValidation;
	private String complexLogicValiation;
	private String hardCodedValuesValiation;
	
	public Expression(Map<String, String> map,String transformationType) {
		super(map,transformationType);
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,"EXP_");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		this.tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
		
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		portNameValidation=validatePortName(validationList,i)?"PASS":"WARNING";
	}
	
	
	public String getTransformationName() {
		return transformationName;
	}

	public String getTransformationType() {
		return transformationType;
	}

	public String getTracingLevel() {
		return tracingLevel;
	}

	public String getComplexLogic() {
		return complexLogic;
	}

	public String getHardCodedValues() {
		return hardCodedValues;
	}

	public String getTransformationNameValidation() {
		return transformationNameValidation;
	}

	public String getTracingLevelValidation() {
		return tracingLevelValidation;
	}

	public String getComplexLogicValiation() {
		return complexLogicValiation;
	}

	public String getHardCodedValuesValiation() {
		return hardCodedValuesValiation;
	}

	public String getPortnameValidation() {
		return portNameValidation;
	}
	
	
	

}
