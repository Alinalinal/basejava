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
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value1 = request.getParameter(type.name());
                    if (value1 == null || value1.trim().length() == 0) {
                        r.getSections().remove(type);
                    } else {
                        r.addSection(type, new TextSection(value1));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value2 = request.getParameter(type.name());
                    if (value2 == null || value2.trim().length() == 0) {
                        r.getSections().remove(type);
                    } else {
                        List<String> content = new ArrayList<>();
                        for (String s : value2.split("\n")) {
                            if (!s.trim().equals("")) {
                                content.add(s);
                            }
                        }
                        r.addSection(type, new ListSection(content));
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    int orgCount = Integer.parseInt(request.getParameter(type.name() + "orgCount"));
                    for (int i = 0; i < orgCount; i++) {
                        String name = request.getParameter(type.name() + i + "name");
                        if (name != null && name.trim().length() != 0) {
                            String url = request.getParameter(type.name() + i + "url");
                            Link link = new Link(name, url);
                            int posCount = Integer.parseInt(request.getParameter(type.name() + i + "posCount"));
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < posCount; j++) {
                                String startDate = request.getParameter(type.name() + i + "_" + j + "startDate");
                                String endDate = request.getParameter(type.name() + i + "_" + j + "endDate");
                                String title = request.getParameter(type.name() + i + "_" + j + "title");
                                String description = request.getParameter(type.name() + i + "_" + j + "description");
                                if (title != null && title.trim().length() != 0) {
                                    positions.add(new Organization.Position(DateUtil.format(startDate),
                                            DateUtil.format(endDate), title, description));
                                }
                            }
                            organizations.add(new Organization(link, positions));
                        }
                    }
                    if (organizations.size() == 0) {
                        r.getSections().remove(type);
                    } else {
                        r.addSection(type, new OrganizationSection(organizations));
                    }
                    break;
            }
        }
        if (uuidLength == 0) {
            storage.save(r);
        } else {
            storage.update(r);
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
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> organizations = new ArrayList<>();
                            organizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization organization : orgSection.getContent()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    positions.add(Organization.Position.EMPTY);
                                    positions.addAll(organization.getPositions());
                                    organizations.add(new Organization(organization.getHomePage(), positions));
                                }
                            }
                            section = new OrganizationSection(organizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view").equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
