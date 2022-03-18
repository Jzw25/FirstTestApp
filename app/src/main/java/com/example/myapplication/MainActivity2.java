package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myapplication.database.MySqlLiteHelp;
import com.example.myapplication.room.PersonDao;
import com.example.myapplication.room.PersonDatabase;
import com.example.myapplication.room.Pserons;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityMain2Binding;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMain2Binding binding;

    private GestureDetector detector;


    /**
     * 不强制实现所有方法，选择实现
     */
    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detector = new GestureDetector(this,listener);
        //双击事件监听
        detector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            //单击事件
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            //双击事件
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
     binding = ActivityMain2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setAsyncTask(){
        MyThread myThread = new MyThread();
        myThread.execute("aaa");
    }

    class MyThread extends AsyncTask<String,Integer,String>{
        //开始前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //线程执行完毕
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        //进度跟新
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        //执行中
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    private void setHandlerThred(){
        HandlerThread mht = new HandlerThread("myhandler");
        mht.start();

        Handler handler = new Handler(mht.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==2){
                    //dosomething
                }
                super.handleMessage(msg);
            }
        };

        handler.sendEmptyMessage(2);
    }

    private void setIntentService(){
        Intent intent = new Intent("com.jzw.myitentservice");
        intent.putExtra("aaa","aaa");
        startService(intent);
    }

    private void setDatabase(){
        MySqlLiteHelp instance = MySqlLiteHelp.getInstance(this);
        SQLiteDatabase writableDatabase = instance.getWritableDatabase();
    }

    //查询数据库
    private void searchDatebase(){
        MySqlLiteHelp instance = MySqlLiteHelp.getInstance(this);
        SQLiteDatabase readableDatabase = instance.getReadableDatabase();//读操作
        //确保数据库打开
        if(readableDatabase.isOpen()){
            String sql = "select * from persons";
            //返回游标对象
            Cursor cursor = readableDatabase.rawQuery(sql,null);
            //遍历数据
            while (cursor.moveToNext()){
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                Log.d("chaxun", "searchDatebase: id="+id+"name="+name);
            }
            //关闭游标
            cursor.close();
            //关闭数据库
            readableDatabase.close();
        }
    }

    //插入数据到数据库
    private void insertDatabase(){
        MySqlLiteHelp instance = MySqlLiteHelp.getInstance(this);
        SQLiteDatabase writableDatabase = instance.getWritableDatabase();//写操作

        //插入语句
        String sql = "insert into pserons(name) values('jzw')";
        if(writableDatabase.isOpen()){
            writableDatabase.execSQL(sql);

            writableDatabase.close();
        }
    }

    //跟新数据
    private void updateDatabase(){
        MySqlLiteHelp instance = MySqlLiteHelp.getInstance(this);
        SQLiteDatabase writableDatabase = instance.getWritableDatabase();//写操作

        if(writableDatabase.isOpen()){

            String sql = "update persons set name = ? where _id = ?";
            writableDatabase.execSQL(sql,new Object[]{"ytt",4});

            writableDatabase.close();
        }
    }

    //删除操作
    private void deletDatabase(){
        MySqlLiteHelp instance = MySqlLiteHelp.getInstance(this);
        SQLiteDatabase writableDatabase = instance.getWritableDatabase();//写操作

        if(writableDatabase.isOpen()){

            String sql = "delete from persons where _id = ?";
            writableDatabase.execSQL(sql,new Object[]{4});

            writableDatabase.close();
        }
    }

    private void setQueryDataBase(){
        //开线程
        PersonDatabase personDatabase = PersonDatabase.getInstance(this);
        PersonDao personDao = personDatabase.getPersonDao();
        List<Pserons> query = personDao.query();
    }

    //录像
    private void setMediaRecorder(){

    }

}