package com.bipullohia.moviecatalogservice.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bipullohia.moviecatalogservice.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@RequestMapping("/{userId}")
	public List<CatalogItem> getMovieCatalogItemsForUser(@PathVariable("userId") String userId){
		return Collections.singletonList(
				new CatalogItem("FordVsFerrari", "The Battle between two car companies", 5));
	}
}
