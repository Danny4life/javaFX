package com.osiki.movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/movie_db";
    private static final String USER = "root";
    private static final String PASSWORD = "PASSword1234!#";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }



//    public static List<Movie> getMovies() {
//        List<Movie> movies = new ArrayList<>();
//        String query = "SELECT * FROM movies";
//        try (Connection connection = getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String director = resultSet.getString("director");
//                int releaseYear = resultSet.getInt("release_year");
//                movies.add(new Movie(id, title, director, releaseYear));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return movies;
//    }

    // Method to fetch movies from the database
    public static ResultSet getMovies() throws SQLException {
        Connection connection = getConnection();
        String query = "SELECT * FROM movies";
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    // Method to add a new movie to the database
    public static void addMovie(String title, String director, int releaseYear) throws SQLException {
        Connection connection = getConnection();
        String query = "INSERT INTO movies (title, director, release_year) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, title);
        statement.setString(2, director);
        statement.setInt(3, releaseYear);
        statement.executeUpdate();
    }
}
