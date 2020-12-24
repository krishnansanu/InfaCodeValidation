package com.wow.dev.infanodes;

import java.util.Map;

public class SourceQualifier extends Transformation{
	
	private String transformationName;
	private String tracingLevel;
	
	
	private String transformationNameValidation;
	private String tracingLevelValidation;
	private String portNameValidation;
	private String sourceSQLQueryValidation;
	private String sourceUserDefinedJoinValidation;
	private String sourceFilterValidation;
	private String sourceSortedInputValidation;
	private String sourceIsDistinctValidation;
	private String sourceIsPartionableValidation;
	private String sourcePRESQLValidation;
	private String sourcePOSTSQLValidation;
	
	public SourceQualifier(Map<String, String> map,String transformationType) {
		super(map,transformationType);
	}
	
	
	public boolean validateTransforamtionName(Map<String, String> validationList, int i) {
		this.transformationName=map.get("TRANSFORMATION.NAME");
		return super.validateTransforamtionName(transformationName, validationList, i,3,"SQ_");
		
	}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		this.tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
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
		transformationNameValidation=validateTransforamtionName(validationList,i)?"PASS":"FAIL";
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
		portNameValidation=validatePortName(validationList,i)?"PASS":"FAIL";
		sourceSQLQueryValidation=validateSourceSQLQuery(validationList,i)?"PASS":"WARNING";
		sourceUserDefinedJoinValidation=(validateSourceUserDefinedJoin()!="")?"YES":"NO";
		sourceFilterValidation=(validateSourceFilter()!="")?"YES":"NO";
		sourceSortedInputValidation=validateSourceSortedPorts();
		sourceIsDistinctValidation=validateSourceIsDistinct();
		sourceIsPartionableValidation=validateSourceIsPartionable();
		sourcePRESQLValidation=validateSourcePRESQLQuery(validationList,i)?"PASS":"WARNING";
		sourcePOSTSQLValidation=validateSourcePOSTSQLQuery(validationList,i)?"PASS":"WARNING";
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


	public String getSourceSQLQueryValidation() {
		return sourceSQLQueryValidation;
	}


	public String getSourceUserDefinedJoinValidation() {
		return sourceUserDefinedJoinValidation;
	}


	public String getSourceFilterValidation() {
		return sourceFilterValidation;
	}


	public String getSourceSortedInputValidation() {
		return sourceSortedInputValidation;
	}


	public String getSourceIsDistinctValidation() {
		return sourceIsDistinctValidation;
	}


	public String getSourceIsPartionableValidation() {
		return sourceIsPartionableValidation;
	}


	public String getSourcePRESQLValidation() {
		return sourcePRESQLValidation;
	}


	public String getSourcePOSTSQLValidation() {
		return sourcePOSTSQLValidation;
	}


	public String getPortNameValidation() {
		return portNameValidation;
	}
	
	

}
