package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.bean.ImageViewBean;
import com.example.myapplication.bean.SuccessBean;
import com.example.myapplication.databinding.ActivityDataBindingTestBinding;

/**
 * 自动空指针检查
 * Data Binding 会对变量进行空判断，假如说未对给定的变量赋值的话，就会给予变量一个默认的值，比如：
 * { user.name } -> null
 * { user.age } -> 0
 *
 * include
 * Data Binding 支持 include 传递变量，如：
 *
 * <include layout = "@layout/name" bind:user = "@{user}"/>
 * 1
 * 但是 Data Binding 并不支持 direct child，例如引入的 layout 根标签为 merge
 */
public class DataBindingTestActivity extends AppCompatActivity {
    private ActivityDataBindingTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test);
        SuccessBean successBean = new SuccessBean();
        successBean.setId("123");
        successBean.setName("zzzzz");
        binding.setBean(successBean);
        ImageViewBean imageViewBean = new ImageViewBean();
        imageViewBean.setError(getResources().getDrawable(R.drawable.ic_launcher_background));
        imageViewBean.setPlaceholder(getResources().getDrawable(R.drawable.ic_launcher_background));
        imageViewBean.setUrl("https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=3653784530,2326162019&os=1719021373,2983305160&simid=85438943,677312878&pn=6&rn=1&di=7060663421280190465&ln=1570&fr=&fmq=1648518811231_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%253A%252F%252Fimg.jj20.com%252Fup%252Fallimg%252F1113%252F061H0102U6%252F20061G02U6-12-1200.jpg%26refer%3Dhttp%253A%252F%252Fimg.jj20.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Dauto%3Fsec%3D1651110811%26t%3Dade8d94784e07da75c50e4ef8e91e870&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&dyTabStr=MCwzLDYsMSw0LDIsNSw3LDgsOQ%3D%3D");
        binding.setImagevo(imageViewBean);
        
    }
}