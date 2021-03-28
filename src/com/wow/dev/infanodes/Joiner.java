package com.wow.dev.infanodes;

import java.util.Map;

public class Joiner extends Transformation{

	private String transformationName;
	private String folderName;
	
	public Joiner(Map<String, String> map, String transformationType,String folderName) {
		super(map, transformationType);
		this.folderName=folderName;
	}

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"JNR");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validateJoinConditionExpression(Map<String, String> validationList, int i) {
		String joinConditionExpression=map.get("TRANSFORMATION_TABLEATTRIBUTE.Join Condition");
		if (joinConditionExpression.isEmpty()) {
			validationList.put(i+"_"+transformationName+"Join Condition Check","Joiner ["+transformationName+"] Join Condition is empty");
			return false;
		}else {
			return true;
		}
	}
	
	public String extractJoinType() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Join Type");
	}
	
	public String extractDataCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Joiner Data Cache Size");
	}
	
	public String extractIndexCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Joiner Index Cache Size");
	}
	
	public String isSortedInputs() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Sorted Input");
	}
	
	public String extractMasterSortOrder() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Master Sort Order");
	}
	
	public boolean validateCacheDirectoryName(Map<String, String> validationList, int i) {
		String cahceDirectory=map.get("TRANSFORMATION_TABLEATTRIBUTE.Cache Directory");
		if(!(cahceDirectory.contains(folderName))) {
			validationList.put(i+"_"+transformationName+"Joiner Cache directory name","Joiner ["+transformationName+"] Cache directory ["+ cahceDirectory +"]is invalid");
			return false;
		}
		return true;
	}
	
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationValidationResults.put("JOINER_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("JOINER_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("JOINER_CONDITION_VALIDATION", validateJoinConditionExpression(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("JOINER_TYPE_VALIDATION", extractJoinType());
		transformationValidationResults.put("JOINER_DATA_CACHE_SIZE_VALIDATION", extractDataCacheSize());
		transformationValidationResults.put("JOINER_INDEX_CACHE_SIZE_VALIDATION", extractIndexCacheSize());
		transformationValidationResults.put("JOINER_IS_SORTED_INPUTS_VALIDATION", isSortedInputs());
		transformationValidationResults.put("JOINER_MASTER_SORT_ORDER_VALIDATION", extractMasterSortOrder());
		transformationValidationResults.put("JOINER_CACHE_DIR_VALIDATION", validateCacheDirectoryName(validationList,i)?"PASS":"WARNING");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
