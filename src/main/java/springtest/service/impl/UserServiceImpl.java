package springtest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import springtest.dao.UserDao;
import springtest.domain.UserEntity;
import springtest.service.UserService;

import java.util.List;

/**
 * Created by trq on 2016/8/17.
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserEntity> list() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        List<UserEntity> entitys = userDao.findByExample(entity);
        if (!CollectionUtils.isEmpty(entitys)) {
            return entitys.get(0);
        } else {
            throw new UsernameNotFoundException(username + " not found");
        }
    }
}
