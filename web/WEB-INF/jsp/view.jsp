<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
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
    <c:choose>
    <c:when test="${sectionEntry.key == 'OBJECTIVE'}">
    <h2>${sectionEntry.key.title}</h2>
    <h3>${sectionEntry.value}<h3/>
        </c:when>
        <c:when test="${sectionEntry.key == 'PERSONAL'}">
        <h2>${sectionEntry.key.title}</h2>
            ${sectionEntry.value}
        </c:when>
        <c:when test="${sectionEntry.key == 'ACHIEVEMENT'}">
        <h2>${sectionEntry.key.title}</h2>
                <%=((ListSection) sectionEntry.getValue()).toHtml()%><br/>
        </c:when>
        <c:when test="${sectionEntry.key == 'QUALIFICATIONS'}">
        <h2>${sectionEntry.key.title}</h2>
                <%=((ListSection) sectionEntry.getValue()).toHtml()%><br/>
        </c:when>
        <c:when test="${sectionEntry.key == 'EXPERIENCE'}">
        <h2>${sectionEntry.key.title}</h2>
                <%=((OrganizationSection) sectionEntry.getValue()).toHtml()%><br/>
        </c:when>
        <c:when test="${sectionEntry.key == 'EDUCATION'}">
        <h2>${sectionEntry.key.title}</h2>
                <%=((OrganizationSection) sectionEntry.getValue()).toHtml()%><br/>
        </c:when>
        </c:choose>
        </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
