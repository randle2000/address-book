<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../includes/header.jsp" />

 <spring:url value="/contacts/add" var="addUrl" />
 <spring:url value="/resources/images/address_book_logo2.gif" var="logoUrl" />


	<div class="container">
	  	<!-- flash box start -->
		<c:if test="${not empty msg}">
		    <div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
                                aria-label="Close">
				<span aria-hidden="true">×</span>
			</button>
			<strong>${msg}</strong>
		    </div>
		</c:if>
	  	<!-- flash box end -->

		<img src="${logoUrl}" alt="Logo" style="width:20%;height:20%;">
		<h1>All contacts</h1>
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#ID</th>
					<th>Name</th>
					<th>Email</th>
					<th>Groups</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="contact" items="${contacts}">
			    <tr>
					<td>
						${contact.contactId}
					</td>
					<td>${contact.name}</td>
					<td>${contact.email}</td>
					<td>
	                	<c:forEach var="groups" items="${contact.groups}" varStatus="loop">
							${groups}
	    					<c:if test="${not loop.last}">,</c:if>
					  	</c:forEach>
	                </td>
					<td>
					  <spring:url value="/contacts/${contact.contactId}" var="showUrl" />
 					  <spring:url value="/contacts/${contact.contactId}/delete" var="deleteUrl" />
 					  <spring:url value="/contacts/${contact.contactId}/update" var="updateUrl" />
					
					  <button class="btn btn-info" onclick="location.href='${showUrl}'">
					  		<span class="glyphicon glyphicon-info-sign"></span> Show
					  </button>
					  <button class="btn btn-primary" onclick="location.href='${updateUrl}'">
					  		<span class="glyphicon glyphicon-ok-sign"></span> Update
					  </button>
					  <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}', {'${_csrf.parameterName}' : '${_csrf.token}'})">
					  		<span class="glyphicon glyphicon-remove-sign"></span> Delete
					  </button>
	                 </td>
			    </tr>
			</c:forEach>
		</table>
		
		<br />
		
		<a href="${addUrl}" class="btn btn-success btn-lg">
   			<span class="glyphicon glyphicon-plus-sign"></span> Add contact 
		</a>

	</div>

	<jsp:include page="../includes/footer.jsp" />
