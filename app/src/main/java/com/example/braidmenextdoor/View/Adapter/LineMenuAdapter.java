package com.example.braidmenextdoor.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braidmenextdoor.R;
import com.example.braidmenextdoor.Model.Bean.TypeCoiffureBean;

import java.util.ArrayList;

public class LineMenuAdapter extends RecyclerView.Adapter<LineMenuAdapter.ViewHolder> {

    /* Déclaration ArrayList*/
    private ArrayList<TypeCoiffureBean> typeCoiffureBeanArrayList;

    /* Constructor */
    public LineMenuAdapter(ArrayList<TypeCoiffureBean> typeCoiffureBeanArrayList) {
        this.typeCoiffureBeanArrayList = typeCoiffureBeanArrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_line,parent,false);
        return new LineMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypeCoiffureBean typeCoiffureBean = typeCoiffureBeanArrayList.get(position);
        holder.title_txt.setText(typeCoiffureBean.getTitle());
        holder.coiffure_img.setImageResource(typeCoiffureBean.getImage());
    }

    @Override
    public int getItemCount() {
        return typeCoiffureBeanArrayList.size();
    }

    /* Sous classe */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView coiffure_img;
        public TextView title_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coiffure_img = (ImageView) itemView.findViewById(R.id.coiffure_img);
            title_txt = (TextView) itemView.findViewById(R.id.title_txt);
        }

    }


}
