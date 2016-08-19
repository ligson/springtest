package springtest.dao.impl;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import springtest.dao.UserDao;
import springtest.domain.UserEntity;

import java.util.List;

/**
 * Created by trq on 2016/8/17.
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateTemplate implements UserDao {
    @Override
    public List<UserEntity> list() {
        return findByExample(new UserEntity());
    }

    @Override
    public List<UserEntity> findByExample(UserEntity entity) {
        return findByExample(entity);
    }
}
