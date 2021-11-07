package oit.is.z0325.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * from matchinfo where isActive = TRUE")
  ArrayList<MatchInfo> selectAllt();

  @Select("SELECT * from matchinfo where user1 = #{id1} and user2 = #{id2} and isActive = TRUE")
  MatchInfo selectbyids(Integer id1, Integer id2);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(MatchInfo matchinfo);

  @Update("UPDATE matchinfo SET isActive=FALSE WHERE user1 = #{user1} and user2 = #{user2} and user1Hand = #{user1Hand} and isActive = TRUE")
  void updateMatch(MatchInfo matchinfo);

}
