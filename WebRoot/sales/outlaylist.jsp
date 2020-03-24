<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ page import="com.winsafe.hbm.util.Internation,com.winsafe.drp.dao.Outlay,java.util.*"%>
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
	OutlayDetail();
	}
	
	function addNew(){
	window.open("../sales/toAddOutlayAction.do","","height=550,width=850,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Refer(){
		if(checkid!=""){
		window.open("../sales/toReferOutlayAction.do?OID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Update(){
		if(checkid!=""){
			window.open("toUpdOutlayAction.do?ID="+checkid,"","height=550,width=850,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function IsCreate(){
		if(checkid!=""){
		window.open("../sales/makePayableAction.do?OID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delOutlayAction.do?OID="+checkid,"newwindow",		"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function OutlayDetail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/outlayDetailAction.do?ID="+checkid;
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
    <td width="772"> 费用记录</td>
  </tr>
</table>
 <form name="search" method="post" action="listOutLayAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
 
   <tr class="table-back"> 
            <td  align="right">是否初审：</td>
            <td>${isauditselect}</td>      
			<td align="right">是否复审：</td>
            <td width="20%">${isaudittwoselect}</td>
            <td width="9%" align="right">是否终审：</td>
            <td width="24%">${isauditendselect}</td>
   </tr>
  <tr class="table-back"> 
	        <td width="12%"  align="right">是否提交：</td>
            <td width="23%">${isreferselect}</td>
		    <td width="12%" align="right">是否生成收付款：</td>
            <td>${iscreateselect}
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
  </tr>

</table>
  </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="4%">编号</td>
            <td width="18%" >客户</td>
            <td width="13%">核算部门</td>
            <td width="10%">总费用</td>
            <td width="8%">是否初审</td>
            <td width="8%">是否复审</td>
            <td width="8%">是否终审</td>
            <td width="8%">是否提交</td>
            <td width="12%">是否生成收付款</td>
          </tr>
          <logic:present name="usList"> 
		  <logic:iterate id="o" name="usList" > 
          <tr class="table-back" onClick="CheckedObj(this,'${o.id}');"> 
            <td ><a href="javascript:Update();">${o.id}</a></td>
            <td>${o.customeridname}</td>
            <td>${o.castdeptname}</td>
            <td>${o.totaloutlay}</td>
            <td>${o.isauditname}</td>
            <td>${o.isaudittwoname}</td>
            <td>${o.isauditendname}</td>
            <td>${o.isrefername}</td>
            <td>${o.iscreatename}</td>
          </tr>
          </logic:iterate> </logic:present> 
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
          <td width="38%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:addNew();">新增</a> </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Refer();">提交</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
                <td width="80"><a href="javascript:IsCreate();">生成应付</a></td>
              </tr>
            </table></td>
          <td width="62%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listOutLayAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

	<table width="62" border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="60" ><a href="javascript:OutlayDetail();">费用详情</a></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
