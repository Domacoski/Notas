package com.domacoski.notas.data;

import com.domacoski.notas.domain.Note;

import java.util.List;

public interface Repository {

    List<Note> all();
    Note byId(final Long id);
    void delete(final Long id);
    void update(final Note note);
    void add(final Note note);

}
