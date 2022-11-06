package com.example.fuelstationclient.util;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelstationclient.FuelStationFuelTypesActivity;
import com.example.fuelstationclient.R;
import com.example.fuelstationclient.model.FuelStation;

import java.util.ArrayList;
import java.util.List;

public class FuelStationListAdapter extends RecyclerView.Adapter<FuelStationListAdapter.ViewHolder> implements Filterable {
    private List<FuelStation> listdata;
    private List<FuelStation> filteredListData;

    // RecyclerView recyclerView;
    public FuelStationListAdapter(List<FuelStation> listdata) {
        this.filteredListData = listdata;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FuelStation myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
//        holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FuelStationFuelTypesActivity.class);
                intent.putExtra("fuelStation", myListData);
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata == null ? 0 : listdata.size();

    }

    @Override
    public Filter getFilter() {
        return fuelStationFilter;
    }

    private Filter fuelStationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FuelStation> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listdata);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FuelStation item : listdata) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData.clear();
            filteredListData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.listItemImageView);
            this.textView = (TextView) itemView.findViewById(R.id.listItemTextView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.listItemRelativeLayout);
        }
    }

}
