package com.sylvester.ams.controller.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.model.Arthropod;
import com.sylvester.ams.controller.Detail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RealmRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Arthropod> arthropodArrayList;

    // MyAdapter를 생성할 때 표시하고자하는 데이터를 전달합니다.
    public RealmRecyclerViewAdapter(ArrayList<Arthropod> arthropodArrayList) {
        this.arthropodArrayList = arthropodArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item
        CardView cardView;
        ImageView iv_picture;
        TextView tv_name, tv_scientific_name, tv_sex, tv_raise_type, tv_life_stages, tv_last_fed;

        ViewHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.cv_item);
            iv_picture = view.findViewById(R.id.iv_picture);
            tv_name = view.findViewById(R.id.tv_name);
            tv_scientific_name = view.findViewById(R.id.tv_scientific_name);
            tv_sex = view.findViewById(R.id.tv_sex);
            tv_raise_type = view.findViewById(R.id.tv_raise_type);
            tv_life_stages = view.findViewById(R.id.tv_life_stages);
            tv_last_fed = view.findViewById(R.id.tv_last_fed);
        }
    }

    // onCreateViewHolder()함수는 RecyclerView의 행을 표시하는데 사용되는 레이아웃 xml을 가져오는 역할을 합니다.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_cardview_row, parent, false);

        return new ViewHolder(v);
    }

    // onBindViewHolder()함수에서 마침내 RecyclerView의 행에 보여질 ImageView와 TextView를 설정합니다.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder myViewHolder = (ViewHolder) holder;
        final Arthropod arthropod = arthropodArrayList.get(position);

        myViewHolder.iv_picture.setImageResource(arthropod.getDrawableId());

        if(arthropod.getName() != null) {
            myViewHolder.tv_name.setText(arthropod.getName());
        } else {
            myViewHolder.tv_name.setVisibility(View.GONE);
        }

        if(arthropod.getArthropodInfo() != null) {
            myViewHolder.tv_scientific_name.setText(arthropod.getArthropodInfo().getScientificName());
        } else {
            myViewHolder.tv_scientific_name.setVisibility(View.GONE);
        }

        if(arthropod.getSex() != 0) {
            myViewHolder.tv_sex.setText(Integer.toString(arthropod.getSex()));
        } else {
            myViewHolder.tv_sex.setVisibility(View.GONE);
        }

        if(arthropod.getArthropodInfo().getBehavior() != null) {
            myViewHolder.tv_raise_type.setText(arthropod.getArthropodInfo().getBehavior());
        } else {
            myViewHolder.tv_raise_type.setVisibility(View.GONE);
        }

        if(arthropod.getLife_stages() != null) {
            myViewHolder.tv_life_stages.setText(arthropod.getLife_stages());
        } else {
            myViewHolder.tv_life_stages.setVisibility(View.GONE);
        }

        if(arthropod.getLast_fed() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.mm.dd hh:mm:ss a");
            String date = formatter.format(arthropod.getLast_fed());
            myViewHolder.tv_last_fed.setText(date);
        } else {
            myViewHolder.tv_last_fed.setVisibility(View.GONE);
        }

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("arthropod", arthropod.getKey());
                view.getContext().startActivity(intent);
            }});
    }

    // getItemCount()함수는 RecyclerView의 행 갯수를 리턴합니다.
    @Override
    public int getItemCount() {
        return arthropodArrayList.size();
    }
}