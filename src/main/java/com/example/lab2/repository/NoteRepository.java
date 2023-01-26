package com.example.lab2.repository;

import com.example.lab2.model.Note;
import com.example.lab2.model.User;
import com.example.lab2.repository.custom.CustomNotesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, CustomNotesRepository {

    Page<Note> findAllByUser(User user, Pageable pageable);

    @Query("select n from notes n join fetch n.user u where n.id=:id")
    Note getNoteByIdWidthUser(Long id);
}
