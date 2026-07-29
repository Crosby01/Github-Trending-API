package com.trendingcli;

public class Main {

    public static void main(String[] args) {

        try {

            String duration = "week";
            int limit = 10;

            for (int i = 0; i < args.length; i++) {

                switch (args[i]) {

                    case "--duration":

                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException(
                                    "Missing value for --duration.");
                        }

                        duration = args[++i];
                        break;

                    case "--limit":

                        if (i + 1 >= args.length) {
                            throw new IllegalArgumentException(
                                    "Missing value for --limit.");
                        }

                        limit = Integer.parseInt(args[++i]);
                        break;

                    case "--help":
                    case "-h":

                        printHelp();
                        return;

                    default:

                        throw new IllegalArgumentException(
                                "Unknown argument: "
                                        + args[i]
                                        + "\nUse --help to see available options.");
                }
            }

            validateDuration(duration);
            validateLimit(limit);

            System.out.println("Duration: " + duration);
            System.out.println("Limit: " + limit);

            GithubService githubService = new GithubService();
            githubService.fetchTrendingRepositories(duration, limit);

        } catch (NumberFormatException e) {

            System.out.println("Limit must be a valid number.");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void validateDuration(String duration) {

        if (!duration.equalsIgnoreCase("day")
                && !duration.equalsIgnoreCase("week")
                && !duration.equalsIgnoreCase("month")
                && !duration.equalsIgnoreCase("year")) {

            throw new IllegalArgumentException(
                    "Invalid duration. Use: day, week, month, or year.");
        }

    }

    private static void validateLimit(int limit) {

        if (limit <= 0) {

            throw new IllegalArgumentException(
                    "Limit must be greater than zero.");

        }

    }

    private static void printHelp() {

        System.out.println();
        System.out.println("GitHub Trending CLI");
        System.out.println("==============================================");
        System.out.println("Fetch the most starred GitHub repositories.");
        System.out.println();

        System.out.println("Usage:");
        System.out.println("  mvn exec:java \"-Dexec.args=--duration week --limit 10\"");
        System.out.println();

        System.out.println("Options:");
        System.out.println("  --duration <day|week|month|year>");
        System.out.println("      Specify the time range.");
        System.out.println("      Default: week");
        System.out.println();

        System.out.println("  --limit <number>");
        System.out.println("      Number of repositories to display.");
        System.out.println("      Default: 10");
        System.out.println();

        System.out.println("  --help, -h");
        System.out.println("      Display this help message.");
        System.out.println();

        System.out.println("Examples:");
        System.out.println("  mvn exec:java \"-Dexec.args=--duration day --limit 5\"");
        System.out.println("  mvn exec:java \"-Dexec.args=--duration month --limit 20\"");
        System.out.println("  mvn exec:java \"-Dexec.args=--help\"");
        System.out.println();
    }

}