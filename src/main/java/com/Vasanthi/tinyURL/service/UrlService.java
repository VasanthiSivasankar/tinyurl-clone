package com.Vasanthi.tinyURL.service;
import java.util.Optional;
import com.Vasanthi.tinyURL.dto.UrlResponse;
import com.Vasanthi.tinyURL.dto.UrlStatsResponse;
import com.Vasanthi.tinyURL.entity.UrlMapping;
import com.Vasanthi.tinyURL.repository.UrlRepository;
import com.Vasanthi.tinyURL.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    public UrlResponse shortenUrl(String originalUrl) {
        Optional<UrlMapping> existing =
                repository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {

            return new UrlResponse(
                    existing.get().getShortCode()
            );

        }

        UrlMapping mapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .clickCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        // Save first to generate the database ID
        mapping = repository.save(mapping);

        // Encode the generated ID
        String shortCode = Base62Encoder.encode(mapping.getId());

        mapping.setShortCode(shortCode);

        repository.save(mapping);

        return new UrlResponse(shortCode);
    }
    public UrlStatsResponse getStats(String shortCode){

        UrlMapping mapping=repository.findByShortCode(shortCode)
                .orElseThrow(()->new RuntimeException("Short URL not found"));

        return new UrlStatsResponse(

                mapping.getOriginalUrl(),
                mapping.getShortCode(),
                mapping.getClickCount(),
                mapping.getCreatedAt()

        );

    }

    public UrlMapping getOriginalUrl(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        mapping.setClickCount(mapping.getClickCount() + 1);

        repository.save(mapping);

        return mapping;
    }
}