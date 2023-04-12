package org.example;

import com.google.gson.annotations.SerializedName;

public class MostPopular {
    String title;
    String byline;
    String published_date;
    String section;
    @SerializedName("abstract")
    String abstractValue;
}
