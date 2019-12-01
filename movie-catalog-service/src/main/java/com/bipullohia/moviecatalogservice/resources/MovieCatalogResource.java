package com.bipullohia.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bipullohia.moviecatalogservice.models.CatalogItem;
import com.bipullohia.moviecatalogservice.models.Movie;
import com.bipullohia.moviecatalogservice.models.Rating;
import com.bipullohia.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getMovieCatalogItemsForUser(@PathVariable("userId") String userId){
		
		//1. Get all rated movieIds
		UserRating userRating = restTemplate.getForObject("http://localhost:8083/rating/users/" + userId, UserRating.class);
		
		//2. For each movieId, call movie info service and get details
		return userRating.getUserRating().stream().map(rating -> {
			
		Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
		
		//3. Put them all together for the final result
		return new CatalogItem(movie.getName(), "Movie Desc", rating.getRating());	
		
		}).collect(Collectors.toList());

	}
}

/*
 * Movie movie = webClientBuilder.build()
 * 					.get()
 * 					.url("")
 * 					.retreive()
 * 					.bodyToMono(Movie.class)
 * 					.block()
 * */
