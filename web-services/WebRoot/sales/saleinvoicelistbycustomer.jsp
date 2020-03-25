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
	Detail();
	}
	
	function addNew(){
	window.open("toAddSaleInvoiceAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid>0){
		window.open("toUpdSaleInvoiceAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid>0){
		document.all.submsg.src="../sales/saleInvoiceDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delSaleInvoiceAction.do?SIID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772"> 零售发票</td>
  </tr>
</table>
<form name="search" method="post" action="listSaleInvoiceByCustomerAction.do">
      <table width="100%" height="56"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="12%"  align="right"><input name="CID" type="hidden" id="CID" value="${cid}">
            发票类型：</td>
            <td width="37%">${invoicetypeselect}</td>
            <td width="11%" align="right">创建日期： </td>
            <td width="40%"><input type="text" name="BeginDate" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" onFocus="javascript:selectDate(this)"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否复核：</td>
            <td>${isauditselect}</td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value=""  onKeyDown="if(event.keyCode==13)return false;">
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
      
      </table>
  </form>
   <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="7%"  >编号</td>
            <td width="17%">发票编号</td>
            <td width="21%" >发票类型</td>
            <td width="21%" >制票日期</td>
            <td width="16%" >开票日期</td>
            <td width="11%" >总金额</td>
            <td width="7%" >是否复核</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alsi" > 
          <tr class="table-back" onClick="CheckedObj(this,${s.id});"> 
            <td >${s.id}</td>
            <td>${s.invoicecode}</td>
            <td>${s.invoicetypename}</td>
            <td>${s.makeinvoicedate}</td>
            <td>${s.invoicedate}</td>
            <td>${s.invoicesum}</td>
            <td>${s.isauditname}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate>
        
      </table>
      </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
                <td width="60"><a href="javascript:SaleBill();">打印</a></td>
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listSaleInvoiceByCustomerAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>
<table width="80"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="80"><c:if test="${count>0}">
        <a href="javascript:Detail();">
       
          零售发票详情</a> </c:if></td>
  </tr>
</table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
