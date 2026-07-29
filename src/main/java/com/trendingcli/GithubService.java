package com.trendingcli;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class GithubService {

    private final HttpClient client;

    public GithubService() {
        client = HttpClient.newHttpClient();
    }

    public void fetchTrendingRepositories(String duration, int limit) {

        LocalDate date;

        switch (duration.toLowerCase()) {

            case "day":
                date = LocalDate.now().minusDays(1);
                break;

            case "week":
                date = LocalDate.now().minusWeeks(1);
                break;

            case "month":
                date = LocalDate.now().minusMonths(1);
                break;

            case "year":
                date = LocalDate.now().minusYears(1);
                break;

            default:
                throw new IllegalArgumentException("Invalid duration.");
        }

        String query = "created:>" + date;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String url = "https://api.github.com/search/repositories?q="
                + encodedQuery
                + "&sort=stars"
                + "&order=desc"
                + "&per_page=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "github-trending-cli")
                .GET()
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            switch (response.statusCode()) {

                case 200:
                    break;

                case 403:
                    System.out.println("GitHub API rate limit exceeded. Please try again later.");
                    return;

                case 404:
                    System.out.println("GitHub API endpoint not found.");
                    return;

                case 500:
                    System.out.println("GitHub server error. Please try again later.");
                    return;

                default:
                    System.out.println("Unexpected HTTP Error: " + response.statusCode());
                    return;
            }

            String json = response.body();

            ObjectMapper mapper = new ObjectMapper();

            GithubResponse githubResponse = mapper.readValue(json, GithubResponse.class);

            if (githubResponse.getItems() == null
                    || githubResponse.getItems().isEmpty()) {

                System.out.println("No repositories found.");
                return;
            }

            System.out.println();
            System.out.println("==============================================================");
            System.out.println("              GitHub Trending Repositories");
            System.out.println("==============================================================");
            System.out.println("Duration : " + duration);
            System.out.println("Showing  : Top " + githubResponse.getItems().size() + " repositories");
            System.out.println();

            for (int i = 0; i < githubResponse.getItems().size(); i++) {

                RepositoryDto repository = githubResponse.getItems().get(i);

                System.out.println("--------------------------------------------------------------");
                System.out.println("#" + (i + 1) + " " + repository.getName());
                System.out.println("--------------------------------------------------------------");

                System.out.println("Description : "
                        + (repository.getDescription() == null
                                ? "No description available"
                                : repository.getDescription()));

                System.out.println("Language    : "
                        + (repository.getLanguage() == null
                                ? "Unknown"
                                : repository.getLanguage()));

                System.out.println("Stars       : "
                        + String.format("%,d", repository.getStars()));

                System.out.println("Repository  : " + repository.getFullName());

                System.out.println("URL         : " + repository.getUrl());

                System.out.println();

            }

        } catch (IOException e) {

            System.out.println("Failed to parse GitHub response.");
            e.printStackTrace();

        } catch (InterruptedException e) {

            System.out.println("Request interrupted.");
            Thread.currentThread().interrupt();

        }

    }

}