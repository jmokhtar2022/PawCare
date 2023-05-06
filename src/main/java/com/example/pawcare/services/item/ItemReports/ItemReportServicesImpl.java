package com.example.pawcare.services.item.ItemReports;


import com.example.pawcare.entities.Item;
import com.example.pawcare.entities.OrderStatus;
import com.example.pawcare.repositories.IItemRepository;
import com.example.pawcare.services.email.MailSender;
import com.example.pawcare.services.item.ItemReports.ItemReportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemReportServicesImpl implements ItemReportServices {
    @Autowired
    IItemRepository iItemRepository;
    @Autowired
    private MailSender mailSender;

    @Override
    @Scheduled(cron = "0 0 10 * * ?") //Daily report

    public void generateDailyRevenueReport() {
        List<Item> orders = iItemRepository.findByOrderstatusIn(Arrays.asList(OrderStatus.Paid, OrderStatus.Delivery_in_progress));

        // Calculate total revenue from paid orders
        double paidRevenue = orders.stream()
                .filter(o -> o.getOrderstatus()== OrderStatus.Paid)
                .mapToDouble(Item::getTotalItem)
                .sum();

        // Calculate total revenue from unpaid orders
        double unpaidRevenue = orders.stream()
                .filter(o -> o.getOrderstatus() == OrderStatus.Delivery_in_progress)
                .mapToDouble(Item::getTotalItem)
                .sum();

        // Generate report
        String report = String.format("Daily Revenue Report:\n\nPaid Revenue: %.2f\nUnpaid Revenue: %.2f", paidRevenue, unpaidRevenue);

        // Send report to admin via email
        String to = "liliyyy1758@gmail.com";
        String subject = "Daily Report";
        String body = "Here's your daily report..."+report;
        mailSender.sendEmail(to, subject, body);

    }
}
