package cn.sanske.sqlSession;

import cn.sanske.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author sanske
 * @date 2018/7/24 下午3:22
 **/
public class Myexcutor implements Excutor {
    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1, parameter.toString());
            set = pre.executeQuery();
            User user = new User();
            while (set.next()) {
                user.setUser_id(set.getString(1));
                user.setName(set.getString(2));
                user.setUser_mobile(set.getString(3));
            }
            return (T) user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return null;
    }


    private Connection getConnection() {

        try {
            Connection connection = xmlConfiguration.build("config.xml");
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("parse config.xml error");
        }
    }
}
