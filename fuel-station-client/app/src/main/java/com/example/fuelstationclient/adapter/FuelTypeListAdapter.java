package com.example.fuelstationclient.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelstationclient.FuelStationAdminPage;
import com.example.fuelstationclient.model.Fuel;

import com.example.fuelstationclient.FuelStationDetailActivity;
import com.example.fuelstationclient.R;
import com.example.fuelstationclient.model.FuelStation;

import java.util.List;

public class FuelTypeListAdapter extends RecyclerView.Adapter<FuelTypeListAdapter.ViewHolder>{
    private FuelStation fuelStation;
    private String fuelStationId;

    // RecyclerView recyclerView;
    public FuelTypeListAdapter(FuelStation fuelStation) {
        this.fuelStation = fuelStation;
    }



    @NonNull
    @Override
    public FuelTypeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        FuelTypeListAdapter.ViewHolder viewHolder = new FuelTypeListAdapter.ViewHolder(listItem);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(FuelTypeListAdapter.ViewHolder holder, int position) {
        final Fuel myListData = fuelStation.getFuelTypes().get(position);
        holder.textView.setText(fuelStation.getFuelTypes().get(position).getFuelType());
//        holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FuelStationDetailActivity.class);
//                Intent intent = new Intent(view.getContext(), FuelStationAdminPage.class);

                intent.putExtra("fuelType", myListData);
                intent.putExtra("fuelStation", fuelStation);
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(),"click on item: ",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return fuelStation.getFuelTypes() == null ? 0 : fuelStation.getFuelTypes().size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public Button btn;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.btn = (Button) itemView.findViewById(R.id.fuelavailabilityDisp);
            this.imageView = (ImageView) itemView.findViewById(R.id.listItemImageView);
            this.textView = (TextView) itemView.findViewById(R.id.listItemTextView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.listItemRelativeLayout);
        }
    }

}