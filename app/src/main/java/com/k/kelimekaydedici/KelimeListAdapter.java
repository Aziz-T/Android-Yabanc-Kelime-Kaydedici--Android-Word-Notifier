package com.k.kelimekaydedici;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KelimeListAdapter extends RecyclerView.Adapter<KelimeListAdapter.ViewHolder> {
    private List<KelimeModel> kelimeModelList;
    private KelimeListener kelimeListener;

    public KelimeListAdapter(List<KelimeModel> kelimeModelList,KelimeListener kelimeListener) {
        this.kelimeModelList = kelimeModelList;
        this.kelimeListener = kelimeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kelime_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(kelimeModelList.get(position).getKelime(),kelimeModelList.get(position).getCevirisi(),position);

    }

    @Override
    public int getItemCount() {
        return kelimeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAnlam;
        private TextView textViewCeviri;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAnlam=itemView.findViewById(R.id.kelimeTxt);
            textViewCeviri=itemView.findViewById(R.id.ceviriTxt);
        }

        public void setData(String kelime, String cevirisi, final int position) {
            textViewAnlam.setText("Kelime: "+kelime);
            textViewCeviri.setText("Çevirisi: "+cevirisi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kelimeListener.onKelimeListener(kelimeModelList.get(position),position);
                }
            });
        }

    }
}
