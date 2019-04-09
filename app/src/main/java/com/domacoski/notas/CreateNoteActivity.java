package com.domacoski.notas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.domacoski.notas.data.Repository;
import com.domacoski.notas.data.RepositoryFactory;
import com.domacoski.notas.domain.Note;
import com.domacoski.notas.presentation.MainActivity;

public class CreateNoteActivity extends AppCompatActivity  {


    private EditText description, title;
    Button actionAdd;

    private Repository repository;

    private Note current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean isSqlite = getIntent().getBooleanExtra(MainActivity.IS_SQLITE, false);

        repository = RepositoryFactory.getInstance().getRepository(this, isSqlite);


        long edit = getIntent().getLongExtra(MainActivity.NOTE_ID, -1l);
        if(-1l == edit){
            current = new Note();
        }else{
            current = repository.byId(edit);
            inflateView();
        }


        actionAdd = Button.class.cast(findViewById(R.id.actionAdd));
        description = EditText.class.cast(findViewById(R.id.description));
        title = EditText.class.cast(findViewById(R.id.title));

        actionAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    saveOrUpdate();
                }
            }
        });



    }

    private void inflateView(){
        title.setText(current.getTitle());
        description.setText(current.getDescription());
    }



    private boolean validate(){
        description.setError(null);
        if("".equals(description.getText().toString().trim())){
            description.setError("Não é possível salvar uma Nota vazia!");
            description.requestFocus();
            return false;
        }
        return true;
    }

    private void saveOrUpdate(){
        current.setTitle(title.getText().toString().trim());
        current.setDescription(description.getText().toString().trim());

        if(current.isInvalid()){
            current.setTimestamp(System.currentTimeMillis());
            repository.add(current);
        }else{
            repository.update(current);
        }
        finish();
    }



}

