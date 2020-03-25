<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script type="text/javascript" src="../javascripts/capxous.js"></script>  
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link rel="stylesheet" type="text/css" href="../styles/capxous.css" /> 
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}




</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  	<tr> 
    	<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    	<td width="772">任务执行者</td>
  	</tr>
	</table>
	

      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="18%"  >执行者</td>
            <td width="82%" >是否收到任务</td>
          </tr>
		  <logic:iterate id="ab" name="aaList" >
		 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${ab.id});"> 
            <td >${ab.useridname}</td>
            <td>${ab.isaffirmname}</td>
            </tr>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%">&nbsp;</td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
            </td>
          </tr>
       
      </table>
	  </td>
  </tr>
</table>
    </td>
  </tr>
</table>
</html>
