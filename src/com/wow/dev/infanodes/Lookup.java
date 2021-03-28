package com.wow.dev.infanodes;

import java.util.Map;

public class Lookup extends Transformation{

	private String folderName;
	private String transformationName;
	
	
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
		if(!(lookupCacheDirectory.contains(folderName))) {
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
		transformationValidationResults.put("LOOKUP_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("LOOKUP_TRACING_LEVEL_VALIDATION",validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("LOOKUP_PORT_NAME_VALIDATION",validatePortName(validationList,i)?"PASS":"WARNING");
		transformationValidationResults.put("LOOKUP_OVERRIDE_QUERY_VALIDATION",validateOverrideQuery(validationList, i)?"PASS":"WARNING");
		transformationValidationResults.put("LOOKUP_IS_CACHE_ENABLED_VALIDATION",validateIsLookupCacheEnabled());
		transformationValidationResults.put("LOOKUP_POLICY_ON_MULTIPLE_MATCH_VALIDATION",validateLookupPolicyOnMultipleMatch());
		transformationValidationResults.put("LOOKUP_CACHE_DIR_VALIDATION",validateLookupCacheDirectoryName(validationList,i)?"PASS":"WARNING");
		transformationValidationResults.put("LOOKUP_IS_PERSISTANT_CACHE_VALIDATION",validateIsPersistantCache());
		transformationValidationResults.put("LOOKUP_DATA_CACHE_SIZE_VALIDATION",validateDataCacheSize());
		transformationValidationResults.put("LOOKUP_INDEX_CACHE_VALIDATION",validateIndexCacheSize());
		transformationValidationResults.put("LOOKUP_IS_DYNAMIC_VALIDATION",validateIsDynamicLookupCache());
		transformationValidationResults.put("LOOKUP_IS_SORTED_INPUT_VALIDATION",validateIsSoretedInput());
		transformationValidationResults.put("LOOKUP_CONDITION_VALIDATION",validateLookupCondition(validationList,i)?"PASS":"WARNING");
	}

	
	public String getTransformationName() {
		return transformationName;
	}

}
