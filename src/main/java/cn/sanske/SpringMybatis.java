package cn.sanske;

import cn.sanske.bean.User;
import cn.sanske.mapper.UserMapper;
import cn.sanske.sqlSession.MySqlsession;

/**
 * @author sanske
 * @date 2018/7/24 上午9:59
 * Configuration(配置)-->SqlSessionFactory（实例化对象）-->sqlSession（基本操作封装）
 *  -->Executor(操作数据库)--> mapper statement(映射操作)
 **/
public class SpringMybatis {
  public static void main(String[] args) {
    MySqlsession sqlsession = new MySqlsession();
    UserMapper mapper = sqlsession.getMapper(UserMapper.class);

    User user = mapper.getUserById("2b7f6bc8e67647aa99080ed97d1cb31f");
    System.out.println("select from nu_user:" + user);
  }
}
