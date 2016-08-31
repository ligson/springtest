package springtest.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springtest.dao.UserDao;
import springtest.domain.UserEntity;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ligson on 2016/8/17.
 * dao
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory;

    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        //super.setSessionFactory(sessionFacotry);
        this.sessionFactory = sessionFacotry;
    }

    @Override
    public List<UserEntity> list() {
        return findByExample(new UserEntity());
    }

    @Override
    public List<UserEntity> findByExample(UserEntity entity) {
        Criteria criteria = currentSession().createCriteria(UserEntity.class);
        if (entity.getId() != null) {
            criteria.add(Restrictions.eq("id", entity.getId()));
        }
        if (entity.getUsername() != null) {
            criteria.add(Restrictions.eq("username", entity.getUsername()));
        }
        if (entity.getPassword() != null) {
            criteria.add(Restrictions.eq("password", entity.getPassword()));
        }
        return criteria.list();
        //return getHibernateTemplate().findByExample(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void save(UserEntity entity) {
        currentSession().save(entity);
        currentSession().flush();
        //getHibernateTemplate().save(entity);
    }

    public Session currentSession() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException exception) {
            //防止当前事务没有绑定session
            Object object = TransactionSynchronizationManager.getResource(sessionFactory);
            if (object == null) {
                session = sessionFactory.openSession();
                TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
            } else {
                SessionHolder sessionHolder = (SessionHolder) object;
                session = sessionHolder.getSession();
            }
        }
        return session;
    }

}
