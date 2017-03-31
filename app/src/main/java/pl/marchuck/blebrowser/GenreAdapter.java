package pl.marchuck.blebrowser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Project "BleBrowser"
 * <p>
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */

public class GenreAdapter extends ExpandableRecyclerViewAdapter<ServiceViewHolder, CharacteristicViewholder> {

    public GenreAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);

    }

    @Override
    public ServiceViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public CharacteristicViewholder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_characteristic, parent, false);
        return new CharacteristicViewholder(view);
    }

    @Override
    public void onBindChildViewHolder(CharacteristicViewholder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
//        final BleCharacteristic artist = ((Artist) group).getItems().get(childIndex);
//        holder.setArtistName(artist.getName());
    }

    @Override
    public void onBindGroupViewHolder(ServiceViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }
}