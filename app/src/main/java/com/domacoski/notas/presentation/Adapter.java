package com.domacoski.notas.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.domacoski.notas.R;
import com.domacoski.notas.domain.Note;

import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<NoteHolder> {


    private final LayoutInflater layoutInflater;
    private List<Note> values;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private Context context;

    public Adapter(final Context context, final List<Note> values){
        this.context = context;
        this.layoutInflater = LayoutInflater.from( context );
        this.values = values;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NoteHolder(
                layoutInflater.inflate(R.layout.content_line,
                        viewGroup,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int i) {
        final Note note = values.get(i);

        holder.txtData.setText( dateFormat.format(note.getTimestamp()) );
        holder.txtDescription.setText(note.getDescription());
        holder.txtTitle.setText( note.getTitle() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void updateItens(final List<Note> notes){
        values = notes;
        notifyItemInserted(values.size());
    }

    public void clean(){
        values.clear();
        notifyItemInserted(values.size());
    }
}
