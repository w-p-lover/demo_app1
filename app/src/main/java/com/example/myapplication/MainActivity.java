package com.example.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
//调用
import android.widget.Toast;
import android.widget.Spinner;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.pojo.User;
import com.example.myapplication.uitls.Myadapter;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase database;//数据库对象
    private List<User> userList;  //用户列表

    //
    private EditText getName,getPassword,getTel,getEmail;
    private int getSex;
    //
    private Button addButton,saveButton,deleteButton,emptyButton;

    private RadioGroup sexRadio;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=getDatabase();//获得数据库对象
        final List<String> datas = new ArrayList<>();
        datas.add("zhangsan");
        datas.add("lisi");
        datas.add("wangwu");
        sexRadio = findViewById(R.id.choosesex);
    }

    private void getText(){
        //四个按钮的对象
        addButton=(Button) findViewById(R.id.buttonread);
        saveButton = (Button) findViewById(R.id.buttonsave);
        deleteButton = (Button) findViewById(R.id.buttondeltet);
        emptyButton = (Button) findViewById(R.id.buttonblank);
        //性别选项
        spinner = (Spinner) findViewById(R.id.spinnerNowUser);

        getName = (EditText) findViewById(R.id.editUserName);
        getPassword = (EditText) findViewById(R.id.editPassword);
        getTel = (EditText) findViewById(R.id.editTel);
        getEmail = (EditText) findViewById(R.id.editEmail);
        getSex = sexRadio.getCheckedRadioButtonId();
    }
    //为“添加”按钮点击点击事件
    public void buttonAdd_Click(View view){
        getText();

        if(TextUtils.isEmpty(getName.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入用户名！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getPassword.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入密码！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getTel.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入手机号！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getEmail.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入邮箱号码！",Toast.LENGTH_LONG).show();
        }
        else if(getSex<0){
            Toast.makeText(MainActivity.this,"请选择性别！",Toast.LENGTH_LONG).show();
        }else{

            //将数据保存到数据库
            ContentValues values = new ContentValues();
            values.put("user_Name",getName.getText().toString());
            values.put("user_Password",getPassword.getText().toString());
            values.put("user_Tel",getTel.getText().toString());
            values.put("user_Email",getEmail.getText().toString());
            values.put("user_Sex",getSex);
            long flag = database.insert("t_user", null, values);
            if (flag > 0) {
                Toast.makeText(MainActivity.this, "添加成功！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "添加失败！", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void buttonSave_Click(View view){
        getText();
        if(TextUtils.isEmpty(getName.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入用户名！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getPassword.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入密码！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getTel.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入手机号！",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getEmail.getText().toString())){
            Toast.makeText(MainActivity.this,"请输入邮箱号码！",Toast.LENGTH_LONG).show();
        }
        else if(getSex<0){
            Toast.makeText(MainActivity.this,"请选择性别！",Toast.LENGTH_LONG).show();
        }else{

            //将数据保存到数据库
            ContentValues values = new ContentValues();
            values.put("user_Name",getName.getText().toString());
            values.put("user_Password",getPassword.getText().toString());
            values.put("user_Tel",getTel.getText().toString());
            values.put("user_Email",getEmail.getText().toString());
            values.put("user_Sex",getSex);
            int is_succeeed=database.update("t_user",values,"user_Name=?",new String[]{getName.getText().toString()});
            if (is_succeeed > 0) {//如果要保存的用户在数据库中
                Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
            } else {//如果不在数据库中，改为添加操作
                long flag = database.insert("t_user", null, values);
                Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_LONG).show();}
        }

    }

    public void buttonDelete_Click(View view){
        getText();

        int is_succeed=database.delete("t_user","user_Name=?",new String[]{getName.getText().toString()});
        if(is_succeed>0)
            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
    }

    public void buttonEmpty_Click(View view){
        getText();
        getSex = sexRadio.getCheckedRadioButtonId();

        getName.setText("");
        getPassword.setText("");
        getEmail.setText("");
        getTel.setText("");

        Toast.makeText(MainActivity.this,"清空成功",Toast.LENGTH_LONG).show();
    }


    //获得数据库对象
    private SQLiteDatabase getDatabase() {
        Connection dbHelper = new Connection(MainActivity.this, "demo_db", null, 1);

        return dbHelper.getReadableDatabase();
    }

}
