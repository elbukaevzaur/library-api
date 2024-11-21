package com.example.library_api.controller;

import com.example.library_api.models.BookReaderChangeRequest;
import com.example.library_api.persistence.entity.AuthorEntity;
import com.example.library_api.persistence.entity.ReadersEntity;
import com.example.library_api.service.AuthorService;
import com.example.library_api.service.BookReaderProgressService;
import com.example.library_api.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("books")
@AllArgsConstructor
public class BooksController {
    private final BookReaderProgressService bookReaderProgressService;
    private final AuthorService authorService;
    private final ReaderService readerService;

    @Operation(summary = "Транзакция с книгой")
    @PostMapping(value = "reader/change")
    public void changeBook(@RequestBody BookReaderChangeRequest request){
        bookReaderProgressService.readerChange(request);
    }

    @Operation(summary = "Самый популярный автор за определенный диапазон дат")
    @GetMapping(value = "authors/top")
    public AuthorEntity getAuthorTop(@RequestParam("from")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate from,
                                     @RequestParam("to")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate to) {
        return authorService.getTopByPeriod(from, to);
    }

    @Operation(summary = "Самый читающий клиент")
    @GetMapping(value = "readers/top")
    public ReadersEntity getReaderTop() {
        return readerService.getTop();
    }

    @Operation(summary = "Список всех читателей отсортированных по убыванию количества не сданных книг")
    @GetMapping(value = "readers/all")
    public List<ReadersEntity> getAll() {
        return readerService.getAll();
    }
}
