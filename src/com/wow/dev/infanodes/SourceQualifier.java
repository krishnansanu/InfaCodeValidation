package com.wow.dev.infanodes;

import java.util.ArrayList;
import java.util.Map;

public class SourceQualifier{
	
	private Map<String, String> map;
	private String sourceType;
	
	public SourceQualifier() {

	}
	
	public void extractSourceDetails() {
		sourceType=map.get("SOURCE.DATABASETYPE");
	}
	
	
	public void validateSourceName(ArrayList<String> errorList) {
		String sourceName=map.get("SOURCE.NAME");

	}
	
	
	
	public void validate(ArrayList<String> infoList,ArrayList<String> errorList, ArrayList<String> warningList,String folderName) {
		
	}

}
