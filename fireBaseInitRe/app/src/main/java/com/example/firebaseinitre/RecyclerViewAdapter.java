package com.example.firebaseinitre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<TrajetInfo> listTrajetInfo;

    public RecyclerViewAdapter(Context context, ArrayList<TrajetInfo> listTrajetInfo,RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listTrajetInfo = listTrajetInfo;
        this.recyclerViewInterface=recyclerViewInterface;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where we are gonna inflate the RecyclerView layout
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cardview_item_liste,parent,false);

        return new RecyclerViewAdapter.CustomViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CustomViewHolder holder, int position) {
        /* where we have to asigne value for the view we created in the recycler_view_row layout
         based on the recycler view position
        * the recycler_view_row is the custom view we create to add data
        in each row (the cardview_item_liste in this case)
        */

        holder.userName.setText(listTrajetInfo.get(position).getNom()+" "+ listTrajetInfo.get(position).getPrenom());
        holder.heureDepart.setText(listTrajetInfo.get(position).getHeureDepart());
        holder.heureArrivee.setText(listTrajetInfo.get(position).getHeureArrivee());
        holder.pointDepart.setText(listTrajetInfo.get(position).getPointDepart());
        holder.pointArrivee.setText(listTrajetInfo.get(position).getPointArrivee());
    }

    @Override
    public int getItemCount() {
        //have to know and return the number of item we need to display
        return listTrajetInfo.size();
    }

    public  static class CustomViewHolder extends RecyclerView.ViewHolder {
        /* grabbing the view from our recycler_view_row layout (the cardview_item_liste in this case)
        *to use it as an oncreate of an activity we have to  extends RecyclerView.ViewHolder
         and impléments the proper constructer usin View inside to call android Activity functions
        */

        TextView userName,heureDepart,heureArrivee, pointDepart,pointArrivee;

        public  CustomViewHolder  (@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            userName=itemView.findViewById(R.id.username_item);
            heureDepart=itemView.findViewById(R.id.heure_depart);
            heureArrivee=itemView.findViewById(R.id.heure_arrivee);
            pointDepart=itemView.findViewById(R.id.destination_depart);
            pointArrivee=itemView.findViewById(R.id.destination_arrivee);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onRecyclerItemClicked(position);
                        }

                    }
                }
            });
        }


    }//CustomViewHolder Class

}//recycler Class
