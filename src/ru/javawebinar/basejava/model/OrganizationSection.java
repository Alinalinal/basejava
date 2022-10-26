package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> content;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... content) {
        this(Arrays.asList(content));
    }

    public OrganizationSection(List<Organization> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public List<Organization> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection that = (OrganizationSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content.toString();
    }

    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : content) {
            Link homePage = org.getHomePage();
            sb.append("<h3>");
            String url = homePage.getUrl();
            if (!url.equals("")) {
                sb.append("<a href=" + url + ">" + homePage.getName() + "</a></h3>");
            } else {
                sb.append(homePage.getName() + "</h3>");
            }
            for (Organization.Position pos : org.getPositions()) {
                sb.append("<table><tr><td width=\"135\" valign=\"top\">" + DateUtil.format(pos.getStartDate()) + " - "
                        + DateUtil.format(pos.getEndDate()) + "</td>" +
                        "<td width=\"750\"><b>" + pos.getTitle() + "</b>");
                String desc = pos.getDescription();
                if (!desc.equals("")) {
                    sb.append("</br>" + desc);
                }
                sb.append("</td></tr></table>");
            }
        }
        return sb.toString();
    }
}
