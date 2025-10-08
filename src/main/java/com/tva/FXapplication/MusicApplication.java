package com.tva.FXapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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

        GridPane pane = new GridPane();
        // InputStream stream = new
        // FileInputStream("/media/tva/save2/dev/demoFX/FXapplication/resources/add.png");
        // Image image = new Image(stream);
        String url =  getClass().getResource("add.png").toString();
        ImageView imageView = new ImageView(url);
        pane.add(imageView, 1, 1);

        pane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();

            boolean success = false;
            if (db.hasFiles()) {

                success = true;
            }
            /*
             * let the source know whether the string was successfully
             * transferred and used
             */
            event.setDropCompleted(success);
            event.consume();
        });

        pane.setOnDragOver((DragEvent event) -> {
            // System.out.println("ICI "+event);
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        Scene scene = new Scene(pane, 1024, 800, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partition simplifier");

        primaryStage.show();
    }

}
