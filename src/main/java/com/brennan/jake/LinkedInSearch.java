package com.brennan.jake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Tool to perform an automated LinkedIn Jobs search using specified keywords, with additional filtering!
 * @author Jake
 */
public class LinkedInSearch {
    private static String API_KEY;
    
    public static void main(String[] args) {
        // System.out.println("Hello World!");
        Dotenv dotenv = Dotenv.load();
	    API_KEY = dotenv.get("API_KEY");
        String result = getPostings("Software Developer", Optional.of(10364427L), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        // System.out.println(result);
        String data = JsonProcessor.getData(result);
        ArrayList<Long> ids = JsonProcessor.extractIds(data);
        // System.out.println(ids);
        System.out.println(returnUrlsNoDegree(ids.toArray(Long[]::new)));
    }

    /*
     * Call the LinkedIn Jobs API
     * @param keywords a list of keywords
     * @param locId a location ID or empty
     * @param companyIds an array of company IDs or empty
     * @param datePosted the post recency filter or empty
     * ...
     * @returns A massive JSON object with job postings
     */
    public static String getPostings(String keywords, Optional<Long> locId, Optional<Long[]> companyIds, Optional<String> datePosted, Optional<String> salary, Optional<String> jobType) {
        final BodyHolder h = new BodyHolder();
        try (AsyncHttpClient client = new DefaultAsyncHttpClient()) {
            String params = "keywords=";
            params += UrlSafeConverter.convertToUrlSafe(keywords);
            params += (locId.isPresent() ? "locationId=" + locId.get() : "");
            params += (companyIds.isPresent() ? "companyIds=" + UrlSafeConverter.convertToUrlSafe(UrlSafeConverter.longArrayParse(companyIds.get())) : "");
            params += (datePosted.isPresent() ? "datePosted=" + UrlSafeConverter.convertToUrlSafe(datePosted.get()) : "");
            params += (salary.isPresent() ? "salary=" + UrlSafeConverter.convertToUrlSafe(salary.get()) : "");
            params += (jobType.isPresent() ? "jobType=" + UrlSafeConverter.convertToUrlSafe(jobType.get()) : "");
            client.prepare("GET", "https://linkedin-data-api.p.rapidapi.com/search-jobs?" + params + "&sort=mostRelevant")
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "linkedin-data-api.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept((res) -> {h.setBody(res.getResponseBody());})
                .join();
            
            return h.getBody();
        } catch (IOException ioe) {
            return "An IOException occurred.";
        }
    }

    public static ArrayList<String> returnUrlsNoDegree(Long... ids) {
        ArrayList<String> urls = new ArrayList<>();
        for (Long id : ids) {
            final BodyHolder h = new BodyHolder();
            try (AsyncHttpClient cli = new DefaultAsyncHttpClient()) {
                cli.prepare("GET", "https://linkedin-data-api.p.rapidapi.com/get-job-details?id=" + id)
                .setHeader("x-rapidapi-key", API_KEY)
                .setHeader("x-rapidapi-host", "linkedin-data-api.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept((res) -> {h.setBody(res.getResponseBody());})
                .join();
            } catch (IOException ioe) {
                System.err.println("An IOException occurred at ID " + id);
                continue;
            }
            if (JsonProcessor.extractStatus(h.getBody())) {
                String desc = JsonProcessor.extractDescription(h.getBody());
                if (!desc.toLowerCase().contains("degree")) {
                    urls.add(JsonProcessor.extractUrl(h.getBody()));
                }
            }
        }
        return urls;
    }
}
