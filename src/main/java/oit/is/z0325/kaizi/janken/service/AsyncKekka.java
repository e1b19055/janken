package oit.is.z0325.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0325.kaizi.janken.model.Match;
import oit.is.z0325.kaizi.janken.model.MatchMapper;
import oit.is.z0325.kaizi.janken.model.MatchInfo;
import oit.is.z0325.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper mMapper;

  @Autowired
  MatchInfoMapper miMapper;

  public ArrayList<Match> syncShowMatchList() {
    return mMapper.selectAllf();
  }

  public Match syncShowMatch() {
    return mMapper.selectbyid();
  }

  public ArrayList<MatchInfo> syncShowMatchInfosList() {
    return miMapper.selectAllt();
  }

  @Transactional
  public void synckekka(MatchInfo matchinfo) {
    boolean one = false;
    Match match = new Match();
    ArrayList<MatchInfo> matchinfos = miMapper.selectAllt();
    for (int i = 0; i < matchinfos.size(); i++) {
      if (matchinfo.getUser1() == matchinfos.get(i).getUser2() && matchinfo.getUser2() == matchinfos.get(i).getUser1()
          && matchinfos.get(i).isActive()) {
        one = true;
      }
    }
    if (one) {
      MatchInfo matchinfos2 = miMapper.selectbyids(matchinfo.getUser2(), matchinfo.getUser1());
      match.setUser1(matchinfos2.getUser1());
      match.setUser2(matchinfos2.getUser2());
      match.setUser1Hand(matchinfos2.getUser1Hand());
      match.setUser2Hand(matchinfo.getUser1Hand());
      match.setActive(true);
      mMapper.insertMatch(match);
      miMapper.updateMatch(matchinfos2);
      this.dbUpdated = true;
    } else {
      miMapper.insertMatch(matchinfo);
    }
  }

  @Async
  public void asyncShowkekka(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        Match match2 = this.syncShowMatch();
        emitter.send(match2);
        TimeUnit.MILLISECONDS.sleep(1000);
        dbUpdated = false;
        mMapper.updateMatch(match2);
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowMatchsList complete");
  }

}
