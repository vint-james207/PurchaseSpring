package com.james;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by jamesyburr on 6/22/16.
 */
@Controller
public class PurchasesController {
    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @PostConstruct
    public void init() throws FileNotFoundException {
        if (customers.count() == 0) {
            File file = new File("customers.csv");
            Scanner firstScanner = new Scanner(file);

            firstScanner.hasNextLine();
            while (firstScanner.hasNextLine()) {
                String cusLine = firstScanner.nextLine();
                String[] columns = cusLine.split(",");
                String name = columns[0];
                String email = columns[1];
                Customer customer = new Customer(name, email);
                customers.save(customer);
            }
        }

        if (purchases.count() == 0) {
            File secondFile = new File("purchases.csv");
            Scanner secondScanner = new Scanner(secondFile);

            secondScanner.hasNextLine();
            while (secondScanner.hasNextLine()) {
                String purLine = secondScanner.nextLine();
                String[] columns = purLine.split(",");
                int id = Integer.valueOf(columns[0]);
                String date = columns[1];
                String creditCard = columns[2];
                String cvv = columns[3];
                String category = columns[4];
                Customer customer = customers.findById(id);
                Purchase purchase = new Purchase(date, creditCard, cvv, category, customer);
                purchases.save(purchase);

            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category) {
        Iterable<Purchase> prchs;
        if (category != null) {
            prchs = purchases.findByCategory(category);
        }
        else {
            prchs = purchases.findAll();
        }

        model.addAttribute("purchases", prchs);
        return "home";
    }
}



