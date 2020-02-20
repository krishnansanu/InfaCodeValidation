package com.wow.dev.main;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class Result {
	
	private Document pdfDoc;
	private Paragraph paragraph;
	private Font font; 
	private PdfWriter pdfWriter;
		
	public Result(String xmlName) {
		try {
			paragraph=new Paragraph();
			font = new Font();
			font.setFamily(BaseFont.COURIER);
			font.setSize(10);
			paragraph.setFont(font);
			pdfDoc= new Document();
			pdfWriter=PdfWriter.getInstance(pdfDoc, new FileOutputStream(xmlName+".pdf"));
		}catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	
	public void generateOutput(ArrayList<String> errorList,ArrayList<String> infoList) {
		writeDetails("");
		
		setFontAttribute(font, 16, BaseColor.BLACK, paragraph, Element.ALIGN_CENTER, font.BOLD);
		writeDetails("-----------------------------------------------------");
		writeDetails("Informatica Code Review – ICC Standards check Report");
		writeDetails("-----------------------------------------------------");
		setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, font.NORMAL);
		
		writeDetails("");
		
		Iterator<String> info = infoList.iterator();
		while(info.hasNext()) {
			writeDetails(info.next());
		}
		
		
		// Results
		if(errorList.size() > 0 ) {
			writeDetails("");writeDetails("");
			writeDetails("Review Status");
			setFontAttribute(font, 12, BaseColor.RED, paragraph, Element.ALIGN_LEFT, font.BOLD);
			writeDetails("Informatica Component(s) is not created as per ICC Standards. Please find the comments Below");
			writeDetails("");
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, font.NORMAL);

			Iterator<String> it = errorList.iterator();
			int count=0;
			while(it.hasNext()) {
				count++;
				String msg=count+". " + it.next();
				writeDetails(msg);
			}
		}		
		else {
			writeDetails("");writeDetails("");
			writeDetails("Review Status");
			setFontAttribute(font, 12, new BaseColor(50, 205, 50) , paragraph, Element.ALIGN_LEFT, font.BOLD);
			writeDetails("Informatica Component(s) is created as per ICC Standards");
			setFontAttribute(font, 10, BaseColor.BLACK, paragraph, Element.ALIGN_LEFT, font.NORMAL);
		}
	}
	
	public void writeDetails(String str) {
		System.out.println(str);
		paragraph.add(str + "\n");
//		generatePDF();
		try {
			pdfDoc.add(paragraph);
			paragraph.clear();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
//	public void generatePDF() {
//		try {
//			pdfDoc.open();
//			pdfDoc.add(paragraph);
//			pdfDoc.close();
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
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
