package com.wow.dev.infanodes;

import java.util.ArrayList;
import java.util.Set;

public class Mapping extends InfaXMLNodes{
	
	
	
	public Mapping() {
		super();
	}
	
	public void validateMappingName(ArrayList<String> errorList) {
		String mappingName=map.get("MAPPING.NAME");
		super.nullValidation("Mapping Name", mappingName, errorList);
		
		if(!mappingName.substring(0, 2).contentEquals("m_")) {
			errorList.add("Invalid Start of Mapping Name [" + map.get("MAPPING.NAME") + "]. Mapping Name Should Start with 'm_'");
		}
		
	}
	
	public void isMappingvalid(ArrayList<String> errorList) {
		String isValid=map.get("MAPPING.ISVALID");
		System.out.println("Validating if Mapping is valid... [Mapping IsValid=" + isValid + "] ");
		
		if(!isValid.equals("YES")) {
			errorList.add("Mapping "+ map.get("MAPPING.NAME")  +" is not valid. Please Validate the mapping to fix the issues.");
		}
	}

	
	public void validateMappingVariableName(ArrayList<String> errorList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("MAPPINGVARIABLE.NAME")) {
				System.out.println("Validating MappingVariable Name... ["+ key +" = "+ map.get(key) + "] ");
				String mappingVariableName=map.get(key);
				if(!mappingVariableName.substring(0, 2).equals("$$")) {
					errorList.add("Invalid MappingVariable Name - " + mappingVariableName + " on [" + map.get("MAPPING.NAME") + "]. MappingVariable should be suffixed with $$");
				}
			}
		}
	}
	
	// Function to identify any Override Queries used in mapping for source
	public void validateSQLQuery(ArrayList<String> errorList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Sql Query")) {
				System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +" ] ");
				String sqlOverride=map.get(key);
				
				if(!sqlOverride.equals("")) {
					errorList.add("Source SQL Override Query has been idenified in the mapping - [" + map.get("MAPPING.NAME") + "].");
				}
			}
		}
	}
	
	// Funcion to identify any Override Queries used in mapping for lookup
	public void validateOverrideQuery(ArrayList<String> errorList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Sql Override")) {
				System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +"] ");
				String sqlOverride=map.get(key);
				if(!sqlOverride.equals("")) {
					errorList.add("LKP Override Query has been idenified in the mapping - [" + map.get("MAPPING.NAME") + "].");
				}
			}
		}
	}
	
	@Override
	public void validate(ArrayList<String> errorList,String folderName) {
		validateMappingName(errorList);
		isMappingvalid(errorList);
		validateMappingVariableName(errorList);
		validateSQLQuery(errorList);
		validateOverrideQuery(errorList);
	}
}
