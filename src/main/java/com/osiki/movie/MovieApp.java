package com.osiki.movie;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MovieApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<Movie> tableView = new TableView<>();
        ObservableList<Movie> movieList = FXCollections.observableArrayList(DatabaseHelper.getMovies());

        // Set up columns
        TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<Movie, String> directorColumn = new TableColumn<>("Director");
        directorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDirector()));

        TableColumn<Movie, Integer> releaseYearColumn = new TableColumn<>("Release Year");
        releaseYearColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getReleaseYear()));

        tableView.setItems(movieList);
        tableView.getColumns().addAll(titleColumn, directorColumn, releaseYearColumn);

        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Movie Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    }

