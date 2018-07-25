package cn.sanske.bean;

/**
 * @author sanske
 * @date 2018/7/24 上午11:04
 **/
public class User {
    private String user_id;
    private String name;
    private String user_mobile;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                '}';
    }
}
