package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;

public class JTableToPDF {

    public static void export(JTable table, String pdfFile) throws Exception {

        TableModel model = table.getModel();   // ALWAYS use the model
        int columns = model.getColumnCount();
        int rows = model.getRowCount();

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();

        PdfPTable pdfTable = new PdfPTable(columns);
        pdfTable.setWidthPercentage(100);

        // ----- HEADERS FROM MODEL -----
        for (int col = 0; col < columns; col++) {
            PdfPCell header = new PdfPCell(new Phrase(model.getColumnName(col)));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pdfTable.addCell(header);
        }

        // ----- ROWS FROM MODEL -----
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Object value = model.getValueAt(row, col);
                pdfTable.addCell(new Phrase(value == null ? "" : value.toString()));
            }
        }

        document.add(pdfTable);
        document.close();

        System.out.println("PDF created: " + pdfFile);
    }
}





