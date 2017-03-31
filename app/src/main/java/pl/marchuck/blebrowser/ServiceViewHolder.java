package pl.marchuck.blebrowser;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Project "BleBrowser"
 * <p>
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */

public class ServiceViewHolder extends GroupViewHolder {

    private TextView genreTitle;

    public ServiceViewHolder(View itemView) {
        super(itemView);
//        genreTitle = itemView.findViewById(R.id.genre_title);
    }

    public void setGenreTitle(ExpandableGroup group) {
        genreTitle.setText(group.getTitle());
    }
}

