package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityTabLayoutTestBinding;
import com.example.myapplication.fragment.TabFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutTestActivity extends AppCompatActivity {
    private ActivityTabLayoutTestBinding binding;
    private String[] tabs = {"tab1", "tab2", "tab3"};
    private List<TabFragment> tabFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabLayoutTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //添加tab,为TabLayout添加tab
        for (int i = 0; i < tabs.length; i++) {
//            TabLayout.Tab tab = binding.tl.newTab();
//            tab.setText(tabs[i]);
//            binding.tl.addTab(tab);
            binding.tl.addTab(binding.tl.newTab().setText(tabs[i]));
            tabFragmentList.add(TabFragment.newInstance(tabs[i]));
        }

        //给ViewPager设置adapter
        binding.vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return tabFragmentList.size();
            }

            /**
             * 在给ViewPager设置Adapter的时候，一定要重写getPageTitle(int position)方法，不然TabLayout中的
             * 标签是看不到的，即使在addTab时newTab().setText(tabs[i])也没用。原因很简单，是在
             * tabLayout.setupWithViewPager的时候，TabLayout中先将所有tabs remove了，然后取的PagerAdapter
             * 中的getPageTitle返回值添加的tab
             * @param position
             * @return
             */
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });

        //设置TabLayout和ViewPager联动
        binding.tl.setupWithViewPager(binding.vp,false);
        //设置指示器线条的宽度和文字得宽度一致，默认flase不一致
        binding.tl.setTabIndicatorFullWidth(true);
        //默认点击每一个tab的时候，会出现渐变的背景色
//        binding.tl.setTabRippleColor();
        //如果想要去掉这个点击时的背景，可以通过设置tabRippleColor属性值为一个透明的背景色就可以了
        //有时候如果设计师需要我们实现，选中的tab文字字体加粗并放大字号，但是TabLayout并没有直接设置字体大小样式的属性，
        // 这时候就可以通过设置自定义属性tabTextAppearance来实现，其值是一个自定义style。
    }

    /**
     * 测试kt调用关键词的java函数
     */

    public static void in(){

    }
}