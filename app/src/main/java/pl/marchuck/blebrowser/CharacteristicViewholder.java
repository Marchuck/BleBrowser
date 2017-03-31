package pl.marchuck.blebrowser;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Project "BleBrowser"
 * <p>
 * Created by Lukasz Marczak
 * on 28.03.2017.
 */

public class CharacteristicViewholder extends ChildViewHolder {

    private TextView artistName;

    public CharacteristicViewholder(View itemView) {
        super(itemView);
//        artistName = itemView.findViewById(R.id.artist_name);
    }

    public void onBind(BleCharacteristic chk) {
        artistName.setText(chk.getUuid());
    }

}
