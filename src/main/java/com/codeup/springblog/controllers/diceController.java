package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/roll-dice")
public class diceController {

    @GetMapping
    public String rollDice(){
        return "roll-dice";
    }

    @GetMapping("/{num}")
    @ResponseBody
    public String diceRoll(@PathVariable int num, Model model){
        model.addAttribute("number",model);
        model.addAttribute("random", Math.floor(Math.random() * 6) + 1);
       return "Roll dice";
    }
}
