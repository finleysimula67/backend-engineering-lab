package com.example.state_comparision.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping ("/api")
public class ComparisionController {

   @GetMapping("/stateful/visit")
   public String statefulVisit(HttpSession session) {

       Integer visits = (Integer) session.getAttribute("visits");

       if (visits == null) {
           visits = 0;
       }

       visits++;

       session.setAttribute("visits", visits);

       return "You have visited this endpoint "
               + visits + " times in this session.";
   }

   @GetMapping ("/stateless/add")
   public int statelessAdd(
          @RequestParam  int a ,
          @RequestParam  int b )
    {
       return a + b;
    }
}
