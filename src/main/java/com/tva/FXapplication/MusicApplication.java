package com.tva.FXapplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.stage.Stage;
import javafx.application.Application;

public class MusicApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
       applicationContext = new SpringApplicationBuilder(Main.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }


    @Override
    public void start(Stage primaryStage) {
    }

}