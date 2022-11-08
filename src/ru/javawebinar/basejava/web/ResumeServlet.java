package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResumeServlet extends HttpServlet {

    private enum THEME {
        dark, light, purple
    }

    private Storage storage;
    private final Set<String> themes = new HashSet<>();

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getSqlStorage();
        for (THEME t : THEME.values()) {
            themes.add(t.name());
        }
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
            Config.get().checkImmutable(uuid);
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.setContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    String value1 = request.getParameter(type.name());
                    if (HtmlUtil.isEmpty(value1)) {
                        r.getSections().remove(type);
                    } else {
                        r.setSection(type, new TextSection(value1));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value2 = request.getParameter(type.name());
                    if (HtmlUtil.isEmpty(value2)) {
                        r.getSections().remove(type);
                    } else {
                        List<String> content = new ArrayList<>();
                        for (String s : value2.split("\n")) {
                            if (!s.trim().equals("")) {
                                content.add(s);
                            }
                        }
                        r.setSection(type, new ListSection(content));
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    int orgCount = Integer.parseInt(request.getParameter(type.name() + "orgCount"));
                    for (int i = 0; i < orgCount; i++) {
                        String prefix1 = type.name() + i;
                        String name = request.getParameter(prefix1 + "name");
                        if (name != null && name.trim().length() != 0) {
                            String url = request.getParameter(prefix1 + "url");
                            Link link = new Link(name, url);
                            int posCount = Integer.parseInt(request.getParameter(prefix1 + "posCount"));
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int j = 0; j < posCount; j++) {
                                String prefix2 = prefix1 + "_" + j;
                                String startDate = request.getParameter(prefix2 + "startDate");
                                String endDate = request.getParameter(prefix2 + "endDate");
                                String title = request.getParameter(prefix2 + "title");
                                if (title != null && title.trim().length() != 0) {
                                    String description = request.getParameter(prefix2 + "description");
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
                        r.setSection(type, new OrganizationSection(organizations));
                    }
                    break;
            }
        }
        if (uuidLength == 0) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume?theme=" + getTheme(request));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        request.setAttribute("theme", getTheme(request));

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                Config.get().checkImmutable(uuid);
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
                    r.setSection(type, section);
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

    private String getTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return themes.contains(theme) ? theme : THEME.light.name();
    }
}
