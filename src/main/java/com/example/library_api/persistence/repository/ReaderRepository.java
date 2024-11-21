package com.example.library_api.persistence.repository;

import com.example.library_api.persistence.entity.ReadersEntity;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class ReaderRepository {

    SessionFactory sessionFactory;

    public ReadersEntity getTop() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from ReadersEntity reader" +
                        " where reader.id = (select prog.readerId from BooksReadersProgressEntity prog" +
                        " where prog.type = 'TAKE'" +
                        " group by prog.readerId" +
                        " having count(prog.readerId) >= (" +
                        " select max(progress.count) from (" +
                        " select count(pr2.readerId) as count from BooksReadersProgressEntity pr2" +
                        " where pr2.type = 'TAKE'" +
                        " group by pr2.readerId" +
                        " ) as progress" +
                        "))", ReadersEntity.class)
                .getSingleResultOrNull();
    }

    public List<ReadersEntity> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select read" +
                        " from ReadersEntity read" +
                        " order by (select count(pr1) from BooksReadersProgressEntity pr1 where pr1.readerId = read.id and pr1.type = 'TAKE') - (select count(pr2) from BooksReadersProgressEntity pr2 where pr2.readerId = read.id and pr2.type = 'RETURN') desc", ReadersEntity.class)
                .list();
    }
}
