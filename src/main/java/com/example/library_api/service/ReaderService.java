package com.example.library_api.service;

import com.example.library_api.persistence.entity.ReadersEntity;
import com.example.library_api.persistence.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReadersEntity getTop() {
        return readerRepository.getTop();
    }

    public List<ReadersEntity> getAll() {
        return readerRepository.getAll();
    }
}
