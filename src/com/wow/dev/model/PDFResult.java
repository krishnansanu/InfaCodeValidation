package com.wow.dev.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

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
import com.wow.dev.infanodes.Filter;
import com.wow.dev.infanodes.CustomTransformation;
import com.wow.dev.infanodes.Joiner;
import com.wow.dev.infanodes.Lookup;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Normalizer;
import com.wow.dev.infanodes.Router;
import com.wow.dev.infanodes.SequenceGen;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.Sorter;
import com.wow.dev.infanodes.SourceQualifier;
import com.wow.dev.infanodes.StoredProcedure;
import com.wow.dev.infanodes.Target;
import com.wow.dev.infanodes.TransactionControl;
import com.wow.dev.infanodes.Transformation;
import com.wow.dev.infanodes.UpdateStrategy;
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
		Target targets[]=opv.getTargets();
		
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
			log_msg("   Workflow Naming Standards" , ":   "+wkf.getValidationResults().get("WORKFLOW_NAME_VALIDATION"),TAB_SPLIT);
			log_msg("   Is Workflow Valid", ":   "+ wkf.getValidationResults().get("WORKFLOW_IS_VALID"),TAB_SPLIT);
			log_msg("   Workflow Integration Service" , ":   "+wkf.getValidationResults().get("WORKFLOW_INTEGRATION_SERVICE_VALIDATION"),TAB_SPLIT);
			log_msg("   Backward compatible on Workflow log" , ":   "+wkf.getValidationResults().get("WORKFLOW_BACKWARD_COMPATIBLE_VALIDATION"),TAB_SPLIT);
			log_msg("   Workflow log naming standard" , ":   "+wkf.getValidationResults().get("WORKFLOW_LOG_VALIDATION"),TAB_SPLIT);
			log_msg("   Workflow log folder name" , ":   "+wkf.getValidationResults().get("WORKFLOW_LOG_DIR_VALIDATION"),TAB_SPLIT);
			log_msg("   Workflow Variables" , ":   "+wkf.getValidationResults().get("WORKFLOW_VARIABLES_VALIDATION"),TAB_SPLIT);
			log_msg("   Workflow Param File Name" , ":   "+wkf.getValidationResults().get("WORKFLOW_PARM_FILE_VALIDATION"),TAB_SPLIT);
			log_msg("   Workflow Param File Directory" , ":   "+wkf.getValidationResults().get("WORKFLOW_PARM_FILE_DIR_VALID"),TAB_SPLIT);
			log_msg("\n");
		}
		
		
		for(int i=0;i<sessions.length;i++) {
			Session sess=sessions[i];
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
			log_msg(sess.getSessionName());
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
			log_msg("   Session Naming Standards" , ":   "+sess.getValidationResults().get("SESSION_NAME_VALIDATION"),TAB_SPLIT);
			log_msg("   Is Session Valid" , ":   "+sess.getValidationResults().get("SESSION_IS_VALID"),TAB_SPLIT);
			log_msg("   Is Session Resuable" , ":   "+sess.getValidationResults().get("SESSION_IS_RESULABLE_VALIDATION"),TAB_SPLIT);
			log_msg("   Backward compatible on sess log" , ":   "+sess.getValidationResults().get("SESSION_BACKWARD_COMPATIBLE_VALIBATION"),TAB_SPLIT);
			log_msg("   Session log naming standard" , ":   "+sess.getValidationResults().get("SESSION_LOG_VALIDATION"),TAB_SPLIT);
			log_msg("   Session log folder name" , ":   "+sess.getValidationResults().get("SESSION_LOG_DIR_VALIDATION"),TAB_SPLIT);
			log_msg("   Stop on Error" , ":   "+sess.getValidationResults().get("SESSION_STOP_ON_ERROS_VALIDATION"),TAB_SPLIT);
			log_msg("   Session DTM Buffered Size" , ":   "+sess.getValidationResults().get("SESSION_DTM_BUFFERED_SIZE_VALIDATION"),TAB_SPLIT); 
			log_msg("   Session SQL Query" , ":   "+sess.getValidationResults().get("SESSION_SQL_QUERY_VALIDATION"),TAB_SPLIT);
			log_msg("   Session Override Query" , ":   "+sess.getValidationResults().get("SESSION_OVERRIDE_VALIDATION"),TAB_SPLIT);
			log_msg("   Session Override Tracing" , ":   "+sess.getValidationResults().get("SESSION_OVERRIDE_TRACING_VALIDATION"),TAB_SPLIT);
			log_msg("   FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN" , ":   "+sess.getValidationResults().get("SESSION_FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN_VALIDATION"),TAB_SPLIT);
			log_msg("   FAIL_PARENT_IF_INSTANCE_FAILS" , ":   "+sess.getValidationResults().get("SESSION_FAIL_PARENT_IF_INSTANCE_FAILS_VALIDATION"),TAB_SPLIT);
			log_msg("\n");
		}
		
		
		for(int i=0;i<mappings.length;i++) {
			Mapping map=mappings[i];
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
			log_msg(map.getMappingName());
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
			log_msg("   Mapping Naming Standards" ,":   "+ map.getValidationResults().get("MAPPING_NAME_VALIDATION"),TAB_SPLIT);
			log_msg("   Is Mapping Valid" , ":   "+ map.getValidationResults().get("MAPPING_IS_VALID"),TAB_SPLIT);
			log_msg("   Mapping  variables Standards" ,":   "+ map.getValidationResults().get("MAPPING_VARIABLE_NAME_VALIDATION"),TAB_SPLIT);
			log_msg("   Total Mapping Variable count",":   "+ map.getValidationResults().get("MAPPING_VARIABLE_COUNT_VALIDATION"),TAB_SPLIT);
			log_msg("   Transformation Names",":   "+ map.getValidationResults().get("MAPPING_TRANSFORMATION_LIST_VALIDATION"),TAB_SPLIT);
			log_msg("   Total Transformation count",":   "+ map.getValidationResults().get("MAPPING_TRANSFORMATION_COUNT_VALIDATION"),TAB_SPLIT);
			log_msg("   Mapping SQL Query Validation" , ":   "+ map.getValidationResults().get("MAPPING_SQL_QUERY_VALIDATION"),TAB_SPLIT);
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
						log_msg("   Expression Naming Standards" ,":   "+ exp.getValidationResults().get("EXPRESSION_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Expression Tracing Level" ,":   "+ exp.getValidationResults().get("EXPRESSION_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Expression PortName Standards" ,":   "+ exp.getValidationResults().get("EXPRESSION_PORT_NAME_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Lookup":
						Lookup lkp=(Lookup)transformation[i];
						log_msg("   Lookup Name" ,":   "+ lkp.getTransformationName(),TAB_SPLIT);
						log_msg("   Lookup Naming Standards" ,":   "+ lkp.getValidationResults().get("LOOKUP_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Tracing Level" ,":   "+ lkp.getValidationResults().get("LOOKUP_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup PortName Standards" ,":   "+ lkp.getValidationResults().get("LOOKUP_PORT_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Override Query" ,":   "+ lkp.getValidationResults().get("LOOKUP_OVERRIDE_QUERY_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Lookup Cache Enabled" ,":   "+ lkp.getValidationResults().get("LOOKUP_IS_CACHE_ENABLED_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Policy on multiple match" ,":   "+ lkp.getValidationResults().get("LOOKUP_POLICY_ON_MULTIPLE_MATCH_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Cache Directory" ,":   "+ lkp.getValidationResults().get("LOOKUP_CACHE_DIR_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Persistent Cache" ,":   "+ lkp.getValidationResults().get("LOOKUP_IS_PERSISTANT_CACHE_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Data Cache Size" ,":   "+ lkp.getValidationResults().get("LOOKUP_DATA_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Index Cache Size" ,":   "+ lkp.getValidationResults().get("LOOKUP_INDEX_CACHE_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Lookup Cache dynamic" ,":   "+ lkp.getValidationResults().get("LOOKUP_IS_DYNAMIC_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Sorted Inputs" ,":   "+ lkp.getValidationResults().get("LOOKUP_IS_SORTED_INPUT_VALIDATION"),TAB_SPLIT);
						log_msg("   Lookup Condition validation" ,":   "+ lkp.getValidationResults().get("LOOKUP_CONDITION_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Aggregator":
						Aggregator agg=(Aggregator)transformation[i];
						log_msg("   Aggregator Name" ,":   "+ agg.getTransformationName(),TAB_SPLIT);
						log_msg("   Aggregator Naming Standards" ,":   "+ agg.getValidationResults().get("AGGREGATOR_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Aggregator Tracing Level" ,":   "+ agg.getValidationResults().get("AGGREGATOR_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Aggregator PortName Standards" ,":   "+ agg.getValidationResults().get("AGGREGATOR_PORT_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Aggregator Cache Directory Validation" ,":   "+ agg.getValidationResults().get("AGGREGATOR_CACHE_DIR_VALIDATION"),TAB_SPLIT);
						log_msg("   Aggregator Data Cache Size" ,":   "+ agg.getValidationResults().get("AGGREGATOR_DATA_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Aggregator Index Cache Size" ,":   "+ agg.getValidationResults().get("AGGREGATOR_INDEX_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Sorted Inputs" ,":   "+ agg.getValidationResults().get("AGGREGATOR_IS_SORTED_INPUT_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Sorter":
						Sorter srt=(Sorter)transformation[i];
						log_msg("   Sorter Name" ,":   "+ srt.getTransformationName(),TAB_SPLIT);
						log_msg("   Sorter Naming Standards" ,":   "+ srt.getValidationResults().get("SORTER_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Sorter PortName Standards" ,":   "+ srt.getValidationResults().get("SORTER_PORT_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Sorter Cache Size" ,":   "+ srt.getValidationResults().get("SORTER_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Sorter Cache Directory " ,":   "+ srt.getValidationResults().get("SORTER_CACHE_DIR_VALIDATION"),TAB_SPLIT);
						log_msg("   Sorter Tracing Level" ,":   "+ srt.getValidationResults().get("SORTER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Is Distinct Data" ,":   "+ srt.getValidationResults().get("SORTER_IS_DISTINCT_INPUT_VALIDATION"),TAB_SPLIT);
					break;
						
					case "Source Qualifier":
						SourceQualifier sq=(SourceQualifier)transformation[i];
						log_msg("   SQ Name" ,":   "+ sq.getTransformationName(),TAB_SPLIT);
						log_msg("   SQ Naming Standards" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Tracing Level" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ PortName Standards",":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_PORT_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Source Override Query" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_SQL_QUERY_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ User Defined Join" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_USER_DEFINED_JOINS_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Source Filter" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_FILTER_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Sorted Input" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_IS_SORTED_INPUTS_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Distinct Option" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_IS_DISTINCT_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ Partionable Option" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_IS_PARTIONABLE_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ PRE SQL" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_PRE_SQL_QUERY_VALIDATION"),TAB_SPLIT);
						log_msg("   SQ POST SQL" ,":   "+ sq.getValidationResults().get("SOURCE_QUALIFIER_POST_SQL_QUERY_VALIDATION"),TAB_SPLIT);
					break;
						
					case "Update Strategy":
						UpdateStrategy upd=(UpdateStrategy)transformation[i];
						log_msg("   Update Strategy Name" ,":   "+ upd.getTransformationName(),TAB_SPLIT);
						log_msg("   Update Strategy Naming Standards" ,":   "+ upd.getValidationResults().get("UPDATE_STRATEGY_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Update Strategy Tracing Level" ,":   "+ upd.getValidationResults().get("UPDATE_STRATEGY_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Update Strategy Expression Validation" ,":   "+ upd.getValidationResults().get("UPDATE_STRATEGY_EXPRESSION_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Sequence":
						SequenceGen seq=(SequenceGen)transformation[i];
						log_msg("   Sequence Generater Name" ,":   "+ seq.getTransformationName(),TAB_SPLIT);
						log_msg("   Sequence Generater Naming Standards" ,":   "+ seq.getValidationResults().get("SEQUENCE_GEN_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Sequence Generater Tracing Level" ,":   "+ seq.getValidationResults().get("SEQUENCE_GEN_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Filter":
						Filter fil=(Filter)transformation[i];
						log_msg("   Filter Name" ,":   "+ fil.getTransformationName(),TAB_SPLIT);
						log_msg("   Filter Naming Standards" ,":   "+ fil.getValidationResults().get("FILTER_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Filter Tracing Level" ,":   "+ fil.getValidationResults().get("FILTER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Filter Condition Validation" ,":   "+ fil.getValidationResults().get("FILTER_CONDITION_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Joiner":
						Joiner jnr=(Joiner)transformation[i];
						log_msg("   Joiner Name" ,":   "+ jnr.getTransformationName(),TAB_SPLIT);
						log_msg("   Joiner Naming Standards" ,":   "+ jnr.getValidationResults().get("JOINER_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Tracing Level" ,":   "+ jnr.getValidationResults().get("JOINER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Condition Validation" ,":   "+ jnr.getValidationResults().get("JOINER_CONDITION_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Type" ,":   "+ jnr.getValidationResults().get("JOINER_TYPE_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Data Cahce Size" ,":   "+ jnr.getValidationResults().get("JOINER_DATA_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Index Cache Size" ,":   "+ jnr.getValidationResults().get("JOINER_INDEX_CACHE_SIZE_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Is Input Sorted" ,":   "+ jnr.getValidationResults().get("JOINER_IS_SORTED_INPUTS_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Master Sort Order" ,":   "+ jnr.getValidationResults().get("JOINER_MASTER_SORT_ORDER_VALIDATION"),TAB_SPLIT);
						log_msg("   Joiner Cache Directory Validation" ,":   "+ jnr.getValidationResults().get("JOINER_CACHE_DIR_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Transaction Control":
						TransactionControl tc=(TransactionControl)transformation[i];
						log_msg("   Transaction Control Name" ,":   "+ tc.getTransformationName(),TAB_SPLIT);
						log_msg("   Transaction Control Naming Standards" ,":   "+ tc.getValidationResults().get("TRANSACTION_CONTROL_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Transaction Control Tracing Level" ,":   "+ tc.getValidationResults().get("TRANSACTION_CONTROL_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Transaction Control Condition Validation" ,":   "+ tc.getValidationResults().get("TRANSACTION_CONTROL_CONDITION_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Custom Transformation":
						CustomTransformation ct=(CustomTransformation)transformation[i];
						log_msg("   Transformation Type" ,":   "+ ct.getValidationResults().get("CUSTOM_TRANSFORMATION_TEMPLATE_VALIDATION"),TAB_SPLIT);
						log_msg("   Transformation Name" ,":   "+ ct.getTransformationName(),TAB_SPLIT);
						log_msg("   Transformation Tracing Level" ,":   "+ ct.getValidationResults().get("CUSTOM_TRANSFORMATION_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
						log_msg("   Transformation Port Name Standards" ,":   "+ ct.getValidationResults().get("CUSTOM_TRANSFORMATION_PORT_NAME_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Stored Procedure":
						StoredProcedure sp=(StoredProcedure)transformation[i];
						log_msg("   Transformation Name" ,":   "+ sp.getTransformationName(),TAB_SPLIT);
						log_msg("   Stored Procedure Name" ,":   "+ sp.getValidationResults().get("STORED_PROCEDURE_NAME"),TAB_SPLIT);
						log_msg("   Stored Procedure Type" ,":   "+ sp.getValidationResults().get("STORED_PROCEDURE_TYPE_VALIDATION"),TAB_SPLIT);
						log_msg("   Stored Procedure Connection Name" ,":   "+ sp.getValidationResults().get("STORED_PROCEDURE_CONNECTION_VALIDATION"),TAB_SPLIT);
						log_msg("   Stored Procedure call Text" ,":   "+ sp.getValidationResults().get("STORED_PROCEDURE_CALL_TEXT_VALIDATION"),TAB_SPLIT);
						log_msg("   Stored Procedure Tracing Level" ,":   "+ sp.getValidationResults().get("STORED_PROCEDURE_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Normalizer":
						Normalizer nrm=(Normalizer)transformation[i];
						log_msg("   Normalizer Transformation Name" ,":   "+ nrm.getTransformationName(),TAB_SPLIT);
						log_msg("   Normalizer Tracing Level" ,":   "+ nrm.getValidationResults().get("NORMALIZER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
					break;
					
					case "Router":
						Router rtr=(Router)transformation[i];
						log_msg("   Router Transformation Name" ,":   "+ rtr.getTransformationName(),TAB_SPLIT);
						log_msg("   Router Naming Standards" ,":   "+ rtr.getValidationResults().get("ROUTER_NAME_VALIDATION"),TAB_SPLIT);
						log_msg("   Router Tracing Level" ,":   "+ rtr.getValidationResults().get("ROUTER_TRACING_LEVEL_VALIDATION"),TAB_SPLIT);
					break;
					
				}
				log_msg("");
			}
		}
		
		for(int i=0;i<targets.length;i++) {
			Target target=targets[i];
			log_msg("   Target Name" ,":   "+ target.getTransformationName(),TAB_SPLIT);
			log_msg("   Target Type" ,":   "+ target.getValidationResults().get("TARGET_TYPE_VALIDATION"),TAB_SPLIT);
			log_msg("   Target Update Override Validation" , ":   "+ target.getValidationResults().get("TARGET_UPDATE_OVERRIDE_VALIDATION"),TAB_SPLIT);
			log_msg("   Target PRE SQL Validation",":   "+ target.getValidationResults().get("TARGET_PRE_SQL_VALIDATION"),TAB_SPLIT);
			log_msg("   Target POST SQL Validation",":   "+ target.getValidationResults().get("TARGET_POST_SQL_VALIDATION"),TAB_SPLIT);
			log_msg("");
		}
		

		setFontAttribute(font, 11, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.BOLD);
		log_msg("Test Case Summary");
		log_msg("-----------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, Font.NORMAL);
		log_msg("   Total Test Case Executed",":   "+ opv.getTOTAL_TEST_CASE_COUNT(),TAB_SPLIT);
		log_msg("   Pass Count",":   "+ opv.getPASS_CASE_COUNT(),TAB_SPLIT);
		log_msg("   Fail Count",":   "+ opv.getFAIL_CASE_COUNT(),TAB_SPLIT);
		log_msg("   Warning Count",":   "+ opv.getWARNING_CASE_COUNT(),TAB_SPLIT);
		log_msg("\n");
		
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
