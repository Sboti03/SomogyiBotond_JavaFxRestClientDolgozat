module hu.petrik.somogyibotond_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens hu.petrik.somogyibotond_javafxrestclientdolgozat to javafx.fxml, com.google.gson;
    opens hu.petrik.api to com.google.gson;
    exports hu.petrik.somogyibotond_javafxrestclientdolgozat;
}