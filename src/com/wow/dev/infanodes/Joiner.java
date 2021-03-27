package com.wow.dev.infanodes;

import java.util.Map;

public class Joiner extends Transformation{

	private String transformationName;
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String joinConditionValidation;
	private String joinType;
	private String dataCacheSize;
	private String indexCacheSize;
	private String isSortedInputs;
	private String masterSortOrder;
	private String cacheDirectoryValidation;
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
	
	public boolean validateFilterConditionExpression(Map<String, String> validationList, int i) {
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
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		super.trace(transformationType, transformationName);
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		joinConditionValidation=validateFilterConditionExpression(validationList,i)?"PASS":"FAIL";
		joinType=extractJoinType();
		dataCacheSize=extractDataCacheSize();
		indexCacheSize=extractIndexCacheSize();
		isSortedInputs=isSortedInputs();
		masterSortOrder=extractMasterSortOrder();
		cacheDirectoryValidation=validateCacheDirectoryName(validationList,i)?"PASS":"WARNING";
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

	public String getJoinConditionValidation() {
		return joinConditionValidation;
	}

	public String getJoinType() {
		return joinType;
	}

	public String getDataCacheSize() {
		return dataCacheSize;
	}

	public String getIndexCacheSize() {
		return indexCacheSize;
	}

	public String getIsSortedInputs() {
		return isSortedInputs;
	}

	public String getMasterSortOrder() {
		return masterSortOrder;
	}

	public String getCacheDirectoryValidation() {
		return cacheDirectoryValidation;
	}
	
}
