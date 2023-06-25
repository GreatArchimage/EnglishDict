package com.example.englishdict.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="username",typeAffinity = ColumnInfo.TEXT)
    public String userName;
    @ColumnInfo(name = "email",typeAffinity = ColumnInfo.TEXT)
    public String email;
    @ColumnInfo(name="password",typeAffinity = ColumnInfo.TEXT)
    public String password;
    @ColumnInfo(name = "portrait",typeAffinity = ColumnInfo.BLOB)
    public byte[] portrait;

    public User(@NonNull String userName, String email, String password, byte[] portrait) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.portrait = portrait;
    }

    @Ignore
    public User(@NonNull String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
