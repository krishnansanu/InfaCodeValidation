package com.wow.dev.infanodes;

import java.util.Map;

public class Aggregator extends Transformation{

	private String folderName;
	private String transformationName;
	
	
	public Aggregator(Map<String, String> map, String transformationType,String folderName) {
		super(map, transformationType);
		this.folderName=folderName;
	}
	

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"AGG");
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
	}
	
	public boolean validateLookupCacheDirectoryName(Map<String,String> validationList,int i) {
		String cacheDirectory=map.get("TRANSFORMATION_TABLEATTRIBUTE.Cache Directory");
		if(!(cacheDirectory.contains(folderName))) {
			validationList.put(i+"_"+transformationName+"Aggregator Cache directory name","Aggregator ["+transformationName+"] Cache directory ["+cacheDirectory+"]is invalid");
			return false;
		}
		return true;
	}
	
	public String validateIsSoretedInput() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Sorted Input");
	}
	
	
	public String validateDataCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Aggregator Data Cache Size");
	}
	
	public String validateIndexCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Aggregator Index Cache Size");
	}
	

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("AGGREGATOR_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("AGGREGATOR_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("AGGREGATOR_PORT_NAME_VALIDATION", validatePortName(validationList,i)?"PASS":"WARNING");
		transformationValidationResults.put("AGGREGATOR_CACHE_DIR_VALIDATION", validateLookupCacheDirectoryName(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("AGGREGATOR_DATA_CACHE_SIZE_VALIDATION", validateDataCacheSize());
		transformationValidationResults.put("AGGREGATOR_INDEX_CACHE_SIZE_VALIDATION", validateIndexCacheSize());
		transformationValidationResults.put("AGGREGATOR_IS_SORTED_INPUT_VALIDATION", validateIsSoretedInput());
	}

	public String getFolderName() {
		return folderName;
	}


	public String getTransformationName() {
		return transformationName;
	}
}
