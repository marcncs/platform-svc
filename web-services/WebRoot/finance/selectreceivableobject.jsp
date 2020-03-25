<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

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
		if(checkid!=""){
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

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
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
            <td width="12%"  align="right">付款对象类型：</td>
            <td width="17%"><windrp:select key="ObjectSort" name="ObjectSort" p="y|f" value="${ObjectSort}"/>            </td>
            <td width="10%" align="right">所属机构：</td>
            <td width="22%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="10%" align="right">关键字：</td>
            <td width="29%"><input type="text" name="KeyWord" value="">
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top">
            <td width="5%" >编号</td>
            <td width="11%"> 付款对象类型</td>
            <td width="20%">付款方名称</td>
            <td width="20%">所属机构</td>
            <!--  <td width="9%">应收款金额</td>
            <td width="9%">实收金额</td>
            <td width="9%">待收金额 </td> -->
          </tr>
          <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" >
            <tr align="center" class="table-back" onClick="CheckedObj(this,'${s.oid}','${s.payer}','${s.waitreceivablesum}');" onDblClick="Affirm();">
              <td  align="left">${s.oid}</td>
              <td align="left">${s.objectsortname}</td>
              <td align="left">${s.payer}</td>
              <td align="left">${s.makeorganidname}</td>
              <!-- <td></td>
              <td></td>
              <td></td>-->
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
                <td width="60"><a href="javascript:window.close();">取消</a></td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/finance/selectReceivableObjectAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
