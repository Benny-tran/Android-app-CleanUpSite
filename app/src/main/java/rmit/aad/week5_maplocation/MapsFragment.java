package rmit.aad.week5_maplocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    //install google map
    private GoogleMap mMap;
    //install firebase firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Marker marker;

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            // Add a marker in Sydney and move the camera
            LatLng rmit =  new LatLng( 10.730183, 106.694264);
            mMap.addMarker(new MarkerOptions().position(rmit).title("RMIT Vietnam").snippet("Click me")
            .icon(bitmapDescriptorfromVector(getContext(),R.drawable.ic_baseline_trash_24)));

            //We can update map UI by calling getUiSettings()
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);

            //Google Map supports a number of map types including satellite and hybrid
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(rmit));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rmit, 14));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //Set Marker
                    marker = mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(bitmapDescriptorfromVector(getContext(),R.drawable.ic_baseline_trash_24)));

                    Intent intent = new Intent(getActivity().getApplicationContext(),AddCleanUpActivity.class);
                    intent.putExtra("latitude", (String.valueOf(latLng.latitude)));
                    intent.putExtra("longitude",(String.valueOf(latLng.longitude)));
                    startActivity(intent);
                }
            });

            //Loaded Marker
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    getMarker();
                }
            });

            //Loaded InfoWindow
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent i = new Intent(getActivity().getApplicationContext(), ShowEventActivity.class);
                    startActivity(i);
                }
            });

        }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    private void getMarker() {
            db.collection("events")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<LatLng> location = new ArrayList<>();
                                List<String> eventName = new ArrayList<>();
                                List<String> eventContact = new ArrayList<>();
                                for (QueryDocumentSnapshot document: task.getResult()){
                                    String lat = (String) document.getData().get("Latitude");
                                    String lng = (String) document.getData().get("Longitude");
                                    String name = (String) document.getData().get("Event Name");
                                    String contact = (String) document.getData().get("Contact");
                                    if (lat == null || lng == null || name == null || contact == null) continue;
                                    LatLng position = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                                    eventName.add(name);
                                    eventContact.add(contact);
                                    location.add(position);
                                }
                                    for (int i = 0; i < location.size(); i++) {
                                        mMap.addMarker(new MarkerOptions().position(location.get(i)).title(eventName.get(i)).snippet(eventContact.get(i))
                                                .icon(bitmapDescriptorfromVector(getContext(), R.drawable.ic_baseline_trash_24)));
                                    }
                        }
                    }
            });
        }


    private BitmapDescriptor bitmapDescriptorfromVector(Context context, int vectorResId) {
            Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
            vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

}