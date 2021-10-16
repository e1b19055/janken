package oit.is.z0325.kaizi.janken.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Entry {
  ArrayList<String> users = new ArrayList<>();
  int entryNo = 1;

  public void addUser(String name) {
    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    this.users.add(name);
  }

  public int getRoomNo() {
    return entryNo;
  }

  public void setRoomNo(int entryNo) {
    this.entryNo = entryNo;
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }

}
