package com.example.library_api.service;

import com.example.library_api.persistence.entity.AuthorEntity;
import com.example.library_api.persistence.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorEntity getTopByPeriod(LocalDate from, LocalDate to) {
        return authorRepository.findTopByPeriod(from, to);
    }
}
