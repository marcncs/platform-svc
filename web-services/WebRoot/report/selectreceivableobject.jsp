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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	var shouldreceivable =0;
	function CheckedObj(obj,objid,objname,objshould){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 shouldreceivable = objshould;
	}

	function Affirm(){
		if(checkid>0){
		setCookie("roid",checkid);
		setCookie("objname",checkname);
		setCookie("shouldreceivable",shouldreceivable);
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择客户</td>
        </tr>
      </table>
      <form name="search" method="post" action="../finance/selectReceivableObjectAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="16%"  align="right">付款对象类型：</td>
            <td width="84%">${objectsortselect}
            <input type="submit" name="Submit" value="查询"></td></tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top-lock">
            <td width="5%" >编号</td>
            <td width="11%"> 付款对象类型</td>
            <td width="20%">付款方名称</td>
            <td width="9%">应收款金额</td>
            <td width="9%">实收金额</td>
            <td width="9%">待收金额 </td>
          </tr>
          <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" >
            <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.id}','${s.payeridname}','${s.shouldreceivablesum}');" onDblClick="Affirm();">
              <td >${s.id}</td>
              <td>${s.objectsortname}</td>
              <td>${s.payeridname}</td>
              <td>${s.totalreceivablesum}</td>
              <td>${s.alreadyreceivablesum}</td>
              <td>${s.shouldreceivablesum}</td>
            </tr>
            <c:set var="count" value="${count+1}"/>
          </logic:iterate>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60">取消</td>
              </tr>
            </table></td>
          <td width="70%"> <presentation:pagination target="/finance/selectReceivableObjectAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
