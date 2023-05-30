package com.example.isp.fragment

import android.os.Bundle
import android.os.Parcelable
import android.preference.PreferenceManager
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.isp.R
import com.example.isp.databinding.FragmentMapBinding
import com.example.isp.model.Server
import com.example.isp.repository.ServerRepository
import com.example.isp.viewmodel.ServerViewModel
import com.example.isp.viewmodel.ServerViewModelFactory
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {

    lateinit var binding: FragmentMapBinding
    lateinit var mapController: IMapController
    lateinit var serverRepository: ServerRepository
    lateinit var serverViewModelFactory: ServerViewModelFactory
    lateinit var serverViewModel: ServerViewModel
    lateinit var serversList: ArrayList<Server>
    private var mapViewSavedInstanceState: SparseArray<Parcelable>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater,container,false)

        getInstance().load(requireActivity(), PreferenceManager.getDefaultSharedPreferences(requireActivity()))

        serverRepository = ServerRepository()
        serverViewModelFactory = ServerViewModelFactory(serverRepository)
        serverViewModel = ViewModelProvider(this,serverViewModelFactory)[ServerViewModel::class.java]

        serversList = ArrayList()

        binding.apply{

            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapController = mapView.controller

            var geoPoint = GeoPoint(19.205049380710747, 73.1622578144776)
            mapController.setCenter(geoPoint)
            mapController.setZoom(15.0)

            serverViewModel.getAllServers()

            serverViewModel.myResponseList.observe(requireActivity()){responseList->
                mapView.overlays.clear()
                serversList = responseList
                serversList.forEach { server->
                    geoPoint = GeoPoint(server.latitude, server.longitude)
                    val marker = Marker(mapView)
                    marker.position = geoPoint
                    if(server.isActive==1){
                        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_marker_green)
                        marker.title = server.type+"\n"+server.loadOnServer+"%"
                    }
                    else{
                        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_marker_red)
                        marker.title = server.type
                    }
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                    mapView.overlays.add(marker)
                }
                mapView.postInvalidate()
            }


        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        binding.mapView.postInvalidate()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    @Deprecated("Deprecated")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsToRequest.toTypedArray(),
                101)
        }
    }


}