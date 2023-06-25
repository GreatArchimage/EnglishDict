package com.example.englishdict.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

import com.example.englishdict.LoginActivity;
import com.example.englishdict.MainActivity;
import com.example.englishdict.MyApplication;
import com.example.englishdict.R;
import com.example.englishdict.TextUtils;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.User;
import com.example.englishdict.database.UserDao;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private EditText et_login_username;
    private EditText et_login_pwd;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        et_login_username = view.findViewById(R.id.et_login_username);
        et_login_pwd = view.findViewById(R.id.et_login_pwd);

        Button btn_to_signup = view.findViewById(R.id.btn_to_signup);
        btn_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.signupFragment);

            }
        });

        Button btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_login_username.getText().toString().trim();
                String password = et_login_pwd.getText().toString().trim();

                UserDao userDao = EnglishWordDataBase.getInstance(getContext()).userDao();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "用户名或密码不能为空", Toast.LENGTH_LONG).show();
                }else {
                    User user = userDao.getUserByName(username);
                    if (user == null) {
                        Toast.makeText(getContext(), "该用户不存在", Toast.LENGTH_LONG).show();
                    } else if (password.equals(user.password)) {
                        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_LONG).show();

                        MyApplication.getInstance().user = user;
                        Intent intent =new Intent();
                        intent.setClass(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

}