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
import java.time.LocalDate;

public class UpdateWorkerController extends Alerts {
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> paymentField;
    @FXML
    private ChoiceBox<Worker.posts> postsSelect;
    @FXML
    private DatePicker hireDateField;

    Worker worker;

    @FXML
    public void initialize() {
        postsSelect.getItems().add(Worker.posts.Worker);
        postsSelect.getItems().add(Worker.posts.TeamMember);
        postsSelect.getItems().add(Worker.posts.TeamLeader);
        postsSelect.getItems().add(Worker.posts.ProjectManager);


    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
        nameField.setText(this.worker.getName());
        paymentField.getValueFactory().setValue(this.worker.getPayment());
        postsSelect.setValue(this.worker.getPost());
        hireDateField.setValue(this.worker.getHireDate());
    }

    public void changeClick(ActionEvent actionEvent) {
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
        Worker newWorker = new Worker(name, payment, post, hireDate);
        Gson converter = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
        String json = converter.toJson(newWorker);
        try {
            String url = App.BASE_URL + "/" + this.worker.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200) {
                warning("Frissítve!");
                Stage stage = (Stage) this.nameField.getScene().getWindow();
                stage.close();
            } else {
                error(response.getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
