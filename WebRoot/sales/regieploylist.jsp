<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}


	function Update(){
		if(checkid>0){
			location.href("toUpdRegiePloyAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delRegiePloyAction.do?RPID="+checkid,"newwindow",		"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
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
    <td width="772"> 专卖店活动 </td>
  </tr>
</table>
<form name="search" method="post" action="listRegiePloyAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
    <tr class="table-back"> 
      <td width="8%" height="32" align="right">关键字：</td>
      <td width="32%"><input type="text" name="KeyWord" value=""> <input type="submit" name="Submit" value="查询"></td>
      <td width="11%" align="right">&nbsp;</td>
      <td width="49%">&nbsp; </td>
    </tr>

</table>
  </form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td >编号</td>
    <td>活动类型</td>
    <td>活动内容</td>
    <td>活动日期</td>
    <td>记录人员</td>
    </tr>
			
	<logic:iterate id="l" name="rpls" >
  <tr align="center" class="table-back" onClick="CheckedObj(this,${l.id});">
    <td ><a href="regiePloyDetailAction.do?ID=${l.id}" target="_self">${l.id}</a></td>
    <td>${l.ploytypename}</td>
    <td>${l.ploycontent}</td>
    <td>${l.ploydate}</td>
    <td>${l.makeidname}</td>
    </tr>
  </logic:iterate>
 
</table>
 </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="../sales/toAddRegiePloyAction.do" >新增</a>                </td>
                <td width="60"><a href="javascript:Update();" >修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listLinkManAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

	</td>
  </tr>
</table>

</body>
</html>
