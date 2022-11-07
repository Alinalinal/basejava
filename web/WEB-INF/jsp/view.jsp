<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <hr/>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                    ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
        <div>${type.title}</div>
        <c:choose>
            <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                <div><%=((TextSection) section).getContent()%>
                </div>
            </c:when>
            <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                <ul>
                    <c:forEach var="item" items="<%=((ListSection) section).getContent()%>">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                <c:forEach var="organization" items="<%=((OrganizationSection) section).getContent()%>">
                    <c:choose>
                        <c:when test="${empty organization.homePage.url}">
                            <div>${organization.homePage.name}</div>
                        </c:when>
                        <c:otherwise>
                            <div><a href="${organization.homePage.url}">${organization.homePage.name}</a></div>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <div><%=DateUtil.format(position.getStartDate())%> - <%=DateUtil.format(position.getEndDate())%>
                        </div>
                        <div>${position.title}</div>
                        <c:choose>
                            <c:when test="${empty position.description}">
                            </c:when>
                            <c:otherwise>
                                <div>${position.description}</div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
