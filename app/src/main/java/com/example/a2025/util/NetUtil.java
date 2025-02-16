package com.example.a2025.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {
    // 天气API基础地址
    public static final String URL_WEATHER_WITH_FUTURE="http://gfeljm.tianqiapi.com/api?unescape=1&version=v9&appid=17329277&appsecret=3DLOd1uW";
    public static String doGet(String urlStr) {
        String result = "";
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader=null;
        //连接网络，执行GET网络请求
        try {
            // 创建URL对象并建立连接
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            //从连接中读取数据(二进制)
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            //二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);
            //从缓冲区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }finally {                     // 关闭
            if(connection!=null){
                connection.disconnect();
            }
            if (inputStreamReader!=null){
                try {
                    inputStreamReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }
    //获取指定城市的天气数据
    public static String getWeatherOfCity(String city){
        //拼接出获取天气数据的URL
        //http://gfeljm.tianqiapi.com/api?unescape=1&version=v9&appid=17329277&appsecret=3DLOd1uW
        String weatherUrl=URL_WEATHER_WITH_FUTURE+"&city="+city;
        Log.d("fan","------"+weatherUrl);
        // 执行网络请求并记录结果
        String weatherResult=doGet(weatherUrl);
        Log.d("fan","------"+weatherResult);

        return weatherResult;
    }
}
