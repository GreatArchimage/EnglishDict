package com.example.englishdict.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDao {

   @Insert
   void insertUser(User user);

   @Delete
   void deleteUser(User user);

   @Update
   void updateUser(User user);

   @Query("select * from user_table where username=:username")
   User getUserByName(String username);
}
