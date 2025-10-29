package com.tva.FXapplication;

import javafx.scene.control.ProgressIndicator;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.image.Image;
import javafx.scene.shape.*;
import java.io.File;
import javafx.concurrent.*;
import javafx.geometry.Pos;

import org.springframework.aop.interceptor.ConcurrencyThrottleInterceptor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class OverlayWaitPane extends StackPane {
    public OverlayWaitPane() {
        super();
        ProgressIndicator progress = new ProgressIndicator();
        progress.setProgress(-1);
        StackPane.setAlignment(progress, Pos.CENTER);
        getChildren().addAll(progress);
        setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
        // glass.setMaxWidth(imageView.getFitWidth() - 40);
        // glass.setMaxHeight(imageView.getFitHeight() - 40);
       
    }

};
