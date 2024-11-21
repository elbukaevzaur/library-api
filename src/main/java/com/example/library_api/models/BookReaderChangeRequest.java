package com.example.library_api.models;

import com.example.library_api.persistence.entity.ReaderStatusEnum;
import lombok.Data;

@Data
public class BookReaderChangeRequest {
    private Long readerId;
    private Long bookId;
    private ReaderStatusEnum type;
}
