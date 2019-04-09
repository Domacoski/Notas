package com.domacoski.notas.data.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.domacoski.notas.data.Repository;
import com.domacoski.notas.domain.Note;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SharedPreferencesRepositoryImpl implements Repository {


    private final SharedPreferences mPreferences;
    private final Gson mGson = new GsonBuilder().create();
    private final String REPOSITORY_NAME = Note.class.getName();

    private static final String PREFIX_KEY = "note_%s";
    private static final String DEFAULT_VALUE = "DEFAULT";

    public SharedPreferencesRepositoryImpl(final Context context) {
        this.mPreferences = context.getSharedPreferences(REPOSITORY_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public List<Note> all() {
        final List<Note> notes = new ArrayList<>(0);
        final Collection collection = mPreferences.getAll().values();
        for(final Object value : collection){
            final Note note = mGson.fromJson(value.toString(), Note.class);
            notes.add(note);
        }
        return notes;
    }

    @Override
    public Note byId(final Long id) {
        if (null == id) {
            return new Note();
        }

        final String key = getKey(id);
        if (mPreferences.contains(key)) {

            final String jsonValue = mPreferences.getString(key, DEFAULT_VALUE);

            if(DEFAULT_VALUE.equals(jsonValue)){
                return new Note();
            }else{

                final Note note = mGson.fromJson(jsonValue, Note.class);
                return note;
            }
        }else{
            return new Note();
        }
    }


    @Override
    public void delete(final Long id) {
        final String key = getKey(id);
        if (mPreferences.contains(key)) {
            final SharedPreferences.Editor editor = getEditor();
            editor.remove(key);
            editor.apply();
        }
    }

    @Override
    public void update(final Note note) {
        if (null == note) {
            return;
        }
        final String key = getKey(note.getId());
        if (mPreferences.contains(key)) {
            final SharedPreferences.Editor editor = getEditor();
            final String jsonValue = mGson.toJson(note);
            editor.putString(key, jsonValue);
            editor.apply();
        }
    }

    @Override
    public void add(final Note note) {
        if (null == note) {
            return;
        }
        if(note.isInvalid()){
            note.setId(System.currentTimeMillis());
        }
        final String key = getKey(note.getId());
        if (!mPreferences.contains(key)) {
            final SharedPreferences.Editor editor = getEditor();
            final String jsonValue = mGson.toJson(note);
            editor.putString(key, jsonValue);
            editor.apply();
        }
    }

    private SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }


    private String getKey(final Long id) {
        return String.format(PREFIX_KEY, (id != null ? id : 0));
    }
}
