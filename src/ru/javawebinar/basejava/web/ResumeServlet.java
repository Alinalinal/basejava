package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.sql.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // response.setHeader("Content-Type", "text/html; charset=UTF-8");
        // response.setContentType("text/html; charset=UTF-8");
        // String name = request.getParameter("name");
        // response.getWriter().write((name == null) ? "Hello Resumes!" : "Hello, " + name + '!');

        Class dbDriver;
        try {
            dbDriver = Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SqlHelper sqlHelper = new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres",
                "postgres");
        try (Connection connection = sqlHelper.getConnectionFactory().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, String> resumes = new HashMap<>();
            while (resultSet.next()) {
                resumes.put(resultSet.getString("uuid"),
                        resultSet.getString("full_name"));
            }
            request.setAttribute("resumes", resumes);
            request.getRequestDispatcher("/resume.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
