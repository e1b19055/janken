package oit.is.z0325.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from user where id = #{id}")
  User selectById(int id);

  @Select("SELECT * from user")
  ArrayList<User> selectAllu();

}
