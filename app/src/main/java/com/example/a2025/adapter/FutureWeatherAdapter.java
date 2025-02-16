package com.example.a2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2025.R;
import com.example.a2025.bean.DayWeatherBean;

import java.util.List;

public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {
    //设置上下文和数据
    private Context mContext;
    private List<DayWeatherBean> mWeatherBeans;
    // 构造方法初始化数据源
    public FutureWeatherAdapter(Context mContext, List<DayWeatherBean> mWeatherBeans ){
        this.mContext = mContext;
        this.mWeatherBeans=mWeatherBeans;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //导入数据
        View view=LayoutInflater.from(mContext).inflate(R.layout.weather_item_layout,parent,false);
        WeatherViewHolder weatherViewHolder=new WeatherViewHolder(view);
        return weatherViewHolder;
    }
    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        //安排好显示的布局
        DayWeatherBean weatherBean=mWeatherBeans.get(position);
        holder.tvWeather.setText(weatherBean.getWea());
        holder.tvDate.setText(weatherBean.getDate());
        holder.tvTem.setText(weatherBean.getTem());
        holder.tvTemLowHigh.setText(weatherBean.getTem2()+"~"+weatherBean.getTem1());
        holder.tvWin.setText(weatherBean.getWin()[0]+weatherBean.getWinSpeed());
        holder.tvAir.setText("空气："+weatherBean.getAir()+weatherBean.getAirLevel());
        holder.ivWeather.setImageResource(getImgResOfWeather(weatherBean.getWeaImg()));


    }
//返回数据的多少项
    @Override
    public int getItemCount() {
        //防止报错
        if (mWeatherBeans==null){
            return 0;
        }
        return mWeatherBeans.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{
        // 声明列表项视图组件
        TextView tvWeather,tvTem,tvTemLowHigh,tvWin,tvAir,tvDate;
        ImageView ivWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            //绑定id
            tvWeather=itemView.findViewById(R.id.tv_weather);
            tvAir=itemView.findViewById(R.id.tv_air);
            tvTem=itemView.findViewById(R.id.tv_tem);
            tvTemLowHigh=itemView.findViewById(R.id.tv_tem_low_high);
            tvWin=itemView.findViewById(R.id.tv_win);
            ivWeather=itemView.findViewById(R.id.iv_weather);
            tvDate=itemView.findViewById(R.id.tv_date);
        }
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

}
