https://roadmap.sh/projects/github-trending-cli

# GitHub Trending CLI

A simple Java command-line application that fetches and displays trending GitHub repositories using the GitHub REST API.

## Features

- Fetch trending public repositories
- Filter by duration:
  - Day
  - Week
  - Month
  - Year
- Limit the number of repositories displayed
- Sort repositories by star count
- Clean and readable CLI output
- Input validation and error handling
- Help command

## Technologies

- Java 17
- Maven
- Java HttpClient
- Jackson Databind
- GitHub REST API

## Installation

Clone the repository:

```bash
git clone https://github.com/Crosby01/Github-Trending-API.git
```

Navigate into the project:

```bash
cd github-trending-cli
```

Compile the project:

```bash
mvn clean compile
```

## Usage

Run with the default options:

```bash
mvn exec:java
```

Specify a duration:

```bash
mvn exec:java "-Dexec.args=--duration month"
```

Specify the number of repositories:

```bash
mvn exec:java "-Dexec.args=--limit 20"
```

Specify both:

```bash
mvn exec:java "-Dexec.args=--duration week --limit 10"
```

Display the help menu:

```bash
mvn exec:java "-Dexec.args=--help"
```

## Example Output

```text
==============================================================
              GitHub Trending Repositories
==============================================================

Duration : week
Showing  : Top 5 repositories

--------------------------------------------------------------
#1 spring-ai

Description : Spring AI Framework
Language    : Java
Stars       : 31,248
Repository  : spring-projects/spring-ai
URL         : https://github.com/spring-projects/spring-ai
```

## Project Structure

```
src
└── main
    └── java
        └── com.trendingcli
            ├── Main.java
            ├── GithubService.java
            ├── GithubResponse.java
            └── RepositoryDto.java
```

## Error Handling

The application handles:

- Invalid command-line arguments
- Missing argument values
- Invalid duration values
- Invalid limit values
- GitHub API errors
- Network errors
- JSON parsing errors

## License

This project is open source and available under the MIT License.
