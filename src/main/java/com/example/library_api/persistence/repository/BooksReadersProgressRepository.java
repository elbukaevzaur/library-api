package com.example.library_api.persistence.repository;

import com.example.library_api.persistence.entity.BooksReadersProgressEntity;
import com.example.library_api.persistence.entity.ReaderStatusEnum;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class BooksReadersProgressRepository {
    SessionFactory sessionFactory;

    public BooksReadersProgressEntity save(BooksReadersProgressEntity booksReadersProgressEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(booksReadersProgressEntity);
        session.getTransaction().commit();
        session.close();
        return booksReadersProgressEntity;
    };

    public ReaderStatusEnum currentType(Long readerId, Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        ReaderStatusEnum currentType = session.createQuery("select bookReader.type from BooksReadersProgressEntity bookReader" +
                " where createdAt = (select max(bp.createdAt) from BooksReadersProgressEntity bp where bp.readerId = :readerId and" +
                " bp.bookId = :bookId)" +
                " and bookId = :bookId and readerId = :readerId", ReaderStatusEnum.class)
                .setParameter("readerId", readerId)
                .setParameter("bookId", bookId)
                .getSingleResultOrNull();
        return currentType;
    }
}
