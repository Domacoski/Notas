package com.domacoski.notas.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.domacoski.notas.R;

public class NoteHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle, txtData, txtDescription;
    public NoteHolder(@NonNull View itemView) {
        super(itemView);
        this.txtTitle = itemView.findViewById(R.id.txtTitle);
        this.txtDescription = itemView.findViewById(R.id.txtDescription);
        this.txtData = itemView.findViewById(R.id.txtData);
    }

}
