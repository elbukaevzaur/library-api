package com.example.library_api.persistence.repository;

import com.example.library_api.persistence.entity.AuthorEntity;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@AllArgsConstructor
@Repository
public class AuthorRepository {

    SessionFactory sessionFactory;

    public AuthorEntity findTopByPeriod(LocalDate from, LocalDate to) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select author from ( " +
                        "select (select count(progress) from BooksReadersProgressEntity progress " +
                        "where progress.bookId = bookAuthor.bookId " +
                        "and progress.type = 'TAKE' " +
                        "and CAST(progress.createdAt as DATE) >= :from and CAST(progress.createdAt as DATE) <= :to " +
                        ") as count, subAuthor.id as authorId from AuthorEntity subAuthor " +
                        "right join BookAuthor bookAuthor on bookAuthor.authorId = subAuthor.id) " +
                        "as authorCount " +
                        "left join AuthorEntity author on author.id = authorCount.authorId " +
                        "order by authorCount.count desc", AuthorEntity.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .setMaxResults(1)
                .getSingleResultOrNull();
    }
}