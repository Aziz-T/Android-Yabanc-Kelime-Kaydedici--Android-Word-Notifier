package com.k.kelimekaydedici;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KelimeAdapter extends RecyclerView.Adapter<KelimeAdapter.ViewHolder> {
    private List<KelimeModel> kelimeModels;

    public KelimeAdapter(List<KelimeModel> kelimeModels) {
        this.kelimeModels = kelimeModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kelime_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(kelimeModels.get(position).getKelime(),kelimeModels.get(position).getAnlami(),position);
    }

    @Override
    public int getItemCount() {
        return kelimeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView kelimeText;
        private TextView anlamText;
        private ImageButton delete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            kelimeText=itemView.findViewById(R.id.kelimeText);
            anlamText=itemView.findViewById(R.id.anlamiText);
            delete=itemView.findViewById(R.id.deleteButton);
        }

        public void setData(String kelime, String anlami, final int position) {
            kelimeText.setText(kelime);
            anlamText.setText(anlami);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kelimeModels.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,kelimeModels.size());
                }
            });

        }
    }
}
