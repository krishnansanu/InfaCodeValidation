package com.wow.dev.infanodes;

import java.util.Map;

public class Lookup extends Transformation{

	private String folderName;
	private String transformationName;
	private String transformationNameValidation;
	private String portNameValidation;
	private String lookupSQLOverrideValidation;
	private String isCacheEnabledValidation;
	private String lookupPolicyOnMultipleMatchValidation;
	private String tracingLevelValidation;
	private String lookupCacheDirectoryValidation;
	private String isPersistantCacheValidation;
	private String dataCacheSizeValidation;
	private String indexCacheSizeValidation;
	private String isDynamicLookupValidation;
	private String isSoretedInputValidation;
	private String lookupConditionValidation;
	
	
	public Lookup(Map<String, String> map,String transformationType,String folderName) {
		super(map, transformationType);
		this.folderName=folderName;
	}
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"LKP");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
		
	}
	
	public boolean validateOverrideQuery(Map<String,String> validationList,int i) {
		String lookupSQLOverride=map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup Sql Override");
		if(!lookupSQLOverride.equals("")) {
			validationList.put(i+"_"+transformationName+"LOOKUP.SQL Override","Override Query has been idenified in the Lookup - [" + transformationName + "].");
			return false;
		}
		return true;
	}
	
	public String validateIsLookupCacheEnabled() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup caching enabled");
	}
	
	public String validateLookupPolicyOnMultipleMatch() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup policy on multiple match");
	}
	
	public boolean validateLookupCacheDirectoryName(Map<String,String> validationList,int i) {
		String lookupCacheDirectory=map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup cache directory name");
		if(!(lookupCacheDirectory.contains("/"+folderName))) {
			validationList.put(i+"_"+transformationName+"LOOKUP Cache directory name","Lookup ["+transformationName+"] Cache directory ["+lookupCacheDirectory+"] is invalid");
			return false;
		}
		return true;
	}
	
	public String validateIsPersistantCache() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup cache persistent");
	}
	
	public String validateDataCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup Data Cache Size");
	}
	
	public String validateIndexCacheSize() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup Index Cache Size");
	}
	
	public String validateIsDynamicLookupCache() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Dynamic Lookup Cache");
	}
	
	public String validateIsSoretedInput() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Sorted Input");
	}
	
	public boolean validateLookupCondition(Map<String,String> validationList,int i) {
		String lookupCondition=map.get("TRANSFORMATION_TABLEATTRIBUTE.Lookup condition");
		if(lookupCondition==null || lookupCondition=="") {
			validationList.put(i+"_"+transformationName+"LOOKUP Condition","Lookup ["+transformationName+"] condition should not be null");
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		super.trace(transformationType, transformationName);
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		portNameValidation=validatePortName(validationList,i)?"PASS":"WARNING";
		lookupSQLOverrideValidation=validateOverrideQuery(validationList, i)?"PASS":"WARNING";
		isCacheEnabledValidation=validateIsLookupCacheEnabled();
		lookupPolicyOnMultipleMatchValidation=validateLookupPolicyOnMultipleMatch();
		lookupCacheDirectoryValidation=validateLookupCacheDirectoryName(validationList,i)?"PASS":"WARNING";
		isPersistantCacheValidation=validateIsPersistantCache();
		dataCacheSizeValidation=validateDataCacheSize();
		indexCacheSizeValidation=validateIndexCacheSize();
		isDynamicLookupValidation=validateIsDynamicLookupCache();
		isSoretedInputValidation=validateIsSoretedInput();
		lookupConditionValidation=validateLookupCondition(validationList,i)?"PASS":"WARNING";
	}

	public String getDataCacheSizeValidation() {
		return dataCacheSizeValidation;
	}

	public String getTransformationName() {
		return transformationName;
	}

	public String getTransformationNameValidation() {
		return transformationNameValidation;
	}

	public String getPortNameValidation() {
		return portNameValidation;
	}

	public String getLookupSQLOverrideValidation() {
		return lookupSQLOverrideValidation;
	}

	public String getIsCacheEnabledValidation() {
		return isCacheEnabledValidation;
	}

	public String getLookupPolicyOnMultipleMatchValidation() {
		return lookupPolicyOnMultipleMatchValidation;
	}

	public String getTracingLevelValidation() {
		return tracingLevelValidation;
	}

	public String getLookupCacheDirectoryValidation() {
		return lookupCacheDirectoryValidation;
	}

	public String getIsPersistantCacheValidation() {
		return isPersistantCacheValidation;
	}

	public String getIndexCacheSizeValidation() {
		return indexCacheSizeValidation;
	}

	public String getIsDynamicLookupValidation() {
		return isDynamicLookupValidation;
	}

	public String getIsSoretedInputValidation() {
		return isSoretedInputValidation;
	}

	public String getLookupConditionValidation() {
		return lookupConditionValidation;
	}
	
	

}
