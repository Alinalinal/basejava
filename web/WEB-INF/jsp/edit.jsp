<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.Config" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>


<jsp:include page="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="theme" value="${theme}">
    <div class="scrollable-panel">
        <div class="form-wrapper">
            <div class="section">ФИО:</div>
            <input class="field" type="text" name="fullName" size=55 placeholder="ФИО" pattern="[А-Яа-яa-zA-Z\s]{2,}"
                   value="${resume.fullName}" required>

            <div class="section">Контакты:</div>

            <c:forEach var="type" items="<%=ContactType.values()%>">
                <input class="field" type="text" name="${type.name()}" size=30 placeholder="${type.title}"
                       value="${resume.getContact(type)}">
            </c:forEach>

            <div class="spacer"></div>

            <div class="section">Секции</div>

            <c:forEach var="type" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
                <div class="field-label">${type.title}</div>
                <c:choose>
                    <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                        <textarea class="field" name="${type.name()}"><%=section%></textarea>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea class="field" name="${type.name()}">
                        <%=String.join("\n", ((ListSection) section).getContent())%></textarea>
                    </c:when>
                    <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                        <input type="hidden" name="${type.name()}orgCount"
                               value="<%=((OrganizationSection) section).getContent().size()%>">
                        <c:forEach var="organization" items="<%=((OrganizationSection) section).getContent()%>"
                                   varStatus="orgCount">
                            <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization"/>
                            <c:choose>
                                <c:when test="${orgCount.index == 0}">
                                </c:when>
                                <c:otherwise>
                                    <div class="spacer"></div>
                                </c:otherwise>
                            </c:choose>

                            <input class="field" type="text" name="${type.name()}${orgCount.index}name"
                                   placeholder="Название" size=100 value="${organization.homePage.name}">
                            <input class="field" type="text" name="${type.name()}${orgCount.index}url"
                                   placeholder="Ссылка" size=100 value="${organization.homePage.url}">

                            <input type="hidden" name="${type.name()}${orgCount.index}posCount"
                                   value="${organization.positions.size()}">
                            <c:forEach var="position" items="${organization.positions}" varStatus="posCount">
                                <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>

                                <div class="date-section">
                                    <input class="field date" type="text"
                                           name="${type.name()}${orgCount.index}_${posCount.index}startDate"
                                           placeholder="Начало, ММ/ГГГГ" size=10
                                           value="<%=DateUtil.format(position.getStartDate())%>">
                                    <input class="field date date-margin" type="text"
                                           name="${type.name()}${orgCount.index}_${posCount.index}endDate"
                                           placeholder="Окончание, ММ/ГГГГ" size=10
                                           value="<%=DateUtil.format(position.getEndDate())%>">
                                </div>

                                <input class="field" type="text"
                                       name="${type.name()}${orgCount.index}_${posCount.index}title"
                                       placeholder="Позиция" size=75 value="${position.title}">

                                <textarea class="field"
                                          name="${type.name()}${orgCount.index}_${posCount.index}description"
                                          placeholder="Описание">${position.description}</textarea>

                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

            <div class="spacer"></div>

            <div class="button-section">
                <button class="red-cancel-button" type="button" onclick="window.history.back()">Отменить</button>
                <c:if test="<%=!Config.get().isImmutable(resume.getUuid())%>">
                    <button class="green-submit-button" type="submit">Сохранить</button>
                </c:if>
            </div>

        </div>
    </div>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
