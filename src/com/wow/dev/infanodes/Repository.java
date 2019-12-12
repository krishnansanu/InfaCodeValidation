package com.wow.dev.infanodes;

public class Repository extends InfaXMLNodes{
	
	
	public Repository() {
		super();
	}
	
	public String getRepositoryName() {
		return map.get("REPOSITORY.NAME");
	}

}
