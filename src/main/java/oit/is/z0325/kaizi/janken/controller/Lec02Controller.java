package oit.is.z0325.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0325.kaizi.janken.model.Entry;
import oit.is.z0325.kaizi.janken.model.User;
import oit.is.z0325.kaizi.janken.model.UserMapper;
import oit.is.z0325.kaizi.janken.model.Match;
import oit.is.z0325.kaizi.janken.model.MatchMapper;

@Controller
public class Lec02Controller {

  @Autowired
  private Entry entry;
  Entry newEntry = new Entry();

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/lec02janken")
  public String janken(@RequestParam String hand, ModelMap model) {
    String Win = "You Win!";
    String Lose = "You Lose";
    String Draw = "Draw";
    model.addAttribute("hand", hand);
    model.addAttribute("cpu", "Gu");
    if (hand.equals("Pa")) {
      model.addAttribute("Win", Win);
      ArrayList<Match> matches = matchMapper.selectAllm(hand);
      model.addAttribute("matches", matches);
    } else if (hand.equals("Choki")) {
      model.addAttribute("Lose", Lose);
      ArrayList<Match> matches = matchMapper.selectAllm(hand);
      model.addAttribute("matches", matches);
    } else {
      model.addAttribute("Draw", Draw);
      ArrayList<Match> matches = matchMapper.selectAllm(hand);
      model.addAttribute("matches", matches);
    }
    ArrayList<User> users = userMapper.selectAllu();
    model.addAttribute("users", users);
    return "lec02.html";

  }

  @PostMapping("/lec02")
  public String name(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
    ArrayList<User> users = userMapper.selectAllu();
    model.addAttribute("users", users);
    return "lec02.html";
  }

  @GetMapping("/lec02")
  public String entry(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    ArrayList<User> users = userMapper.selectAllu();
    model.addAttribute("users", users);
    return "lec02.html";
  }

  @GetMapping("/match")
  public String sample23(@RequestParam Integer id, ModelMap model) {
    User user = userMapper.selectById(id);
    model.addAttribute("user", user);
    return "match.html";
  }

  @GetMapping("/matchjanken")
  public String addjanken(@RequestParam String hand, ModelMap model) {
    Match match = new Match();
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
    match.setUser1(2);
    match.setUser2(1);
    match.setUser1Hand("Gu");
    match.setUser2Hand(hand);
    matchMapper.insertMatch(match);
    return "match.html";
  }

}
