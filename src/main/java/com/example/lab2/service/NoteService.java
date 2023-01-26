package com.example.lab2.service;

import com.example.lab2.model.Note;
import com.example.lab2.model.Permission;
import com.example.lab2.model.User;
import com.example.lab2.repository.NoteRepository;
import com.example.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;


    public Note addNote(Long userId, String topic, String content, Date date) {
        User user = userRepository.getById(userId);
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getLogin()) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permission.ADD_NOTE)) {
            return noteRepository.save(getNoteFromParams(user, topic, content, date));
        }

        return null;
    }

    public Note updateNote(Long id, String topic, String content, Date date) {
        Note note = noteRepository.getNoteByIdWidthUser(id);

        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(note.getUser().getLogin()) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permission.EDIT_NOTE)) {
            note.setDate(date);
            note.setContent(content);
            note.setTopic(topic);
            return noteRepository.save(note);
        }

        return null;
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.getNoteByIdWidthUser(id);
        if (note.getUser().getLogin()
                .equals(SecurityContextHolder.getContext().getAuthentication().getName()) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .contains(Permission.DELETE_NOTES)) {
            noteRepository.deleteById(id);
        }
    }

    public Note getOne(Long id) {
        Note note = noteRepository.getNoteByIdWidthUser(id);
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(note.getUser().getLogin()) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .contains(Permission.GET_NOTE)) {
            return note;
        }
        return null;
    }

    public Page<Note> getNotes(int n, int s, Sort sort) {
        Pageable pageable = PageRequest.of(n, s, sort);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("GET_ALL_NOTES")) {
            return noteRepository.findAll(pageable);
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            return noteRepository.findAllByUser(user, pageable);
        }
        return null;

    }

    private Note getNoteFromParams(User user, String topic, String content, Date date) {
        Note note = new Note();
        note.setUser(user);
        note.setContent(content);
        note.setDate(date);
        note.setTopic(topic);
        return note;
    }
}

