package com.example.muproject.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muproject.R;
import com.example.muproject.databinding.ItemRvBinding;
import com.example.muproject.ui.home.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************
 * Created By LiXin  2020/10/30 13:35
 * ****************************************
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private List<DataBean> dataList = new ArrayList<>();

   public void setNewData(List<DataBean> list){
       dataList.clear();
       dataList.addAll(list);
       notifyDataSetChanged();
   }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_rv,parent,false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setDatabean(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ItemRvBinding binding;
        public MyViewHolder(@NonNull ItemRvBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

}
