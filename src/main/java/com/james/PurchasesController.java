package com.james;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

            firstScanner.nextLine();
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

            secondScanner.nextLine();
            while (secondScanner.hasNextLine()) {
                String purLine = secondScanner.nextLine();
                String[] columns = purLine.split(",");
                String date = columns[1];
                String creditCard = columns[2];
                String cvv = columns[3];
                String category = columns[4];
                int id = Integer.valueOf(columns[0]);
                Customer customer = customers.findById(id);
                Purchase purchase = new Purchase(date, creditCard, cvv, category, customer);
                purchases.save(purchase);

            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category, Integer page) {
        page = (page == null) ? 0 : page;

        PageRequest pr = new PageRequest(page, 10);

        Page<Purchase> prchs;
        if (category != null) {
            prchs = purchases.findByCategory(pr, category);
        }
        else {
            prchs = purchases.findAll(pr);

        }

        model.addAttribute("category", category);
        model.addAttribute("nextPage", page + 1);

        model.addAttribute("showNext", prchs.hasNext());
        model.addAttribute("purchases", prchs);

        model.addAttribute("prevPage", page - 1);
        model.addAttribute("showPrev", prchs.hasPrevious());
        return "home";
    }
}



