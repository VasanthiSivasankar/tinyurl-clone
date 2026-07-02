package com.Vasanthi.tinyURL.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UrlStatsResponse {

    private String originalUrl;
    private String shortCode;
    private long clickCount;
    private LocalDateTime createdAt;

}