package org.example;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        API newApi = new API();
        JDBC newJdbc = new JDBC();
        newApi.getData();
        newJdbc.insertData();
    }

}