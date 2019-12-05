package com.wow.dev.infanodes;

import java.util.ArrayList;

public class Source extends InfaXMLNodes{
	
	private String sourceType;
	
	public Source() {
		super();
	}
	
	
	public void extractSourceDetails() {
		sourceType=map.get("SOURCE.DATABASETYPE");
	}
	
	
	public void validateSourceName(ArrayList<String> errorList) {
		String sourceName=map.get("SOURCE.NAME");
		super.nullValidation("Source Name", sourceName, errorList);
	}
	
	
	@Override
	public void validate(ArrayList<String> errorList,String folderName) {
		
	}

}
