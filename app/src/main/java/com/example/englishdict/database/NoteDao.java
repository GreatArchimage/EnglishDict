package com.example.englishdict.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("select * from Note where username = :userName and word=:word")
    Note getNoteOne(String userName, String word);

    @Query("SELECT * FROM EnglishWord WHERE word IN (SELECT word FROM Note WHERE username = :userName)")
    LiveData<List<EnglishWord>> getWordListFromNote(String userName);

    @Query("SELECT * FROM EnglishWord WHERE word IN (SELECT word FROM Note WHERE username = :userName) AND word LIKE '%' || :keyword || '%'")
    LiveData<List<EnglishWord>> getWordListFromNoteByKeyWord(String userName, String keyword);
}
