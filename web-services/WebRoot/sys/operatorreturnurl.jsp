<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title></title>
<html:base/>
<script language="javascript">
var bb = setInterval("winClose(1);",300);

	function winClose(isRefrash) 
	{ 
		<c:choose><c:when test="${op=='call'}">
			calledcenter();
			</c:when><c:otherwise>
			gotourl("${gourl}");
			</c:otherwise>
		</c:choose>
		window.returnValue=isRefrash; 
		if (window.opener){
			window.opener.location.reload();
			//window.opener.location.href=window.opener.location.href;
		}
	} 
	
	function gotourl(url){
		location.href(url);
	}
	
function calledcenter(){
	location.href="../sales/calledCenterAction.do?cid=${cid}&tel=${calltel}";
	//window.open("../sales/calledCenterAction.do?cid=${cid}&tel=${calltel}","呼叫中心","height=1000,width=1200,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
}

</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr> 
    <td> <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> <bean:message key="sys.operator.dealresult"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><bean:message bundle="purchase" key="${result}" /></td>
        </tr>
        <tr> 
          <td align="center"> 
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
