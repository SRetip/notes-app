package com.example.lab2.repository.custom.implementation;

import com.example.lab2.model.Note;
import com.example.lab2.model.User;
import com.example.lab2.repository.custom.CustomNotesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CustomNotesRepositoryImpl implements CustomNotesRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Note saveNote(Note note) {
        User user = entityManager.getReference(User.class, note.getUser().getId());
        note.setUser(user);
        entityManager.persist(note);
        return note;
    }
}
