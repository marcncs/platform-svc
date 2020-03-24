<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Receive(smid){
			showloading();
			window.open("../warehouse/completeProductInterconvertReceiveAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelReceive(smid){
			showloading();
			window.open("../warehouse/cancelCompleteProductInterconvertReceiveAction.do?SMID="+smid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddProductInterconvertIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../warehouse/toAddProductInterconvertIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
	}
	function prints(id){
			popWin("../warehouse/printProductInterconvertAction.do?ID="+id,900,600);
	}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="869"> 产品互转单详情 </td>
          <td width="364" align="right"><table   border="0" cellpadding="0" cellspacing="0">
            <tr>
            <!--  <td width="60" align="center"><c:choose>
                  <c:when test="${smf.isaudit==0}"><a href="javascript:Audit('${smf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${smf.id}')">取消复核</a></c:otherwise>
              </c:choose></td>-->
            <!--  <td width="60" align="center"><c:choose>
                  <c:when test="${smf.isshipment==0}"><a href="javascript:Shipment('${smf.id}')">发货</a></c:when>
                  <c:otherwise><a href="javascript:CancelShipment('${smf.id}')">取消发货</a></c:otherwise>
              </c:choose></td>
              -->
               <td width="60" align="center"><a href="javascript:prints('${smf.id}');">打印</a></td>
			  <td width="60" align="center"><c:choose>
                  <c:when test="${smf.iscomplete==0}"><a href="javascript:Receive('${smf.id}')">签收</a></c:when>
                  <c:otherwise><a href="javascript:CancelReceive('${smf.id}')">取消签收</a></c:otherwise>
              </c:choose></td>
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
	  	<td width="9%"  align="right">转换日期：</td>
          <td width="25%"><windrp:dateformat value='${smf.movedate}' p='yyyy-MM-dd'/></td>
          <td width="9%" align="right">转出仓库：</td>
          <td width="23%"><windrp:getname key='warehouse' value='${smf.outwarehouseid}' p='d'/></td>
	      <td width="9%" align="right">转入仓库：</td>
	      <td width="25%"><windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">原因：</td>
	    <td colspan="5">${smf.movecause}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${smf.remark}</td>
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
	    <td  align="right">制单机构：</td>
	    <td><windrp:getname key='organ' value='${smf.makeorganid}' p='d'/></td>
	    <td align="right">制单部门：</td>
	    <td><windrp:getname key='dept' value='${smf.makedeptid}' p='d'/></td>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="25%"><windrp:getname key='users' value='${smf.makeid}' p='d'/></td>
         </tr>
	  <tr>
          <td width="9%" align="right">制单日期：</td>
          <td width="23%">${smf.makedate}</td>
	      <td width="9%" align="right">是否复核：</td>
	      <td width="25%"><windrp:getname key='YesOrNo' value='${smf.isaudit}' p='f'/></td>
	    <td  align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${smf.auditid}' p='d'/></td>
        	  </tr>
	  <tr>
	    <td align="right">复核日期：</td>
	    <td>${smf.auditdate}</td>
	    <td align="right">是否发货：</td>
	    <td><windrp:getname key='YesOrNo' value='${smf.isshipment}' p='f'/></td>
	    <td  align="right">发货人：</td>
	    <td><windrp:getname key='users' value='${smf.shipmentid}' p='d'/></td>
        	  </tr>
	  <tr>
	    <td align="right">发货日期：</td>
	    <td>${smf.shipmentdate}</td>
	    <td align="right">是否签收：</td>
	    <td><windrp:getname key='YesOrNo' value='${smf.iscomplete}' p='f'/></td>

	    <td  align="right">签收人：</td>
	    <td><windrp:getname key='users' value='${smf.receiveid}' p='d'/></td>
        	  </tr>
	  <tr>
	    <td align="right">签收日期：</td>
	    <td>${smf.receivedate}</td>
	    <td  align="right"></td>
	    <td></td>
        <td  align="right"></td>
	    <td></td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>调拨单产品列表</td>
        </tr>
      </table>
	  </legend>

      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td>
          <td width="22%" >产品名称</td>
          <td width="29%">规格</td>
          <td width="12%">批次</td>
          <td width="8%">单位</td>
          <td width="7%">序号</td>
          <td width="9%">数量</td>
          </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back-colorbar">
            <td>${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
             <td>${p.batch}</td>
            <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
            <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${smf.id}','${p.id}','${smf.inwarehouseid}','${smf.iscomplete}')" bitclick="toaddbit('${smf.id}','${p.id}','${smf.inwarehouseid}','${smf.iscomplete}')"/></td>
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
