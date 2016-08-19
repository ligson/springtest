package springtest.dao;

import springtest.domain.UserEntity;

import java.util.List;

/**
 * Created by trq on 2016/8/17.
 */
public interface UserDao {
    List<UserEntity> list();

    List<UserEntity> findByExample(UserEntity entity);
}
