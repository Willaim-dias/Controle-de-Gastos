package view.util;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.entities.Card;
import model.entities.Extract;

public class CreatePdf {

    public static void createHistoryExtract(Extract obj, String totalValue, Date date, File url) {
        try {
            //title
            Font font1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD, BaseColor.BLACK);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(url));

            document.open();

            Paragraph title = new Paragraph("Extrato", font1);
            document.add(title);

            Paragraph paragraph = new Paragraph("Referente ao mes: " + sdf.format(obj.getDate()) + " | " + "Data de criação: " + sdf.format(date));
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

            PdfPCell descriptionItems = new PdfPCell(new Phrase("Items"));
            descriptionItems.setFixedHeight(16f);
            descriptionItems.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell descriptionValue = new PdfPCell(new Phrase("Valor"));
            descriptionValue.setFixedHeight(16f);
            descriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell descriptionSum = new PdfPCell(new Phrase("Valor Total: R$ " + totalValue));
            descriptionSum.setColspan(2);
            descriptionSum.setFixedHeight(20f);
            descriptionSum.setHorizontalAlignment(Element.ALIGN_CENTER);

            tableDado.addCell(description);
            tableDado.addCell(descriptionItems);
            tableDado.addCell(descriptionValue);

            String list = obj.getDataList();

            String[] itemVetor = list.split("\n");

            for (String vetor : itemVetor) {
                String[] data = vetor.split(",");
                if (data.length == 2) {
                    PdfPCell itemCell = new PdfPCell(new Phrase(data[0].trim()));
                    PdfPCell valueCell = new PdfPCell(new Phrase("R$ " + data[1].trim()));
                    tableDado.addCell(itemCell);
                    tableDado.addCell(valueCell);
                }
            }

            tableDado.addCell(descriptionSum);

            document.add(tableDado);

            document.close();
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public static void createHistoryCard(List<Card> list,Date date, File url) {
        try {
            //title
            Font font1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD, BaseColor.BLACK);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(url));

            document.open();

            Paragraph title = new Paragraph("Historico de Gastos do Cartão", font1);
            document.add(title);

            Paragraph paragraph = new Paragraph("Data de criação: " + sdf.format(date));
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

            PdfPCell descriptionItems = new PdfPCell(new Phrase("Data"));
            descriptionItems.setFixedHeight(16f);
            descriptionItems.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell descriptionValue = new PdfPCell(new Phrase("Valor"));
            descriptionValue.setFixedHeight(16f);
            descriptionValue.setHorizontalAlignment(Element.ALIGN_CENTER);

            tableDado.addCell(description);
            tableDado.addCell(descriptionItems);
            tableDado.addCell(descriptionValue);

            Double sum = 0.0;
            
            for (Card card : list) {      
                PdfPCell itemCell = new PdfPCell(new Phrase(sdf.format(card.getDate())));
                PdfPCell valueCell = new PdfPCell(new Phrase("R$ "+card.getValue()));
                tableDado.addCell(itemCell);
                tableDado.addCell(valueCell);
                sum += card.getValue();
            }

            PdfPCell descriptionSum = new PdfPCell(new Phrase("Valor Total: R$ " + String.format("%.2f", sum)));
            descriptionSum.setColspan(2);
            descriptionSum.setFixedHeight(20f);
            descriptionSum.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tableDado.addCell(descriptionSum);

            document.add(tableDado);

            document.close();
        } catch (FileNotFoundException | DocumentException ex) {
            System.out.println("Error: " + ex);
        }
    }
}
