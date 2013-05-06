<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<portlet:defineObjects />
<form>
	<p>ログファイルパス: ${logpath}</p>
	<table>
		<c:forEach var="fileObject" items="${fileObjects}">
			<tr>
				<portlet:resourceURL var="resourceURL">
					<portlet:param name="filepath"
						value="${fileObject.file.absolutePath}" />
				</portlet:resourceURL>
				<td><a href="${resourceURL} }"><c:out
							value="${fileObject.file.absolutePath}" /></a></td>
			</tr>
		</c:forEach>
	</table>
</form>
