<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects />
<form action="<portlet:actionURL />" method="post">
	<p>
		ディレクトリパス指定<input type="submit" value="設定" />
	</p>
	<p>
		現在のディレクトリパス:<br />${logpath}
	</p>
	<p>
		新しいディレクトリパス: <br /> <input type="radio" name="prefix" value="path"
			checked="checked" />ファイルパス<input type="radio" name="prefix"
			value="env" />環境変数<br /> <input type="text" name="logpath"
			style="width: 200px;" />
	</p>
</form>
