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
  <div class="container ">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form method="POST" action="${contextPath}/saveOrUpdate" enctype="multipart/form-data">
            
        <h2 class="text-left">Welcome ${pageContext.request.userPrincipal.name}</h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <h4 class="text-right"><a href="${contextPath}/logout">Logout</a></h4>
        </div>
        
        <div class="starter-template">
			<h1 class="text-center">Guest Book Entry</h1>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="inputType" id="inputType1" value="Text" checked>
  				<label class="form-check-label" for="inputType1">Text</label>
 			</div>
  			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="inputType" id="inputType2" value="Image">
  				<label class="form-check-label" for="inputType2">Image</label>
 			</div>
 			<div class="form-group row" id="Text">
        		<label for="inputGuestBookDesc">Content</label>
        		<textarea class="form-control" id="inputGuestBookDesc" name="guestBookDesc" placeholder="Your opinion" rows="3" required></textarea>
        	</div>
        	<div id="Image" class="form-group row" style="display: none">
          		<label for="inputfile">File input</label>
                <input type="file" class="form-control-file" id="inputfile" name="file" accept="image/*">
            </div>
            &nbsp;
            <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit" value="submit" >Submit</button>
            <button class="btn btn-lg btn-primary btn-block" type="back" name="submit" id="backButton" value="back">Back</button>
			
		</div>
        </form>
    </c:if>
  </div>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script type="text/javascript">
	$(document).ready(function(){
		$("#inputType1").click(function()
		{
			$('#Text').show();
		   	$("#Image").hide();
		   	$('#inputGuestBookDesc').attr('required', 'required');
	   	});
		   
  	 	$("#inputType1").click(function()
  		{
	    	if($('inputType1').prop('checked')===false)
		   	{
		    	$('#Text').hide();
		    	$('#inputGuestBookDesc').removeAttr('required');
		   	}
	  	});

  	 	$("#inputType2").click(function()
		{
			$('#Text').hide();
		   	$("#Image").show();
		   	$('#inputGuestBookDesc').removeAttr('required');
	   	});
		   
  	 	$("#inputType2").click(function()
  		{
	    	if($('inputType2').prop('checked')===false)
		   	{
		    	$('#Image').hide();
		   	}
	  	});

  	 	$("#backButton").click(function()
	 	{
		   	$('#inputGuestBookDesc').removeAttr('required');
		});
	});
</script>
</body>
</html>