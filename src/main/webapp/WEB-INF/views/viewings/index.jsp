<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Viewings" scope="request"/>
<%@ include file="../fragments/header.jspf" %>
<section class="page-heading"><div><p class="eyebrow">Make time to explore</p><h1>Your viewings.</h1>
    <p class="muted">Manage requests, confirm plans, and keep appointments organised.</p></div>
    <a class="button" href="<c:url value='/viewings/new'/>">Request viewing</a></section>
<%@ include file="../fragments/messages.jspf" %>
<p class="small muted">All times are in Asia/Colombo. Viewing slots last 30 minutes.</p>
<c:choose>
    <c:when test="${empty viewings}"><div class="empty-state"><h2>No viewings yet</h2><p>Choose a property and request your first viewing.</p></div></c:when>
    <c:otherwise><div class="table-wrap"><table><thead><tr><th>Property</th><th>Customer</th><th>Date &amp; time</th><th>Status</th><th>Details</th></tr></thead><tbody>
        <c:forEach var="viewing" items="${viewings}">
            <c:url var="detailUrl" value="/viewings/${viewing.id}"/>
            <tr><td><c:out value="${listingTitles[viewing.listingId]}"/></td><td><c:out value="${viewing.customerName}"/></td>
                <td><c:out value="${viewing.scheduledAt}"/></td><td><span class="badge"><c:out value="${viewing.status}"/></span></td>
                <td><a class="text-link" href="<c:out value='${detailUrl}'/>">Open →</a></td></tr>
        </c:forEach>
    </tbody></table></div></c:otherwise>
</c:choose>
<%@ include file="../fragments/footer.jspf" %>