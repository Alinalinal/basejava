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
                    <textarea name="${type.name()}" cols="95" rows="3"><%=section%></textarea>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea name="${type.name()}" cols="95" rows="5"><%=section.toString()%></textarea>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <br/>

                    <input type="hidden" name="${type.name()}orgCount"
                           value="<%=((OrganizationSection) section).getContent().size()%>">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getContent()%>"
                               varStatus="orgCount">
                        <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>

                        <input type="text" name="${type.name()}${orgCount.index}name" placeholder="Название" size=50
                               value="${organization.homePage.name}">
                        <input type="text" name="${type.name()}${orgCount.index}url" placeholder="Ссылка" size=50
                               value="${organization.homePage.url}">

                        <input type="hidden" name="${type.name()}${orgCount.index}posCount"
                               value="${organization.positions.size()}">
                        <c:forEach var="position" items="${organization.positions}" varStatus="posCount">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>

                            <div>
                                <input type="text" name="${type.name()}${orgCount.index}_${posCount.index}startDate"
                                       placeholder="Начало, ММ/ГГГГ" size=50
                                       value="<%=DateUtil.format(position.getStartDate())%>">
                                <input type="text" name="${type.name()}${orgCount.index}_${posCount.index}endDate"
                                       placeholder="Окончание, ММ/ГГГГ" size=50
                                       value="<%=DateUtil.format(position.getEndDate())%>">
                            </div>

                            <input type="text" name="${type.name()}${orgCount.index}_${posCount.index}title"
                                   placeholder="Позиция" size=50 value="${position.title}">

                            <textarea name="${type.name()}${orgCount.index}_${posCount.index}description"
                                      placeholder="Описание" cols="95" rows="5">${position.description}</textarea>
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
