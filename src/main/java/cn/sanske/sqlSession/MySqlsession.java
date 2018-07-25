package cn.sanske.sqlSession;

import java.lang.reflect.Proxy;

/**
 * @author sanske
 * @date 2018/7/24 上午11:07
 * 封装来自程序的请求
 **/
public class MySqlsession {
    private Excutor excutor = new Myexcutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return excutor.query(statement, parameter);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> c) {
      //调用方法的入口,通过初始化 MyMapper连接操作数据库和配置文件 -->
      return (T) Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c},
              new MyMapperProxy(myConfiguration, this));
    }

}
