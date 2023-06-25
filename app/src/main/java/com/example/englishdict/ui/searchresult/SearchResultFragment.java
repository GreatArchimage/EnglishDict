package com.example.englishdict.ui.searchresult;

import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.englishdict.MyApplication;
import com.example.englishdict.R;
import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.Note;
import com.example.englishdict.database.User;
import com.example.englishdict.databinding.FragmentSearchResultBinding;

public class SearchResultFragment extends Fragment {

    private SearchResultViewModel mViewModel;
    private FragmentSearchResultBinding binding;

    private User user;
    String word;
    boolean isFavor = false;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = MyApplication.getInstance().user;

        Bundle bundle=getArguments();
        word=bundle.getString("word");

        new SelectEnglishWordTask(word).execute();

        binding.imgbtnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWordToNoteBook();
            }
        });
        binding.tvFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWordToNoteBook();
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);
        // TODO: Use the ViewModel
    }

    private void addWordToNoteBook(){
        if(user != null){
            Note note = new Note(user.userName,word);
            if(isFavor){
                EnglishWordDataBase.getInstance(getContext()).noteDao().deleteNote(note);
                binding.tvFavor.setText("添加至单词本");
                binding.imgbtnFavor.setImageResource(R.drawable.icon_favor);
                isFavor = false;
            }else{
                EnglishWordDataBase.getInstance(getContext()).noteDao().insertNote(note);

                binding.tvFavor.setText("已添加至单词本");
                binding.imgbtnFavor.setImageResource(R.drawable.icon_favor_enable);
                isFavor = true;
            }
        }else{
            Toast.makeText(getContext(), "尚未登录", Toast.LENGTH_LONG).show();
        }

    }


    //查找数据的异步任务
    public class SelectEnglishWordTask extends AsyncTask<Void, Void, Void>
    {
        EnglishWord englishWord;
        Note note;
        String word;
        public SelectEnglishWordTask(String word)
        {
            this.word=word ;
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            EnglishWordDataBase dbInstance = EnglishWordDataBase.getInstance(getContext());
            englishWord = dbInstance.englishWordDao().getEnglishWordByWord(this.word);

            if(user != null){
                //查询单词是否收藏
                note = dbInstance.noteDao().getNoteOne(user.userName, word);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if(englishWord != null){
                binding.tvWord.setText(englishWord.word);
                binding.tvPron.setText(englishWord.pron);
                binding.tvInterpret.setText(englishWord.interpret);
                binding.tvInstance.setText(englishWord.instance);
                binding.tvTranslation.setText(englishWord.translation);

                if(note != null){
                    isFavor = true;
                    binding.tvFavor.setText("已添加至单词本");
                    binding.imgbtnFavor.setImageResource(R.drawable.icon_favor_enable);
                }
            }else {
                Toast.makeText(getContext(),"找不到此单词",Toast.LENGTH_LONG).show();
            }
        }
    }
}