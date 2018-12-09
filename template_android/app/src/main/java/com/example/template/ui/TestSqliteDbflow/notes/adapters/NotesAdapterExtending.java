package com.example.template.ui.TestSqliteDbflow.notes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.sqlite.Note;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapterExtending;


public class NotesAdapterExtending extends CustomRecyclerViewAdapterExtending<Note>

{


    public NotesAdapterExtending(Context context, OnViewHolderClick listener) {
        super(context, listener);
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_note, viewGroup, false);

        return view;
    }

    @Override
    protected void bindView(final Note item, ViewHolder viewHolder, int position) {

        TextView textViewNoteText = (TextView) viewHolder.getView(R.id.textViewNoteText);
        TextView textViewNoteComment = (TextView) viewHolder.getView(R.id.textViewNoteComment);
        textViewNoteText.setText(item.getText());
        textViewNoteComment.setText(item.getComment());

    }


}
