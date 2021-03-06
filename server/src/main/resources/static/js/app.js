/**
 * Basic controller class that mitigates the interaction between user interaction
 * and
 *
 * Created by mhotan on 5/11/14.
 */
$(document).ready(function() {

    // Citibike Model.
    var model = new CitiBikeModel();

    // Initialize Google Maps first
    var map = new google.maps.Map(document.getElementById('map_canvas'), {
        zoom: 13,
        center: new google.maps.LatLng(40.7192,-73.95)
    });

    // Set the current layer to be the bicycle layer.
    var bikeLayer = new google.maps.BicyclingLayer();
    bikeLayer.setMap(map);

    // The Map view.
    var mapView = new CitiBikeMapView(map, model);
    var mapController = new MapController(mapView, model);
    mapController.addListener(this);

    // Input View.
    var inputView = new InputView($('#controller-pane'), model);
    var inputController = new InputController(inputView, model);
    inputController.addListener(this);


});