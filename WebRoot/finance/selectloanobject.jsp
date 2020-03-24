<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";

	function CheckedObj(obj,objid,objname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;

	}

	function Affirm(){
		if(checkid!=""){
		setCookie("uid",checkid);
		setCookie("uname",checkname);

		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择用户</td>
        </tr>
      </table>
       <form name="search" method="post" action="../finance/selectLoanObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="13%"  align="right">用户编号：</td>
            <td width="22%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="15%" >&nbsp;</td>
            <td width="24%" >&nbsp;</td>
            <td width="26%" align="right" ><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager action="../finance/selectLoanObjectAction.do"/></td>
        </tr>
      </table>
	   </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top">
            <td width="5%" >编号</td>
            <td width="11%">用户编号</td>
            <td width="20%">用户名称</td>
          </tr>
          <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" >
            <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.uid}','${s.uidname}');" onDblClick="Affirm();">
              <td >${s.id}</td>
              <td>${s.uid}</td>
              <td>${s.uidname}</td>
            </tr>
            <c:set var="count" value="${count+1}"/>
          </logic:iterate>
        
      </table>
      </form>
	  </div>
    </td>
  </tr>
</table>
</body>
</html>
