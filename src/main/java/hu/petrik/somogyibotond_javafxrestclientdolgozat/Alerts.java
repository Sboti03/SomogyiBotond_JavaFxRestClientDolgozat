package hu.petrik.somogyibotond_javafxrestclientdolgozat;

import javafx.scene.control.Alert;

public abstract class Alerts {


    protected void error(String headerText, String text) {
        alert(Alert.AlertType.ERROR, headerText, text);
    }

    protected void error(String text) {
        alert(Alert.AlertType.ERROR, "", text);
    }

    protected void warning(String text) {
        alert(Alert.AlertType.WARNING, "" , text);
    }

    protected void alert(Alert.AlertType alertType, String headerText, String contextText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

}
