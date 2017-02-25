package com.vigorous.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.style.sources.VectorSource;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;
import com.vigorous.testandroideverything.R;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.Filter.all;
import static com.mapbox.mapboxsdk.style.layers.Filter.gte;
import static com.mapbox.mapboxsdk.style.layers.Filter.lt;
import static com.mapbox.mapboxsdk.style.layers.Filter.neq;
import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleBlur;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class MapboxActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String tag = "11111";
    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";

    MapView mMapView;
    Button mBtnLayer;

    //also can use fragment with map
    SupportMapFragment mSupportMapFragment;

    //or dynamic create map


    //a control for map
    MapboxMap mMapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapbox);//note can use different style   test it
        mBtnLayer = (Button) findViewById(R.id.btn_layer);
        //mBtnLayer.setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                //testAddLayer();
                //testAddNewLayerBelowLabels();
                //testAddClusteredGeoJsonSource(mMapboxMap);
                //testAddGeoJsonLine();
                testOfflineMap();
            }
        });
        requestPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_layer:
                Layer layer = mMapboxMap.getLayer("museums");
                if (layer != null) {
                    if (VISIBLE.equals(layer.getVisibility().getValue())) {
                        layer.setProperties(visibility(NONE));
                    } else {
                        layer.setProperties(visibility(VISIBLE));
                    }
                }
                break;
            default:
                break;
        }
    }

    //=============================================================
    private void testAddLayer() {
        VectorSource museums = new VectorSource("museums_source", "mapbox://mapbox.2opop9hr");
        mMapboxMap.addSource(museums);

        CircleLayer museumsLayer = new CircleLayer("museums", "museums_source");
        museumsLayer.setSourceLayer("museum-cusco");
        museumsLayer.setProperties(
                visibility(VISIBLE),
                circleRadius(8f),
                circleColor(Color.argb(1, 55, 148, 179))
        );

        mMapboxMap.addLayer(museumsLayer);
        mBtnLayer.setOnClickListener(this);
    }

    private void testAddNewLayerBelowLabels() {
        try {
            URL geoJsonUrl = new URL("https://d2ad6b4ur7yvpq.cloudfront.net/naturalearth-3.3.0/ne_50m_urban_areas.geojson");
            GeoJsonSource urbanAreasSource = new GeoJsonSource("urban-areas", geoJsonUrl);
            mMapboxMap.addSource(urbanAreasSource);

            FillLayer urbanArea = new FillLayer("urban-areas-fill", "urban-areas");

            urbanArea.setProperties(
                    fillColor(Color.parseColor("#ff0088")),
                    fillOpacity(0.4f)
            );

            mMapboxMap.addLayer(urbanArea, "water");
        } catch (MalformedURLException malformedUrlException) {
            malformedUrlException.printStackTrace();
        }
    }

    private void testAddClusteredGeoJsonSource(MapboxMap mapboxMap) {

        // Add a new source from our GeoJSON data and set the 'cluster' option to true.
        try {
            mapboxMap.addSource(
                    // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes from
                    // 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
                    new GeoJsonSource("earthquakes",
                            new URL("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"),
                            new GeoJsonOptions()
                                    .withCluster(true)
                                    .withClusterMaxZoom(15) // Max zoom to cluster points on
                                    .withClusterRadius(20) // Use small cluster radius for the heatmap look
                    )
            );
        } catch (MalformedURLException malformedUrlException) {
            Log.e(tag, "Check the URL " + malformedUrlException.getMessage());
        }

        // Use the earthquakes source to create four layers:
        // three for each cluster category, and one for unclustered points

        // Each point range gets a different fill color.
        final int[][] layers = new int[][]{
                new int[]{150, Color.parseColor("#E55E5E")},
                new int[]{20, Color.parseColor("#F9886C")},
                new int[]{0, Color.parseColor("#FBB03B")}
        };

        CircleLayer unclustered = new CircleLayer("unclustered-points", "earthquakes");
        unclustered.setProperties(
                circleColor(Color.parseColor("#FBB03B")),
                circleRadius(20f),
                circleBlur(1f));
        unclustered.setFilter(
                neq("cluster", true)
        );
        mapboxMap.addLayer(unclustered, "building");

        for (int i = 0; i < layers.length; i++) {

            CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(70f),
                    circleBlur(1f)
            );
            circles.setFilter(
                    i == 0
                            ? gte("point_count", layers[i][0]) :
                            all(gte("point_count", layers[i][0]), lt("point_count", layers[i - 1][0]))
            );
            mapboxMap.addLayer(circles, "building");
        }
    }

    private void testAddGeoJsonLine() {
        // Create a list to store our line coordinates.
        List<Position> routeCoordinates = new ArrayList<Position>();
        routeCoordinates.add(Position.fromCoordinates(-118.39439114221236, 33.397676454651766));
        routeCoordinates.add(Position.fromCoordinates(-118.39421054012902, 33.39769799454838));
        routeCoordinates.add(Position.fromCoordinates(-118.39408583869053, 33.39761901490136));
        routeCoordinates.add(Position.fromCoordinates(-118.39388373635917, 33.397328225582285));
        routeCoordinates.add(Position.fromCoordinates(-118.39372033447427, 33.39728514560042));
        routeCoordinates.add(Position.fromCoordinates(-118.3930882271826, 33.39756875508861));
        routeCoordinates.add(Position.fromCoordinates(-118.3928216241072, 33.39759029501192));
        routeCoordinates.add(Position.fromCoordinates(-118.39227981785722, 33.397234885594564));
        routeCoordinates.add(Position.fromCoordinates(-118.392021814881, 33.397005125197666));
        routeCoordinates.add(Position.fromCoordinates(-118.39090810203379, 33.396814854409186));
        routeCoordinates.add(Position.fromCoordinates(-118.39040499623022, 33.39696563506828));
        routeCoordinates.add(Position.fromCoordinates(-118.39005669221234, 33.39703025527067));
        routeCoordinates.add(Position.fromCoordinates(-118.38953208616074, 33.39691896489222));
        routeCoordinates.add(Position.fromCoordinates(-118.38906338075398, 33.39695127501678));
        routeCoordinates.add(Position.fromCoordinates(-118.38891287901787, 33.39686511465794));
        routeCoordinates.add(Position.fromCoordinates(-118.38898167981154, 33.39671074380141));
        routeCoordinates.add(Position.fromCoordinates(-118.38984598978178, 33.396064537239404));
        routeCoordinates.add(Position.fromCoordinates(-118.38983738968255, 33.39582400356976));
        routeCoordinates.add(Position.fromCoordinates(-118.38955358640874, 33.3955978295119));
        routeCoordinates.add(Position.fromCoordinates(-118.389041880506, 33.39578092284221));
        routeCoordinates.add(Position.fromCoordinates(-118.38872797688494, 33.3957916930261));
        routeCoordinates.add(Position.fromCoordinates(-118.38817327048618, 33.39561218978703));
        routeCoordinates.add(Position.fromCoordinates(-118.3872530598711, 33.3956265500598));
        routeCoordinates.add(Position.fromCoordinates(-118.38653065153775, 33.39592811523983));
        routeCoordinates.add(Position.fromCoordinates(-118.38638444985126, 33.39590657490452));
        routeCoordinates.add(Position.fromCoordinates(-118.38638874990086, 33.395737842093304));
        routeCoordinates.add(Position.fromCoordinates(-118.38723155962309, 33.395027006653244));
        routeCoordinates.add(Position.fromCoordinates(-118.38734766096238, 33.394441819579285));
        routeCoordinates.add(Position.fromCoordinates(-118.38785936686516, 33.39403972556368));
        routeCoordinates.add(Position.fromCoordinates(-118.3880743693453, 33.393616088784825));
        routeCoordinates.add(Position.fromCoordinates(-118.38791956755958, 33.39331092541894));
        routeCoordinates.add(Position.fromCoordinates(-118.3874852625497, 33.39333964672257));
        routeCoordinates.add(Position.fromCoordinates(-118.38686605540683, 33.39387816940854));
        routeCoordinates.add(Position.fromCoordinates(-118.38607484627983, 33.39396792286514));
        routeCoordinates.add(Position.fromCoordinates(-118.38519763616081, 33.39346171215717));
        routeCoordinates.add(Position.fromCoordinates(-118.38523203655761, 33.393196040109466));
        routeCoordinates.add(Position.fromCoordinates(-118.3849955338295, 33.393023711860515));
        routeCoordinates.add(Position.fromCoordinates(-118.38355931726203, 33.39339708930139));
        routeCoordinates.add(Position.fromCoordinates(-118.38323251349217, 33.39305243325907));
        routeCoordinates.add(Position.fromCoordinates(-118.3832583137898, 33.39244928189641));
        routeCoordinates.add(Position.fromCoordinates(-118.3848751324406, 33.39108499551671));
        routeCoordinates.add(Position.fromCoordinates(-118.38522773650804, 33.38926830725471));
        routeCoordinates.add(Position.fromCoordinates(-118.38508153482152, 33.38916777794189));
        routeCoordinates.add(Position.fromCoordinates(-118.38390332123025, 33.39012280171983));
        routeCoordinates.add(Position.fromCoordinates(-118.38318091289693, 33.38941192035707));
        routeCoordinates.add(Position.fromCoordinates(-118.38271650753981, 33.3896129783018));
        routeCoordinates.add(Position.fromCoordinates(-118.38275090793661, 33.38902416443619));
        routeCoordinates.add(Position.fromCoordinates(-118.38226930238106, 33.3889451769069));
        routeCoordinates.add(Position.fromCoordinates(-118.38258750605169, 33.388420985121336));
        routeCoordinates.add(Position.fromCoordinates(-118.38177049662707, 33.388083490107284));
        routeCoordinates.add(Position.fromCoordinates(-118.38080728551597, 33.38836353925403));
        routeCoordinates.add(Position.fromCoordinates(-118.37928506795642, 33.38717870977523));
        routeCoordinates.add(Position.fromCoordinates(-118.37898406448423, 33.3873079646849));
        routeCoordinates.add(Position.fromCoordinates(-118.37935386875012, 33.38816247841951));
        routeCoordinates.add(Position.fromCoordinates(-118.37794345248027, 33.387810620840135));
        routeCoordinates.add(Position.fromCoordinates(-118.37546662390886, 33.38847843095069));
        routeCoordinates.add(Position.fromCoordinates(-118.37091717142867, 33.39114243958559));
        // Create the LineString from the list of coordinates and then make a GeoJSON
        // FeatureCollection so we can add the line to our map as a layer.
        LineString lineString = LineString.fromCoordinates(routeCoordinates);

        FeatureCollection featureCollection =
                FeatureCollection.fromFeatures(new Feature[]{Feature.fromGeometry(lineString)});

        Source geoJsonSource = new GeoJsonSource("line-source", featureCollection);

        mMapboxMap.addSource(geoJsonSource);

        LineLayer lineLayer = new LineLayer("linelayer", "line-source");

        // The layer properties for our line. This is where we make the line dotted, set the
        // color, etc.
        lineLayer.setProperties(
                PropertyFactory.lineDasharray(new Float[]{0.01f, 2f}),
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
        );

        mMapboxMap.addLayer(lineLayer);
    }

    private void testOfflineMap() {
        // Set up the OfflineManager
        OfflineManager offlineManager = OfflineManager.getInstance(this);

        // Create a bounding box for the offline region
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new LatLng(37.7897, -119.5073)) // Northeast
                .include(new LatLng(37.6744, -119.6815)) // Southwest
                .build();

        // Define the offline region
        OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
                mMapView.getStyleUrl(),
                latLngBounds,
                10,
                20,
                this.getResources().getDisplayMetrics().density);

        // Set the metadata
        byte[] metadata;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_FIELD_REGION_NAME, "Yosemite National Park");
            String json = jsonObject.toString();
            metadata = json.getBytes(JSON_CHARSET);
        } catch (Exception exception) {
            Log.e(tag, "Failed to encode metadata: " + exception.getMessage());
            metadata = null;
        }

        // Create the region asynchronously
        offlineManager.createOfflineRegion(
                definition,
                metadata,
                new OfflineManager.CreateOfflineRegionCallback() {
                    @Override
                    public void onCreate(OfflineRegion offlineRegion) {
                        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);

                        // Display the download progress bar
                        //progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                        //startProgress();

                        // Monitor the download progress using setObserver
                        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                            @Override
                            public void onStatusChanged(OfflineRegionStatus status) {

                                // Calculate the download percentage and update the progress bar
                                double percentage = status.getRequiredResourceCount() >= 0
                                        ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                                        0.0;

                                if (status.isComplete()) {
                                    // Download complete
                                    //endProgress("Region downloaded successfully.");
                                    Log.d(tag, "====end progress & success");
                                } else if (status.isRequiredResourceCountPrecise()) {
                                    // Switch to determinate state
                                    //setPercentage((int) Math.round(percentage));
                                    Log.d(tag, "====down progress " + (int) Math.round(percentage));
                                }
                            }

                            @Override
                            public void onError(OfflineRegionError error) {
                                // If an error occurs, print to logcat
                                Log.e(tag, "====onError reason: " + error.getReason());
                                Log.e(tag, "====onError message: " + error.getMessage());
                            }

                            @Override
                            public void mapboxTileCountLimitExceeded(long limit) {
                                // Notify if offline region exceeds maximum tile count
                                Log.e(tag, "====Mapbox tile count limit exceeded: " + limit);
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(tag, "====Error: " + error);
                    }
                });

    }

    //=============================================================需要处理运行时权限请求
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(tag, "not permission");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0x99);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.d(tag, "has permission");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d(tag, "got permission");

                } else {
                    Log.d(tag, "don't got permission");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
