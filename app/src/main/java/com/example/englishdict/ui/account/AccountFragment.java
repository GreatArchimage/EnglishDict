package com.example.englishdict.ui.account;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.englishdict.LoginActivity;
import com.example.englishdict.MyApplication;
import com.example.englishdict.R;
import com.example.englishdict.TextUtils;
import com.example.englishdict.database.EnglishWordDataBase;
import com.example.englishdict.database.User;
import com.linchaolong.android.imagepicker.ImagePicker;


import java.io.ByteArrayOutputStream;
import java.io.File;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;

    private User user;
    String username;
    String email;

    TextView tv_username;
    TextView tv_email;

    private ImagePicker imagePicker = new ImagePicker();
    ImageView img_portrait;
//    String fileurl;
    byte[] photobytes;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        img_portrait = (ImageView) view.findViewById(R.id.img_portrait);

        showUserInfo(view);

        LinearLayout linear_edit_password = view.findViewById(R.id.linear_edit_password);
        linear_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    final View dialog_edit_pwd = View.inflate(getContext(),R.layout.dialog_edit_password,null);

                    new AlertDialog.Builder(getContext())
                            .setTitle("修改密码")
                            .setIcon(R.drawable.icon_password)
                            .setView(dialog_edit_pwd)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    EditText edt_old_password = dialog_edit_pwd.findViewById(R.id.edt_old_password);
                                    EditText edt_new_password = dialog_edit_pwd.findViewById(R.id.edt_new_password);
                                    String old_pwd = edt_old_password.getText().toString();
                                    String new_pwd = edt_new_password.getText().toString();
                                    if(TextUtils.isEmpty(old_pwd) || TextUtils.isEmpty(new_pwd)){
                                        Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_LONG).show();
                                    }else if(old_pwd.equals(user.password)){
                                        user.password = new_pwd;
                                        new UpdateUserTask(user).execute();
                                        Toast.makeText(getContext(),"密码修改成功",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getContext(),"密码错误",Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            }
        });

        LinearLayout linear_edit_email = view.findViewById(R.id.linear_edit_email);
        linear_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    final EditText edt_email = new EditText(getContext());
                    new AlertDialog.Builder(getContext())
                            .setTitle("更改邮箱")
                            .setView(edt_email)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String email = edt_email.getText().toString();
                                    tv_email.setText(email);
                                    user.email = email;
                                    new UpdateUserTask(user).execute();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            }
        });
        LinearLayout linear_edit_portrait = view.findViewById(R.id.linear_edit_portrait);
        linear_edit_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    startCameraOrGallery();
                    new UpdateUserTask(MyApplication.getInstance().user).execute();
                }
            }
        });


        //退出登录
        TextView tv_logout = view.findViewById(R.id.logout);
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().user = null;
                Intent intent =new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void showUserInfo(View view){
        user = MyApplication.getInstance().user;
        if(user == null){
            username = "请先登录";
            email = "";
        }else{
            username = user.userName;
            email = user.email;
            photobytes = user.portrait;
            //显示头像
            if (photobytes != null && photobytes.length > 0) {
                Glide.with(getActivity()).load(photobytes)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(img_portrait);
            }
        }
        //设置标题栏文本
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(username);
        //显示用户名和邮箱
        tv_username.setText(username);
        tv_email.setText(email);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    private void startCameraOrGallery() {
        new AlertDialog.Builder(getActivity()).setTitle("设置图片")
                .setItems(new String[] { "从相册中选取图片", "拍照" }, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override public void onPickImage(Uri imageUri) {
                            }

                            @Override public void onCropImage(Uri imageUri) {

                                Glide.with(getActivity()).load(new File(imageUri.getPath()))
                                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                        .into(img_portrait);

                                Glide.with(getActivity()).asBitmap().load(new File(imageUri.getPath()))
                                        .into(new SimpleTarget<Bitmap>(100, 100) {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap,
                                                                @Nullable Transition<? super Bitmap> transition) {
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);

                                        MyApplication.getInstance().user.portrait = stream.toByteArray();
                                    }
                                });
                            }

                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(AccountFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(AccountFragment.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }

    //更新用户表的异步任务
    public class UpdateUserTask extends AsyncTask<Void, Void, Void>
    {
        User user;
        public UpdateUserTask(User user) {
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            EnglishWordDataBase.getInstance(getContext()).userDao().updateUser(this.user);
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }
    }
}