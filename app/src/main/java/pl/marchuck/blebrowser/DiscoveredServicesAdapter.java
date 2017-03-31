package pl.marchuck.blebrowser;

/**
 * Project "BleBrowser"
 * <p>
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;


/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class DiscoveredServicesAdapter extends RecyclerView.Adapter<DiscoveredServicesAdapter.DiscveredServicesAdapterViewHolder> {

    List<BleService> dataSet = new ArrayList<>();
    
    public DiscoveredServicesAdapter(List<BleService> dataSet) {
        this.dataSet = dataSet;
    }
    public DiscoveredServicesAdapter() {
        this(new ArrayList<BleService>());
    }
    
    public void refreshDataset(List<BleService> dataSet) {
        this.dataSet = dataSet;
        notifyItemRangeChanged(0,getItemCount());
        notifyDataSetChanged();
    }

    @Override
    public DiscveredServicesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);
        return new DiscveredServicesAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DiscveredServicesAdapterViewHolder holder, int position) {
        final BleService item = dataSet.get(position);
        holder.textView.setText(String.valueOf(item));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo:
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet==null ? 0 : dataSet.size();
    }

    public static class DiscveredServicesAdapterViewHolder extends RecyclerView.ViewHolder {
        
        TextView textView;

        public DiscveredServicesAdapterViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}

