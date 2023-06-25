package com.example.englishdict.ui.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.englishdict.MyApplication;
import com.example.englishdict.TextUtils;
import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.User;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private EnglishWordDataBase englishWordDataBase;
    private LiveData<List<EnglishWord>> liveDataEnglishWord;
    private User user;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        englishWordDataBase = EnglishWordDataBase.getInstance(application);
        user = MyApplication.getInstance().user;
        if(user != null){
            liveDataEnglishWord = englishWordDataBase.noteDao().getWordListFromNote(user.userName);
        }else{
            liveDataEnglishWord = new MutableLiveData<>();
        }
    }
    public LiveData<List<EnglishWord>> getLiveDataEnglishWord(){return liveDataEnglishWord;}

    public void filterWordList(String keyword){
        if(user != null) {
            if(TextUtils.isEmpty(keyword)){
                liveDataEnglishWord = englishWordDataBase.noteDao().getWordListFromNote(user.userName);
            }else{
                liveDataEnglishWord = englishWordDataBase.noteDao().getWordListFromNoteByKeyWord(user.userName, keyword);
            }
        }
    }
}