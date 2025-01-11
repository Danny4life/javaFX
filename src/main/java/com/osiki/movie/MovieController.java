package com.osiki.movie;

import javafx.application.Platform;
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

//    This is an observable list that holds a list of Movie objects.
//    It's initialized as an empty list and will hold the movies fetched from the database or added by the user.
//    The @FXML annotation makes it accessible to the FXML file to bind with the UI.
    @FXML
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();

//    These are text fields bound to the UI to capture the title, director, and release year of a movie.
//    The @FXML annotation ensures that these fields are linked to the FXML UI elements.

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
//    initialize(): This method is automatically called when the controller is initialized (after the FXML is loaded).
//            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title")): Binds the column to the title property of the Movie object.
//            Similarly, directorColumn and releaseYearColumn are bound to the respective properties of the Movie object.
//            Finally, loadMovies() is called to load the movies from the database into the table.
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        loadMovies();
    }

    // Method to load movies from the database
//    loadMovies(): This method loads the list of movies from the database:
//    movieList = FXCollections.observableArrayList() creates a new observable list to store the movies.
//    It calls DatabaseHelper.getMovies() to fetch the movies from the database.
//    The method returns a ResultSet, which is iterated over using while (resultSet.next()).
//    For each row in the ResultSet, a new Movie object is created with the values retrieved (e.g., id, title, director, release_year).
//    The movieList.add(movie) adds the movie to the observable list.
//    If any exceptions occur, an error message is shown using showError().
//    Finally, movieTable.setItems(movieList) sets the items in the table to the movieList,
//    and movieTable.refresh() ensures the table is updated.

    private void loadMovies() {
         movieList = FXCollections.observableArrayList();
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

        movieTable.refresh();
    }

    // Method to handle adding a new movie
//    handleAddMovie(): This method is triggered when the "Add Movie" button is clicked:
//    It retrieves the values entered by the user in the text fields (titleField, directorField, and releaseYearField).
//    The release year is parsed into an integer. If it’s not a valid number, an error message is shown.
//    The method then calls DatabaseHelper.addMovie() to insert the movie into the database.
//    After successfully adding the movie, loadMovies() is called to refresh the movie list and update the table.
//    clearFields() is called to clear the input fields.
    @FXML
    private void handleAddMovie() {
        System.out.println("Adding movie...");
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

    @FXML
    public void handleRetrieveMovies() throws SQLException {

//        // Loop through the movie list and print each movie's details
        for (Movie movie : movieList) {

            System.out.println("Title: " + movie.getTitle() + ", Director: " + movie.getDirector() + ", Release Year: " + movie.getReleaseYear());
        }

    }


    // Method to clear input fields
    private void clearFields() {
        //System.out.println("Clearing input fields...");

        Platform.runLater(()->{
            titleField.clear();
            directorField.clear();
            releaseYearField.clear();
                }

        );

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
