package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println(storage);
        storage = Config.get().getSqlStorage();
        System.out.println(storage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        StringBuilder resumeTable = new StringBuilder();
        resumeTable.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Resume</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <style>\n" +
                "        table, th, td {\n" +
                "            border: 1px solid black;\n" +
                "            border-collapse: collapse;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table class=\"resume-table\" style=\"width:500px\">\n" +
                "    <caption><h4>Resume table</h4></caption>\n" +
                "    <tr style=\"height:40px\">\n" +
                "        <th>UUID</th>\n" +
                "        <th>FULL NAME</th>\n" +
                "    </tr>\n");
        List<Resume> resumes = storage.getAllSorted();
        for (Resume r : resumes) {
            resumeTable.append("<tr style=\"height:40px\">\n" +
                    "        <td>" + r.getUuid() + "</td>\n" +
                    "        <td>" + r.getFullName() + "</td>\n" +
                    "    </tr>\n");
        }
        resumeTable.append("</table>\n" +
                "</body>\n" +
                "</html>");

        response.getWriter().write(String.valueOf(resumeTable));
    }
}
