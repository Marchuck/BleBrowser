package pl.marchuck.blebrowser.main

/**
 * Project "BleBrowser"
 *
 *
 * Created by Lukasz Marczak
 * on 31.03.2017.
 */

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

import pl.marchuck.blebrowser.R
import pl.marchuck.blebrowser.repository.WrappedBondedDevice


/**
 * @author Lukasz Marczak
 * *
 * @since 08.05.16.
 */
class BondedDevicesAdapter @JvmOverloads
constructor(dataSet: List<WrappedBondedDevice> = ArrayList<WrappedBondedDevice>())
    : RecyclerView.Adapter<BondedDevicesAdapter.ViewHolder>() {

    companion object {
        val TAG = "BondedDevicesAdapter"
    }

    internal var dataSet: List<WrappedBondedDevice>? = ArrayList()

    var listener: ConnectDeviceListener? = null

    init {
        this.dataSet = dataSet
    }

    fun refreshDataset(dataSet: List<WrappedBondedDevice>) {
        this.dataSet = dataSet
        notifyItemRangeChanged(0, itemCount)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.device_item, null, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet!![position]

        holder.name.text = item.alias
        holder.mac.text = item.device!!.address

        if (item.connected){
            holder.connect.text =  "DISCONNECT"
            holder.connect.setOnClickListener {
                Log.d(TAG, "click " + item.toString())

                listener?.disconnect(item.device)
            }
        }else {
            holder.connect.text =  "CONNECT"
            holder.connect.setOnClickListener {
                Log.d(TAG, "click " + item.toString())

                listener?.connect(item.device)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (dataSet == null) 0 else dataSet!!.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var name: TextView
        internal var mac: TextView
        internal var connect: TextView

        init {
            name = v.findViewById(R.id.nameAlias) as TextView
            mac = v.findViewById(R.id.macAddress) as TextView
            connect = v.findViewById(R.id.connect) as TextView
            connect.isClickable =true
        }
    }
}

