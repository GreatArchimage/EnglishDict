package com.example.englishdict.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"username","word"})
public class Note {

    @NonNull
    @ColumnInfo(name="username",typeAffinity = ColumnInfo.TEXT)
    public String userName;

    @NonNull
    @ColumnInfo(name="word",typeAffinity = ColumnInfo.TEXT)
    public String word;

    public Note(@NonNull String userName, @NonNull String word) {
        this.userName = userName;
        this.word = word;
    }
}

