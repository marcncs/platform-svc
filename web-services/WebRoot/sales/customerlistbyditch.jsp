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
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
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
	window.open("../sales/toAddCustomerAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdCustomerAction.do?Cid="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	window.open("customerDetailAction.do?Cid="+checkid,"","height=650,width=950,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	
	function Appoints(){
		if(checkid!=""){
			window.open("../sales/toAppointsCustomerAction.do?CID="+checkid,"newwindow","height=280,width=520,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delCustomerAction.do?CID="+checkid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
    <td width="772"> 客户资料</td>
  </tr>
</table>
  <form name="search" method="post" action="listCustomerByDitchAction.do">
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
      
          <tr class="table-back"> 
            <td width="10%"  align="right">客户分类：</td>
            <td width="24%">${customertypeselect}</td>
            <td width="10%" align="right">客户状态：</td>
            <td width="23%">${customerstatusselect}</td>
            <td width="8%" align="right">关键字： </td>
            <td width="25%"><input type="text" name="KeyWord" value="">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
 </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="6%"   align="center">编号</td>
            <td width="23%"  align="center">客户名称</td>
            <td width="7%" align="center">活跃率</td>
            <td width="15%"  align="center">办公电话</td>
            <td width="12%"  align="center">客户状态</td>
            <td width="11%"  align="center">客户分类</td>
            <td width="9%"  align="center">客户来源</td>
            <td width="17%" align="center">登记日期</td>
          </tr>
		<c:set var="count" value="0"/>
		  <logic:iterate id="c" name="usList" >
		 
          <tr class="table-back" onClick="CheckedObj(this,'${c.cid}','${c.cname}');"> 
            <td ><a href="javascript:Detail();">${c.cid}</a></td>
            <td>${c.cname}</td>
            <td>${c.yauldname}</td>
            <td>${c.officetel}</td>
            <td>${c.customerstatusname}</td>
            <td>${c.customertypename}</td>
            <td>${c.sourcename}</td>
            <td>${c.registdate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Appoints();">指派</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listCustomerByDitchAction.do"/>	
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
