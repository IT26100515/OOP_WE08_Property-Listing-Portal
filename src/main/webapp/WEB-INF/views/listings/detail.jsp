<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Property details" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<a class="back-link" href="<c:url value='/listings'/>">← All properties</a>
<%@ include file="../fragments/messages.jspf" %>
<section class="detail-layout">
    <div class="detail-illustration" aria-hidden="true">⌂</div>
    <article class="panel">
        <span class="badge"><c:out value="${listing.type}"/></span>
        <h1><c:out value="${listing.title}"/></h1>
        <p class="price large"><c:out value="${listing.priceLabel}"/></p>
        <dl><dt>City</dt><dd><c:out value="${listing.city}"/></dd>
            <dt>Address</dt><dd><c:out value="${listing.address}"/></dd></dl>
        <h2>About this property</h2>
        <p class="preserve-lines"><c:out value="${listing.description}"/></p>
        <c:url var="bookUrl" value="/viewings/new"><c:param name="listingId" value="${listing.id}"/></c:url>
        <c:url var="editUrl" value="/listings/${listing.id}/edit"/>
        <div class="actions"><a class="button" href="<c:out value='${bookUrl}'/>">Request viewing</a>
            <a class="button secondary" href="<c:out value='${editUrl}'/>">Edit property</a></div>
    </article>
</section>
<section class="danger-zone">
    <h2>Delete property</h2><p>Deletion is permanent. Remove linked viewings first, including cancelled or completed records.</p>
    <c:url var="deleteUrl" value="/listings/${listing.id}/delete"/>
    <form action="<c:out value='${deleteUrl}'/>" method="post">
        <input type="hidden" name="<c:out value='${_csrf.parameterName}'/>" value="<c:out value='${_csrf.token}'/>">
        <button class="danger" type="submit">Delete property permanently</button>
    </form>
</section>
<%@ include file="../fragments/footer.jspf" %>