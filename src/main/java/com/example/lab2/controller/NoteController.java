package com.example.lab2.controller;

import com.example.lab2.model.Note;
import com.example.lab2.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public Page<Note> notes(@RequestParam int n,
                            @RequestParam(required = false, defaultValue = "10") int s,
                            @RequestParam(required = false) String sort
    ) {
        if (sort != null) {
            return noteService.getNotes(n, s, Sort.by(sort));
        } else return noteService.getNotes(n, s, Sort.unsorted());
    }

    @GetMapping("/notes/{id}")
    public Note getOne(@PathVariable Long id) {
        return noteService.getOne(id);
    }

    @PostMapping("/notes")
    public Note saveNote(@RequestBody Map<String, String> note) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        try {
            return noteService.addNote(
                    Long.parseLong(note.get("user_id")),
                    note.get("topic"),
                    note.get("content"),
                    format.parse(note.get("date")));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/notes/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@RequestBody Map<String, String> note, @PathVariable Long id) {
        System.out.println(note);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        try {
            return noteService
                    .updateNote(
                            id,
                            note.get("topic"),
                            note.get("content"),
                            format.parse(note.get("date")));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
