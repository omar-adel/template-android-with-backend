package com.example.template.ui.TestSqliteDbflow.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.template.R;
import com.example.template.model.bean.sqlite.Note;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;

/**
 * Created by Net22 on 9/18/2017.
 */

public class NoteVH extends RecyclerView.ViewHolder    {
    public TextView text;
    public TextView comment;
    View itemView ;
    int positionClicked ;
    Object itemClicked ;
    Context context ;
    CustomRecyclerViewAdapter customRecyclerViewAdapter ;
    public NoteVH(Context context, View itemView,
                  CustomRecyclerViewAdapter customRecyclerViewAdapter) {
        super(itemView);
        this.context=context;
        this.itemView=itemView;
        this.customRecyclerViewAdapter=customRecyclerViewAdapter;
        text= (TextView) itemView.findViewById(R.id.textViewNoteText);
        comment= (TextView) itemView.findViewById(R.id.textViewNoteComment);
    }

    public void bindData(final Object item, final int position) {
        final Note itemInfo = (Note)item;
        text.setText(itemInfo.getText());
        comment.setText(itemInfo.getComment());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionClicked = position;
                itemClicked = item;
                onClickItem(view);
            }
        });

    }

    public static View getView(Context context,ViewGroup viewGroup){
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.row_note,viewGroup,false);
    }

    public void onClickItem(View view) {
        Note note = (Note)customRecyclerViewAdapter.getItem(positionClicked);
        ((NotesActivity)context).getNotePresenter().deleteNote(note);
        customRecyclerViewAdapter.removeItem(positionClicked);

    }
}
