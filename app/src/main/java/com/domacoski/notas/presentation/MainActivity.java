package com.domacoski.notas.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.domacoski.notas.CreateNoteActivity;
import com.domacoski.notas.R;
import com.domacoski.notas.data.Repository;
import com.domacoski.notas.data.RepositoryFactory;
import com.domacoski.notas.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     private RecyclerView recycler;
     private Switch switchDataBase;

     private Repository repository;
     private Boolean isSqlite = false;

     public static final String IS_SQLITE = "is_sqlite";
    public static final String NOTE_ID = "note_id";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = RecyclerView.class.cast(findViewById(R.id.recycler));
        switchDataBase = Switch.class.cast(findViewById(R.id.switchDataBase));
        switchDataBase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSqlite = isChecked;
                updateRepository();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateView();
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
            }
        });
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRepository();
    }

    private void openCreateView(){
        final Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.putExtra(IS_SQLITE, isSqlite);
        startActivity(intent);
    }

    private void updateRepository(){
        repository = RepositoryFactory.getInstance().getRepository(this, isSqlite);
        final List<Note> notes = repository.all();
        recycler.setAdapter(new Adapter(this, notes));

    }

    private void setup(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new Adapter(this, new ArrayList<Note>(0)));
        recycler.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
