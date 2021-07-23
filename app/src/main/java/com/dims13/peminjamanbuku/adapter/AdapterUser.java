package com.dims13.peminjamanbuku.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dims13.peminjamanbuku.databuku.Buku;
import com.dims13.peminjamanbuku.pengguna.LihatBuku;
import com.dims13.peminjamanbuku.pengguna.PinjamBuku;

import java.util.ArrayList;
import java.util.List;

import com.dims13.peminjamanbuku.R;

//RECYCLERVIEW ADAPTER
public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> implements Filterable {

    private ArrayList<Buku> listKonten2;
    private ArrayList<Buku> listKonten2_full;
    private Context context;

    public interface dataListener{
        void onDeleteData(Buku data, int position);
    }

    dataListener listener2;

    public AdapterUser(ArrayList<Buku> listKonten2, Context context) {
        this.listKonten2 = listKonten2;
        listKonten2_full = new ArrayList<>(listKonten2);
        this.context = context;
        listener2 = (dataListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design_buku, parent, false);
        return new ViewHolder(V);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final String nama_buku = listKonten2.get(position).getNama_buku();
        final String nama_loket = listKonten2.get(position).getNama_loket();
        final String harga = listKonten2.get(position).getHarga();
        final String gambar = listKonten2.get(position).getGambar();

        //FUNGSI REPLACE PADA URL GAMBAR
        String sumber = gambar;
        String a1 = sumber.replace("o?name=", "o/");
        String a2 = a1.replace("&uploadType=resumable", "?alt=media&uploadType=resumable");
        String a3 = a2.replace("&upload_id", "&upload_ids");
        String a4 = a3.replace("&upload_protocol=resumable", "");
        String imgsumber = a4;

        holder.nama_buku.setText("Judul : " + nama_buku);
        holder.nama_loket.setText("Loket : " + nama_loket);
        Glide.with(context).load(imgsumber).into(holder.gambar);
        holder.harga.setText("Harga : " + harga + " /hari");

        holder.ListItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("datanama_buku", listKonten2.get(position).getNama_buku());
                bundle.putString("datanama_loket", listKonten2.get(position).getNama_loket());
                bundle.putString("datagambar", listKonten2.get(position).getGambar());
                bundle.putString("dataharga", listKonten2.get(position).getHarga());
                bundle.putString("getPrimaryKey", listKonten2.get(position).getKey());
                Intent intent = new Intent(v.getContext(), PinjamBuku.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKonten2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama_buku, nama_loket, harga;
        private ImageView gambar;
        private LinearLayout ListItem2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_buku = itemView.findViewById(R.id.Nama_Buku);
            nama_loket = itemView.findViewById(R.id.Nama_Loket);
            gambar = itemView.findViewById(R.id.ImageView);
            harga = itemView.findViewById(R.id.Harga);
            ListItem2 = itemView.findViewById(R.id.list_item1);
        }
    }

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Buku> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filteredList.addAll(listKonten2_full);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Buku item : listKonten2_full){
                    if(item.getNama_buku().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        };
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listKonten2.clear();
            listKonten2.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
