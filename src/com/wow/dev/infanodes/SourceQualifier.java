package com.wow.dev.infanodes;

import java.util.Map;

public class SourceQualifier extends Transformation{
	
	private String transformationName;
	
	public SourceQualifier(Map<String, String> map,String transformationType) {
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
	
	public boolean validatePortName(Map<String, String> validationList, int i) {
		return super.validatePortName(transformationName, validationList, i);
		
	}
	
	public boolean validateSourceSQLQuery(Map<String, String> validationList, int i) {
		String sourceSQLQuery=map.get("TRANSFORMATION_TABLEATTRIBUTE.Sql Query");
		if (sourceSQLQuery!="") {
			validationList.put(i+"_"+transformationName+"Source Qualifier Source Override Query","SQ ["+transformationName+"] Override Query is detected");
			return false;
		}else { 
			return true;
		}
	}
	
	public String validateSourceUserDefinedJoin() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.User Defined Join");
	}
	
	public String validateSourceFilter() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Source Filter");
	}
	
	public String validateSourceSortedPorts() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Number Of Sorted Ports");
	}
	
	public String validateSourceIsDistinct() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Select Distinct");
	}
	
	public String validateSourceIsPartionable() {
		return map.get("TRANSFORMATION_TABLEATTRIBUTE.Is Partitionable");
	}
	
	public boolean validateSourcePRESQLQuery(Map<String, String> validationList, int i) {
		String sourceSQLQuery=map.get("TRANSFORMATION_TABLEATTRIBUTE.Pre SQL");
		if (sourceSQLQuery!="") {
			validationList.put(i+"_"+transformationName+"Source Qualifier PRE SQL Query","SQ ["+transformationName+"] PRE SQL Query is detected");
			return false;
		}else { 
			return true;
		}
	}
	
	public boolean validateSourcePOSTSQLQuery(Map<String, String> validationList, int i) {
		String sourceSQLQuery=map.get("TRANSFORMATION_TABLEATTRIBUTE.Post SQL");
		if (sourceSQLQuery!="") {
			validationList.put(i+"_"+transformationName+"Source Qualifier POST SQL Query","SQ ["+transformationName+"] POST SQL Query is detected");
			return false;
		}else { 
			return true;
		}
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		// TODO Auto-generated method stub
		transformationValidationResults.put("SOURCE_QUALIFIER_NAME_VALIDATION", validateTransforamtionName(validationList,i)?"PASS":"FAIL");
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("SOURCE_QUALIFIER_TRACING_LEVEL_VALIDATION", validatetracingLevel(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("SOURCE_QUALIFIER_PORT_NAME_VALIDATION", validatePortName(validationList,i)?"PASS":"FAIL");
		transformationValidationResults.put("SOURCE_QUALIFIER_SQL_QUERY_VALIDATION", validateSourceSQLQuery(validationList,i)?"PASS":"WARNING");
		transformationValidationResults.put("SOURCE_QUALIFIER_USER_DEFINED_JOINS_VALIDATION", (validateSourceUserDefinedJoin()!="")?"YES":"NO");
		transformationValidationResults.put("SOURCE_QUALIFIER_FILTER_VALIDATION", (validateSourceFilter()!="")?"YES":"NO");
		transformationValidationResults.put("SOURCE_QUALIFIER_IS_SORTED_INPUTS_VALIDATION", validateSourceSortedPorts());
		transformationValidationResults.put("SOURCE_QUALIFIER_IS_DISTINCT_VALIDATION", validateSourceIsDistinct());
		transformationValidationResults.put("SOURCE_QUALIFIER_IS_PARTIONABLE_VALIDATION", validateSourceIsPartionable());
		transformationValidationResults.put("SOURCE_QUALIFIER_PRE_SQL_QUERY_VALIDATION", validateSourcePRESQLQuery(validationList,i)?"PASS":"WARNING");
		transformationValidationResults.put("SOURCE_QUALIFIER_POST_SQL_QUERY_VALIDATION", validateSourcePOSTSQLQuery(validationList,i)?"PASS":"WARNING");
	}


	public String getTransformationName() {
		return transformationName;
	}

}
