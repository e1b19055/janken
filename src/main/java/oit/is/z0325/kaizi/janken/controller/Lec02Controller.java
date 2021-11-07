package oit.is.z0325.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0325.kaizi.janken.model.Entry;
import oit.is.z0325.kaizi.janken.model.User;
import oit.is.z0325.kaizi.janken.model.UserMapper;
import oit.is.z0325.kaizi.janken.model.Match;
import oit.is.z0325.kaizi.janken.model.MatchMapper;
import oit.is.z0325.kaizi.janken.model.MatchInfo;
import oit.is.z0325.kaizi.janken.model.MatchInfoMapper;
import oit.is.z0325.kaizi.janken.service.AsyncKekka;

@Controller
public class Lec02Controller {

  @Autowired
  private Entry entry;
  Entry newEntry = new Entry();

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchinfoMapper;

  @Autowired
  AsyncKekka kekka;

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
    ArrayList<Match> matches = matchMapper.selectAllf();
    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    ArrayList<MatchInfo> actives = matchinfoMapper.selectAllt();
    model.addAttribute("actives", actives);
    return "lec02.html";
  }

  @GetMapping("/lec02")
  public String entry(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    ArrayList<User> users = userMapper.selectAllu();
    model.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllf();
    model.addAttribute("matches", matches);
    ArrayList<MatchInfo> actives = matchinfoMapper.selectAllt();
    model.addAttribute("actives", actives);
    return "lec02.html";
  }

  @GetMapping("/match")
  public String sample23(@RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    User user = userMapper.selectById(id);
    model.addAttribute("user", user);
    User me = userMapper.selectByname(loginUser);
    model.addAttribute("me", me);
    return "match.html";
  }

  @GetMapping("/matchjanken")
  @Transactional
  public String addjanken(@RequestParam Integer id1, Integer id2, String hand, ModelMap model) {
    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(id1);
    matchinfo.setUser2(id2);
    matchinfo.setUser1Hand(hand);
    matchinfo.setActive(true);
    kekka.synckekka(matchinfo);
    return "wait.html";
  }

  @GetMapping("/wait")
  public SseEmitter waitupdate() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncShowkekka(sseEmitter);
    return sseEmitter;
  }

}
