package com.example.pawcare.services.item;

import com.example.pawcare.entities.Accessory;
import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.OrderStatus;
import com.example.pawcare.entities.User;
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
import com.stripe.exception.StripeException;
import lombok.Setter;
import org.springframework.data.repository.query.Param;
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
import java.util.Map;

public interface IItemServices {

    public List<Item> retrieveAllItems();

    public Item addItem(Item item);

    public Item updateItem(Item item, Long idItem);

    public Item retrieveItemById(Long idItem);

   // public byte[] generateBillPdf(Item item) throws IOException;

    //public ResponseEntity<Item> createOrderFromCart(Item order, Long idCart, Long idUser) ;
    public void deleteItem(long idItem);

    public ResponseEntity<Item> createOrderFromCart(Long idCart, Long userId);

    public Map<String, Object> payOrder(Long orderId,
                                        Long userId,
                                        String cardNumber,
                                        String expMonth,
                                        String expYear,
                                        String cvc) throws StripeException;

    public void updateOrderStatusAsPaid(Long orderId);
    public List<Accessory> getAccessoriesByOrderId(Long orderId) ;
    public User getUserByIdOrder(Long idOrder) ;
    public byte[] generateBillPdf(Long itemId) throws IOException ;


    }