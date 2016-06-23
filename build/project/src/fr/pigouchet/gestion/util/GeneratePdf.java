package fr.pigouchet.gestion.util;


import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.model.Produit;
import fr.pigouchet.gestion.model.SubCategorie;

public class GeneratePdf {
	private static fr.pigouchet.gestion.model.Utils pathPdf= fr.pigouchet.gestion.util.Utils.searchDataUtil("pdf");
	private static String FILE = pathPdf.getData();
	 /* private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	      Font.BOLD);
	  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.NORMAL, BaseColor.RED);*/
	  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	      Font.BOLD);
	  private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
	      Font.NORMAL);
	  private static java.util.List<Produit> data;
	 // private static java.util.List<Produit> dataProd;
	  private static java.util.List<Categorie> dataCate;
	  private static java.util.List<SubCategorie> dataSubCate;

	  static boolean passed = false;
	  static PdfWriter writer;

	  public static void main(String[] args) {
	    try {
	    	createFullPage();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		/*dataProd = Connection.getAllProduct();
		java.util.List<Categorie> dataCate = Connection.getAllCategory();
		for (Categorie categorie : dataCate) {
			java.util.List<Produit> r = categorie.getProduit();
			for (Produit produit : r) {
				System.out.println(produit.getNom()+" "+produit.getPriceDetail());
			}
		}

		Document document=new Document();
		try {
			PdfWriter.getInstance(document,new FileOutputStream("tablePDF.pdf"));
			document.open();
			PdfPTable table = createTable();
			document.add(table);
			document.newPage();
			document.add(table);
			document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	  }

	  public static void loadAllData(){
		  //data = Connection.categoryToProduct();
		//  dataProd = Connection.getAllProduct();
		  dataCate = Connection.getAllCategory();
	  }

	  public static void createPageByCategory(Categorie category){
		  loadAllData();
		  Document document=new Document(PageSize.A4, 36, 36, 54, 36);
			try {
				PdfWriter.getInstance(document,new FileOutputStream(FILE+"/Fiche "+category.getNom()+".pdf"));
				document.open();
				PdfPTable table = oneCategory(category);
				document.add(table);
				document.newPage();
				document.close();

				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File(FILE+"/Fiche "+category.getNom()+".pdf");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				        // no application registered for PDFs
				    }
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }

	  public static PdfPTable oneCategory(Categorie category) throws DocumentException{
		  PdfPTable table = new PdfPTable(5);

	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{1, 2, 1, 1, 1});

	        dataSubCate = Connection.listCategoryToSubCategory(category);
	        for (SubCategorie subcategorie : dataSubCate) {
	        	PdfPCell cell;
	        	if(passed==true){
		        	cell = new PdfPCell(new Phrase(" "));
				    cell.setColspan(5);
				    table.addCell(cell);
	        	}

	        	cell = new PdfPCell(new Phrase(category.getNom(),subFont));
		        cell.setColspan(1);
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

	        	cell = new PdfPCell(new Phrase(subcategorie.getNom(),subFont));
	        	cell.setColspan(5);
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

		        cell = new PdfPCell(new Phrase("Code",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Nom",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Achat",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Artisant",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Detail",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

		        data = Connection.listSubCategoryToProduit(subcategorie);

		        for (Produit produit1 : data) {
		        	cell = new PdfPCell(new Phrase(produit1.getCode(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getNom(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceAchat(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceArtisant(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceDetail(),smallFont));
		        	table.addCell(cell);
				}
		        passed=true;
			}

		  return table;
	  }

	  public static void createFullPage() throws IOException{
		  loadAllData();
		  Document document=new Document(PageSize.A4, 36, 36, 54, 36);
			try {
			writer = PdfWriter.getInstance(document,new FileOutputStream(FILE+"/Tous les produits.pdf"));
				document.open();
				for (Categorie categorie : dataCate) {
					PdfPTable table = allSubCategory(categorie, document);
					document.add(table);
					document.newPage();
					passed=false;
				}
				//System.out.println(writer.getPageNumber());
				document.close();
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File(FILE+"/Tous les produits.pdf");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				        // no application registered for PDFs
				    }
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	  }

	  public static PdfPTable allSubCategory(Categorie categorie, Document document) throws DocumentException{
		  PdfPTable table = new PdfPTable(5);

	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{1, 2, 1, 1, 1});

	        dataSubCate = Connection.listCategoryToSubCategory(categorie);
	        for (SubCategorie subcategorie : dataSubCate) {
	        	PdfPCell cell;
	        	if(passed==true){
		        	cell = new PdfPCell(new Phrase(" "));
				    cell.setColspan(5);
				    table.addCell(cell);
	        	}

	        	cell = new PdfPCell(new Phrase(categorie.getNom(),subFont));
		        cell.setColspan(1);
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

	        	cell = new PdfPCell(new Phrase(subcategorie.getNom(),subFont));
	        	cell.setColspan(5);
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

		        cell = new PdfPCell(new Phrase("Code",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Nom",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Achat",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Artisant",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);
		        cell = new PdfPCell(new Phrase("Detail",smallFont));
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		        table.addCell(cell);

		        data = Connection.listSubCategoryToProduit(subcategorie);

		        for (Produit produit1 : data) {
		        	cell = new PdfPCell(new Phrase(produit1.getCode(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getNom(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceAchat(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceArtisant(),smallFont));
		        	table.addCell(cell);
		        	cell = new PdfPCell(new Phrase(produit1.getPriceDetail(),smallFont));
		        	table.addCell(cell);
				}
		        passed=true;
			}
	     return table;
	  }

	 /* public static PdfPTable createTable() throws DocumentException {
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{1, 2, 1, 1, 1});
	        PdfPCell cell;
	        cell = new PdfPCell(new Phrase("Agglos"));
	        cell.setColspan(5);
	        table.addCell(cell);
	        table.addCell("Code");
	        table.addCell("Nom");
	        table.addCell("Achat");
	        table.addCell("Artisant");
	        table.addCell("Detail");

	        for (Produit produit : dataProd) {
	        	table.addCell(produit.getCode());
	        	table.addCell(produit.getNom());
	        	table.addCell(produit.getPriceAchat());
	        	table.addCell(produit.getPriceArtisant());
	        	table.addCell(produit.getPriceDetail());
			}



	        return table;
	    }*/
/*
	  // iText allows to add metadata to the PDF which can be viewed in your Adobe
	  // Reader
	  // under File -> Properties
	  private static void addMetaData(Document document) {
	    document.addTitle("My first PDF");
	    document.addSubject("Using iText");
	    document.addKeywords("Java, PDF, iText");
	    document.addAuthor("Lars Vogel");
	    document.addCreator("Lars Vogel");
	  }

	  private static void addTitlePage(Document document)
	      throws DocumentException {
	    Paragraph preface = new Paragraph();
	    // We add one empty line
	    addEmptyLine(preface, 1);
	    // Lets write a big header
	    preface.add(new Paragraph("Title of the document", catFont));

	    addEmptyLine(preface, 1);
	    // Will create: Report generated by: _name, _date
	    preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	        smallBold));
	    addEmptyLine(preface, 3);
	    preface.add(new Paragraph("This document describes something which is very important ",
	        smallBold));

	    addEmptyLine(preface, 8);

	    preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
	        redFont));

	    document.add(preface);
	    // Start a new page
	    document.newPage();
	  }

	  private static void addContent(Document document) throws DocumentException {
	    Anchor anchor = new Anchor("First Chapter", catFont);
	    anchor.setName("First Chapter");

	    // Second parameter is the number of the chapter
	    Chapter catPart = new Chapter(new Paragraph(anchor), 1);

	    Paragraph subPara = new Paragraph("Subcategory 1", subFont);
	    Section subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("Hello"));

	    subPara = new Paragraph("Subcategory 2", subFont);
	    subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("Paragraph 1"));
	    subCatPart.add(new Paragraph("Paragraph 2"));
	    subCatPart.add(new Paragraph("Paragraph 3"));

	    // add a list
	    createList(subCatPart);
	    Paragraph paragraph = new Paragraph();
	    addEmptyLine(paragraph, 5);
	    subCatPart.add(paragraph);

	    // add a table
	    createTable(subCatPart);

	    // now add all this to the document
	    document.add(catPart);

	    // Next section
	    anchor = new Anchor("Second Chapter", catFont);
	    anchor.setName("Second Chapter");

	    // Second parameter is the number of the chapter
	    catPart = new Chapter(new Paragraph(anchor), 1);

	    subPara = new Paragraph("Subcategory", subFont);
	    subCatPart = catPart.addSection(subPara);
	    subCatPart.add(new Paragraph("This is a very important message"));

	    // now add all this to the document
	    document.add(catPart);

	  }

	  private static void createTable(Section subCatPart)
	      throws BadElementException {
	    PdfPTable table = new PdfPTable(3);

	    // t.setBorderColor(BaseColor.GRAY);
	    // t.setPadding(4);
	    // t.setSpacing(4);
	    // t.setBorderWidth(1);

	    PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Table Header 2"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);

	    c1 = new PdfPCell(new Phrase("Table Header 3"));
	    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(c1);
	    table.setHeaderRows(1);

	    table.addCell("1.0");
	    table.addCell("1.1");
	    table.addCell("1.2");
	    table.addCell("2.1");
	    table.addCell("2.2");
	    table.addCell("2.3");

	    subCatPart.add(table);

	  }

	  private static void createList(Section subCatPart) {
	    List list = new List(true, false, 10);
	    list.add(new ListItem("First point"));
	    list.add(new ListItem("Second point"));
	    list.add(new ListItem("Third point"));
	    subCatPart.add(list);
	  }

	  private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	 }*/

	  public static PdfPTable getHeaderTable(int x, int y) {
	        PdfPTable table = new PdfPTable(2);
	        table.setTotalWidth(527);
	        table.setLockedWidth(true);
	        table.getDefaultCell().setFixedHeight(20);
	        table.getDefaultCell().setBorder(Rectangle.BOTTOM);
	        table.addCell("FOOBAR FILMFESTIVAL");
	        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.addCell(String.format("Page %d of %d", x, y));
	        return table;
	    }
}
