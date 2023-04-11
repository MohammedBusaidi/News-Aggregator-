package org.example;

import com.google.gson.Gson;

import java.sql.*;

public class JDBC {
    Doc[] doc;
    public void insertData() {
        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;
        String user = "sa";
        String pass = "root";
        String databaseName = "NewsAggregator";
        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();

            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, user, pass);

            String sql = "INSERT INTO universities(Name, Country, State_Province, Domains, Web_Pages, Alpha_Two_Code) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            Gson gson = new Gson();
            for (int i = 0; i < doc.length; i++) {
                Doc newDoc = doc[i];
                ps.setString(1, newDoc.headline.main);
                ps.setString(2, newDoc.byline.original);
                ps.setString(3, newDoc.section_name);
                ps.setString(4, newDoc.lead_paragraph);
                ps.setString(5, newDoc.pub_date);
                ps.executeUpdate();
            }
            System.out.println("DATA INSERTED!");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
