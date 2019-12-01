package com.bipullohia.moviecatalogservice.resources;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bipullohia.moviecatalogservice.models.CatalogItem;
import com.bipullohia.moviecatalogservice.models.Movie;
import com.bipullohia.moviecatalogservice.models.UserRating;
import com.netflix.discovery.DiscoveryClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;
	
	//this is used to get more details about the instances running on Discovery Server
//	@Autowired
//	private DiscoveryClient discoveryClient;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getMovieCatalogItemsForUser(@PathVariable("userId") String userId){
		
		//1. Get all rated movieIds
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/rating/users/" + userId, UserRating.class);
		
		//2. For each movieId, call movie info service and get details
		return userRating.getUserRating().stream().map(rating -> {
			
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		
		//3. Put them all together for the final result
		return new CatalogItem(movie.getName(), "Movie Desc", rating.getRating());	
		
		}).collect(Collectors.toList());

	}
}


/*This is the new way to make internal and external (sync/async) API calls
 * Movie movie = webClientBuilder.build()
 * 					.get()
 * 					.url("")
 * 					.retreive()
 * 					.bodyToMono(Movie.class)
 * 					.block()
 * */
