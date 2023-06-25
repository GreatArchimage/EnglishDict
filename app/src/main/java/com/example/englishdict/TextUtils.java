package com.example.englishdict;

public class TextUtils {
    public static boolean isEmpty(String str){
        if(str==null||"".equals(str)){
            return true;
        }
        return false;
    }
}
