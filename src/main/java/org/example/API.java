package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class API {
    static Article article;
    static ArrayList<Article> articleList = new ArrayList<>();
    static ArrayList<Article> mostPopularList = new ArrayList<>();


    public void getData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What are you searching for?");
        String search = sc.next();
        String apiUrl = "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="+ search +"&api-key=gd5lZIkC8L1AzTXCJS4yqjS3ukFok7W4";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();

            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            conn.disconnect();
            Gson gson = new Gson();
            article = gson.fromJson(json.toString(), Article.class);
                    for (Doc doc : article.response.docs) {
                        System.out.println("Title: " + doc.headline.main);
                        System.out.println("Author: " + doc.byline.original);
                        System.out.println("Date: " + doc.pub_date);
                        System.out.println("Category: " + doc.section_name);
                        System.out.println("Content: " + doc.lead_paragraph);
                        System.out.println("=======================================");
                    }
            articleList.add(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostPopular() {
        String apiUrl = "https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=sq0oZI0Mf0YtP5ZnkYJNFcSUFm8mlhXR";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();

            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            conn.disconnect();
            Gson gson = new Gson();
            article = gson.fromJson(json.toString(), Article.class);
            for (MostPopular popular: article.results) {
                System.out.println("Title: " + popular.title);
                System.out.println("Author: " + popular.byline);
                System.out.println("Date: " + popular.published_date);
                System.out.println("Category: " + popular.section);
                System.out.println("Content: " + popular.abstractValue);
                System.out.println("=======================================");
            }
//            mostPopularList.add(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
