package cn.sanske.sqlSession;

import cn.sanske.config.Function;
import cn.sanske.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author sanske
 * @date 2018/7/24 上午11:07
 **/
public class MyMapperProxy implements InvocationHandler {
  private MySqlsession mySqlsession;
  private MyConfiguration myConfiguration;

  public MyMapperProxy(MyConfiguration myConfiguration, MySqlsession mySqlsession) {
     this.myConfiguration = myConfiguration;
     this.mySqlsession =mySqlsession;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      MapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
      if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
          return null;
      }

      List<Function> list = readMapper.getList();
      if(null != list || 0!= list.size()) {
        for(Function function : list) {
          if(method.getName().equals(function.getFuncName())) {
              return mySqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
          }

        }
      }
      return null;

  }
}
