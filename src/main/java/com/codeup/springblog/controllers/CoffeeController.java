package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Coffee;
import com.codeup.springblog.models.Supplier;
import com.codeup.springblog.repository.CoffeeRepository;
import com.codeup.springblog.repository.SupplierRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeRepository coffeeDao;
    private final SupplierRepository suppliersDao;

    public CoffeeController(CoffeeRepository coffeeDao, SupplierRepository suppliersDao){
        this.coffeeDao = coffeeDao;
        this.suppliersDao = suppliersDao;
    }

    @GetMapping
    public String coffee(){
        return "Coffee";
    }

    @GetMapping("/{roast}")
    public String roast(@RequestParam(name="roast") String roast, @RequestParam(name="origin") String origin, @RequestParam(name="brand") String brand){
        Coffee coffee = new Coffee(roast,origin,brand);
        coffeeDao.save(coffee);
        return "Coffee";
    }
    @PostMapping
    public String signup(@RequestParam(name="email")String email, Model model){
        model.addAttribute("email", email);
        return "Coffee";
    }

    @GetMapping("/suppliers")
    public String showSuppliersForm(Model model){
        List<Supplier> suppliers = suppliersDao.findAll();
        model.addAttribute("suppliers", suppliers);
        return "/suppliers";
    }

    @PostMapping("/suppliers")
    public String insertSupplier(@RequestParam(name="name") String name){
        Supplier supplier = new Supplier(name);
        suppliersDao.save(supplier);
        return "redirect:/coffee/suppliers";
    }
}
