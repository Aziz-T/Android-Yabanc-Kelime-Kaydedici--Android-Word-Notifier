package com.k.kelimekaydedici;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    private List<KlasorModel> klasorModelList;
    private KlasorListener klasorListener;
    private String durum;
    private Context context;
    public DialogAdapter(List<KlasorModel> klasorModelList,KlasorListener klasorListener,String durum,Context context) {
        this.klasorModelList = klasorModelList;
        this.klasorListener = klasorListener;
        this.durum = durum;
        this.context=context;
    }
    @NonNull
    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(durum.equals("asil")){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_baslik_item_iki,parent,false);
        } else if (durum.equals("dialog")){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_baslik_item,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogAdapter.ViewHolder holder, int position) {
        holder.setData(klasorModelList.get(position).getIsim(),position);
    }

    @Override
    public int getItemCount() {
        return klasorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textBaslik);

        }

        public void setData(final String isim, final int position) {
            textView.setText(isim);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    klasorListener.onKlasorListener(klasorModelList.get(position),position);
                }
            });
        }
    }
}
