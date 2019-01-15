package com.example.template.model.bean.sqlite;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.example.template.model.db.NoteTypeConverter;
import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.dbFlowDatabases.DatabaseModule;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.converter.DateConverter;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.ArrayList;

import modules.general.model.bean.sqlite.BaseModelWithIData;


@Table(database = DatabaseModule.class)
public class Note extends BaseModelWithIData implements   Parcelable {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id = 0l;

    @Column
    private String text;
    @Column
    private String comment;
    @Column(typeConverter = DateConverter.class)
    private java.util.Date date;

    @Column(typeConverter = NoteTypeConverter.class)
    private NoteType type;

    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    public Note(Long id, String text, String comment, java.util.Date date, NoteType type) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
        this.type = type;
    }

    protected Note(Parcel in) {
        text = in.readString();
        comment = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public NoteType getType() {
        return type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }

    @Override
    public void insertData(Object object   , SqliteCallBack sqliteCallBack) {
        ((Note)object).insert();
     }

    @Override
    public void updateData(Object object , SqliteCallBack sqliteCallBack) {
        ((Note)object).save();

    }

    @Override
    public void deleteData(  Object object , SqliteCallBack sqliteCallBack) {
        ((Note)object).delete();

    }

    @Override
    public Note getItemByID( int id ) {
        Note note = SQLite.select()
                .from(Note.class)
                .where(Note_Table.id.eq(Long.valueOf(id)))
                .querySingle();

        return note;
    }

    @Override
    public void getAll(   final SqliteCallBack sqliteCallBack) {

        SQLite.select().from(Note.class)
                .async()
                .queryResultCallback(
                        new QueryTransaction.QueryResultCallback<Note>() {
                            @Override
                            public void onQueryResult(final QueryTransaction<Note> transaction,
                                                      @NonNull final CursorResult<Note> tResult) {
                                sqliteCallBack.onDBDataListLoaded(
                                        new ArrayList<Note>(tResult.toListClose())
                                        , "getAllNotes"
                                );
                            }
                        }
                ).execute();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(comment);
    }
}
