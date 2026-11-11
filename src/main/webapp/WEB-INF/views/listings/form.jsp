<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" value="Property form" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<a class="back-link" href="<c:url value='/listings'/>">← All properties</a>
<section class="form-heading"><p class="eyebrow">Property management</p>
    <h1><c:choose><c:when test="${empty listingForm.id}">Add a property</c:when><c:otherwise>Edit property</c:otherwise></c:choose></h1>
    <p class="muted">Keep details clear. All fields are required.</p></section>
<%@ include file="../fragments/messages.jspf" %>
<c:url var="saveUrl" value="/listings/save"/>
<form:form class="panel form-panel" modelAttribute="listingForm" action="${saveUrl}" method="post" htmlEscape="true">
    <form:hidden path="id"/>
    <form:errors path="*" cssClass="field-error" element="p"/>
    <div class="form-grid">
        <div class="field"><label for="title">Title</label><form:input path="title" id="title" required="required" maxlength="120" placeholder="A quiet family home"/></div>
        <div class="field"><label for="type">Listing type</label><form:select path="type" id="type"><form:option value="SALE" label="For sale"/><form:option value="RENT" label="For rent"/></form:select></div>
        <div class="field"><label for="city">City</label><form:input path="city" id="city" required="required" maxlength="80"/></div>
        <div class="field"><label for="price">Price in LKR</label><form:input path="price" id="price" type="number" min="0.01" step="0.01" required="required" aria-describedby="price-help"/><small id="price-help">Total sale price, or monthly rent.</small></div>
        <div class="field full"><label for="address">Address</label><form:input path="address" id="address" required="required" maxlength="250"/></div>
        <div class="field full"><label for="description">Description</label><form:textarea path="description" id="description" rows="6" required="required" maxlength="2000"/></div>
    </div>
    <div class="actions"><button type="submit">Save property</button><a class="text-link" href="<c:url value='/listings'/>">Cancel</a></div>
</form:form>
<%@ include file="../fragments/footer.jspf" %>