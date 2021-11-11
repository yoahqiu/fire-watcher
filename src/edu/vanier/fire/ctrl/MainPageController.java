package edu.vanier.fire.ctrl;

import edu.vanier.fire.AboutPopup;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.Polygon;
import com.lynden.gmapsfx.shapes.PolygonOptions;
import edu.vanier.fire.ChecklistPopup;
import edu.vanier.fire.ContactPopup;
import edu.vanier.fire.Loader;
import edu.vanier.fire.NotificationPopup;
import edu.vanier.fire.model.FireModel;
import edu.vanier.fire.model.PersonalInfo;
import edu.vanier.fire.model.WeatherModel;
import edu.vanier.fire.util.email.Email;
import edu.vanier.fire.util.email.InfoController;
import edu.vanier.fire.util.map.MapProjection;
import edu.vanier.fire.util.network.RESTAPIController;
import edu.vanier.fire.util.parser.FireParser;
import edu.vanier.fire.util.parser.WeatherParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DateCell;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import netscape.javascript.JSObject;

/**
 *
 * @author doraFC
 */
public class MainPageController implements Initializable, MapComponentInitializedListener {

    final private String mapKey = "AIzaSyBjCe0RtBkpcjIUKgx36r1-0YVqq17aTm0";
    final private String weatherKey = "dfee42369d44d77131f6aeac638522f2";

    private final int today = Integer.parseInt(LocalDate.now().toString().split("-")[2]);

    private ArrayList<Circle> fireCircles;
    private List<FireModel>[] fullFireCircless;
    private List<LatLong> fireLatLongList = new ArrayList<>();
    private Polygon projectionPolygon;
    private InfoWindow weatherInfo;
    private LatLongBounds bounds;
    double latCenter = 45.501167;
    double lonCenter = -73.650230;

    private Queue<Long> weatherCallTimers;

    @FXML
    private Label lblDatePicker;
    @FXML
    private DatePicker dpDatePicker;
    @FXML
    private RadioButton rbAllDay;
    @FXML
    private Button btnSetNotifications;
    @FXML
    private Button btnReset;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private CheckMenuItem miProjection;
    @FXML
    private CheckMenuItem miPlay;
    @FXML
    private RadioMenuItem miAllDay;
    @FXML
    private Slider sHours;
    @FXML
    private Label lblHour;

    private GoogleMap map;

    private int selectedDay = today;

    private Stage stage;

    private boolean isProjectionVisible = false;

    private final boolean isFireVisible = false;

    private Timeline playTimeline;

    private final double playRate = 1500;

    @FXML
    private void handleDatePicker(ActionEvent event) {
        selectedDay = Integer.parseInt(dpDatePicker.getValue().toString().split("-")[2]);

        if (!dpDatePicker.getValue().isEqual(LocalDate.now())) {
            miProjection.setDisable(true);
            miProjection.setSelected(false);
        } else {
            miProjection.setDisable(false);
            miProjection.setSelected(true);
        }
    }

    @FXML
    private void handleProjectedPathsButton(ActionEvent event) {
        System.out.println("Clicked Projected Paths");
        System.out.println("Projections are now " + (miProjection.isSelected() ? "visible" : "not visible"));
        isProjectionVisible = miProjection.isSelected();

        for (Circle fire : fireCircles) {
            map.addUIEventHandler(fire, UIEventType.click, (JSObject jso) -> {
                System.out.println("Clicked on Fire Circle");
                addFireProjection(fire.getCenter());
            });
        }

        if (!dpDatePicker.getValue().isEqual(LocalDate.now())) {
            miProjection.setDisable(true);
            miProjection.setSelected(false);
            isProjectionVisible = false;
            if (projectionPolygon != null) {
                map.removeMapShape(projectionPolygon);
            }
        }

        if (!isProjectionVisible) {
            if (projectionPolygon != null) {
                map.removeMapShape(projectionPolygon);
            }
        }
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {
        System.out.println("Clicked Play Animation");

        if (miPlay.isSelected()) {
            System.out.println("playing");
            playTimeline.play();
        } else {
            System.out.println("pausing");
            playTimeline.pause();
        }
    }

    @FXML
    private void handleAllDayButton(ActionEvent event) {
        System.out.println("Clicked All Day Button");

        if (miPlay.isSelected()) {
            miPlay.setSelected(false);
            playTimeline.pause();
        }

        handleSearch(event);
    }

    @FXML
    private void handleSetNotificationButton(ActionEvent event) {
        NotificationPopup dialog;
        try {
            dialog = new NotificationPopup(stage);
            dialog.showAndWait();
            Loader loader = new Loader();
            loader.setAsMainPage();
            Parent root = loader.getLoader().load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
            System.out.println("Couldn't Load Notif Stage");
        }
    }

    @FXML
    private void handleEmergencyContacts(ActionEvent event) {
        System.out.println("Clicked Emergency Contacts");

        ContactPopup popup;
        try {
            popup = new ContactPopup(map.getCenter(), stage);
            popup.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Couldn't Load Contacts Stage");
        }

    }

    @FXML
    private void handleEmergencyCheckList(ActionEvent Event) {
        System.out.println("Clicked Emergency Check List");

        ChecklistPopup popup;
        try {
            popup = new ChecklistPopup(stage);
            popup.showAndWait();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Couldn't Load Checklist Stage");
        }
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        AboutPopup dialog;
        try {
            dialog = new AboutPopup(stage);
            dialog.showAndWait();
        } catch (IOException ex) {
            System.out.println("Couldn't Load About Stage");
        }
    }

    @FXML
    private void handleReset(ActionEvent event) {
        System.out.println("Clicked Reset");
        dpDatePicker.setValue(LocalDate.now());

        int currHour = LocalDateTime.now().getHour();
        sHours.setValue(currHour);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exiting program");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fire Watcher");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        bounds = map.getBounds();
        removeFireCircles();
        setFireCircle();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isProjectionVisible = true;
        fireCircles = new ArrayList<>();
        fireLatLongList = new ArrayList<>();
        weatherCallTimers = new LinkedList<>();
        RESTAPIController ctrl = new RESTAPIController();
        fullFireCircless = FireParser.parse(ctrl.GetCsvItems(FireParser.HEAD_LINK, true));

        mapView.setKey(mapKey);
        mapView.addMapInializedListener(this);
        restrictDatePicker();

        sHours.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    handleSliderUpdate();
                });

        Duration duration = new Duration(playRate);
        KeyFrame frame = new KeyFrame(duration, (ActionEvent event) -> {
            System.out.println("hello");
            handlePlayFrame();
        });
        playTimeline = new Timeline(frame);
        playTimeline.setCycleCount(Timeline.INDEFINITE);

    }

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(45.501167, -73.650230));
        mapOptions.overviewMapControl(false);
        mapOptions.panControl(false);
        mapOptions.rotateControl(false);
        mapOptions.scaleControl(false);
        mapOptions.streetViewControl(false);
        mapOptions.zoomControl(true);
        mapOptions.zoom(10);

        map = mapView.createMap(mapOptions);
        setLocationMarker();
        checkLocationDanger();
    }

    /**
     * method to show the fire as markers on map
     */
    private void setFireCircle() {
        CircleOptions circleOptions1;
        Circle fireCircle;
        map = MainPageController.this.map;
        bounds = this.map.getBounds();

        int currIdx = 0;
        while (currIdx < 8 && fullFireCircless[currIdx].get(0).getDay() != selectedDay) {
            currIdx++;
        }

        if (currIdx == 8) {
            System.out.println("No fires to show!");
            return;
        }

        for (FireModel fire : fullFireCircless[currIdx]) {
            if (bounds.contains(new LatLong(fire.getLatitude(), fire.getLongitude()))) {
                if (rbAllDay.isSelected() || fire.getHours() == sHours.getValue()) {
                    circleOptions1 = new CircleOptions();
                    circleOptions1.center(new LatLong(fire.getLatitude(), fire.getLongitude()));
                    circleOptions1.radius(850);
                    circleOptions1.strokeColor("red");
                    circleOptions1.fillColor("red");

                    fireCircle = new Circle(circleOptions1);
                    map.addMapShape(fireCircle);
                    fireCircle.setVisible(true);
                    fireCircles.add(fireCircle);
                    fireLatLongList.add(fireCircle.getCenter());

                    if (isProjectionVisible) {
                        map.addUIEventHandler(fireCircle, UIEventType.click, (JSObject jso) -> {
                            System.out.println("Clicked on Fire Circle");
                            addFireProjection(new LatLong(fire.getLatitude(), fire.getLongitude()));
                        });
                    }
                }
            }
        }
        System.out.println("Added " + fireCircles.size() + " fires of the: " + selectedDay);
    }

    /**
     * method to remove fire circles that are on the map
     */
    private void removeFireCircles() {
        if (!fireCircles.isEmpty()) {
            for (Circle circle : fireCircles) {
                map.removeMapShape(circle);
            }
        }

        fireCircles.clear();

        System.out.println("Removed all fires");
    }

    /**
     * method to add the projection (USING HAVERSINE NOT VERY ACCURATE)
     *
     * @param latlong the center of fire
     */
    private void addFireProjection(LatLong latlong) {
        if (!miProjection.isSelected()) {
            return;
        }

        if (weatherCallTimers.size() == 45) {
            if (System.nanoTime() - weatherCallTimers.peek() <= Long.parseLong("60000000000")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR!");
                alert.setHeaderText(null);
                alert.setContentText("You cannot check more than 45 projection in a minute!");
                alert.showAndWait();
                return;
            } else {
                weatherCallTimers.remove();
            }
        }

        weatherCallTimers.add(System.nanoTime());

        RESTAPIController controller;
        String response;
        WeatherModel fireWeather;
        MVCArray coorStack;
        PolygonOptions polygonOptions;
        Polygon projectionShape;
        InfoWindow weatherInfoWindow;

        // to remove the previous projection
        if (projectionPolygon != null) {
            map.removeMapShape(projectionPolygon);
        }

        if (weatherInfo != null) {
            weatherInfo.close();
        }

        // getting the weather info
        controller = new RESTAPIController();
        response = controller.getJSon(String.format(WeatherParser.HEAD_LINK,
                latlong.getLatitude(), latlong.getLongitude(), weatherKey));
        fireWeather = WeatherParser.parse(response);

        // getting the MVCArray
        coorStack = MapProjection.getProjectionPath(latlong, fireWeather);

        // creating the triangle
        polygonOptions = new PolygonOptions();
        polygonOptions.strokeColor("Orange");
        polygonOptions.fillColor("Orange");
        polygonOptions.fillOpacity(0.7);
        projectionShape = new Polygon(polygonOptions);
        projectionShape.setPath(coorStack);

        map.addMapShape(projectionShape);
        projectionPolygon = projectionShape;
        System.out.println("added triangle");

        // adding window info
        weatherInfoWindow = MapProjection.getProjectionInfoWindow(fireWeather);
        weatherInfoWindow.setPosition(latlong);
        weatherInfoWindow.open(map);
        weatherInfo = weatherInfoWindow;
    }

    /**
     * method to restrict the date in DatePicker
     */
    private void restrictDatePicker() {
        dpDatePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now().minusDays(7))
                        || item.isAfter(LocalDate.now().plusDays(0))) {
                    setDisable(true);
                }
            }
        };
        dpDatePicker.setDayCellFactory(dayCellFactory);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleSaveWindow(ActionEvent event) {
        WritableImage snapshot = stage.getScene().snapshot(null);

        File file = getPNGFile();

        if (file != null) {
            saveFile(snapshot, file);
        }
    }

    @FXML
    private void handleSaveMap(ActionEvent event) {
        WritableImage snapshot = mapView.snapshot(null, null);

        File file = getPNGFile();

        if (file != null) {
            saveFile(snapshot, file);
        }
    }

    private File getPNGFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);

        return file;
    }

    private void saveFile(WritableImage snapshot, File file) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method for slider
     */
    private void handleSliderUpdate() {
        lblHour.setText("" + Math.round(sHours.getValue()) + ":00");
        if (rbAllDay.isSelected()) {
            rbAllDay.setSelected(false);
        }
        if (!sHours.isValueChanging()) {
            //if past current time, make it go back
            int currHour = LocalDateTime.now().getHour();
            if (dpDatePicker.getValue().isEqual(LocalDate.now()) && sHours.getValue() > currHour) {
                sHours.setValue(currHour);
                return;
            }
            System.out.println("Current search time is:" + sHours.getValue());
            handleSearch(null);
        }
    }

    /**
     * method to play the frame
     */
    private void handlePlayFrame() {
        System.out.println("hi");
        double nextValue = (sHours.getValue() + 1) % 23;
        int currHour = LocalDateTime.now().getHour();
        if (dpDatePicker.getValue().isEqual(LocalDate.now()) && sHours.getValue() + 1 > currHour) {
            nextValue = 0;
        }
        sHours.setValue(nextValue);
    }

    /**
     * method to check if the location set by user is under a projection or not
     */
    public void checkLocationDanger() {
        Duration duration = new Duration(30 * 60 * 1000);
        KeyFrame frame = new KeyFrame(duration, (ActionEvent event) -> {
            PersonalInfo personalInfo;
            LatLong userLocation;
            LatLong fireLocation;
            double distance;
            LatLong small1;
            LatLong small2;

            personalInfo = new InfoController().getInfo();
            userLocation = new LatLong(personalInfo.getLat(), personalInfo.getLon());
            small1 = fireLatLongList.get(0);
            small2 = fireLatLongList.get(1);

            for (int i = 0; i < fireLatLongList.size(); i++) {
                fireLocation = fireLatLongList.get(i);
                distance = MapProjection.distanceTo(userLocation, fireLocation);

                if (distance < MapProjection.distanceTo(userLocation, small1)) {
                    small2 = small1;
                    small1 = fireLatLongList.get(i);
                } else if (distance < MapProjection.distanceTo(userLocation, small2)) {
                    small2 = fireLatLongList.get(i);
                }
            }

            RESTAPIController controller = new RESTAPIController();
            String response1 = controller.getJSon(String.format(WeatherParser.HEAD_LINK,
                    small1.getLatitude(), small1.getLongitude(), weatherKey));
            WeatherModel fireWeather1 = WeatherParser.parse(response1);

            String response2 = controller.getJSon(String.format(WeatherParser.HEAD_LINK,
                    small2.getLatitude(), small2.getLongitude(), weatherKey));
            WeatherModel fireWeather2 = WeatherParser.parse(response2);

            // getting the MVCArray
            MVCArray coorStack1 = MapProjection.getProjectionPath(small1, fireWeather1);
            MVCArray coorStack2 = MapProjection.getProjectionPath(small2, fireWeather2);

            // creating the triangle
            PolygonOptions polygonOptions1 = new PolygonOptions();
            Polygon projectionShape1 = new Polygon(polygonOptions1);
            projectionShape1.setPath(coorStack1);

            PolygonOptions polygonOptions2 = new PolygonOptions();
            Polygon projectionShape2 = new Polygon(polygonOptions2);
            projectionShape2.setPath(coorStack2);

            if (projectionShape1.getBounds().contains(userLocation)
                    || projectionShape2.getBounds().contains(userLocation)) {
                Email.send(userLocation);
            }
        });
        Timeline line = new Timeline(frame);
        line.setCycleCount(Timeline.INDEFINITE);
    }

    private void setLocationMarker() {
        InfoController infoController = new InfoController();
        PersonalInfo personalInfo = infoController.getInfo();
        if (personalInfo.getLat() != Double.NaN) {
            Marker locationMarker = new Marker(new MarkerOptions().position(new LatLong(personalInfo.getLat(), personalInfo.getLon())));
            map.addMarker(locationMarker);
        }
    }
}
