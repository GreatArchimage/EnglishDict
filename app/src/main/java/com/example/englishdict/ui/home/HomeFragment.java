package com.example.englishdict.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.englishdict.R;
import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.User;
import com.example.englishdict.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        new GetRandWordTask().execute();

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("word", String.valueOf(binding.editWord.getText()));
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_searchResultFragment,bundle);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    //随机从数据库抽取一个单词
    public class GetRandWordTask extends AsyncTask<Void, Void, Void>
    {
        EnglishWord englishWord;
        public GetRandWordTask() {

        }
        //不能在这里更新UI,否则有异常
        @Override
        protected Void doInBackground(Void... arg0)
        {
            englishWord = EnglishWordDataBase.getInstance(getContext()).englishWordDao().getRandEnglishWord();
            return null;
        }
        //onPostExecute用于doInBackground执行完后，可以更新界面UI。
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            binding.tvRandWord.setText(englishWord.word);
            binding.tvRandInterpret.setText(englishWord.interpret);
        }
    }
}