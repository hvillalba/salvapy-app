package py.ccenturion.salvapy_app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import py.ccenturion.salvapy_app.R;

public class ArrayAdapterSpinner extends ArrayAdapter<String> {

    private Context context;
    private List<String> barrioList = new ArrayList<>();

    public ArrayAdapterSpinner(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource);
        this.context = context;
        barrioList = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_tiposangre, parent, false);
        TextView textView = view.findViewById(R.id.tvPeriodo);
        String barrio = barrioList.get(position);
        textView.setText(barrio);
        return view;
    }

    @Override
    public int getCount() {
        return barrioList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return barrioList.get(position);
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_tiposangre, parent, false);
        TextView textView = view.findViewById(R.id.tvPeriodo);
        String barrio = barrioList.get(position);
        textView.setText(barrio);
        return view;
    }

//    @NonNull
//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//                ArrayList<Barrio> gestionArrayList = new ArrayList<>();
//                constraint = constraint.toString().toLowerCase();
//                for (int i=0;i < barrioList.size(); i++){
//                    Barrio gestion = (barrioList.get(i));
//                    if (gestion.getDescripcion().toLowerCase().contains(constraint.toString())){
//                        gestionArrayList.add(gestion);
//                    }
//                }
//                if (gestionArrayList.size() > 0){
//                    results.count = gestionArrayList.size();
//                    results.values = gestionArrayList;
//                    Log.e("VALUES", results.values.toString());
//                }else {
//                    results.count = barrioList.size();
//                    results.values = barrioList;
//                    Log.e("VALUES", results.values.toString());
//                }
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                barrioList = (List<Barrio>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//        return filter;
//    }
}
