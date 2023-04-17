package com.example.pawcare.services.accessory;
import com.example.pawcare.entities.Accessory;
import com.example.pawcare.repositories.IAccessoryRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service

public class AccessoryServicesImpl implements AccessoryServices {
    @Autowired
    IAccessoryRepository iAccessoryRepository;

    @Override
    public List<Accessory> retrieveAllAccessories() {

        return iAccessoryRepository.findAll();

    }

    @Override
    public Accessory addAccessory(Accessory accessory) {

        return iAccessoryRepository.save(accessory);
    }

    @Override
    public Accessory updateAccessory( Long idAccessory,Accessory accessory ) {
     //   return iAccessoryRepository.save(accessory);
        Accessory updatedAccessory = iAccessoryRepository.findById(idAccessory).get();
        updatedAccessory.setName(accessory.getName());
        updatedAccessory.setPrice(accessory.getPrice());
        updatedAccessory.setDescription(accessory.getDescription());
        updatedAccessory.setImage(accessory.getImage());
        return iAccessoryRepository.save(updatedAccessory);

    }

    @Override
    public Accessory retrieveAccessoryById(Long idAccessory) {
        return iAccessoryRepository.findById(idAccessory).get();
    }

    @Override
    public void deleteAccessory(Long idAccessory) {
        iAccessoryRepository.deleteById(idAccessory);
    }

    @Override
    public byte[] generatePdf(Accessory accessory) throws IOException {

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

        table.addCell(new Cell().add(new Paragraph("Price:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getPrice()))).setFont(fontRegular));

        table.addCell(new Cell().add(new Paragraph("Name:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(accessory.getName().toString())).setFont(fontRegular));

        table.addCell(new Cell().add(new Paragraph("Description:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getDescription()))).setFont(fontRegular));


        doc.add(table);

        // Close document
        doc.close();

        return outputStream.toByteArray();
    }

}
