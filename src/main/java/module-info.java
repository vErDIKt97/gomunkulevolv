module com.hacman.gomunkulevolv {
    requires javafx.controls;
    requires javafx.base;
    requires org.jetbrains.annotations;


    opens com.hacman.gomunkulevolv to javafx.fxml;
    exports com.hacman.gomunkulevolv;
    exports com.hacman.gomunkulevolv.controller;
    opens com.hacman.gomunkulevolv.controller to javafx.fxml;
}