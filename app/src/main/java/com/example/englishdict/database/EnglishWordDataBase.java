package com.example.englishdict.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EnglishWord.class, User.class, Note.class},version=2,exportSchema=false)
public abstract class EnglishWordDataBase extends RoomDatabase {
    private static final String DATABASE_NAME="word_db";
    private static EnglishWordDataBase englishWordDataBase;

    public static EnglishWordDataBase getInstance(Context context){
        if(englishWordDataBase==null){
            englishWordDataBase= Room
                    .databaseBuilder(context.getApplicationContext(),EnglishWordDataBase.class,DATABASE_NAME)
                    .createFromAsset("databases/word_db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return englishWordDataBase;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //创建数据表
            database.execSQL("CREATE TABLE 'user_table' ('username'  TEXT NOT NULL,'password' TEXT,'email' TEXT,'portrait' BLOB,PRIMARY KEY ('username'))");
            database.execSQL("CREATE TABLE 'note' ('username'  TEXT NOT NULL,'word' TEXT NOT NULL,PRIMARY KEY ('username','word'))");
        }
    };

    public abstract EnglishWordDao englishWordDao();

    public abstract UserDao userDao();

    public abstract NoteDao noteDao();
}


