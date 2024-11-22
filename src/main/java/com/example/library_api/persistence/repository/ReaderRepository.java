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
        return session.createQuery("select reader from (" +
                        "select ( " +
                        "select count(progress) from BooksReadersProgressEntity progress " +
                        "where progress.readerId = subReader.id " +
                        "and progress.type = 'TAKE' " +
                        ") as count, subReader.id as readerId from ReadersEntity subReader ) as readerCount " +
                        "left join ReadersEntity reader on reader.id = readerCount.readerId " +
                        "order by readerCount.count desc", ReadersEntity.class)
                .setMaxResults(1)
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
