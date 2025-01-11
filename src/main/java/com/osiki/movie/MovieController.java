package com.osiki.movie;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField directorField;

    @FXML
    private TextField releaseYearField;

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> directorColumn;

    @FXML
    private TableColumn<Movie, Integer> releaseYearColumn;

    // Method to initialize the table
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        loadMovies();
    }

    // Method to load movies from the database
    private void loadMovies() {
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DatabaseHelper.getMovies();
            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("director"),
                        resultSet.getInt("release_year")
                );
                movieList.add(movie);
            }
        } catch (SQLException e) {
            showError("Error loading movies", e.getMessage());
        }
        movieTable.setItems(movieList);
    }

    // Method to handle adding a new movie
    @FXML
    private void handleAddMovie() {
        String title = titleField.getText();
        String director = directorField.getText();
        int releaseYear;
        try {
            releaseYear = Integer.parseInt(releaseYearField.getText());
        } catch (NumberFormatException e) {
            showError("Invalid input", "Release year must be a valid number.");
            return;
        }

        try {
            DatabaseHelper.addMovie(title, director, releaseYear);
            loadMovies();
            clearFields();
        } catch (SQLException e) {
            showError("Error adding movie", e.getMessage());
        }
    }

    // Method to clear input fields
    private void clearFields() {
        titleField.clear();
        directorField.clear();
        releaseYearField.clear();
    }

    // Method to show error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
