package com.wow.dev.infanodes;

import java.util.Map;

public class Sorter extends Transformation{

	private String folderName;
	private String transformationName;
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String portNameValidation;
	private String cacheDirectoryValidation;
	private String cacheSizeValidation;
	private String isDistinctInputValidation;
	
	public Sorter(Map<String, String> map, String transformationType,String folderName) {
		super(map, transformationType);
		this.folderName=folderName;
		// TODO Auto-generated constructor stub
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"SRT");
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
	}
	
	public String validateSorterCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Sorter Cache Size");
	}
	
	public boolean validateCacheDirectoryName(Map<String,String> validationList,int i) {

		String cacheDirectory=map.get("TRANSFORMATION_TABLEATTRIBUTE.Work Directory");
		if(!(cacheDirectory.contains(folderName))) {
			validationList.put(i+"_"+transformationName+"Sorter Cache directory name","Sorter ["+transformationName+"] Cache directory ["+cacheDirectory+"] is invalid");
			return false;
		}
		return true;
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
	}
	
	public String validateIsDistinctInput() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Distinct");
	}

	@Override
	public void validate(Map<String, String> validationList, int i) {
		// TODO Auto-generated method stub
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		super.trace(transformationType, transformationName);
		portNameValidation=validatePortName(validationList,i)?"PASS":"WARNING";
		cacheSizeValidation=validateSorterCacheSize();
		cacheDirectoryValidation=validateCacheDirectoryName(validationList,i)?"PASS":"FAIL";
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		isDistinctInputValidation=validateIsDistinctInput();
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

	public String getPortNameValidation() {
		return portNameValidation;
	}

	public String getCacheDirectoryValidation() {
		return cacheDirectoryValidation;
	}

	public String getCacheSizeValidation() {
		return cacheSizeValidation;
	}

	public String getIsDistinctInputValidation() {
		return isDistinctInputValidation;
	}
	
	

}
