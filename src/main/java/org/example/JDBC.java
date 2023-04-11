package org.example;

import com.google.gson.Gson;

import java.sql.*;

public class JDBC {

    public void createTable() {
        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;
        String user = "sa";
        String pass = "root";
        String databaseName = "NewsAggregator";
        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();

            String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'articles')\n"
                    + "BEGIN\n"
                    + "CREATE TABLE articles(\n"
                    + "article_title VARCHAR(150),\n"
                    + "author VARCHAR(150),\n"
                    + "date VARCHAR(150),\n"
                    + "category VARCHAR(150),\n"
                    + "content VARCHAR(150)\n"
                    + ");\n"
                    + "END\n";
            st.executeUpdate(sql);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

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

            String sql = "INSERT INTO articles(article_title, author, date, category, content) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < API.articleList.size(); i++) {
                for (int j = 0; j < API.articleList.get(i).response.docs.length; j++) {
                    ps.setString(1, API.articleList.get(i).response.docs[j].headline.main);
                    ps.setString(2, API.articleList.get(i).response.docs[j].byline.original);
                    ps.setString(3, API.articleList.get(i).response.docs[j].pub_date);
                    ps.setString(4, API.articleList.get(i).response.docs[j].section_name);
                    ps.setString(5, API.articleList.get(i).response.docs[j].lead_paragraph);
                    ps.executeUpdate();
                }
            }
            ps.close();
            con.close();
            System.out.println("DATA INSERTED!");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
