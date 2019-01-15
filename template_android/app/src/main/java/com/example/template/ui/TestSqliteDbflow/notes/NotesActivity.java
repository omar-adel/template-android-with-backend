/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.template.ui.TestSqliteDbflow.notes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.basgeekball.awesomevalidation.model.IsEmptyRule;
import com.basgeekball.awesomevalidation.model.Rule;
import com.example.template.R;
import com.example.template.model.bean.sqlite.Note;
import com.example.template.ui.TestSqliteDbflow.notes.presenter.NoteContract;
import com.example.template.ui.TestSqliteDbflow.notes.presenter.NotePresenter;
import modules.general.ui.parentview.ParentActivity;
import modules.general.ui.utils.adapters.CustomRecyclerViewAdapter;

import modules.general.utils.KeyBoardUtil;
import modules.general.utils.validation.awesome.ValidationUtilAwesome;
import modules.general.utils.validation.listeners.OnValidationCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static modules.general.ui.utils.adapters.CustomRecyclerViewAdapter.NotesType;

public class NotesActivity extends ParentActivity<NotePresenter>

        implements NoteContract.IView ,OnValidationCallBack

    // , CustomRecyclerViewAdapterExtending.OnViewHolderClick

{

    @BindView(R.id.editTextNote)
    EditText editText;
    @BindView(R.id.buttonAdd)
    View addNoteButton;
    @BindView(R.id.recyclerViewNotes)
    RecyclerView recyclerView;
     private CustomRecyclerViewAdapter notesAdapter;
    // private NotesAdapterExtending notesAdapterExtending;

    ValidationUtilAwesome validationUtilAwesome ;

    @Override
    public int getExtraLayout()
    {
        return R.layout.act_sqlite_notes;
    }

    @Override
    public void configureUI() {

        super.configureUI();
        disableDrawerSwipe();
        getCsTitle().hideMenuAndSettingsAndBack();
        getCsTitle().updateTitle(getString(R.string.sqlite_notes));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         // notesAdapterExtending =new NotesAdapterExtending(this,this);
         notesAdapter =new CustomRecyclerViewAdapter<Note>(this,NotesType);
        //recyclerView.setAdapter(notesAdapterExtending);
        recyclerView.setAdapter(notesAdapter);
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });

        //AWESOME VALIDATION
        validationUtilAwesome = new ValidationUtilAwesome(this, this);
        addValidationForEditText();

        getNotePresenter().getNotes();

    }

    private void addValidationForEditText() {

        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(new IsEmptyRule(getString(R.string.empty_note)).build());
        validationUtilAwesome.getAwesomeValidation().addValidation(editText, rules);
        validationUtilAwesome.addOnFocusChangeListeners();
    }

    @Override
    public NotePresenter injectDependencies() {
        return new NotePresenter(this,this);
    }



    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        validationUtilAwesome.validateAllFirstError();
        //   validationUtilAwesome.validateAll();
    }


    @Override
    public void showNotes(List<Note> notes) {
       //  notesAdapterExtending.setAll(notes);
         notesAdapter.setAll(notes);
    }



    @Override
    public void onValidationSucceeded() {

        //Toast.makeText(this, "Form Successfully Validated Notes Activity", Toast.LENGTH_SHORT).show();

        String noteText = editText.getText().toString();
        editText.setText("");
        getNotePresenter().addNote(noteText);
        KeyBoardUtil.hideSoftKeyboard(this);
        getNotePresenter().getNotes();

    }

    public NotePresenter getNotePresenter() {
        return ((NotePresenter) this.getPresenter());
    }

//    @Override
//    public void onClick(View view, int position) {
//        Note note = (Note)notesAdapterExtending.getItem(position);
//        getNotePresenter().deleteNote(note);
//        notesAdapterExtending.removeItem(position);
//    }
}