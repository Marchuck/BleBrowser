package pl.marchuck.blebrowser.main

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import pl.marchuck.blebrowser.R
import pl.marchuck.blebrowser.repository.WrappedBondedDevice


/**
 * BleBrowser
 *
 *
 * Created by lukasz
 *
 *
 * Since 31.03.2017
 */
class MainFragment : Fragment(), MainView {
    override fun showConnecting() {
        handler.removeCallbacks { timeout }
        progressBar.post {
            progressBar.visibility = View.VISIBLE
        }
        handler.postDelayed(timeout, 7000)
    }


    val handler: Handler = Handler(Looper.getMainLooper())

    val timeout = {
        progressBar.visibility = View.GONE
        Toast.makeText(activity, "FAILED TO CONNECT",
                Toast.LENGTH_LONG).show()
    }

    override fun hideConnecting() {
        progressBar.post {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        val TAG = MainFragment::class.java.simpleName

        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            return fragment
        }
    }

    val rxPermissions: RxPermissions by lazy { RxPermissions(activity) }

    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.recyclerViewHolder)
    lateinit var swipeLayout: SwipeRefreshLayout

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.afterConnectLayout)
    lateinit var afterConnectLayout: RelativeLayout

    @BindView(R.id.sendValue_A)
    lateinit var sendA: Button

    @BindView(R.id.sendValue_B)
    lateinit var sendB: Button

    @BindView(R.id.sendAnyValue)
    lateinit var sendAny: Button

    @BindView(R.id.editText)
    lateinit var editText: EditText

    val repository: MainRepository = MainRepository()

    val adapter: BondedDevicesAdapter = BondedDevicesAdapter()

    var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        presenter = MainPresenter(this, repository)

        setupViews()
        presenter?.requestBondedDevices()
        return view
    }

    private fun setupViews() {
        afterConnectLayout.visibility = View.GONE
        adapter.listener = presenter

        recyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        recyclerView.adapter = adapter

        sendA.setOnClickListener { presenter?.onSendValue("a") }
        sendB.setOnClickListener { presenter?.onSendValue("b") }
        sendAny.setOnClickListener { presenter?.onSendValue(editText.text.toString()) }

        swipeLayout.setOnRefreshListener {
            Handler().postDelayed({
                presenter?.requestBondedDevices()
                swipeLayout.isRefreshing = false
            }, 500)
        }
    }

    override fun onBondedDevicesReceived(devicesList: List<WrappedBondedDevice>) {

        handler.post({
            adapter.refreshDataset(devicesList)
        })
    }

    override fun requestBluetooth(): Observable<Boolean> {
        return rxPermissions.request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN)
    }

    override fun showWeNeedBluetooth() {
        Toast.makeText(activity, "BLUETOOTH IS NEEDED FOR CONNECTING WITH PERIPHERALS",
                Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null
    }

    override fun onConnected(who: String) {
        handler.removeCallbacks { timeout }
        hideConnecting()

        afterConnectLayout.post {
            Toast.makeText(activity, "CONNECTED TO " + who, Toast.LENGTH_LONG).show()
            afterConnectLayout.visibility = View.VISIBLE
        }

    }

    override fun onDisconnected(who: String) {
        Toast.makeText(activity, "CONNECTED FROM " + who, Toast.LENGTH_LONG).show()
        afterConnectLayout.visibility = View.GONE
    }


    override fun onResume() {
        super.onResume()
        presenter?.reconnect()
    }

    override fun onPause() {
        super.onPause()
        presenter?.disconnect()
    }
}
