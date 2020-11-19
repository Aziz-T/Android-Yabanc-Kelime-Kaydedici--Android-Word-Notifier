package com.k.kelimekaydedici;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DilAdapter extends RecyclerView.Adapter<DilAdapter.ViewHolder> {
   private List<DilModel> dilModelList;

    public DilAdapter(List<DilModel> dilModelList) {
        this.dilModelList = dilModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dil_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(dilModelList.get(position).getIsim(),position);
    }

    @Override
    public int getItemCount() {
        return dilModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.baslikText);
            imageButton=itemView.findViewById(R.id.imageDelete);


        }

        public void setData(final String isim, final int position) {
            textView.setText(isim);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dilModelList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,dilModelList.size());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),TurActivity.class);
                    intent.putExtra("ata",isim);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}
