package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MyViewPage2Adapter;
import com.example.myapplication.databinding.ActivityAndroidViewTestBinding;
import com.google.android.material.snackbar.Snackbar;

public class AndroidViewTestActivity extends AppCompatActivity {
    private ActivityAndroidViewTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDate();
    }

    private void initDate() {
        MyViewPage2Adapter adapter = new MyViewPage2Adapter();
        binding.vp2.setAdapter(adapter);
        //设置纵向滑动
        binding.vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss = s.toString();
                if(ss.length()<5){
                    binding.ilInput.setError("请输入大于五位");
                }else {
                    binding.ilInput.setError(null);
                }
            }
        });

        binding.tb.inflateMenu(R.menu.toolbarnemu);
        binding.tb.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.action_search:
                    Toast.makeText(AndroidViewTestActivity.this,"click action_search",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_notifications:
                    Toast.makeText(AndroidViewTestActivity.this,"click action_notifications",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:
                    Toast.makeText(AndroidViewTestActivity.this,"click action_settings",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        });

        /**
         * Snackbar是Android Support Design Library库支持的一个控件，使用的时候需要一个控件容器用来容纳Snackbar.
         * 官方推荐使用CoordinatorLayout这个另一个Android Support Design Library库支持的控件容纳。因为使用这
         * 个控件，可以保证Snackbar可以让用户通过向右滑动退出。
         *
         * 和Toast使用方法类似,第一个参数是一个view，这里是CoordinatorLayout，第二个参数是提示的内容，第三个参数是显示的时常
         *
         * Snackbar.LENGTH_INDEFINITE 是一直显示，只有右滑或者点击事件以后，可以移除
         *
         * Snackbar.LENGTH_SHORT 和Toast的显示时长属性一样
         *
         * Snackbar.LENGTH_LONG 和Toast的显示时长属性一样
         *
         * 第一个参数是可点击文字内容
         *
         * 第二个参数是文字的点击事件
         *
         * Snackbar.class并没有给我们提供接口让我们来修改描述文字的字体颜色，如果一定要改也不是没有办法，可以获取TextView实例以后，修改字体颜色，然后再show()出来。
         *
         * 定义一个修改Snackbar描述文字颜色的方法（修改字体大小等属性也是同理，很简单，不一一举例了）
         */
        binding.floatingActionButton.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        Snackbar snackbar = Snackbar.make(binding.cl, "有一条消息",
                Snackbar.LENGTH_LONG).setAction("查看", v1 -> Toast.makeText(AndroidViewTestActivity.this,
                "i see", Toast.LENGTH_SHORT).show());
        View view = snackbar.getView();
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ed52fb"));
        snackbar.setActionTextColor(Color.parseColor("#ff52fb"));
        snackbar.show();
    }
}