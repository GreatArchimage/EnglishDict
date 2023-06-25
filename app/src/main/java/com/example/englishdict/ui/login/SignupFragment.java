package com.example.englishdict.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishdict.R;
import com.example.englishdict.TextUtils;
import com.example.englishdict.database.EnglishWord;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.User;
import com.example.englishdict.database.UserDao;

public class SignupFragment extends Fragment {

    private SignupViewModel mViewModel;
    private EditText et_email;
    private EditText et_username;
    private EditText et_password;
    private EditText et_secondpwd;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        et_email = view.findViewById(R.id.et_email);
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        et_secondpwd = view.findViewById(R.id.et_secondpwd);

        Button btn_to_login = view.findViewById(R.id.btn_to_login);
        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.loginFragment);

            }
        });

        //注册按钮
        Button btn_signup = view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String secondpwd = et_secondpwd.getText().toString().trim();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)
                    || TextUtils.isEmpty(secondpwd)){
                    Toast.makeText(getContext(), "请完善信息", Toast.LENGTH_LONG).show();
                }else if(password.equals(secondpwd)){
                    UserDao userDao = EnglishWordDataBase.getInstance(getContext()).userDao();
                    if(userDao.getUserByName(username)!=null){
                        Toast.makeText(getContext(), "该用户名已存在", Toast.LENGTH_LONG).show();
                    }else{
                        User user = new User(username, email, password);
                        userDao.insertUser(user);
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.loginFragment);
                    }
                }else {
                    Toast.makeText(getContext(), "两次输入的密码不匹配", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        // TODO: Use the ViewModel
    }

}