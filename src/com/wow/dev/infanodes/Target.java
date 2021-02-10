package com.wow.dev.infanodes;

import java.util.Map;

public class Target extends Transformation{
	private Map<String, String> instanceObject[];
	private String transformationName;
	private String targetType;
	private String updateOverrideValidation;
	private String preSQLValidation;
	private String postSQLValidation;

	public Target(Map<String, String> map, String transformationType,Map<String, String> instanceObject[]) {
		super(map, transformationType);
		this.instanceObject=instanceObject;
	}

	public String extractTargetObjectName() {
		return map.get("TARGET.NAME");
	}
	
	public String extractTargetObjectType() {
		return map.get("TARGET.DATABASETYPE");
	}
	
	public boolean validateTargetUpdateOverride(Map<String, String> validationList, int i) {
		for(int j=0;j<instanceObject.length;j++) {
			
			Map<String, String> instance=instanceObject[j];
			if(instance.get("INSTANCE.TRANSFORMATION_TYPE").contentEquals("Target Definition") && instance.get("INSTANCE.TRANSFORMATION_NAME").contentEquals(map.get("TARGET.NAME"))) {
				String updateOverride=instance.get("INSTANCE_TABLEATTRIBUTE.Update Override");
				if(updateOverride!=null && (!updateOverride.isEmpty())) {
					validationList.put(i+"_"+transformationName+"Target Update Override","Target ["+transformationName+"] update override query is detected." );
					return false;
				}else {
					
					return true;
				}
			}
			
		}
		return true;
	}
		
	public boolean validateTargetPRESQL(Map<String, String> validationList, int i) {
		for(int j=0;j<instanceObject.length;j++) {
			Map<String, String> instance=instanceObject[j];
			if(instance.get("INSTANCE.TRANSFORMATION_TYPE").contentEquals("Target Definition") && instance.get("INSTANCE.TRANSFORMATION_NAME").contentEquals(map.get("TARGET.NAME"))) {
				String preSQL=instance.get("INSTANCE_TABLEATTRIBUTE.Pre SQL");
				if(preSQL!=null && (!preSQL.isEmpty())) {
					validationList.put(i+"_"+transformationName+"Target PRE SQL Query","Target ["+transformationName+"] PRE SQL Query detected" );
					return false;
				}else {
					return true;
				}
			}
			
		}
		return true;
	}
	
	public boolean validateTargetPOSTSQL(Map<String, String> validationList, int i) {
		for(int j=0;j<instanceObject.length;j++) {
			Map<String, String> instance=instanceObject[j];
			if(instance.get("INSTANCE.TRANSFORMATION_TYPE").contentEquals("Target Definition") && instance.get("INSTANCE.TRANSFORMATION_NAME").contentEquals(map.get("TARGET.NAME"))) {
				String postSQL=instance.get("INSTANCE_TABLEATTRIBUTE.Post SQL");
				if(postSQL!=null && (!postSQL.isEmpty())) {
					validationList.put(i+"_"+transformationName+"Target POST SQL Query","Target ["+transformationName+"] POST SQL Query detected" );
					return false;
				}else {
					return true;
				}
			}
			
		}
		return true;
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationName=extractTargetObjectName();
		targetType=extractTargetObjectType();
		updateOverrideValidation=validateTargetUpdateOverride(validationList,i)?"PASS":"WARNING";
		preSQLValidation=validateTargetPRESQL(validationList,i)?"PASS":"WARNING";
		postSQLValidation=validateTargetPOSTSQL(validationList,i)?"PASS":"WARNING";
	}

	public String getTransformationName() {
		return transformationName;
	}

	public String getTargetType() {
		return targetType;
	}

	public String getUpdateOverrideValidation() {
		return updateOverrideValidation;
	}

	public String getPreSQLValidation() {
		return preSQLValidation;
	}

	public String getPostSQLValidation() {
		return postSQLValidation;
	}
	
	
	
	
}
