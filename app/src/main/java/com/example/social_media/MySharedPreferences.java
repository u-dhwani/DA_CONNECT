package com.example.social_media;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private Context context;

//    constructor

    public MySharedPreferences(Context context){
        this.context=context;
    }

    // Email and password already in authorization
    public void setData(String number){
        SharedPreferences.Editor editor=context.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
        editor.putString("number",number);
        editor.apply();
    }

    // getting contact number
    public String getNumber(){
        SharedPreferences editor=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return editor.getString("number",null);
    }
}
