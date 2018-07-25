package cn.sanske.sqlSession;

/**
 * @author sanske
 * @date 2018/7/24 下午3:26
 **/
public interface Excutor {
   <T> T query(String statement,Object parameter);
}
