package hu.petrik.somogyibotond_javafxrestclientdolgozat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.petrik.api.RequestHandler;
import hu.petrik.api.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NewWorkerController extends Alerts {
    @javafx.fxml.FXML
    private TextField nameField;
    @javafx.fxml.FXML
    private Spinner<Integer> paymentField;
    @javafx.fxml.FXML
    private ChoiceBox<Worker.posts> postsSelect;
    @javafx.fxml.FXML
    private DatePicker hireDateField;


    @FXML
    public void initialize() {
        postsSelect.getItems().add(Worker.posts.Worker);
        postsSelect.getItems().add(Worker.posts.TeamMember);
        postsSelect.getItems().add(Worker.posts.TeamLeader);
        postsSelect.getItems().add(Worker.posts.ProjectManager);

    }

    public void newClick(ActionEvent actionEvent) {
        String name = nameField.getText();
        int payment = paymentField.getValue();
        Worker.posts post = postsSelect.getValue();
        LocalDate hireDate = hireDateField.getValue();
        if (name.isEmpty()) {
            warning("A név mező kitöltése kötelező!");
            return;
        }
        if (post == null) {
            warning("A poszt kiválasztása kötelező");
            return;
        }
        if (hireDate == null) {
            hireDate = LocalDate.now();
        }
        Worker newWorker = new Worker(name,payment,post, hireDate);
        Gson converter = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
        String json = converter.toJson(newWorker);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201) {
                warning("Hozzá adva!");
                Stage stage = (Stage)this.nameField.getScene().getWindow();
                stage.close();
            } else {
                error(response.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cancelClick(ActionEvent actionEvent) {
    }
}
