package com.example.a2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2025.R;
import com.example.a2025.bean.OtherTipsBean;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {
    // 数据源与上下文声明
    private Context mContext;
    private List<OtherTipsBean> mTipsBeans;
    //构造方法初始化适配器
    public TipsAdapter(Context context,List<OtherTipsBean> mTipsBeans) {
        this.mTipsBeans = mTipsBeans;
        this.mContext = context;
    }
    // 加载列表项布局文件
    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.tips_item_layout,parent,false);
        return new TipsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        // 获取当前位置的生活提示数据
        OtherTipsBean otherTipsBean=mTipsBeans.get(position);
        // 数据绑定到视图组件
        holder.tvTitle.setText(otherTipsBean.getTitle());
        holder.tv_Level.setText(otherTipsBean.getLevel());
        holder.tvDesc.setText(otherTipsBean.getDesc());

    }

    @Override
    public int getItemCount() {
        //保险
        if (mTipsBeans==null){
            return 0;
        }
        return mTipsBeans.size();
    }
    //ViewHolder内部类实现复用视图组件
    class TipsViewHolder extends RecyclerView.ViewHolder{
        // 声明列表项视图组件
        TextView tvTitle,tvDesc,tv_Level;
        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            //绑定ID
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvDesc=itemView.findViewById(R.id.tv_desc);
            tv_Level=itemView.findViewById(R.id.tv_level);
        }
    }
}
