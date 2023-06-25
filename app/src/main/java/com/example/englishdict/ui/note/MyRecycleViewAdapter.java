package com.example.englishdict.ui.note;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishdict.R;
import com.example.englishdict.database.EnglishWord;

import java.util.List;

class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHoder> {
    private List<EnglishWord> mWordList;

    public MyRecycleViewAdapter(List<EnglishWord> mWordList) {
        this.mWordList = mWordList;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_list,null);
        MyViewHoder myViewHoder = new MyViewHoder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        EnglishWord englishWord = mWordList.get(position);
        holder.tvWord.setText(englishWord.word);
        holder.tvInterpret.setText(englishWord.interpret);
        holder.linearLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("word", englishWord.word);
                Navigation.findNavController(v).navigate(R.id.action_noteFragment_to_searchResultFragment,bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvInterpret;
        LinearLayout linearLayoutView;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_item_word);
            tvInterpret = itemView.findViewById(R.id.tv_item_interpret);
            linearLayoutView = itemView.findViewById(R.id.linearLayoutView);
        }
    }
}



