package com.example.pawcare.services.item;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
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
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public interface IItemServices {

    public List<Item> retrieveAllItems();

    public Item addItem(Item item);

    public Item updateItem(Item item, Long idItem);

    public Item retrieveItemById(Long idItem);


    public byte[] generateBillPdf(Item item) throws IOException ;
    public ResponseEntity<Item> createOrder(@RequestBody Item item, @RequestParam("idCart") Long idCart) ;
    public void deleteItem(long idItem) ;


    }
