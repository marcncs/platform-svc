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
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight-5)+"px";
}

var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function SelectUsers(){
		var u=showModalDialog("../common/selectUsersAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if(u==undefined){return;}
		document.search.UserID.value=u.uid;
		document.search.UserName.value=u.uname;
	}
	
	function excput(){
	search.action="../sys/excPutUserLogAction.do?type=nccode";
	search.submit();
	search.action="../sys/listUserLogAction.do";
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">系统设置>>日志查询</td>
        </tr>
      </table>
       <form name="search" method="post" action="../sys/listUserLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="13%"  align="right">操作用户：</td>
            <td width="35%"><input name="UserID" type="hidden" id="UserID" value="${UserID}">
            <input name="UserName" type="text" id="UserName" value="${UserName}"><a 
            href="javascript:SelectUsers();"><img src="../images/CN/find.gif" width="18" align="absmiddle" height="18" border="0"></a></td>
            <td width="11%" align="right">日志日期：</td>
            <td width="41%"><input id="BeginDate" onFocus="javascript:selectDate(this)" size="12" name="BeginDate" value="${begindate}" readonly="readonly">
-
  <input id="EndDate" onFocus="javascript:selectDate(this)" size="12" name="EndDate" value="${enddate}" readonly="readonly"></td>
  			<td >&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">功能模块：</td>
            <td><windrp:select key="ModelType" name="ModelType" p="y|f" value="${ModelType}"/></td>
            <td align="right">关键字：</td>
			<td ><input name="KeyWord" type="text" id="KeyWord" value="${keyword}"></td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" >
				<a href="#" onClick="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>				
		  <td class="SeparatePage"><pages:pager action="../sys/listNcLogAction.do"/></td>
							
		</tr>
	 </table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%"  cellpadding="0" cellspacing="1" >
        
          <tr align="center" class="title-top-lock"> 
            <td width="9%" >操作用户</td>
            <td width="19%">日志日期</td>
			<td width="13%">模块</td>
            <td width="26%">日志内容</td>
			<td width="33%" >详情</td>
          </tr>
          <logic:iterate id="u" name="uls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
            <td height="20"><windrp:getname key='users' value='${u.userid}' p='d'/></td>
            <td>${u.logtime}</td>
            <td><windrp:getname key='ModelType' value='${u.modeltype}' p='f'/></td>
			<td>${u.detail}</td>
			<td title='${u.modifycontent}'>${u.modifycontent}</td>
            </tr>
          </logic:iterate> 
       
      </table>    
       </form>
	  </div> 
    </td>
  </tr>
</table>
</body>
</html>
