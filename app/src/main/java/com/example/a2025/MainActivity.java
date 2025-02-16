package com.example.a2025;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2025.adapter.FutureWeatherAdapter;
import com.example.a2025.bean.DayWeatherBean;
import com.example.a2025.bean.WeatherBean;
import com.google.gson.Gson;
import com.example.a2025.util.NetUtil;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //声明UI组件
    private AppCompatSpinner mSpinner;
    private ArrayAdapter<String> mspAdapter;
    private String[] mCities;

    private TextView tvWeather,tvTem,tvTemLowHigh,tvWin,tvAir;
    private ImageView ivWeather;
    private RecyclerView rlvFutureWeather;
    private FutureWeatherAdapter mWeatherAdapter;
    private DayWeatherBean todayWeather;//储存当天天气数据

    /**
     * 主线程Handler，用于处理子线程发送的天气数据
     * 功能：接收网络请求返回的天气数据，更新UI
     */
    private Handler mHandler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String weather=(String) msg.obj;
                Log.d("fan","--主线程收到了线程数据-weather---"+weather);
                // 使用Gson解析JSON数据为WeatherBean对象
                Gson gson=new Gson();
                WeatherBean weatherBean=gson.fromJson(weather, WeatherBean.class);
                Log.d("fan","--解析后的数据-weather---"+weatherBean.toString());

                updateUiOfWeather(weatherBean);//更新界面


            }
        }
    };
    //此方法主要是为了把数据搞到界面上
    private void updateUiOfWeather(WeatherBean weatherBean) {
        //为了保险
        if(weatherBean==null){
            return;
        }
        List<DayWeatherBean> dayWeathers=weatherBean.getDayWeathers();
        todayWeather=dayWeathers.get(0);
        //再次保险
        if (todayWeather==null){
            return;
        }
        //规定好显示的情况（今天的）
        tvTem.setText(todayWeather.getTem());
        tvWeather.setText(todayWeather.getWea()+"("+todayWeather.getDate()+todayWeather.getWeek()+")");
        tvTemLowHigh.setText(todayWeather.getTem2()+"~"+todayWeather.getTem1());
        tvWin.setText(todayWeather.getWin()[0]+todayWeather.getWinSpeed());
        tvAir.setText("空气:"+todayWeather.getAir()+" "+todayWeather.getAirLevel()+"\n"+todayWeather.getAirTips());
        ivWeather.setImageResource(getImgResOfWeather(todayWeather.getWeaImg()));
        //去掉当天的天气
        dayWeathers.remove(0);
        //显示未来的
        mWeatherAdapter=new FutureWeatherAdapter(this,dayWeathers);
        rlvFutureWeather.setAdapter(mWeatherAdapter);
        //线性布局，所以这么搞
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rlvFutureWeather.setLayoutManager(layoutManager);
    }
    //图片显示情况
    private  int getImgResOfWeather(String weaStr){
        //九种天气
        int result=0;
        switch (weaStr){
            case "qing":
                result=R.drawable.qing;
                break;
            case "yin":
                result=R.drawable.yin;
                break;
            case "yu":
                result=R.drawable.yu;
                break;
            case "yun":
                result=R.drawable.yun;
                break;
            case "binbao":
                result=R.drawable.binbao;
                break;
            case "wu":
                result=R.drawable.wu;
                break;
            case "shachen":
                result=R.drawable.shachen;
                break;
            case "lei":
                result=R.drawable.lei;
                break;
            case "xue":
                result=R.drawable.xue;
                break;
            default:
                break;
        }
        return result;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initView();


    }



    private void initView() {
        // 初始化城市选择下拉框
        mSpinner=findViewById(R.id.sp_city);
        mCities=getResources().getStringArray(R.array.cities);
        mspAdapter=new ArrayAdapter<>(this,R.layout.sp_item_layout,mCities);
        mSpinner.setAdapter(mspAdapter);
        // 设置城市选择监听
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCity=mCities[position];

                getWeatherOfCity(selectedCity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 绑定天气信息显示组件
        tvWeather=findViewById(R.id.tv_weather);
        tvAir=findViewById(R.id.tv_air);
        tvTem=findViewById(R.id.tv_tem);
        tvTemLowHigh=findViewById(R.id.tv_tem_low_high);
        tvWin=findViewById(R.id.tv_win);
        ivWeather=findViewById(R.id.iv_weather);
        rlvFutureWeather=findViewById(R.id.rlv_future_weather);
        //设置点击事件
        tvAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,TipsActivity2.class);
                //将数据传递给TipsActivity
                intent.putExtra("tips",todayWeather);
                 startActivity(intent);

            }
        });
    }
    //启动子线程进行网络请求，结果通过Handler传递
    private void getWeatherOfCity(String selectedCity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求网络
                String weatherOfCity=NetUtil.getWeatherOfCity(selectedCity);// 网络请求
                //使用handler将数据传递给主线程
                Message message=Message.obtain();//这么搞效率高些
                message.what=0;
                message.obj=weatherOfCity;

                mHandler.sendMessage(message);// 发送到主线程处理

            }
        }).start();
    }


}
