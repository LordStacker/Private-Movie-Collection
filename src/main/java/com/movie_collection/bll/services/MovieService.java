package com.movie_collection.bll.services;

import com.google.inject.Inject;
import com.movie_collection.be.Category;
import com.movie_collection.be.Category2;
import com.movie_collection.be.Movie;
import com.movie_collection.be.Movie2;
import com.movie_collection.bll.services.interfaces.ICategoryService;
import com.movie_collection.bll.services.interfaces.IMovieService;
import com.movie_collection.dal.interfaces.ICategoryDAO;
import com.movie_collection.dal.interfaces.IMovieDAO;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class MovieService implements IMovieService {

    private final IMovieDAO movieDAO;

    private final ICategoryDAO categoryDAO;

    @Inject
    public MovieService(IMovieDAO movieDAO,ICategoryDAO categoryDAO) {
        this.movieDAO = movieDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Movie2> getAllMovies()  {
        return movieDAO.getAllMoviesTest();
    }

    @Override
    public Optional<Movie2> getMovieById(int id)  {
        return Optional.of(movieDAO.getMovieById(id).get()); // still sketchy
    }

    @Override
    public Optional<List<Movie2>> getAllMoviesInTheCategory(int categoryId){
        return Optional.of(movieDAO.getAllMoviesInTheCategoryTest(categoryId).get()); // have better check
    }

    @Override
    public int createMovie(Movie2 movie) {
        int result = movieDAO.createMovieTest(movie);
        if(result != 0){ // -> this is sktchy atm
            movie.setId(result);
            for (Category2 category : movie.getCategories()
                 ) {
                Optional<Category2> category2 = categoryDAO.getCategoryByName(category.getName());// -> inject categoryDAO
                category2.ifPresent(value -> movieDAO.addCategoryToMovie(value, movie)); // -> check if category present and tries to add it
            }
        }
        return result;
    }

    @Override
    public int updateMovie(Movie2 movie){
        // try to update movie
        int finalResult = 0;
        Objects.requireNonNull(movie,"Movie cannot be null");

        int resultId = movieDAO.updateMovie(movie,movie.getId());

        // tries to get movie by id
        if(resultId > 0){
            finalResult = 1;
            Optional<Movie2> fetchedMovie = movieDAO.getMovieById(resultId);
            // if found tries to delete categories for it so that they can be assigned
            if(fetchedMovie.isPresent()){
                int resultRemove = movieDAO.removeCategoryFromMovie(fetchedMovie.get().getId());
                if(resultRemove < 0) {
                    finalResult = 0;
                    System.out.println("Could not remove role for movie with id :" + fetchedMovie.get().getId());
                }
            }
        }
        // if ok tries to assign the new categories again
        if(finalResult > 0){
            for (Category2 category : movie.getCategories()
            ) {
                Optional<Category2> category2 = categoryDAO.getCategoryByName(category.getName());// -> inject categoryDAO
                category2.ifPresent(value -> movieDAO.addCategoryToMovie(value, movie)); // -> check if category present and tries to add it
            }
        }
        return finalResult;
    }

    @Override
    public int deleteMovie(int id) {
        return movieDAO.deleteMovie(id);
    }

}
