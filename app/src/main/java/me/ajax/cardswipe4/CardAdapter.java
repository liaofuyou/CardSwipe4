package me.ajax.cardswipe4;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aj on 2018/4/23
 */


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private List<Integer> datas = new ArrayList<>();

    CardAdapter() {
        for (int i = 0; i < 13; i++) {
            datas.add(0);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setStr(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义Holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }

        public void setStr(int drawRes) {
            imageView.setImageResource(drawRes);
        }

    }

    public void setData(List<Integer> datas) {
        this.datas = datas;
    }
}

