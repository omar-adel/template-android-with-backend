package com.example.template.model.db;

import com.example.template.model.bean.sqlite.NoteType;
import com.raizlabs.android.dbflow.converter.TypeConverter;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class NoteTypeConverter extends TypeConverter<String, NoteType> {


    @Override
    public String getDBValue(NoteType noteType) {
        return noteType == null ? null : noteType.name();
    }

    @Override
    public NoteType getModelValue(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }


}
