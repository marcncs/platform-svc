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
	window.open("toAddSaleOrderAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdSaleOrderAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delSaleOrderAction.do?SOID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../sales/toReferSaleLogAction.do?SaleID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleOrder(){
		if(checkid!=""){
			window.open("../sales/saleOrderAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/saleOrderDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Complete(){
		if(checkid!=""){
			window.open("../sales/completeSaleLogAction.do?slid="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772"> 零售单</td>
  </tr>
</table>
 <form name="search" method="post" action="listSaleOrderAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="9%"  align="right">是否复核：</td>
            <td width="34%">${isauditselect}</td>
            <td width="7%" align="right">是否结案： </td>
            <td width="23%">${isendcaseselect} </td>
            <td width="9%" align="right">是否作废： </td>
            <td width="18%">${isblankoutselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">创建日期：</td>
            <td><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12">
  <input type="submit" name="Submit" value="查询"></td>
            <td align="right"></td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="6%"  >编号</td>
          <td width="22%" >客户名称</td>
          <td width="10%" >客户方订单号</td>
          <td width="15%" >交货日期</td>
          <td width="8%" >总金额</td>
          <td width="8%" >是否复核</td>
          <td width="8%" >是否结案</td>
          <td width="8%" >是否作废</td>
          <td width="8%" >制单人</td>
        </tr>
        <logic:iterate id="s" name="also" >
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');">
            <td >${s.id}</td>
            <td>${s.cname}</td>
            <td>${s.customerbillid}</td>
            <td>${s.consignmentdate}</td>
            <td>${s.totalsum}</td>
            <td>${s.isauditname}</td>
            <td>${s.isendcasename}</td>
            <td>${s.isblankoutname}</td>
            <td>${s.makeidname} </td>
          </tr>
        </logic:iterate>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
                <td width="60"><a href="javascript:SaleOrder();">打印</a></td>
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listSaleOrderByCustomerAction.do"/>	
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
              
                零售单详情</a></c:if></td>
        </tr>
      </table>
      <!--
      <table  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2"> 
          <td width="60"><a href="javascript:IncomeLog();">收款记录</a></td>
        </tr>
      </table>
	  -->
</td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
