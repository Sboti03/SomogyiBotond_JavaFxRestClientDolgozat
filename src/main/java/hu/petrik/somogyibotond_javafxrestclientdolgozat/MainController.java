package hu.petrik.somogyibotond_javafxrestclientdolgozat;

import com.google.gson.*;
import hu.petrik.api.RequestHandler;
import hu.petrik.api.Response;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class MainController extends Alerts {

    @FXML
    private Button updateBtn, newBtn, deleteBtn;
    @FXML
    private TableView<Worker> workerTable;
    @FXML
    private TableColumn<Worker, String> nameCol;
    @FXML
    private TableColumn<Worker, Integer> paymentCol;
    @FXML
    private TableColumn<Worker, Worker.posts> postCol;
    @FXML
    private TableColumn<Worker, String> hireDateCol;


    @FXML
    private void initialize() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        hireDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDateText"));

        Platform.runLater(() -> {
            try {
                loadOrdersFromServer();
            }/* catch (Exception e) {
                error("Nem sikerült lekérni az adatokat a szerverről!", e.getMessage());
                Platform.exit();
            }*/ catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void loadOrdersFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String context = response.getContent();

        Gson converter = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer()).create();

        Worker[] workers = converter.fromJson(context, Worker[].class);
        workerTable.getItems().clear();
        for (Worker worker : workers) {
            workerTable.getItems().add(worker);
        }
    }

    @FXML
    public void updateBtnClick(ActionEvent actionEvent) {
        int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Válasszon ki egy dolgozót!");
            return;
        }
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-worker-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 350);
            Stage stage = new Stage();
            stage.setTitle("Dolgozó módosítás");
            UpdateWorkerController controller = fxmlLoader.getController();
            controller.setWorker(selectedWorker);
            showStage(scene, stage);
        } catch (IOException e) {
            error("Nem sikerült betölteni", e.getMessage());
        }




    }

    private void showStage(Scene scene, Stage stage) {
        stage.setScene(scene);
        stage.show();
        deleteBtn.setDisable(true);
        newBtn.setDisable(true);
        updateBtn.setDisable(true);
        stage.setOnHidden(event -> {
            deleteBtn.setDisable(false);
            newBtn.setDisable(false);
            updateBtn.setDisable(false);
            try {
                loadOrdersFromServer();
            } catch (IOException e) {
                error("Nem sikerült lekérni az adatokat a szerverről", e.getMessage());
            }
        });
    }

    @FXML
    public void newBtnClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("new-worker-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 350);
            Stage stage = new Stage();
            stage.setTitle("Új dolgozó");
            showStage(scene, stage);
        } catch (IOException e) {
            error("Nem sikerült betölteni", e.getMessage());
        }

    }

    @FXML
    public void deleteBtnClick(ActionEvent actionEvent) {
        int selectedIndex = workerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Válasszon ki egy dolgozót!");
            return;
        }
        Worker selectedWorker = workerTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Biztos kiakarod törölni: %s?", selectedWorker.getName()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()) {
            error("Ismeretlen hiba");
            return;
        }
        ButtonType clickedButton = optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)) {
            String url = App.BASE_URL + "/" + selectedWorker.getId();
            try {
                RequestHandler.delete(url);
                loadOrdersFromServer();
            } catch (IOException e) {
                error("Hiba a szerverre történő kapcsolódás során");
            }
        }
    }
}