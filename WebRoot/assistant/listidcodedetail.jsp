<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script type="text/javascript" src="../javascripts/capxous.js"></script>  
<link rel="stylesheet" type="text/css" href="../styles/capxous.css" /> 
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 OrderDetail();
	}
	
function SelectCustomer(){
	var c=showModalDialog("../sales/toSelectSaleOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	document.search.CID.value=c.cid;
	document.search.cname.value=c.cname;
}

function OrderDetail(){
	if(checkid!=""){
	document.all.submsg.src="../assistant/idcodeDetailAction.do?ID="+checkid;
	}else{
	alert("请选择你要操作的记录!");
	}
}

function ToBill(bc,billcode){
	 switch(bc){
		case 0:window.open("../warehouse/purchaseIncomeDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 1:window.open("../warehouse/otherIncomeDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 2:window.open("../warehouse/takeTicketDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 3:window.open("../aftersale/withdrawDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 4:window.open("../sales/vocationOrderDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 5:window.open("../aftersale/purchaseTradesDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 6:window.open("../warehouse/stockAlterMoveDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no"); break;
		case 7:window.open("../warehouse/stockMoveDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");break;	
		case 8:window.open("../assistant/idcodeResetDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");break;	
		case 9:window.open("../sales/peddleOrderDetailAction.do?ID="+billcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");break;	
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
    <td width="772">序号追踪记录</td>
  </tr>
</table>
 <form name="search" method="post" action="listIdcodeDetailAction.do">
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="8%"  align="right">制单机构：</td>
            <td width="24%"><select name="MakeOrganID" id="MakeOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">配送机构：</td>
            <td width="24%"><select name="EquipOrganID" id="EquipOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
			<td width="11%" align="right">序号：</td>
            <td width="25%"><input type="text" name="IDCode"></td>
          </tr>
		   <tr class="table-back"> 
            <td width="8%" align="right">会员：</td>
            <td width="24%"><input name="CID" type="hidden" id="CID" value="">
              <input name="cname" type="text" id="cname" value="" readonly>
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
            <td width="8%" align="right">操作日期：</td>
            <td width="24%"><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
<input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12"></td>
			<td width="11%" align="right">仓库：</td>
            <td width="25%"><select name="WarehouseID" id="WarehouseID">
              <option value="" selected>所有仓库</option>
              <logic:iterate id="w" name="wls" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
          </tr>
		   <tr class="table-back"> 
            <td width="8%" align="right">关键字：</td>
            <td width="24%"><input name="KeyWord" type="text" id="KeyWord" >
              <input name="submit" type="submit" value="查询"></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="24%">&nbsp;</td>
            <td width="11%" align="right">&nbsp;</td>
            <td width="25%">&nbsp;</td>
		   </tr>
       
      </table>
       </form>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="15%">序号</td>
			<td width="10%">产品编号</td>
            <td width="10%">产品名称</td>
            <td width="10%">制单机构</td>
			<td width="10%">配送机构</td>
			<td width="10%">客户</td>
            <td width="8%">操作人</td>
            <td width="12%">操作时间</td>
			<td width="10%">单据类型</td>
			<td width="12%">详情</td>
          </tr>
		  <logic:iterate id="ms" name="list" >
		 
          <tr align="center" class="table-back" onClick="CheckedObj(this,${ms.id});"> 
            <td >${ms.id}</td>
			<td>${ms.idcode}</td>
            <td>${ms.productid}</td>
            <td>${ms.productname}</td>
            <td>${ms.makeorganidname}</td>
			<td>${ms.equiporganidname}</td>
			<td>${ms.cname}</td>
            <td>${ms.makeuser}</td>
            <td>${ms.makedate}</td>
			<td>${ms.idbilltypename}</td>
			<td><a href="javascript:ToBill(${ms.idbilltype},'${ms.billid}');">${ms.billid}</a></td>
          </tr>
          </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><!--<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="../sales/toAddCustomerAction.do">新增</a> 
                </td>
                <td width="60"><a href="javascript:Update(${c.cid});">修改</a></td>
                <td width="60">移交</td>
                <td width="60">共享</td>
              </tr>
            </table>--></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >
          <tr> 		  
            <td width="50%" align="right">
				<presentation:pagination target="/assistant/listIdcodeDetailAction.do"/>	
            </td>
          </tr>       
      </table></td>
  </tr>
</table>
	<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:OrderDetail();">详情</a></td>
    </tr>
</table>
</td>
</tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%;" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</html>
