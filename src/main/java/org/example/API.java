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
                        System.out.println("=======================================");
                        System.out.println(doc.headline.main);
                        System.out.println(doc.byline.original);
                        System.out.println(doc.section_name);
                        System.out.println(doc.lead_paragraph);
                        System.out.println(doc.pub_date);
                        System.out.println("=======================================");
                    }
            articleList.add(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
