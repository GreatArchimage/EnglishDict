package com.example.englishdict.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class EnglishWord {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="word",typeAffinity = ColumnInfo.TEXT)
    public String word;
    @ColumnInfo(name = "pron",typeAffinity = ColumnInfo.TEXT)
    public String pron;
    @ColumnInfo(name="interpret",typeAffinity = ColumnInfo.TEXT)
    public String interpret;
    @ColumnInfo(name = "instance",typeAffinity = ColumnInfo.TEXT)
    public String instance;
    @ColumnInfo(name = "translation",typeAffinity = ColumnInfo.TEXT)
    public String translation;

    public EnglishWord(@NonNull String word, String pron, String interpret, String instance, String translation) {
        this.word = word;
        this.pron = pron;
        this.interpret = interpret;
        this.instance = instance;
        this.translation = translation;
    }

    @Ignore
    public EnglishWord(@NonNull String word, String pron, String interpret) {
        this.word = word;
        this.pron = pron;
        this.interpret = interpret;
    }

    @Ignore
    public EnglishWord() {

    }

    @Override
    public String toString() {
        return "EnglishWord{" +
                "word='" + word + '\'' +
                ", pron='" + pron + '\'' +
                ", interpret='" + interpret + '\'' +
                ", instance='" + instance + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
