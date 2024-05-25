package com.example.myapplication;
import android.annotation.SuppressLint;
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
    RadioButton selectedRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinnerNowUser);
        database=getDatabase();//获得数据库对象
        getAllUserNamesFromDatabase();
        // 监听 Spinner 选择事件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 根据选择的用户名查询用户信息并展示到界面上
                String selectedUserName = parent.getItemAtPosition(position).toString();
                showUserInfo(selectedUserName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 如果没有选择，则不进行任何操作
            }
        });
        sexRadio = findViewById(R.id.choosesex);
    }

    // 根据用户名从数据库中查询用户信息并展示到界面上
    @SuppressLint("Range")
    // 根据用户名从数据库中查询用户信息并展示到界面上
    private void showUserInfo(String userName) {
        getText();
        Cursor cursor = database.rawQuery("SELECT * FROM t_user WHERE user_Name = ?", new String[]{userName});
        if (cursor.moveToFirst()) {
            getName.setText(cursor.getString(cursor.getColumnIndex("user_Name")));
            getPassword.setText(cursor.getString(cursor.getColumnIndex("user_Password")));
            getTel.setText(cursor.getString(cursor.getColumnIndex("user_Tel")));
            getEmail.setText(cursor.getString(cursor.getColumnIndex("user_Email")));
            int sex = cursor.getInt(cursor.getColumnIndex("user_Sex"));
            if (sex == 0) {
                sexRadio.check(R.id.sex_boy);
            } else {
                sexRadio.check(R.id.sex_girl);
            }
        }
        selectedRadioButton = findViewById(sexRadio.getCheckedRadioButtonId());
        if(selectedRadioButton.getText().toString().equals("男")){
            getSex = 0; // 假设男性用 0 表示
        } else {
            getSex = 1; // 假设女性用 1 表示
        }
        cursor.close();
    }
    public void getAllUserNamesFromDatabase() {
        ArrayList<String> userNames = new ArrayList<>();
        // 在这里执行查询数据库的操作，获取所有用户名数据，并存储到userNames中
        Cursor cursor = database.rawQuery("SELECT user_Name FROM t_user", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String userName = cursor.getString(cursor.getColumnIndex("user_Name"));

                userNames.add(userName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 设置适配器给Spinner
        spinner.setAdapter(adapter);
    }

    private void getText(){
        //四个按钮的对象
        addButton=(Button) findViewById(R.id.buttonread);
        saveButton = (Button) findViewById(R.id.buttonsave);
        deleteButton = (Button) findViewById(R.id.buttondeltet);
        emptyButton = (Button) findViewById(R.id.buttonblank);
        //性别选项
        getName = (EditText) findViewById(R.id.editUserName);
        getPassword = (EditText) findViewById(R.id.editPassword);
        getTel = (EditText) findViewById(R.id.editTel);
        getEmail = (EditText) findViewById(R.id.editEmail);
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
            selectedRadioButton = findViewById(sexRadio.getCheckedRadioButtonId());
            if(selectedRadioButton.getText().toString().equals("男")){
                getSex = 0; // 假设男性用 0 表示
            } else {
                getSex = 1; // 假设女性用 1 表示
            }
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
            selectedRadioButton = findViewById(sexRadio.getCheckedRadioButtonId());
            if(selectedRadioButton.getText().toString().equals("男")){
                getSex = 0; // 假设男性用 0 表示
            } else {
                getSex = 1; // 假设女性用 1 表示
            }
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
