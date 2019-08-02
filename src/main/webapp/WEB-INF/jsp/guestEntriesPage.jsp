<%@page import="java.util.List"%>
<%@page import="com.guestbook.model.GuestRole"%>
<%@page import="com.guestbook.model.GuestUser"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Guest Book</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
  <div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutNewForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <h4 class="text-right"><a href="${contextPath}/logout">Logout</a></h4>
        </div>
        
        <div class="starter-template">
			<h1>Guest Book List</h1>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Content</th>
						<th>Status</th>
						<%
						List<String> guestRole = (List<String>)request.getAttribute("roleInfo");
						if(guestRole.get(0).equals("ROLE_ADMIN")){
						%>
						<th>Created User</th>
						<th>Approve</th>
						<th>Reject</th>
						<th>Delete</th>
						<%} %>
					</tr>
					<c:forEach var="guestBookEntries" items="${guestBookEntries}">
					<tr>
						<td>${guestBookEntries.guestBookDesc}</td>
						<td>
						<c:set var="fieldStatus" value="${guestBookEntries.status}"/>
						<c:set var="fieldStatusValue1" value="${0}"/>
						<c:set var="fieldStatusValue2" value="${1}"/>
						
						<c:set var="userInfo" value="${guestBookEntries.guestUser}"/>
						
						<c:choose>
						<c:when test="${fieldStatus == fieldStatusValue1}">Pending</c:when>
						<c:when test="${fieldStatus == fieldStatusValue2}">Approved</c:when>
						<c:otherwise>Rejected</c:otherwise>
						</c:choose>
						</td>
						
						<%
						if(guestRole.get(0).equals("ROLE_ADMIN")){
						%>
						<td>${userInfo.userName}</td>
						<td>
							<a href="${contextPath}/approve/${guestBookEntries.guestBookId}">Approve</a>&nbsp;
						</td>
						<td>
							<a href="${contextPath}/reject/${guestBookEntries.guestBookId}">Reject</a>&nbsp;
						</td>
						<td>
							<a href="${contextPath}/delete/${guestBookEntries.guestBookId}">Delete</a>&nbsp;
						</td>
						<%} %>
					</tr>
					</c:forEach>
				</thead>
			</table>
			<br/>
    		<a class="btn btn-primary" href="${contextPath}/addEntry">Add Guest Entry</a>
		</div>
        
    </c:if>
  </div>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>