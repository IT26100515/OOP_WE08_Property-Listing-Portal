<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Request could not be completed" scope="request"/>
<%@ include file="fragments/header.jspf" %>
<section class="empty-state"><p class="eyebrow">Request error <c:out value="${errorCode}"/></p><h1>We could not complete that request.</h1>
    <p><c:out value="${errorMessage}"/></p><div class="actions centered"><a class="button" href="<c:url value='/listings'/>">Back to properties</a><a class="button secondary" href="<c:url value='/viewings'/>">Back to viewings</a></div></section>
<%@ include file="fragments/footer.jspf" %>