package com.example.englishdict.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EnglishWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEnglishWord(EnglishWord englishWord);

    @Delete
    void deleteEnglishWord(EnglishWord englishWord);

    @Update
    void updateEnglishWord(EnglishWord englishWord);

    @Query("select * from englishWord")
    List<EnglishWord> getEnglishWordList();

    @Query("select * from englishWord where word=:word")
    EnglishWord getEnglishWordByWord(String word);

    @Query("SELECT * FROM englishWord ORDER BY RANDOM() limit 1")
    EnglishWord getRandEnglishWord();
}
