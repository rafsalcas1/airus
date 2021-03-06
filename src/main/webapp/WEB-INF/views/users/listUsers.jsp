<!DOCTYPE html>
<html lang="es">  
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		
<body>
	<div class="container">
        <h1>Hola usuario</h1>
    </div>
    <div>
        <c:forEach items="${users}" var="user">
            <tr>
			<p>Hola</p>
            	<td>
            		Nombre: <c:out value="${user.nombre}"/>
                </td>
			</tr>	
			</c:forEach>
    </div>
</body>
</html>