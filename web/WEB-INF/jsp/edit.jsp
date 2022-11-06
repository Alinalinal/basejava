<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
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
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <div>
        <div>ФИО:</div>
        <input type="text" name="fullName" size=50 placeholder="ФИО" pattern="[А-Яа-яa-zA-Z\s]{2,}"
               value="${resume.fullName}" required>

        <div>Контакты:</div>
        <c:forEach var="type" items="<%=ContactType.values()%>">
        <input type="text" name="${type.name()}" size=50 placeholder="${type.title}" value="${resume.getContact(type)}">
        </c:forEach>

        <hr/>

        <div>Секции:</div>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
        <div>${type.title}
            <div/>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                    <textarea name="${type}" cols="95" rows="3"><%=section%></textarea>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea name="${type}" cols="95" rows="5"><%=section.toString()%></textarea>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <br/>

                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getContent()%>">
                        <c:set var="orgCounter" value="${0}"/>

                        <input type="text" name="${type}" placeholder="Название" size=50
                               value="${organization.homePage.name}">
                        <input type="text" name="${type}url" placeholder="Ссылка" size=50
                               value="${organization.homePage.url}">

                        <c:forEach var="position" items="${organization.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>

                            <div>
                                <input name="${type}${orgCounter}startDate" placeholder="Начало, ММ/ГГГГ"
                                       size=50 value="<%=DateUtil.format(position.getStartDate())%>">
                                <input name="${type}${orgCounter}endDate" placeholder="Окончание, ММ/ГГГГ" size=50
                                       value="<%=DateUtil.format(position.getEndDate())%>">
                            </div>

                            <input type="text" name="${type}${orgCounter}title" placeholder="Позиция" size=50
                                   value="${position.title}">

                            <textarea name="${type}${orgCounter}description" placeholder="Описание" cols="95"
                                      rows="5">${position.description}</textarea>
                            <c:set var="orgCounter" value="${orgCounter + 1}"/>
                            <br/>
                            <hr/>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
            </c:forEach>
            <hr/>
            <div>
                <button type="submit">Сохранить</button>
                <button type="reset" onclick="window.history.back()">Отменить</button>
            </div>
        </div>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
