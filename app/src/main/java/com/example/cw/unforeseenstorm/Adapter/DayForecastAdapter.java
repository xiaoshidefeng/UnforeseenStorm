package com.example.cw.unforeseenstorm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cw.unforeseenstorm.Bean.DayForecastBean;
import com.example.cw.unforeseenstorm.R;

import java.util.ArrayList;

/**
 * Created by cw on 2017/5/30.
 */

public class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.ViewHolder> {

    private ArrayList<DayForecastBean> mData;

    public DayForecastAdapter(ArrayList<DayForecastBean> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_forecast_item_layout, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.tvDay.setText(mData.get(position).date);
        holder.tvMaxTmp.setText(mData.get(position).maxTmp + "℃");
        holder.tvMinTmp.setText(mData.get(position).minTmp + "℃");
        holder.tvDayWeather.setText(mData.get(position).txt_d);
        String weather = mData.get(position).txt_d;
        if(weather.equals("晴")) {
            holder.imageViewWeather.setImageResource(R.mipmap.sun);

        } else if(weather.contains("雨")) {
            holder.imageViewWeather.setImageResource(R.mipmap.rainy);

        }else {
            holder.imageViewWeather.setImageResource(R.mipmap.cloud);
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay;
        ImageView imageViewWeather;
        TextView tvMaxTmp;
        TextView tvMinTmp;
        TextView tvDayWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.id_TvDayItem);
            imageViewWeather = (ImageView) itemView.findViewById(R.id.id_img_dayWeather);
            tvMaxTmp = (TextView) itemView.findViewById(R.id.id_TvMaxTmp);
            tvMinTmp = (TextView) itemView.findViewById(R.id.id_TvMinTmp);
            tvDayWeather = (TextView) itemView.findViewById(R.id.id_TvDayWeather);
        }
    }
}
