package com.wow.dev.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.wow.dev.controller.OnPremValidation;
import com.wow.dev.infanodes.Aggregator;
import com.wow.dev.infanodes.Expression;
import com.wow.dev.infanodes.Lookup;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.Transformation;
import com.wow.dev.infanodes.Workflow;

public class PDFResult {
	
	private Document pdfDoc;
	private Paragraph paragraph;
	private Font font; 
	private PdfWriter pdfWriter;
	
	public PDFResult() {
		try {
			paragraph=new Paragraph();
			font = new Font();
			font.setFamily(BaseFont.COURIER);
			font.setSize(10);
			paragraph.setFont(font);
			pdfDoc= new Document();
		}catch(Exception e) {log_msg(e.getMessage());}
	}
	
	
	public void generateOutput(OnPremValidation opv,Map<String, String> validationList,String peerReviewerName) {
		try {
				pdfWriter=PdfWriter.getInstance(pdfDoc, new FileOutputStream(opv.getWorkflowName()+".pdf"));
				openDocument();
		} catch (FileNotFoundException e) {
			System.out.println("The process cannot access the file because it is being used by another process"+ opv.getWorkflowName()+".pdf. Please close the file before running the process.");
			System.exit(-1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		try {
			Image logo = Image.getInstance("res/logo.jpg");
			logo.scalePercent(45f);
			pdfDoc.add(logo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		float TAB_SPLIT=300f;
		log_msg("");
		
		setFontAttribute(font, 16, BaseColor.BLACK, paragraph, Element.ALIGN_CENTER, Font.BOLD);
		log_msg("----------------------------------------------------");
		log_msg("Powercenter Code Review � ICC Standards check Report");
		log_msg("----------------------------------------------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		
		log_msg("\n");
		log_msg("Workflow Name",":   "+opv.getWorkflowName(),TAB_SPLIT);
		log_msg("Folder Name",":   "+opv.getFolderName(),TAB_SPLIT);
		log_msg("Generated By",":   "+peerReviewerName,TAB_SPLIT);
		log_msg("Generated On",":   "+Calendar.getInstance().getTime().toString(),TAB_SPLIT);
		String environment=opv.getRepositoryName().contains("DVLP")?"DEV":opv.getRepositoryName().contains("TEST")?"TEST":opv.getRepositoryName().contains("ACPT")?"ACPT":"PROD";
		log_msg("Environment",":   "+environment,TAB_SPLIT);
		log_msg("Repository Name",":   "+opv.getRepositoryName(),TAB_SPLIT);
		
		Workflow workflows[]=opv.getWorkflows();
		Session sessions[]=opv.getSessions();
		Mapping mappings[]=opv.getMappings();
		Transformation transformation[]=opv.getTransformation();
		
		log_msg("");
		setFontAttribute(font, 11, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
		log_msg("Objects validated");
		log_msg("-----------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		log_msg("");
		
		for(int i=0;i<workflows.length;i++)
			log_msg("Workflow",":   "+workflows[i].getWorkflowName(),TAB_SPLIT);
		
		for(int i=0;i<sessions.length;i++)
			log_msg("Session",":   "+sessions[i].getSessionName(),TAB_SPLIT);
		
		for(int i=0;i<mappings.length;i++)
			log_msg("Mapping",":   "+mappings[i].getMappingName(),TAB_SPLIT);
		
		log_msg("");
		setFontAttribute(font, 11, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
		log_msg("Code Compliance Checklist and Status");
		log_msg("------------------------------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		log_msg("");
		
		
		for(int i=0;i<workflows.length;i++) {
			Workflow wkf=workflows[i];
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
			log_msg(wkf.getWorkflowName());
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
			log_msg("   Workflow Naming Standards" , ":   "+wkf.getWorkflowNameValidation(),TAB_SPLIT);
			log_msg("   Is Workflow Valid", ":   "+wkf.getWorkflowIsValidValidation(),TAB_SPLIT);
			log_msg("   Workflow Integration Service" , ":   "+wkf.getWorkflowIntegrationServiceValidation(),TAB_SPLIT);
			log_msg("   Backward compatible on Workflow log" , ":   "+wkf.getWORKFLOW_BACKWARD_COMPATIBLEValidaiton(),TAB_SPLIT);
			log_msg("   Workflow log naming standard" , ":   "+wkf.getWorkflowlogDirectoryValidation(),TAB_SPLIT);
			log_msg("   Workflow log folder name" , ":   "+wkf.getWorkflowLogNameValidation(),TAB_SPLIT);
			log_msg("\n");
		}
		
		
		for(int i=0;i<sessions.length;i++) {
			Session sess=sessions[i];
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
			log_msg(sess.getSessionName());
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
			log_msg("   Session Naming Standards" , ":   "+sess.getSessionNameValidation(),TAB_SPLIT);
			log_msg("   Is Session Valid" , ":   "+sess.getSessionIsValidValidation(),TAB_SPLIT);
			log_msg("   Backward compatible on sess log" , ":   "+sess.getSESSION_BACKWARD_COMPATIBLEValidation(),TAB_SPLIT);
			log_msg("   Session log naming standard" , ":   "+sess.getSessionLogDirectoryValidation(),TAB_SPLIT);
			log_msg("   Session log folder name" , ":   "+sess.getSessionLogNameValidation(),TAB_SPLIT);
			log_msg("   Stop on Error" , ":   "+sess.getSessionStopOnErrosValidation(),TAB_SPLIT);
			log_msg("   Session DTM Buffered Size" , ":   "+sess.getSessionDTMBufferedSizeValidation(),TAB_SPLIT); 
			log_msg("   Session SQL Query" , ":   "+sess.getSessionSqlQueryValidation(),TAB_SPLIT);
			log_msg("   Session Override Query" , ":   "+sess.getSessionSqlOverrideValidation(),TAB_SPLIT);
			log_msg("   Session Override Tracing" , ":   "+sess.getSessionOverrideTracing(),TAB_SPLIT);
			log_msg("   FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN" , ":   "+sess.getSessionFAIL_PARENT_IF_INSTANCE_DID_NOT_RUNValidation(),TAB_SPLIT);
			log_msg("   FAIL_PARENT_IF_INSTANCE_FAILS" , ":   "+sess.getSessionFAIL_PARENT_IF_INSTANCE_FAILSValidation(),TAB_SPLIT);
			log_msg("\n");
		}
		
		
		for(int i=0;i<mappings.length;i++) {
			Mapping map=mappings[i];
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
			log_msg(map.getMappingName());
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
			log_msg("   Mapping Naming Standards" ,":   "+ map.getMappingNameValidation(),TAB_SPLIT);
			log_msg("   Is Mapping Valid" , ":   "+map.getMappingIsValidValidation(),TAB_SPLIT);
			log_msg("   Total Transformation count",":   "+map.getTransformationCount(),TAB_SPLIT);
			log_msg("   Transformation Names",":   "+map.getTransformationList(),TAB_SPLIT);
			log_msg("   Total Mapping Variable count",":   "+map.getMappingVariableCount(),TAB_SPLIT);
			log_msg("   Mapping  variables Standards" ,":   "+map.getMappingVariableNameValidation(),TAB_SPLIT);
			log_msg("   Mapping SQL Query Validation" , ":   "+map.getMappingSQLQueryValidation(),TAB_SPLIT);
			log_msg("\n");
		}
		
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
		log_msg("Transformation Validation");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		
		for(int i=0;i<transformation.length;i++) {
			
			if(transformation[i]!=null) {
				switch(transformation[i].getTransformationType()) {
					case "Expression": 	
						Expression exp=(Expression)transformation[i];
						log_msg("   Expression Name" ,":   "+ exp.getTransformationName(),TAB_SPLIT);
						log_msg("   Expression Naming Standards" ,":   "+ exp.getTransformationNameValidation(),TAB_SPLIT);
						log_msg("   Expression Tracing Level" ,":   "+ exp.getTracingLevelValidation(),TAB_SPLIT);
						log_msg("   Expression PortName Standards" ,":   "+ exp.getPortnameValidation(),TAB_SPLIT);
					break;
					
					case "Lookup":
						Lookup lkp=(Lookup)transformation[i];
						log_msg("   Lookup Name" ,":   "+ lkp.getTransformationName(),TAB_SPLIT);
						log_msg("   Lookup Naming Standards" ,":   "+ lkp.getTransformationNameValidation(),TAB_SPLIT);
						log_msg("   Lookup PortName Standards" ,":   "+ lkp.getPortNameValidation(),TAB_SPLIT);
						log_msg("   Is Lookup Cache Enabled" ,":   "+ lkp.getIsCacheEnabledValidation(),TAB_SPLIT);
						log_msg("   Lookup Policy on multiple match" ,":   "+ lkp.getLookupPolicyOnMultipleMatchValidation(),TAB_SPLIT);
						log_msg("   Lookup Tracing Level" ,":   "+ lkp.getTracingLevelValidation(),TAB_SPLIT);
						log_msg("   Lookup Cache Directory" ,":   "+ lkp.getLookupCacheDirectoryValidation(),TAB_SPLIT);
						log_msg("   Is Persistent Cache" ,":   "+ lkp.getIsPersistantCacheValidation(),TAB_SPLIT);
						log_msg("   Lookup Data Cache Size" ,":   "+ lkp.getDataCacheSizeValidation(),TAB_SPLIT);
						log_msg("   Lookup Index Cache Size" ,":   "+ lkp.getIndexCacheSizeValidation(),TAB_SPLIT);
						log_msg("   Is Lookup Cache dynamic" ,":   "+ lkp.getIsDynamicLookupValidation(),TAB_SPLIT);
						log_msg("   Is Sorted Inputs" ,":   "+ lkp.getIsSoretedInputValidation(),TAB_SPLIT);
						log_msg("   Lookup Condition validation" ,":   "+ lkp.getLookupConditionValidation(),TAB_SPLIT);
					break;
					
					case "Aggregator":
						Aggregator agg=(Aggregator)transformation[i];
						log_msg("   Aggregator Name" ,":   "+ agg.getTransformationName(),TAB_SPLIT);
						log_msg("   Aggregator Naming Standards" ,":   "+ agg.getTransformationNameValidation(),TAB_SPLIT);
						log_msg("   Aggregator PortName Standards" ,":   "+ agg.getPortNameValidation(),TAB_SPLIT);
						log_msg("   Is Sorted Inputs" ,":   "+ agg.getIsSoretedInputValidation(),TAB_SPLIT);
						log_msg("   Aggregator Data Cache Size" ,":   "+ agg.getDataCacheSizeValidation(),TAB_SPLIT);
						log_msg("   Aggregator Index Cache Size" ,":   "+ agg.getIndexCacheSizeValidation(),TAB_SPLIT);
						

				}
				log_msg("");
			}
		}
		
		
		
		
		setFontAttribute(font, 11, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
		log_msg("Please find the review comments below");
		log_msg("-------------------------------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		
		if(validationList.isEmpty()) {
			log_msg("Informatica components are created as per the ICC Standards..");
		}else
		{
			Set<String> keys=validationList.keySet();
			for(String key:keys) {
				log_msg("   � "+validationList.get(key));
			}
		}
		
		closeDocument();
	}
	
	public void log_msg(String str) {
		System.out.println(str);
		paragraph.add(str + "\n");
		try {
			pdfDoc.add(paragraph);
			paragraph.clear();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void log_msg(String value1,String value2, float splitvalue) {
		System.out.println(value1 + "" + value2);
		paragraph=paragraphTabFormating(value1,value2,splitvalue);
		try {
			pdfDoc.add(paragraph);
			paragraph.clear();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private Paragraph paragraphTabFormating(String value1, String value2,float splitvalue) {
		paragraph.add(value1);
		paragraph.setTabSettings(new TabSettings(splitvalue));
	    paragraph.add(Chunk.TABBING);
	    paragraph.add(value2);
	    return paragraph;
	}
	
	public void openDocument() {
		pdfDoc.open();
	}
	
	public void closeDocument() {
		pdfDoc.close();
	}
	
	
	private void setFontAttribute(Font font, int fontSize, BaseColor color, Paragraph paragraph, int alignment, int fontStyle) {
		font.setSize(fontSize); 
		font.setColor(color); 
		font.setStyle(fontStyle);
		paragraph.setFont(font);
		paragraph.setAlignment(alignment);
	}

}
