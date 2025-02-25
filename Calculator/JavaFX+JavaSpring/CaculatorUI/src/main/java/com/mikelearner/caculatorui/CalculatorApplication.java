package com.mikelearner.caculatorui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("CalculatorBody.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400,700 );
        stage.setTitle("Caculator");
        stage.setScene(scene);
        Image newico = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/mikelearner/caculatorui/icon/calculator.png")));
        stage.getIcons().add(newico);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}