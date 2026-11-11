<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Properties" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<section class="page-heading">
    <div><p class="eyebrow">A place to begin</p><h1>Find your next place.</h1>
        <p class="muted">A simple collection of homes for sale and rent.</p></div>
    <a class="button" href="<c:url value='/listings/new'/>">Add property</a>
</section>
<%@ include file="../fragments/messages.jspf" %>
<form class="search-bar" action="<c:url value='/listings'/>" method="get">
    <label class="sr-only" for="q">Search by title, city, or address</label>
    <input id="q" name="q" value="<c:out value='${query}'/>" placeholder="Search title, city, or address">
    <button type="submit">Search</button>
    <a class="text-link" href="<c:url value='/listings'/>">Clear</a>
</form>
<c:if test="${empty listings}">
    <div class="empty-state"><h2>No properties found</h2><p>Add your first property or try a different search.</p></div>
</c:if>
<div class="card-grid">
    <c:forEach var="listing" items="${listings}">
        <c:url var="detailUrl" value="/listings/${listing.id}"/>
        <article class="property-card">
            <div class="card-illustration" aria-hidden="true"><span>⌂</span></div>
            <div class="card-body">
                <span class="badge"><c:out value="${listing.type}"/></span>
                <p class="muted small"><c:out value="${listing.city}"/></p>
                <h2><a href="<c:out value='${detailUrl}'/>"><c:out value="${listing.title}"/></a></h2>
                <p class="price"><c:out value="${listing.priceLabel}"/></p>
                <p class="muted"><c:out value="${listing.address}"/></p>
                <a class="text-link" href="<c:out value='${detailUrl}'/>">View property →</a>
            </div>
        </article>
    </c:forEach>
</div>
<%@ include file="../fragments/footer.jspf" %>