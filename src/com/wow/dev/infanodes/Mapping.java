package com.wow.dev.infanodes;

import java.util.Map;
import java.util.Set;

public class Mapping{
	
	private Map<String, String> map;
	private String mappingName;
	private String mappingNameValidation;
	private String mappingIsValidValidation;
	private int transformationCount;
	private String transformationList;
	private int mappingVariableCount;
	private String mappingVariableNameValidation;
	private String mappingSQLQueryValidation;
	
	
	public Mapping(Map<String, String> map,String folderName) {
		this.map=map;
	}
	
	
	public boolean  validateMappingName(Map<String,String> validationList,int i) {
		this.mappingName=map.get("MAPPING.NAME");
		if(!mappingName.substring(0, 2).contentEquals("m_")) {
			validationList.put(i+"_MAPPING.NAME","Invalid Start of Mapping Name [" + map.get("MAPPING.NAME") + "]. Mapping Name Should Start with 'm_'");
			return false;
		}
		return true;
	}
	
	public boolean isMappingvalid(Map<String,String> validationList,int i) {
		String isValid=map.get("MAPPING.ISVALID");
		if(!isValid.equals("YES")) {
			validationList.put(i+"_MAPPING.ISVALID","Mapping "+ map.get("MAPPING.NAME")  +" is not valid. Please Validate the mapping to fix the issues.");
			return false;
		}
		return true;
	}

	
	public boolean validateMappingVariableName(Map<String,String> validationList,int i) {
		String mappingVariablesList=null;
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("MAPPINGVARIABLE.NAME")) {
				mappingVariableCount++;
				String mappingVariableName=map.get(key);
				
				if (mappingVariablesList==null) {
					mappingVariablesList=mappingVariableName;
				}else {
					mappingVariablesList+=", "+mappingVariableName;
				}
				
				if(!mappingVariableName.substring(0, 2).equals("$$")) {
					validationList.put(i+"_MAPPINGVARIABLE.NAME","Invalid MappingVariable Name - " + mappingVariableName + " on [" + map.get("MAPPING.NAME") + "]. MappingVariable should be suffixed with $$");
					return false;
				}
			}
		}
		return true;
	}
	
	// Function to identify any Override Queries used in mapping for source
	public boolean validateSQLQuery(Map<String,String> validationList,int i) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Sql Query")) {
				String sqlOverride=map.get(key);
				
				if(!sqlOverride.equals("")) {
					validationList.put(i+"_MAPPING.Sql Query","Source SQL Override Query has been idenified in the mapping - [" + map.get("MAPPING.NAME") + "].");
					return false;
				}
			}
		}
		return true;
	}

	public void getTransformationDetails() {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("TRANSFORMATION.NAME")) {
				transformationCount++;
				if(transformationList==null) {
					transformationList=map.get(key);
				}else {
					transformationList+=", "+map.get(key);
				}
			}
		}
	}
	
	public void validate(Map<String,String> validationList,int i) {
		mappingNameValidation=validateMappingName(validationList,i)?"PASS":"FAIL";
		System.out.println("Validating Mapping - " + mappingName);
		mappingIsValidValidation=isMappingvalid(validationList,i)?"PASS":"FAIL";
		mappingVariableNameValidation=validateMappingVariableName(validationList,i)?"PASS":"FAIL";
		mappingSQLQueryValidation=validateSQLQuery(validationList,i)?"PASS":"WARNING";
		getTransformationDetails();
	}
	
	
	public Map<String, String> getMap() {
		return map;
	}


	public String getMappingName() {
		return mappingName;
	}


	public String getMappingNameValidation() {
		return mappingNameValidation;
	}


	public String getMappingIsValidValidation() {
		return mappingIsValidValidation;
	}


	public String getMappingVariableNameValidation() {
		return mappingVariableNameValidation;
	}


	public String getMappingSQLQueryValidation() {
		return mappingSQLQueryValidation;
	}


	public int getMappingVariableCount() {
		return mappingVariableCount;
	}


	public int getTransformationCount() {
		return transformationCount;
	}


	public String getTransformationList() {
		return transformationList;
	}
	
	
	
}
