<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ include file="../common/tag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head><title>手工上传
</title><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<SCRIPT  src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/billlayout.css" rel="stylesheet" type="text/css" />
<link href="../css/ss.css" rel="stylesheet" type="text/css">
 <script language="javascript">
 	function openUploadidcode(){
 	    var url = "../warehouse/reloadidcodebyfail.jsp";
 		// popWin2("../scanner/uploadidcode.jsp");
 			var x = 500;
			var y = 250;
 			var top = (window.screen.height -y)/2;
	       	var left = (window.screen.width -x)/2;
            var newwin=window.open(url,"PopWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,top="+top+",left="+left+" ,width="+x+",height="+y);
		    newwin.focus();
		    return false;
 	}
 </script>     

</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									数据录入&gt;&gt;手工上传数据
								</td>
							</tr>
	</table>
    <form name="search"  id="form1">

    <div id="bodydiv">
   		
	<table border="0" cellspacing="0" width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1" >
		<tbody>
			
			<tr class="table-back" width="40%">
			    <td>
			     &nbsp;&nbsp;&nbsp;  手工上传（入库、出库，请都在此处操作）
			    </td>
			    <td width="60%">
			       <button type="button" onclick="openUploadidcode();">上传</button>
			    </td>
			</tr>
		</tbody>
	</table>
 
    </div>
    </form>
</body>
</html>