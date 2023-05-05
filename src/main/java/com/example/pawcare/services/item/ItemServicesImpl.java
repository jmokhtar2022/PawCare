package com.example.pawcare.services.item;
import com.example.pawcare.entities.*;
import com.example.pawcare.repositories.IItemRepository;
import com.example.pawcare.repositories.IUserRepository;
import com.example.pawcare.services.email.MailSender;
import com.example.pawcare.services.stripe.StripeServiceImpl;
import com.example.pawcare.services.cart.CartServices;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
@Slf4j
public class ItemServicesImpl implements IItemServices {


    @Autowired
    IItemRepository iItemRepository;
    @Autowired
    CartServices cartServices;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    StripeServiceImpl stripeService;
    @Autowired
    MailSender mailSender;
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
    public byte[] generateBillPdf(Long itemId) throws IOException {
        Item item=retrieveItemById(itemId);
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

        /* Add PawCare logo
         Image logo = new Image(ImageDataFactory.create("images/pawcare-logo.png"))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(logo);*/
        // Add title
        Paragraph title = new Paragraph("Payment Details")
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
        List<Accessory> accessoriesList = item.getCart().getAccessories();

        for(Accessory accessory: accessoriesList)
        {

            table.addCell(new Cell().add(new Paragraph("Accessory image:")).setFont(fontBold));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getImage()))).setFont(fontRegular));

            table.addCell(new Cell().add(new Paragraph("Accessory name:")).setFont(fontBold));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getName()))).setFont(fontRegular));
            table.addCell(new Cell().add(new Paragraph("Accessory price:")).setFont(fontBold));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getPrice()))).setFont(fontRegular));
            table.addCell(new Cell().add(new Paragraph("Accessory description:")).setFont(fontBold));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(accessory.getDescription()))).setFont(fontRegular));


        }
        table.addCell(new Cell().add(new Paragraph("Delivery Price:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getDeliveryPrice()))).setFont(fontRegular));
        table.addCell(new Cell().add(new Paragraph("Total paid :")).setFont(fontBold));

        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getTotalItem()))).setFont(fontRegular));
        table.addCell(new Cell().add(new Paragraph("Order status:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(item.getOrderstatus().toString())).setFont(fontRegular));

        table.addCell(new Cell().add(new Paragraph("Date:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getDate()))).setFont(fontRegular));
        table.addCell(new Cell().add(new Paragraph("Username:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getUser().getUsername()))).setFont(fontRegular));
        table.addCell(new Cell().add(new Paragraph("User email:")).setFont(fontBold));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getUser().getEmail()))).setFont(fontRegular));
        doc.add(table);

        // Close document
        doc.close();
        // Save PDF to a file on the server's file system
        String fileName = "bill" + itemId + ".pdf"; // set file name
        String filePath = "/C:/pawcarepdf/" + fileName; // set file path
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(outputStream.toByteArray());
        fileOutputStream.close();

        return outputStream.toByteArray();
    }

    @Override
    public ResponseEntity<Item> createOrderFromCart(Long idCart, Long userId) {
        Cart cart = cartServices.GetCartById(idCart);

        Item order = new Item();
        order.setCart(cart);
        // set default values
        order.setDate(LocalDate.now());
        order.setDeliveryPrice(7.0f);
        order.setTrackingCode("");
        order.setOrderstatus(OrderStatus.Delivery_in_progress);
        order.setTotalItem(order.getCart().getTotalCart() + order.getDeliveryPrice());

        User user = iUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        order.setUser(user);

        // set the accessories of the order to the same as the cart
        Item savedItem = addItem(order);
        // update cart to include the item
        cart.setItem(savedItem);
        cartServices.updateCart(cart, idCart);
        return ResponseEntity.ok(savedItem);
    }



    @Transactional
    @Override
    public void deleteItem(long idItem) {
        Optional<Item> optionalItem = iItemRepository.findById(idItem);

            Item order = optionalItem.get();
            Cart cart = order.getCart();
            if (cart != null) {
                cart.setItem(null);
            }
            iItemRepository.delete(order);
        }

        @Override
        public Map<String, Object> payOrder(Long orderId,
                                            Long userId,
                                            String cardNumber,
                                            String expMonth,
                                            String expYear,
                                            String cvc) throws StripeException {
            // Retrieve the order
            Item order = retrieveItemById(orderId);
            if (order == null) {
                throw new RuntimeException("Order not found");
            }

            // Calculate the total order amount
            float totalAmount = order.getTotalItem();

            // Create Stripe customer
            String customerId = stripeService.createStripeCustomer(userId);

            // Add card to customer
            stripeService.createCardForCustumorStripe(customerId, cardNumber, expMonth, expYear, cvc);

            // Charge the customer
            stripeService.chargeCustomer(customerId,(int) (totalAmount * 100));

            // Update the order status as paid
            updateOrderStatusAsPaid(orderId);

            // Send an email to the user
            String email = getUserByIdOrder(orderId).getEmail();
            String subject = "Your order has been paid";
            String body = "Thank you for your purchase ! Your payment of " + totalAmount + " DT has been received and your order will be shipped soon.";
            mailSender.sendEmail(email, subject, body);

            // Return success message as a JSON object
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment Successful!");

            return response;
        }

        /*public Map<String, Object> payOrder(Long orderId,
                                            Long userId,
                                            String cardNumber,
                                            String expMonth,
                                            String expYear,
                                            String cvc) throws StripeException {
            // Retrieve the order
            Item order = retrieveItemById(orderId);
            if (order == null) {
                throw new RuntimeException("Order not found");
            }

            // Calculate the total order amount
            float totalAmount = order.getTotalItem();

            // Create Stripe customer
            String customerId = stripeService.createStripeCustomer(userId);

            // Add card to customer
            stripeService.createCardForCustumorStripe(customerId, cardNumber, expMonth, expYear, cvc);

            // Charge the customer
            stripeService.chargeCustomer(customerId,(int) (totalAmount * 100));

            // Update the order status as paid
            updateOrderStatusAsPaid(orderId);

            // Return success message as a JSON object
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Payment Successful!");

            return response;
        }

*/
    @Override
    public void updateOrderStatusAsPaid(Long orderId) {
        Optional<Item> orderOptional = iItemRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Item order = orderOptional.get();
            order.setOrderstatus(OrderStatus.Paid);
            iItemRepository.save(order);
        }
    }

@Override
public List<Accessory> getAccessoriesByOrderId(Long orderId) {
    return iItemRepository.findAccessoriesByOrderId(orderId);
}
    @Override
    public User getUserByIdOrder(Long idOrder) {
        Item order = iItemRepository.findByIdItem(idOrder);
        if (order == null) {
            return null;
        } else {
            User user = order.getUser();
            return user;
        }
    }
    @Override
    public List<Item> getPaidOrders() {
        OrderStatus orderStatus = OrderStatus.Paid;

        return iItemRepository.findByOrderstatus(orderStatus);
    }

    @Override

    public List<Item> getNotPaidOrders() {
        OrderStatus orderStatus = OrderStatus.Delivery_in_progress;


        return iItemRepository.findByOrderstatus(orderStatus);    }

}
