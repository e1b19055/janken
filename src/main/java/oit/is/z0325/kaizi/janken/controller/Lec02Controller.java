package oit.is.z0325.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0325.kaizi.janken.model.Entry;

@Controller
public class Lec02Controller {

  @Autowired
  private Entry entry;
  Entry newEntry = new Entry();

  @GetMapping("/lec02janken")
  public String janken(@RequestParam String hand, ModelMap model) {
    String Win = "You Win!";
    String Lose = "You Lose";
    String Draw = "Draw";
    model.addAttribute("hand", hand);
    model.addAttribute("cpu", "Gu");
    if (hand.equals("Pa")) {
      model.addAttribute("Win", Win);
    } else if (hand.equals("Choki")) {
      model.addAttribute("Lose", Lose);
    } else {
      model.addAttribute("Draw", Draw);
    }
    return "lec02.html";

  }

  @PostMapping("/lec02")
  public String name(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    return "lec02.html";
  }

  @GetMapping("/lec02")
  public String entry(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    return "lec02.html";
  }

}
