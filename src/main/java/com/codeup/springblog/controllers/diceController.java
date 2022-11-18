package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roll-dice")
public class diceController {

    @GetMapping
    public String rollDice(){
        return "roll-dice";
    }

    @GetMapping("/{guess}")
    public String diceRoll(@PathVariable String guess, Model model){
        //This casts the random number to an int to get rid of the decimal------------>
        int randomNum = (int) (Math.floor(Math.random() * 6) + 1);
        model.addAttribute("randomNumber",randomNum);
        model.addAttribute("guess", guess) ;
       return "roll-dice";
    }
}
