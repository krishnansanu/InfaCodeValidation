package com.wow.dev.infanodes;

import java.util.Map;

public class Aggregator extends Transformation{

	private String folderName;
	private String transformationName;
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String portNameValidation;
	private String cacheDirectoryValidation;
	private String dataCacheSizeValidation;
	private String indexCacheSizeValidation;
	private String isSoretedInputValidation;
	
	
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
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		super.trace(transformationType, transformationName);
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		portNameValidation=validatePortName(validationList,i)?"PASS":"WARNING";
		cacheDirectoryValidation=validateLookupCacheDirectoryName(validationList,i)?"PASS":"FAIL";
		dataCacheSizeValidation=validateDataCacheSize();
		indexCacheSizeValidation=validateIndexCacheSize();
		isSoretedInputValidation=validateIsSoretedInput();
		
	}


	public String getFolderName() {
		return folderName;
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


	public String getDataCacheSizeValidation() {
		return dataCacheSizeValidation;
	}


	public String getIndexCacheSizeValidation() {
		return indexCacheSizeValidation;
	}


	public String getIsSoretedInputValidation() {
		return isSoretedInputValidation;
	}
	
	

}
