<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<title>WINDRP-分销系统</title>
<script language="javascript">
function Audit(osid){
		showloading();
		popWin2("../warehouse/auditOtherShipmentBillAction.do?ID="+osid);
	}
	
	function CancelAudit(osid){
		showloading();
		popWin2("../warehouse/cancelAuditOtherShipmentBillAction.do?ID="+osid);
	}
	
	function Endcase(osid){
		showloading();
		popWin2("../warehouse/endcaseOtherShipmentBillAction.do?id="+osid);
	}
	
	function CancelEndcase(osid){
		showloading();
		popWin2("../warehouse/cancelEndcaseOtherShipmentBillAction.do?id="+osid);
	}
	
	function Ratify(osid){
			window.open("../warehouse/ratifyOtherShipmentBillAction.do?OSID="+osid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelRatify(osid){
			window.open("../warehouse/cancelRatifyOtherShipmentBillAction.do?OSID="+osid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function OtherShipmentBill(ssid){
			window.open("../warehouse/otherShipmentBillAction.do?ID="+ssid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function BlankOut(osid){
		if(window.confirm("您确认要作废所选的记录吗？如果作废将永远不能恢复!")){
			window.open("../warehouse/blankOutOtherShipmentBillAction.do?id="+osid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
	
	function PrintOtherShipmentBill(id){
			popWin4("../warehouse/printOtherShipmentBillAction.do?ID="+id,900,600,"print");
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddOtherShipmentBillIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddOtherShipmentBillIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}	
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="967">盘亏单详情 </td>
          <td width="266" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PrintOtherShipmentBill('${osbf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${osbf.isaudit==0}"><a href="javascript:Audit('${osbf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${osbf.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			  <!--<td width="60" align="center"><c:choose>
                  <c:when test="${osbf.isendcase==0}"><a href="javascript:Endcase('${osbf.id}');">结案</a></c:when>
                  <c:otherwise> <a href="javascript:CancelEndcase('${osbf.id}')">取消结案</a></c:otherwise>
              </c:choose></td>-->
              
            </tr>
          </table></td>
  </tr>
</table>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">出货仓库：</td>
          <td width="25%"><windrp:getname key='warehouse' value='${osbf.warehouseid}' p='d'/></td>
          <td width="9%" align="right">出库类别：</td>
          <td width="23%"><windrp:getname key='ShipmentSort' value='${osbf.shipmentsort}' p='f'/></td>
	      <td width="9%" align="right">需求日期：</td>
	      <td width="25%">${osbf.requiredate}</td>
	  </tr>
		<tr>
		  <td  align="right">备注：</td>
		  <td colspan="5">${osbf.remark}</td>
		  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="9%"  align="right">制单机构：</td>
          <td width="25%"><windrp:getname key='organ' value='${osbf.makeorganid}' p='d'/></td>
          <td width="9%" align="right">制单人：</td>
          <td width="23%"><windrp:getname key='users' value='${osbf.makeid}' p='d'/></td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="25%">${osbf.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${osbf.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${osbf.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${osbf.auditdate}</td>
	    </tr>
	  
		<!--<tr>
	    <td  align="right">是否结案：</td>
	    <td>${osbf.isendcase}</td>
	    <td align="right">结案人：</td>
	    <td>${osbf.endcaseid}</td>
	    <td align="right">结案日期：</td>
	    <td>${osbf.endcasedate}</td>
	    </tr>-->
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>盘亏单产品列表</td>
        </tr>
      </table>
	  </legend>

<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="11%">产品编号</td> 
          <td width="19%" >产品名称</td>
          <td width="21%">规格</td>
          <td width="7%">单位</td>
          <td width="11%">批次</td>
          <!--  
          <td width="4%">序号</td>-->
          <td width="8%">数量</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td>${p.batch}</td>
          <!-- 
           <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${osbf.id}','${p.id}','${osbf.warehouseid}','${osbf.isaudit}')" bitclick="toaddbit('${osbf.id}','${p.id}','${osbf.warehouseid}','${osbf.isaudit}')"/></td>
          -->
          <td>${p.quantity}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
	  

</td>
  </tr>
</table>
</body>
</html>
