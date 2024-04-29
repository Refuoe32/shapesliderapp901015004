module com.letsela901015004.shapesliderapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.letsela901015004.shapesliderapp to javafx.fxml;
    exports com.letsela901015004.shapesliderapp;
}