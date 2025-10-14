package com.tva.FXapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;

import org.springframework.aop.interceptor.ConcurrencyThrottleInterceptor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MusicApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private final PartitionClientController controller;

    public MusicApplication() {
        super();
        this.controller = new PartitionClientController("http://localhost:8080");

    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
        this.controller.ping();
    }

    @Override
    public void stop() {
        applicationContext.close();
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        ColumnConstraints colThird = new ColumnConstraints();
        colThird.setPercentWidth(100.0 / 3.0);
        RowConstraints rowThird = new RowConstraints();
        rowThird.setPercentHeight(100.0 / 3.0);
        pane.getColumnConstraints().addAll(colThird, colThird, colThird);
        pane.getRowConstraints().addAll(rowThird, rowThird, rowThird);
        pane.setGridLinesVisible(true);
        // pane.setHgap(10);
        // pane.setVgap(10);
        // pane.setPadding(new Insets(0, 10, 0, 10));
        // InputStream stream = new
        // FileInputStream("/media/tva/save2/dev/demoFX/FXapplication/resources/add.png");
        // Image image = new Image(stream);
        String url = getClass().getResource("/add.png").toString();
        ImageView imageView = new ImageView(url);
        // imageView.getStyleClass().add("dropped");
        pane.getChildren().add(imageView);
        pane.setConstraints(imageView, 1, 1);
        imageView.fitWidthProperty().bind(pane.widthProperty().multiply(0.5));
        imageView.fitHeightProperty().bind(pane.heightProperty().multiply(0.5));
        imageView.setPreserveRatio(true);

        pane.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();

            boolean success = false;
            if (db.hasFiles()) {

                success = true;
            }
            /*
             * let the source know whether the string was successfully transferred and used
             */
            event.setDropCompleted(success);

            event.consume();
        });
        pane.setOnDragExited((DragEvent event) -> {
            imageView.getStyleClass().clear();
            System.out.println("TEST");
            event.consume();
        });
        pane.setOnDragOver((DragEvent event) -> {

            imageView.getStyleClass().add("dropped");
            // System.out.println("ICI "+event);
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        Scene scene = new Scene(pane, 800, 600, true);
        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Partition simplifier");

        primaryStage.show();

    }

}
