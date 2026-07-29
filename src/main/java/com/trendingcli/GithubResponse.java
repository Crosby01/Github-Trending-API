package com.trendingcli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubResponse {

    private List<RepositoryDto> items;

    public GithubResponse() {
    }

    public List<RepositoryDto> getItems() {
        return items;
    }

    public void setItems(List<RepositoryDto> items) {
        this.items = items;
    }
}