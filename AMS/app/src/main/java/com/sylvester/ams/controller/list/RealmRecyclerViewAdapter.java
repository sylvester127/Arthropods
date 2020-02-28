package com.sylvester.ams.controller.list;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sylvester.ams.R;
import com.sylvester.ams.entity.Arthropod;
import com.sylvester.ams.controller.detail.Detail;
import com.sylvester.ams.entity.ScientificName;
import com.sylvester.ams.service.ArthropodListService;
import com.sylvester.ams.service.realm.RealmArthropodListService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RealmRecyclerViewAdapter extends RecyclerView.Adapter<RealmRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Arthropod> arthropods;
    private ArthropodListService service;

    // 생성자
    public RealmRecyclerViewAdapter(ArrayList<Arthropod> arthropods) {
        this.arthropods = arthropods;
        service = new RealmArthropodListService();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item
        CardView cardView;
        ImageView iv_picture;
        TextView tv_name, tv_scientific_name, tv_content, tv_last_fed;

        ViewHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.cv_item);
            iv_picture = view.findViewById(R.id.iv_picture);
            tv_name = view.findViewById(R.id.tv_name);
            tv_scientific_name = view.findViewById(R.id.tv_scientific_name);
            tv_content = view.findViewById(R.id.tv_content);
            tv_last_fed = view.findViewById(R.id.tv_last_fed);
        }
    }

    // viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성
    @Override
    public RealmRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_list_cardview_row, parent, false);
        RealmRecyclerViewAdapter.ViewHolder vh = new RealmRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(RealmRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Arthropod arthropod = arthropods.get(position);
        ScientificName scientificName = service.getScientificName(arthropod.getId());
        
        holder.iv_picture.setImageBitmap(service.getArthropodImg(arthropod.getImgDir()));

        if (arthropod.getName() != null)
            holder.tv_name.setText(arthropod.getName());
        else
            holder.tv_name.setVisibility(View.GONE);

        if (!scientificName.equals(null)) {
            holder.tv_scientific_name.setText(scientificName.getGenus() +" "+ scientificName.getSpecies());
        }

        String content = "";
        content += arthropod.getGender();

        if (!arthropod.getHabit().equals(""))
            content += ", " + arthropod.getHabit();

        if (!arthropod.getMoltCount().equals(""))
            content += ", " + arthropod.getMoltCount();

        holder.tv_content.setText(content);

        if (arthropod.getLastFeedDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.mm.dd hh:mm:ss a");
            String date = formatter.format(arthropod.getLastFeedDate());
            holder.tv_last_fed.setText(date);
        } else
            holder.tv_last_fed.setVisibility(View.GONE);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("arthropodId", arthropod.getId());
                view.getContext().startActivity(intent);
                ArthropodListContext.activity.finish();
            }
        });
    }

    // 전체 아이템 갯수 리턴
    @Override
    public int getItemCount() {
        return arthropods.size();
    }
}