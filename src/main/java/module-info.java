module hu.petrik.somogyibotond_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.somogyibotond_javafxrestclientdolgozat to javafx.fxml;
    exports hu.petrik.somogyibotond_javafxrestclientdolgozat;
}