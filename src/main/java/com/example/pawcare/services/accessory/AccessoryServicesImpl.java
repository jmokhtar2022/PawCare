package com.example.pawcare.services.accessory;
import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.repositories.IAccessoryRepository;
import com.example.pawcare.services.fileUpload.FileUploadServices;
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service

public class AccessoryServicesImpl implements AccessoryServices {
    @Autowired
    IAccessoryRepository iAccessoryRepository;
    @Autowired
    FileUploadServices fileUploadServices;

    @Override
    public List<Accessory> retrieveAllAccessories() {

        return iAccessoryRepository.findAll();

    }

    @Override
    public Accessory addAccessory(Accessory accessory) {

        return iAccessoryRepository.save(accessory);
    }

    @Override
    public Accessory updateAccessory(Long idAccessory, Accessory accessory) {
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
        Table table = new Table(new UnitValue[]{UnitValue.createPercentValue(40), UnitValue.createPercentValue(60)});
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

    @Override
    public Page<Accessory> findAll(int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return iAccessoryRepository.findAll(pageable);
    }

    @Override
    public List<Accessory> searchAccessories(String name, Float price) {
        if (name == null && price == null) {
            // If no criteria is provided, return all accessories
            return iAccessoryRepository.findAll();
        } else if (name != null && price == null) {
            // If only name criteria is provided, search by name
            return iAccessoryRepository.findByNameContainingIgnoreCase(name);
        } else if (name == null && price != null) {
            // If only price criteria is provided, search by price
            return iAccessoryRepository.findByPrice(price);
        } else {
            // If both name and price criteria are provided, search by both
            return iAccessoryRepository.findByNameContainingIgnoreCaseAndPrice(name, price);
        }
    }
@Override
    public void ExportAccessoriesToCsv(HttpServletResponse servletResponse) throws IOException {
        List<Accessory> accessories = iAccessoryRepository.findAll();

        String filename = "accessories.csv";
        String csvContent = ConvertAccessoriesToCsv(accessories);

        servletResponse.setContentType("text/csv");
      servletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename);

    servletResponse.getOutputStream().print(csvContent);
    }
@Override
public String ConvertAccessoriesToCsv(List<Accessory> accessories) throws IOException {
    StringWriter writer = new StringWriter();
    CSVFormat format = CSVFormat.DEFAULT
            .withHeader("Name", "Price", "Description","Image")
            .withDelimiter(';');
    try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
        for (Accessory accessory : accessories) {
            csvPrinter.printRecord(accessory.getName(), accessory.getPrice(), accessory.getDescription(), URLEncoder.encode(accessory.getImage(), "UTF-8")
            );
        }
    } catch (IOException e) {
        log.error("Error while writing CSV", e);
    }

    return writer.toString();
}



}
