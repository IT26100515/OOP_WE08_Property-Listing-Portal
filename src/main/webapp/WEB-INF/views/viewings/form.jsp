<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Viewing form" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<a class="back-link" href="<c:url value='/viewings'/>">← All viewings</a>
<section class="form-heading"><p class="eyebrow">Plan your visit</p>
    <h1><c:choose><c:when test="${empty viewingForm.id}">Request a viewing</c:when><c:otherwise>Edit viewing</c:otherwise></c:choose></h1>
    <p class="muted">All times are in Asia/Colombo. Choose a future slot on the hour or half-hour.</p></section>
<%@ include file="../fragments/messages.jspf" %>
<c:if test="${empty listings}"><div class="notice error" role="alert">Add a property before requesting a viewing.</div></c:if>
<c:url var="saveUrl" value="/viewings/save"/>
<form:form class="panel form-panel" modelAttribute="viewingForm" action="${saveUrl}" method="post" htmlEscape="true">
    <form:hidden path="id"/>
    <form:errors path="*" cssClass="field-error" element="p"/>
    <div class="form-grid">
        <div class="field full"><label for="listingId">Property</label>
            <c:choose><c:when test="${empty viewingForm.id}">
                <form:select path="listingId" id="listingId" required="required"><form:option value="" label="Choose a property"/>
                    <form:options items="${listings}" itemValue="id" itemLabel="title"/></form:select>
            </c:when><c:otherwise>
                <form:hidden path="listingId"/>
                <p id="listingId" class="readonly-value"><c:forEach var="listing" items="${listings}"><c:if test="${listing.id == viewingForm.listingId}"><c:out value="${listing.title}"/></c:if></c:forEach></p>
                <small>A viewing stays linked to its original property.</small>
            </c:otherwise></c:choose>
        </div>
        <div class="field"><label for="customerName">Customer name</label><form:input path="customerName" id="customerName" required="required" maxlength="100"/></div>
        <div class="field"><label for="email">Email address</label><form:input path="email" id="email" type="email" required="required" maxlength="254"/></div>
        <div class="field"><label for="scheduledAt">Date and time</label><form:input path="scheduledAt" id="scheduledAt" type="datetime-local" step="1800" required="required" aria-describedby="time-help"/><small id="time-help">30-minute slots: 09:00, 09:30, 10:00, etc.</small></div>
        <div class="field"><label for="type">Viewing type</label><form:select path="type" id="type"><form:option value="IN_PERSON" label="In person"/><form:option value="VIRTUAL" label="Virtual"/></form:select></div>
        <div class="field full"><label for="meetingDetails">Meeting point or HTTPS meeting URL</label><form:input path="meetingDetails" id="meetingDetails" required="required" maxlength="500" aria-describedby="meeting-help"/><small id="meeting-help">In person: a clear meeting point (up to 250 characters). Virtual: an absolute https:// meeting URL (up to 500 characters).</small></div>
        <c:if test="${not empty viewingForm.id}">
            <div class="field"><label for="status">Appointment status</label><form:select path="status" id="status">
                <c:if test="${viewingForm.status == 'REQUESTED'}"><form:option value="REQUESTED" label="Requested"/></c:if>
                <form:option value="CONFIRMED" label="Confirmed"/>
                <c:if test="${viewingForm.status == 'CONFIRMED'}"><form:option value="COMPLETED" label="Completed"/></c:if>
                <form:option value="CANCELLED" label="Cancelled"/>
            </form:select><small>Completion is allowed only after the scheduled time. Terminal records cannot be edited.</small></div>
        </c:if>
    </div>
    <div class="actions"><button type="submit" <c:if test="${empty listings}">disabled</c:if>>Save viewing</button><a class="text-link" href="<c:url value='/viewings'/>">Cancel</a></div>
</form:form>
<%@ include file="../fragments/footer.jspf" %>