package majed.eddin.digitalturbine.ui.view.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import am.leon.LeonImageView;
import majed.eddin.digitalturbine.R;
import majed.eddin.digitalturbine.data.model.service.Offer;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private final List<Offer> items;

    public OffersAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = items.get(position);

        holder.img_offer.loadImage(offer.getThumbnail().getHires());
        holder.txt_offerTitle.setText(offer.getTitle());

    }

    public void addAll(List<Offer> items) {
        for (Offer result : items) {
            add(result);
        }
    }

    private void add(Offer r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView txt_offerTitle;
        private final LeonImageView img_offer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_offerTitle = itemView.findViewById(R.id.txt_offerTitle);
            img_offer = itemView.findViewById(R.id.img_offer);
        }
    }
}