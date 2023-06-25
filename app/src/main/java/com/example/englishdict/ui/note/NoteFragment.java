package com.example.englishdict.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishdict.MyApplication;
import com.example.englishdict.R;
import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.Note;
import com.example.englishdict.database.User;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private NoteViewModel mViewModel;
    private RecyclerView mRecyclerView;
    MyRecycleViewAdapter myRecycleViewAdapter;
    private List<EnglishWord> mWordList;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        mRecyclerView = view.findViewById(R.id.mRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mWordList = new ArrayList<>();
        myRecycleViewAdapter = new MyRecycleViewAdapter(mWordList);
        mRecyclerView.setAdapter(myRecycleViewAdapter);

        NoteViewModel noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getLiveDataEnglishWord().observe(getViewLifecycleOwner(), new Observer<List<EnglishWord>>() {
            @Override
            public void onChanged(List<EnglishWord> englishWords) {
                mWordList.clear();
                mWordList.addAll(englishWords);
                myRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        EditText et_filter = view.findViewById(R.id.et_filter);
        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                noteViewModel.filterWordList(et_filter.getText().toString());
                noteViewModel.getLiveDataEnglishWord().observe(getViewLifecycleOwner(), new Observer<List<EnglishWord>>() {
                    @Override
                    public void onChanged(List<EnglishWord> englishWords) {
                        mWordList.clear();
                        mWordList.addAll(englishWords);
                        myRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        // TODO: Use the ViewModel
    }

}