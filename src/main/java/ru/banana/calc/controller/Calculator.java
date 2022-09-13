package ru.banana.calc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class Calculator {

    @GetMapping("/calc")
    public String calculator(@RequestParam("a") int a, @RequestParam("b") int b,
                             @RequestParam("action") String action, Model model) {
        double result = switch (action) {
            case "multipltcation" -> a * b;
            case "division" -> a / (double) b;
            //деление на вещественное число даст дробную часть
            case "subtraction" -> a - b;
            case "addition" -> a + b;
            default -> 404;
            //что бы result был инициализирован, даже в случае некорректного ввода
        };

        model.addAttribute("result", result);

        return "first/calc";
    }
}
