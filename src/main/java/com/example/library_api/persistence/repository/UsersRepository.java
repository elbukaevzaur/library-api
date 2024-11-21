package com.example.library_api.persistence.repository;

import com.example.library_api.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class UsersRepository {

    SessionFactory sessionFactory;

    public UserEntity findByLogin(String login){
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        UserEntity user = session.createQuery("from UserEntity where login = :login", UserEntity.class)
                .setParameter("login", login).getSingleResultOrNull();
       return user;
    };
}
