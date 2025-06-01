package application;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTeste {

    public static void main(String[] args) {
        try {
            //title
            Font font1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD, BaseColor.BLACK);
            //subTitle
            Font font2 = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("/home/anonymous/Documentos/Extrato.pdf"));

            document.open();

            Paragraph title = new Paragraph("Extrato", font1);
            document.add(title);
            
            Paragraph paragraph = new Paragraph("Referente ao mes: "+" | "+"Data de criação: ");
            paragraph.setSpacingBefore(10f);
            paragraph.setSpacingAfter(5f);
            document.add(paragraph);
            
            document.add(Chunk.NEWLINE);
 
            //table expenses ------------------------------------------------------------------------------
            PdfPTable tableDado = new PdfPTable(2);
            
            PdfPCell description = new PdfPCell(new Phrase("Gastos do Mes"));
            description.setColspan(2);
            description.setFixedHeight(20f);
            description.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell description2 = new PdfPCell(new Phrase("Items"));
            description2.setFixedHeight(16f);
            description2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell description3 = new PdfPCell(new Phrase("Valor"));
            description3.setFixedHeight(16f);
            description3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell description4 = new PdfPCell(new Phrase("Valor Total: R$ "));
            description4.setColspan(2);
            description4.setFixedHeight(20f);
            description4.setHorizontalAlignment(Element.ALIGN_CENTER);
 
            tableDado.addCell(description);
            tableDado.addCell(description2);
            tableDado.addCell(description3);
            
            for (int i = 0; i < 10; i++) {
              PdfPCell item = new PdfPCell(new Phrase("Celular"+i));
              PdfPCell value = new PdfPCell(new Phrase("R$ 1"+(i+2)));
              tableDado.addCell(item);
              tableDado.addCell(value);  
            }
            
            
            tableDado.addCell(description4);
            
            document.add(tableDado);

            document.close();
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(MainTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
