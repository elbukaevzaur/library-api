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
        return session.createQuery("select a from BookAuthor ba" +
                        " left join AuthorEntity a on a.id = ba.authorId" +
                        " where ba.bookId in (" +
                        "   select id from BookEntity" +
                        "   where id in (select prog.bookId from BooksReadersProgressEntity prog" +
                        "               where prog.type = 'TAKE' and" +
                        "                   CAST(prog.createdAt as DATE) >= :from and CAST(prog.createdAt as DATE) <= :to" +
                        "               group by prog.bookId" +
                        "               having count(prog.bookId) >= (" +
                        "                   select max(progress.count) from (" +
                        "                                                       select count(pr2.bookId) as count from BooksReadersProgressEntity pr2" +
                        "                                                       where pr2.type = 'TAKE'" +
                        "                                                         and CAST(pr2.createdAt as DATE) >= :from and CAST(pr2.createdAt as DATE) <= :to" +
                        "                                                       group by pr2.bookId" +
                        "                                                   ) as progress" +
                        "               )))", AuthorEntity.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .setMaxResults(1)
                .getSingleResultOrNull();
    }
}