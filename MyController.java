package com.example.demo;




import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MyController {


    @Autowired
    private OmdbService omdbService;

    @GetMapping("/")
    public String getMovieForm() {
    	 return "getMovieForm";
    }

    @PostMapping("/getMovie")
    public String getMovie(@RequestParam("title") String title, Model model) {
        String movieDetails = omdbService.getMovieDetails(title);
        
        // Parse JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(movieDetails);
            model.addAttribute("title", jsonNode.get("Title").asText());
            model.addAttribute("director", jsonNode.get("Director").asText());
            model.addAttribute("actors", jsonNode.get("Actors").asText());
            model.addAttribute("poster", jsonNode.get("Poster").asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "movieDetails";
    }
}


