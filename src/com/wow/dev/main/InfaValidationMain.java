package com.wow.dev.main;

import java.util.LinkedHashMap;
import java.util.Map;
import com.wow.dev.controller.OnPremValidation;
import com.wow.dev.model.PDFResult;


public class InfaValidationMain 
{  
	public static void main(String[] args)  
	{
		String repositoryName = args[0];
		String folderName = args[1];
		String workflowName = args[2];
		String peerReviewerName = args[3];
		
		//Creation of Arraylist to catpure information, errors and warnings from the input XML
		Map<String, String> validationList=new LinkedHashMap<String, String>();
				
		
		OnPremValidation opv = new OnPremValidation(repositoryName,workflowName,folderName,validationList);
		opv.validateWorkflow();
		
		PDFResult pdf = new PDFResult();
		pdf.generateOutput(opv, validationList, peerReviewerName);
			
	}
}  