package oit.is.z0325.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches where user2Hand = #{user2Hand}")
  ArrayList<Match> selectAllm(String user2Hand);

  @Select("SELECT * from matches where isActive = FALSE")
  ArrayList<Match> selectAllf();

  @Select("SELECT * from matches where isActive = TRUE")
  Match selectbyid();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("UPDATE matches SET isActive=FALSE WHERE user1 = #{user1} and user2 = #{user2} and user1Hand = #{user1Hand} and user2Hand = #{user2Hand} and isActive = TRUE")
  void updateMatch(Match match);

}
