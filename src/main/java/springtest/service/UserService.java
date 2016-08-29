package springtest.service;

import springtest.domain.UserEntity;

import java.util.List;

/**
 * Created by trq on 2016/8/17.
 */
public interface UserService {
    List<UserEntity> list();

    UserEntity register(String username, String password);
}
