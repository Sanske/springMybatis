package cn.sanske.mapper;

import cn.sanske.bean.User;

/**
 * @author sanske
 * @date 2018/7/24 上午11:05
 **/
public interface UserMapper {
  User getUserById(String id);
}
