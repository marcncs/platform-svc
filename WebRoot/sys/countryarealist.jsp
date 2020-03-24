<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}

	function Update(){
		if(checkid>0){
			location.href("../warehouse/toUpdShipmentBillAction.do?ID="+checkid);
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 国家地区列表</td>
        </tr>
      </table>	  
       <form name="search" method="post" action="../sys/listCountryAreaAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="14%"  align="right">地区名关键字：</td>
            <td width="34%" > <input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}">
              </td>
            <td width="11%"  align="right">&nbsp;</td>
            <td width="41%" class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"> </td>
          </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<!--<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>	-->
		  <td class="SeparatePage" ><pages:pager action="../sys/listCountryAreaAction.do"/></td>
							
		</tr>
	</table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">

          <tr align="center" class="title-top"> 
            <td width="54" >编号</td>
            <td width="253">地区名</td>
            <td width="347">上一级地区名</td>
          </tr>
          <logic:iterate id="c" name="cls" ><tr class="table-back-colorbar" onClick="CheckedObj(this,${c.id});"> 
            <td height="20">${c.id}</td>
            <td>${c.areaname}</td>
            <td>${c.parentname}</td>
          </tr>
          </logic:iterate> 
      </table>
      
	  </td>
  </tr>
</table>
</body>
</html>
