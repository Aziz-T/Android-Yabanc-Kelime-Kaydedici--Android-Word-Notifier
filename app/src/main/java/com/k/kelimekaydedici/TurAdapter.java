package com.k.kelimekaydedici;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TurAdapter extends RecyclerView.Adapter<TurAdapter.ViewHolder> {
   private List<TurModel> turModelList;

    public TurAdapter(List<TurModel> turModelList) {
        this.turModelList = turModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dil_item,parent,false);
        return new TurAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(turModelList.get(position).getIsim(),position);
    }

    @Override
    public int getItemCount() {
        return turModelList.size();
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
                    turModelList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,turModelList.size());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),KelimeActivity.class);
                    intent.putExtra("ata",isim);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
