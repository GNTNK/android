package com.example.categoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerHolder> {
    private List<Spinner> spinners = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public SpinnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_item, parent, false);
        return new SpinnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerHolder holder, int position) {
        Spinner currentSpinner = spinners.get(position);
        holder.categoryName.setText(currentSpinner.getSpinner());
    }

    @Override
    public int getItemCount() {
        return spinners.size();
    }

    public void setSpinners(List<Spinner> spinners) {
        this.spinners = spinners;
        notifyDataSetChanged();
    }

    public Spinner getSpinnerAt(int position) {
        return spinners.get(position);
    }

    class SpinnerHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public SpinnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.text_view_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(spinners.get(position));
                    }
                }
            });
        }
    }

    public void update(List<Spinner> spinners) {
        this.spinners = new ArrayList<>();
        this.spinners.addAll(spinners);

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Spinner spinner);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
