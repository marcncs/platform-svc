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
	
	}


	function Approve(){
		if(checkid>0){
			location.href("toApproveProductIncomeAction.do?ID="+checkid);
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
    <td width="772"> 入库单 </td>
  </tr>
</table>
 <form name="search" method="post" action="../self/waitApproveProductIncomeAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
 
   <tr class="table-back"> 
            <td width="11%"  align="right">是否审阅：</td>
            <td width="28%">${approveselect}</td>      
			<td width="24%" align="right">申请时间：</td>
            <td width="37%"><input type="text" name="BeginDate" value="" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" value="" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
    </tr>
 
</table>
 </form>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="58">编号</td>
            <td width="112">入货仓库</td>
            <td width="259" height="37">采购单</td>
            <td width="128">供应商</td>
            <td width="130">入库日期</td>
            <td width="148">是否提交</td>
            <td width="137">是否审阅</td>
          </tr>
          <logic:present name="arpl">
		   <logic:iterate id="p" name="arpl" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${p.id});"> 
            <td ><a href="../warehouse/productIncomeDetailAction.do?ID=${p.id}">${p.id}</a></td>
            <td>${p.warehousename}</td>
            <td>${p.pbid}</td>
            <td>${p.providename}</td>
            <td>${p.incomedate}</td>
            <td>${p.isrefername}</td>
            <td>${p.approvestatusname}</td>
          </tr>
          </logic:iterate> </logic:present> 
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
          <td width="33%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a> 
                </td>
                <td width="60">&nbsp;</td>
                <td width="60">&nbsp;</td>
              </tr>
            </table></td>
          <td width="67%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/self/waitApproveProductIncomeAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

	</td>
  </tr>
</table>

</body>
</html>
