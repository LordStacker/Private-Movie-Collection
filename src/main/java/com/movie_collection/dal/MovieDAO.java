package com.movie_collection.dal;

import com.movie_collection.be.Movie;

import java.sql.*;
import java.time.ZoneId;

public class MovieDAO {
    private static final ConnectionManager cm = new ConnectionManager();

    public void createMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO Movie (name, rating, path, lastview) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path());
            pstmt.setDate(4, Date.valueOf(movie.lastview().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            pstmt.executeUpdate();
        }
    }

    public void deleteMovie(int id) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void updateMovie(Movie movie) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Movie SET name = ?, rating = ?, path = ?, lastview = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, movie.name());
            pstmt.setDouble(2, movie.rating());
            pstmt.setString(3, movie.path());
            pstmt.setDate(4, Date.valueOf(movie.lastview().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            pstmt.setInt(5, movie.id());
            pstmt.executeUpdate();
        }
    }
}
