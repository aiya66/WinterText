package com.example.a2025;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2025.adapter.TipsAdapter;
import com.example.a2025.bean.DayWeatherBean;

public class TipsActivity2 extends AppCompatActivity {
    //声明UI组件
    private RecyclerView rlvTips;
    private TipsAdapter mTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tips2);
        //绑定ID
        rlvTips=findViewById(R.id.rlv_tips);
        //数据传递
        Intent intent=getIntent();
        DayWeatherBean weatherBean=(DayWeatherBean) intent.getSerializableExtra("tips");
        //保险
        if (weatherBean==null){
            return;
        }
        // 配置RecyclerView及数据绑定
        mTipsAdapter=new TipsAdapter(this,weatherBean.getTipsBeans());
        //绑定适配器
        rlvTips.setAdapter(mTipsAdapter);
        //设置布局管理
        rlvTips.setLayoutManager(new LinearLayoutManager(this));

    }
}