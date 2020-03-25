<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script type="text/javascript" src="../javascripts/capxous.js"></script>  
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
	 AfficheDetail();
	}

	function AfficheDetail(){
		if(checkid>0){
		document.all.submsg.src="afficheDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  	<tr> 
    	<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    	<td width="772">公告</td>
  	</tr>
	</table>
	
  <form name="search" method="post" action="listAfficheAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
      
          <tr class="table-back"> 
            <td width="13%"  align="right">公告类型：</td>
            <td width="29%">${afficheType}</td>
            <td width="13%" align="right">发布日期：</td>
            <td width="45%"><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);"> 
            - 
              <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">公告内容：</td>
            <td><input type="text" name="KeyWord" value="">
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="68" >编号</td>
            <td width="200">公告主题</td>
            <td width="201">发布人</td>
            <td width="408">公告内容</td>
            <td width="234">发布日期</td>
            <td width="131">公告类型</td>
          </tr>
		  <logic:iterate id="af" name="aaList" >
		 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${af.id});"> 
            <td >${af.id}
              <c:if test="${af.isbrowse==0}"><font color="#FF0000" size="-4">新</font></c:if></td>
            <td>${af.affichetitle}</td>
            <td>${af.publishmanname}</td>
            <td>${af.affichecontent}</td>
            <td>${af.publishdate}</td>
            <td>${af.affichetype}${af.isbrowse}</td>
          </tr>
          </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%">&nbsp;</td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/self/listAfficheAction.do"/>	
            </td>
          </tr>
       
      </table>
	  </td>
  </tr>
</table>
<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:AfficheDetail();">公告详情</a></td>
    </tr>
</table></td>
</tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
	
</html>
