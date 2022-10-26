package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        int uuidLength = uuid.trim().length();
        if (Pattern.matches("^[А-Яа-яa-zA-Z]+[\\s[А-Яа-яa-zA-Z]*]*", fullName)) {
            Resume r;
            if (uuidLength == 0) {
                r = new Resume(fullName);
            } else {
                r = storage.get(uuid);
                r.setFullName(fullName);
            }
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (value == null || value.trim().length() == 0) {
                    r.getContacts().remove(type);
                } else {
                    r.addContact(type, value);
                }
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                if (value == null || value.trim().length() != 0) {
                    r.getSections().remove(type);
                } else {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            r.addSection(type, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            String[] arr = value.split("\n");
                            List<String> list = new ArrayList<>();
                            for (String s : arr) {
                                if (s.trim().length() != 0) {
                                    list.add(s + "\n");
                                }
                            }
                            r.addSection(type, new ListSection(list));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            String[] names = request.getParameterValues(String.valueOf(type));
                            String[] urls = request.getParameterValues(type + "url");
                            List<Organization> organizations = new ArrayList<>();
                            for (int i = 0; i < names.length; i++) {
                                String name = names[i];
                                Link link = new Link(name, urls[i]);
                                if (name != null || name.trim().length() != 0) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    String prefix = String.valueOf(type) + i;
                                    String[] startDates = request.getParameterValues(prefix + "startDate");
                                    String[] endDates = request.getParameterValues(prefix + "endDate");
                                    String[] titles = request.getParameterValues(prefix + "header");
                                    String[] descriptions = request.getParameterValues(prefix + "description");
                                    for (int j = 0; j < titles.length; j++) {
                                        String title = titles[j];
                                        if (title != null || title.trim().length() != 0) {
                                            positions.add(new Organization.Position(DateUtil.format(startDates[j]),
                                                    DateUtil.format(endDates[j]), titles[j],
                                                    type == SectionType.EXPERIENCE ? descriptions[j] : ""));
                                        }
                                    }
                                    organizations.add(new Organization(link, positions));
                                }
                            }
                            r.addSection(type, new OrganizationSection(organizations));
                            break;
                    }
                }
            }
            if (uuidLength == 0) {
                storage.save(r);
            } else {
                storage.update(r);
            }
        }
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view").equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
