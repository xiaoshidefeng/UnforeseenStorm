package com.example.cw.unforeseenstorm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cw.unforeseenstorm.Bean.SuggestionBean;
import com.example.cw.unforeseenstorm.R;

import java.util.ArrayList;

/**
 * Created by cw on 2017/6/2.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {

    private ArrayList<SuggestionBean> mData;

    private int[] imgId = {
            R.mipmap.happy,
            R.mipmap.car,
            R.mipmap.cloth,
            R.mipmap.run,
            R.mipmap.sun
    };

    public SuggestionAdapter(ArrayList<SuggestionBean> data) {
        this.mData = data;
    }

    @Override
    public SuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_layout, parent, false);
        // 实例化viewholder
        SuggestionAdapter.ViewHolder viewHolder = new SuggestionAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SuggestionAdapter.ViewHolder holder, int position) {
        // 绑定数据
        holder.imageViewSug.setImageResource(imgId[position]);

        holder.tvSuggestion.setText(mData.get(position).suggesion);
        holder.tvSuggestionValue.setText(mData.get(position).brf);
        holder.tvSuggestionContent.setText(mData.get(position).txt);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSug;
        TextView tvSuggestion;
        TextView tvSuggestionValue;
        TextView tvSuggestionContent;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewSug = (ImageView) itemView.findViewById(R.id.id_img_suggestion);
            tvSuggestion = (TextView) itemView.findViewById(R.id.id_TvSuggestion);
            tvSuggestionValue = (TextView) itemView.findViewById(R.id.id_TvSuggestionValue);
            tvSuggestionContent = (TextView) itemView.findViewById(R.id.id_TvSuggestionContent);
        }
    }
}
