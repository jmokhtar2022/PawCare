package com.example.pawcare.services.item;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.repositories.IAccessoryRepository;
import com.example.pawcare.repositories.IItemRepository;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
@Service

public class ItemServicesImpl implements IItemServices {


    @Autowired
    IItemRepository iItemRepository;

    @Override
    public List<Item> retrieveAllItems() {

        return iItemRepository.findAll();

    }

    @Override
    public Item addItem(Item item) {
        return iItemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item, Long idItem) {
        return iItemRepository.save(item);

    }

    @Override
    public Item retrieveItemById(Long idItem) {
        return iItemRepository.findById(idItem).get();
    }

    @Override
    public void deleteItem(Long idItem) {
        iItemRepository.deleteById(idItem);
    }

    @Override
    public byte[] generateBillPdf(Item item) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Initialize PDF writer and document
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        Document doc = new Document(pdf, PageSize.A4);

        // Set up fonts
        byte[] fontData = Files.readAllBytes(Paths.get("C:/Users/PCS/PawCare/src/main/resources/fonts/Roboto/Roboto-Bold.ttf"));
        PdfFont fontBold = PdfFontFactory.createFont(fontData, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        byte[] fontData2 = Files.readAllBytes(Paths.get("C:/Users/PCS/PawCare/src/main/resources/fonts/Roboto/Roboto-Regular.ttf"));

        PdfFont fontRegular = PdfFontFactory.createFont(fontData2, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

        // Add PawCare logo
        /* Image logo = new Image(ImageDataFactory.create("images/pawcare-logo.png"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(logo);
*/
        // Add title
        Paragraph title = new Paragraph("Accessory Details")
                .setFont(fontBold)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20)
                .setMarginBottom(20);
        doc.add(title);

        // Add item details
        Table table = new Table(new UnitValue[] { UnitValue.createPercentValue(40), UnitValue.createPercentValue(60) });
        table.setWidth(UnitValue.createPercentValue(80))
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(20)
                .setMarginBottom(20);

        table.addCell(new Cell().add(new Paragraph("Delivery Price:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getDeliveryPrice()))).setFont(fontRegular));

        table.addCell(new Cell().add(new Paragraph("Status:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(item.getOrderstatus().toString())).setFont(fontRegular));

        table.addCell(new Cell().add(new Paragraph("Description:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getDate()))).setFont(fontRegular));


        doc.add(table);

        // Close document
        doc.close();

        return outputStream.toByteArray();
    }

}
