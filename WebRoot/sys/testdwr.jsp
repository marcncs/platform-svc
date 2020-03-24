<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<HTML>
<HEAD>
<TITLE> New Document </TITLE>
<script src='../dwr/interface/listAwakeDWR.js'></script>
<script src='../dwr/engine.js'></script>
<script src='../dwr/util.js'></script>
<script src='../js/prototype.js'></script>
<script>
function abc(){						//显示一条提醒 
  alert("bbb");
		listAwakeDWR.getAwake(fillTable1,1);
	alert("ccc");
	}

	function fillTable1(awake)
	{
		alert("111");
		}
</script>
<body onload="abc();">
</BODY>
</HTML>
