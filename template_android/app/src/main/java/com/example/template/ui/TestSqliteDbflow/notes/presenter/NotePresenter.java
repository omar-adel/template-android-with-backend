package com.example.template.ui.TestSqliteDbflow.notes.presenter;

import android.content.Context;
import android.util.Log;

import com.example.template.model.DataManager;
import com.example.template.model.bean.sqlite.Note;
import com.example.template.model.bean.sqlite.NoteType;
import modules.general.model.db.SqliteCallBack;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Net22 on 9/18/2017.
 */

public class NotePresenter extends SqliteCallBack
        implements NoteContract.IPresenter {

    NoteContract.IView mView;
    Context mContext;
    private final DataManager mDataManager;

    public NotePresenter(Context context, NoteContract.IView view) {
        mView = view;
        mContext = context;
        mDataManager = DataManager.getInstance(mContext
        );
        mDataManager.setPresenterSqliteCallBack(this);

    }

    public void getNotes() {
        mDataManager.getAll(new Note(), this);
    }

    public void deleteNote(Note note) {
        mDataManager.deleteData(note, this);
    }

    public void addNote(String noteText) {

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setType(NoteType.TEXT);
        mDataManager.insertData(note, this);

      }



    @Override
    public void onDBDataListLoaded(ArrayList data , String localDbOperation) {
        mView.showNotes(data);
    }



}

