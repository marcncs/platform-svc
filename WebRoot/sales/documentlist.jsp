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
	
	function addNew(){
	window.open("toAddDocumentAction.do","","height=450,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function AllotTask(tpid){
	window.open("../self/allotTaskPlanAction.do?ID="+tpid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
			location.href("updLinkManAction.do?id="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
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
    <td width="772"> 相关文档 </td>
  </tr>
</table>
 <form name="search" method="post" action="listDocumentAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
  <!--<tr class="table-back"> 
      <td width="8%" height="33" align="right">类别：</td>
      <td width="32%">${sortselect}</td>
      <td width="11%" align="right">时间段：</td>
      <td width="49%">从 <input type="text" name="BeginDate" value="${begindate}" onFocus="javascript:selectDate(this)">
        到 <input type="text" name="EndDate" value="${enddate}" onFocus="javascript:selectDate(this)"></td>
    </tr>-->
    <tr class="table-back"> 
      <td width="8%" height="32" align="right">关键字：</td>
      <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
      <td align="right"><INPUT TYPE="hidden" name="Cid" value="${cid}"></td>
      <td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
</table>
 </form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top">
    <td width="181">编号</td>
            <td width="421">文档说明</td>
            <td width="347" >文档名<font color="#FF0000">(下载请点击右键另存为)</font></td>
    <td width="289">更新日期</td>
  </tr>
	<logic:iterate id="c" name="usList" >
  <tr class="table-back" onClick="CheckedObj(this,${c.id});">
    <td>${c.id}</td>
    <td>${c.documentname}</td>
    <td ><img src="../images/CN/beizheng.gif" border="0">
	<a href="../${c.realpathname}">${c.realpathname}</a></td>
    <td>${c.updatedate}</td>
  </tr>
  </logic:iterate>
  
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
<tr> 
                <td width="60" align="center"><a href="javascript:addNew();">上传</a></td>
                <!--<td width="60"><a href="javascript:Update(${l.id});">修改</a></td>-->
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listDocumentAction.do"/>	
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
