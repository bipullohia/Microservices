package com.bipullohia.ratingsdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bipullohia.ratingsdataservice.models.Rating;
import com.bipullohia.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping("/rating")
public class RatingResource {

	@RequestMapping("/{movieId}")
	public Rating getRatingData(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 5);
	}
	
	@RequestMapping("/users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> ratingList = Arrays.asList(
						new Rating("movie01", 4),
						new Rating("movie02", 5),
						new Rating("movie03", 3)
						);
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratingList);
		return userRating;
	}
}
