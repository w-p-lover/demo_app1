package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Connection extends SQLiteOpenHelper {

    private static final String CREATE_T_USER = "create table t_user("
            +"id integer primary key autoincrement,"
            +"user_Name text,"
            +"user_Password text,"
            +"user_Tel text,"
            +"user_Email text,"
            +"user_Sex int)";
    private Context context;

    public Connection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_T_USER);//创建数据表
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
