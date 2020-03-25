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
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function addNew(){
	window.open("../sales/toAddPactAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
			window.open("../sales/toUpdPactAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delPactAction.do?PID="+checkid,"newwindow",		"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid>0){
		document.all.submsg.src="../sales/pactDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
	        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 相关合同列表
          </td>
        </tr>
      </table>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
		  <tr class="title-top" align="center"> 
            <td width="38" >编号 </td>
            <td width="117">合同编号</td>
            <td width="184">合同类型</td>
            <td width="180">客户方代表</td>
            <td width="183">供方代表</td>
            <td width="101">签定日期</td>
            <td width="121">签定地址</td>
          </tr>
		<c:set var="count" value="0"/>
           <logic:iterate id="p" name="usList" >
          <tr class="table-back" onClick="CheckedObj(this,${p.id});"> 
            <td  >${p.id}</td>
            <td >${p.pactcode}</td>
            <td >${p.pacttype}</td>
            <td >${p.cdeputy}</td>
            <td >${p.pdeputy}</td>
            <td >${p.signdate}</td>
            <td >${p.signaddr}</td>
            </tr>
			<c:set var="count" value="${count+1}"/>
		  </logic:iterate>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="40%">
<table  border="0" cellpadding="0" cellspacing="0">
            <tr align="center">
              <td width="60"><a href="javascript:addNew();">新增</a></td>
              <td width="60"><a href="javascript:Update();">修改</a></td>
			  <td width="60"><a href="javascript:Del();">删除</a></td>
            </tr>
          </table> 
          </td>
          <td width="60%" align="right"> <presentation:pagination target="/sales/listPackAction.do"/> 
          </td>
        </tr>
      </table>      
      <table width="60"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="60"><c:if test="${count>0}">
              <a href="javascript:Detail();">
              
                合同详情</a></c:if></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
