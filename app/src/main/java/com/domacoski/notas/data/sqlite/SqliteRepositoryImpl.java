package com.domacoski.notas.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.domacoski.notas.data.Repository;
import com.domacoski.notas.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class SqliteRepositoryImpl extends SQLiteOpenHelper implements Repository {

    private static final int DATA_BASE_VERSION = 1;
    private static final String DATA_BASE_NAME = "dataBaseName.db";

    public SqliteRepositoryImpl(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(DataBaseNote.SQL_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public List<Note> all() {
        final SQLiteDatabase database = getWritableDatabase();
        final List<Note> all = new ArrayList<>(0);
        final Cursor cursor = database.rawQuery(DataBaseNote.SELECT_ALL, null);
        while (cursor.moveToNext()) {
            final Note note = inflateFromCursor(cursor);
            all.add(note);
        }
        database.close();
        return all;
    }

    @Override
    public Note byId(final Long id) {
        if(null == id){
            return new Note();
        }
        final SQLiteDatabase database = getReadableDatabase();
        final String[] param = {id.toString()};
        final Cursor cursor = database.rawQuery(DataBaseNote.SELECT_BY_ID, param);
        Note note = new Note();
        if(cursor.moveToNext()){
            note = inflateFromCursor( cursor );
        }
        database.close();
        return note;
    }

    @Override
    public void delete(final Long id) {
        if (null == id) {
            return;
        }
        final SQLiteDatabase database = getReadableDatabase();
        final String[] param = {id.toString()};
        final String query = String.format("%s = ?", DataBaseNote.ID);
        database.delete(DataBaseNote.TABLE, query, param);
        database.close();
    }

    @Override
    public void update(final Note note) {
        if (null == note) {
            return;
        }
        final ContentValues contentValues = getContentValues(note);
        final SQLiteDatabase database = getReadableDatabase();
        database.update(DataBaseNote.TABLE, contentValues,
                String.format("%s = ?", DataBaseNote.ID),
                new String[]{String.valueOf(note.getId())});

        database.close();
    }

    @Override
    public void add(final Note note) {
        if (null == note) {
            return;
        }
        final ContentValues contentValues = getContentValues(note);
        final SQLiteDatabase database = getReadableDatabase();
        database.insert(DataBaseNote.TABLE, null, contentValues);
        database.close();
    }


    private Note inflateFromCursor(final Cursor cursor) {
        final Note note = new Note();
        note.setId(cursor.getLong(cursor.getColumnIndex(DataBaseNote.ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(DataBaseNote.TITLE)));
        note.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseNote.DESCRIPTION)));
        note.setTimestamp(cursor.getLong(cursor.getColumnIndex(DataBaseNote.TIMESTAMP)));
        return note;
    }


    private ContentValues getContentValues(final Note note) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseNote.TITLE, note.getTitle());
        contentValues.put(DataBaseNote.DESCRIPTION, note.getDescription());
        contentValues.put(DataBaseNote.TIMESTAMP, note.getTimestamp());

        return contentValues;
    }

    interface DataBaseNote {
        String TABLE = "NOTE";
        String ID = "note_id";
        String TITLE = "note_title";
        String DESCRIPTION = "note_description";
        String TIMESTAMP = "note_timestamp";

        String SQL_CREATE =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s INTEGER NOT NULL)",
                        TABLE, ID, TITLE, DESCRIPTION, TIMESTAMP);

//        String SQL_DROP = String.format("DROP TABLE IF EXISTS %s", TABLE);

        String SELECT_ALL = String.format("SELECT %s, %s, %s, %s FROM %s ",
                ID, TITLE, DESCRIPTION, TIMESTAMP, TABLE);

        String SELECT_BY_ID = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = ?",
                ID, TITLE, DESCRIPTION, TIMESTAMP, TABLE, ID);
//        String[] properties = {ID, TITLE, DESCRIPTION, TIMESTAMP};

    }


}
