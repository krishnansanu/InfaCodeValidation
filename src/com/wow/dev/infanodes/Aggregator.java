package com.wow.dev.infanodes;

import java.util.Map;

public class Aggregator extends Transformation{

	private String folderName;
	private String transformationName;
	private String tracingLevel;
	private String cacheDirectory;
	private String dataCacheSize;
	private String indexCacheSize;
	private String isSoretedInput;
	
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
		// TODO Auto-generated constructor stub
	}
	

	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,"AGG_");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		this.tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
		
	}
	
	public boolean validateLookupCacheDirectoryName(Map<String,String> validationList,int i) {
		System.out.println("Validating Aggregator Cache Directory Name...");
		cacheDirectory=map.get("TRANSFORMATION_TABLEATTRIBUTE.Cache Directory");
		cacheDirectory.replace("$PMCacheDir","/infadata/Cache/");
		if(!(cacheDirectory.equals("/infadata/Cache/"+folderName) || cacheDirectory.equals("/infadata/Cache/"+folderName+"/"))) {
			validationList.put(i+"_"+transformationName+"Aggregator Cache directory name","Aggregator ["+transformationName+"] Cache directory should be pointing to /infadata/Cache/"+folderName);
			return false;
		}
		return true;
	}
	
	public String validateIsSoretedInput() {
		System.out.println("Validating If Lookup inputs are sorted...");
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Sorted Input");
	}
	
	
	public String validateDataCacheSize() {
		System.out.println("Validating Lookup Cache Data Size...");
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Aggregator Data Cache Size");
	}
	
	public String validateIndexCacheSize() {
		System.out.println("Validating Lookup Cache Index Size...");
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Aggregator Index Cache Size");
	}
	

	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
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


	public String getTracingLevel() {
		return tracingLevel;
	}


	public String getCacheDirectory() {
		return cacheDirectory;
	}


	public String getDataCacheSize() {
		return dataCacheSize;
	}


	public String getIndexCacheSize() {
		return indexCacheSize;
	}


	public String getIsSoretedInput() {
		return isSoretedInput;
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
