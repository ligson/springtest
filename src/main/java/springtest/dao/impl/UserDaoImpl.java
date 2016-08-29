package springtest.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import springtest.dao.UserDao;
import springtest.domain.UserEntity;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by trq on 2016/8/17.
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateTemplate implements UserDao {

    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }

    @Override
    public List<UserEntity> list() {
        return findByExample(new UserEntity());
    }

    @Override
    public List<UserEntity> findByExample(UserEntity entity) {
        return super.findByExample(entity);
    }

    @Override
    public void save(UserEntity entity) {
        super.save(entity);
    }
}
