<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(scid){
		showloading();
		popWin2("../warehouse/auditStockCheckAction.do?SCID="+scid);
	}
	
	function CancelAudit(scid){
		showloading();
		popWin2("../warehouse/cancelAuditStockCheckAction.do?SCID="+scid);
	}
	
	function Create(scid, isbar){
		showloading();
		if ( isbar=='0' ){
			popWin2("../warehouse/stockCheckCreateBillAction.do?SCID="+scid);
		}else{
			popWin2("../warehouse/stockCheckBarCreateBillAction.do?SCID="+scid);
		}
	}
	
	function StockCheckEmpty(scid){
			window.open("../warehouse/printStockCheckEmptyAction.do?ID="+scid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="869"> 盘点单详情 </td>
          <td width="364" align="right"><table width="220"  border="0" cellpadding="0" cellspacing="0">
            <tr>
			<td width="60" align="center"><a href="javascript:StockCheckEmpty('${scf.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${scf.isaudit==0}"><a href="javascript:Audit('${scf.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${scf.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
              <td width="100" align="center"><c:choose>
                  <c:when test="${scf.iscreate==0}"><a href="javascript:Create('${scf.id}','${scf.isbar}')">生成入库出库</a></c:when>
                  <c:otherwise>已生成入库出库</c:otherwise>
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
	  	<td width="11%"  align="right">盘点仓库：</td>
          <td width="23%"><windrp:getname key='warehouse' value='${scf.warehouseid}' p='d'/></td>
          <td width="9%" align="right">盘点仓位：</td>
          <td width="23%">${scf.warehousebit}</td>
	      <td width="9%" align="right">&nbsp;</td>
	      <td width="25%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${scf.memo}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0" >
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">制单机构：</td>
          <td width="23%"><windrp:getname key='organ' value='${scf.makeorganid}' p='d'/></td>
          <td width="9%" align="right">制单人：</td>
          <td width="23%"><windrp:getname key='users' value='${scf.makeid}' p='d'/></td>
	      <td width="9%" align="right">制单日期：</td>
	      <td width="25%">${scf.makedate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否复核：</td>
	    <td><windrp:getname key='YesOrNo' value='${scf.isaudit}' p='f'/></td>
	    <td align="right">复核人：</td>
	    <td><windrp:getname key='users' value='${scf.auditid}' p='d'/></td>
	    <td align="right">复核日期：</td>
	    <td>${scf.auditdate}</td>
	    </tr>
	  <tr>
	    <td  align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="90" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>盘点单产品列表</td>
        </tr>
      </table>
	  </legend>
      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="13%">产品编号</td>
          <td width="25%" >产品名称</td>
          <td width="20%">规格</td>
          <td width="6%">单位</td>
          <td width="8%">仓位</td>
          <td width="10%">批次</td>
          <td width="10%">账面数量</td>
          <td width="10%">盘点数量</td>
        </tr>
        <logic:iterate id="p" name="als" >
          <tr class="table-back-colorbar">
            <td >${p.productid}</td>
            <td  >${p.productname}</td>
            <td>${p.specmode}</td>
            <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
            <td>${p.warehousebit}</td>
            <td>${p.batch}</td>
            <td>${p.reckonquantity}</td>
            <td>${p.checkquantity}</td>
          </tr>
        </logic:iterate>
      </table>
	  </fieldset>
      
</td>
  </tr>
</table>
</body>
</html>
