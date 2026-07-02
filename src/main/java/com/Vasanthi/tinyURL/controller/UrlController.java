package com.Vasanthi.tinyURL.controller;

import com.Vasanthi.tinyURL.dto.UrlRequest;
import com.Vasanthi.tinyURL.dto.UrlResponse;
import com.Vasanthi.tinyURL.dto.UrlStatsResponse;
import com.Vasanthi.tinyURL.entity.UrlMapping;
import com.Vasanthi.tinyURL.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest request) {

        return ResponseEntity.ok(
                service.shortenUrl(request.getOriginalUrl())
        );
    }

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        UrlMapping mapping = service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(302)
                .location(URI.create(mapping.getOriginalUrl()))
                .build();
    }
    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<UrlStatsResponse> stats(
            @PathVariable String shortCode){

        return ResponseEntity.ok(

                service.getStats(shortCode)

        );

    }

}