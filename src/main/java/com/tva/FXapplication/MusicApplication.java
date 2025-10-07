package com.tva.FXapplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MusicApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private static Stage stage;
    @Override
    public void init() {
       applicationContext = new SpringApplicationBuilder(Main.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

    private Parent createUI()
    {
        return new StackPane(new Text("Hello World"));
    }

    @Override
    public void start(Stage primaryStage) {
         stage = primaryStage;
         stage.setScene(new Scene(createUI(), 300, 300));
         stage.show();
    }

}