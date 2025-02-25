module com.mikelearner.caculatorui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens com.mikelearner.caculatorui to javafx.fxml;
    exports com.mikelearner.caculatorui;
}