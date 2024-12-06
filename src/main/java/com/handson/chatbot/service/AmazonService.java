package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonService {

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword),keyword);
    }
    public static final Pattern PRODUCT_PATTERN = Pattern.compile(
            "\"name\":\"([^\"]+)\",.*?\"ratingValue\":([0-9.]+),.*?\"genre\":\"([^\"]+)\",.*?\"duration\":\"([^\"]+)\""
    );

    public String parseProductHtml(String html, String keyword) {
        StringBuilder res = new StringBuilder();
        Matcher matcher = PRODUCT_PATTERN.matcher(html);

        // Iterate over all matches in the HTML
        while (matcher.find()) {
            String name = matcher.group(1);
            String ratingValue = matcher.group(2);
            String genre = matcher.group(3);
            String duration = matcher.group(4);
            if (name.toLowerCase().contains(keyword.toLowerCase())) {
                // If the name matches the keyword, append the details to the result
                res.append("Name: ").append(name).append("\n")
                        .append("Rating Value: ").append(ratingValue).append("\n")
                        .append("Genre: ").append(genre).append("\n")
                        .append("Duration: ").append(duration).append("\n\n");
            }
        }
        // If no match was found, inform the user
        if (res.length() == 0) {
            res.append("No results found for keyword: ").append(keyword);
        }
        return res.toString();
    }



    private String getProductHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.imdb.com/chart/top/")
                .get()
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-US,en;q=0.9,he;q=0.8")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "session-id=147-4273519-5244267; session-id-time=2082787201l; ad-oo=0; ci=e30; csm-hit=tb:RVPWJHVESA3TYBRKAP33+s-RVPWJHVESA3TYBRKAP33|1733320477070&t:1733320477070&adb:adblk_no; session-id=140-7604346-1027864; session-id-time=2082787201l")
                .addHeader("priority", "u=0, i")
                .addHeader("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response);
            }
            // Read the response body once
            String responseBody = response.body().string();
            System.out.println("Response HTML: " + responseBody); // Print to console
            return responseBody; // Return the response body
        }
    }
}