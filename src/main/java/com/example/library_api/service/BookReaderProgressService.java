package com.example.library_api.service;

import com.example.library_api.models.BookReaderChangeRequest;
import com.example.library_api.persistence.entity.BooksReadersProgressEntity;
import com.example.library_api.persistence.entity.ReaderStatusEnum;
import com.example.library_api.persistence.repository.BooksReadersProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookReaderProgressService {

    BooksReadersProgressRepository progressRepository;

    public void readerChange(BookReaderChangeRequest request){
        ReaderStatusEnum currentType = progressRepository.currentType(request.getReaderId(), request.getBookId());
        if (!request.getType().equals(currentType) && !(request.getType().equals(ReaderStatusEnum.RETURN) && currentType == null)){
            BooksReadersProgressEntity entity = BooksReadersProgressEntity.builder()
                    .bookId(request.getBookId())
                    .readerId(request.getReaderId())
                    .type(request.getType()).build();
            progressRepository.save(entity);
        }
    }
}
