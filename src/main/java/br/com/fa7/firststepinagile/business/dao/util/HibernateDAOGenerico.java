package br.com.fa7.firststepinagile.business.dao.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public class HibernateDAOGenerico<T, ID extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    public org.hibernate.Session session() {
        return sessionFactory.getCurrentSession();
    }

	public HibernateDAOGenerico() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Class<T> persistentClass;

    public Class<T> clazz() {
        return this.persistentClass;
    }

    public void delete(T entity) {
        session().delete(entity);
    }

    public T findById(ID id) {
        return (T) session().get(clazz(), id);
    }

    public List<T> listAll() {
        Criteria crit = session().createCriteria(clazz());
        return crit.list();
    }

    public int size() {
        Criteria crit = session().createCriteria(clazz());
        crit.setProjection(Projections.rowCount());
        Object result = crit.uniqueResult();
        Long size = (Long) result;
        return size.intValue();
    }

    public T save(T entity) {
        session().saveOrUpdate(entity);
        return entity;
    }

    public List<T> findByCriteriaReturnList(Criterion... criterion) {
        return findByCriteria(null, criterion);
    }

    public T findByCriteriaReturnUniqueResult(Criterion... criterion) {
        return findByCriteriaReturnUniqueResult(null, criterion);
    }

    public List<T> findByCriteria(Order order, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }

    public T findByCriteriaReturnUniqueResult(Order order, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return (T) crit.uniqueResult();
    }

    public T findByCriteriaReturnUniqueResult(Order order, int offSet, int size, Criterion... criterion) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.setFirstResult(offSet);
        crit.setMaxResults(size);
        for (Criterion c : criterion) {
            crit.add(c);
        }
        if (order != null) {
            crit.addOrder(order);
        }
        return (T) crit.uniqueResult();
    }

    public List<T> findByCriteria(Order order) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }

    public List<T> findByCriteria(Order order, int offSet, int size) {
        Criteria crit = session().createCriteria(clazz());
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.setFirstResult(offSet);
        crit.setMaxResults(size);
        if (order != null) {
            crit.addOrder(order);
        }
        return crit.list();
    }
}
