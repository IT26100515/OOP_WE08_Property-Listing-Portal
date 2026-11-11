<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Viewing details" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<a class="back-link" href="<c:url value='/viewings'/>">← All viewings</a>
<%@ include file="../fragments/messages.jspf" %>
<article class="panel narrow">
    <p class="eyebrow">Viewing appointment</p><h1><c:out value="${listing.title}"/></h1>
    <span class="badge"><c:out value="${viewing.status}"/></span>
    <dl><dt>Customer</dt><dd><c:out value="${viewing.customerName}"/></dd>
        <dt>Email</dt><dd><c:out value="${viewing.email}"/></dd>
        <dt>Scheduled time</dt><dd><c:out value="${viewing.scheduledAt}"/> (Asia/Colombo)</dd>
        <dt>Viewing type</dt><dd><c:out value="${viewing.type}"/></dd>
        <dt>Meeting instructions</dt><dd class="preserve-lines"><c:out value="${viewing.instructions}"/></dd></dl>
    <p class="muted">Requested and confirmed appointments reserve one 30-minute slot for this property.</p>
    <div class="actions">
        <c:if test="${viewing.status == 'REQUESTED' || viewing.status == 'CONFIRMED'}">
            <c:url var="editUrl" value="/viewings/${viewing.id}/edit"/>
            <a class="button" href="<c:out value='${editUrl}'/>">Edit / update status</a>
        </c:if>
        <c:url var="propertyUrl" value="/listings/${listing.id}"/>
        <a class="button secondary" href="<c:out value='${propertyUrl}'/>">View property</a>
    </div>
</article>
<section class="danger-zone narrow"><h2>Delete viewing</h2><p>Deletion permanently removes this record. To retain its history, use Edit to cancel an active viewing instead.</p>
    <c:url var="deleteUrl" value="/viewings/${viewing.id}/delete"/>
    <form action="<c:out value='${deleteUrl}'/>" method="post">
        <input type="hidden" name="<c:out value='${_csrf.parameterName}'/>" value="<c:out value='${_csrf.token}'/>">
        <button class="danger" type="submit">Delete viewing permanently</button>
    </form>
</section>
<%@ include file="../fragments/footer.jspf" %>