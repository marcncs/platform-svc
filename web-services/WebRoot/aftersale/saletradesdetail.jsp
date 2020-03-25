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
	function Audit(piid){
		showloading();
		popWin2("../aftersale/auditSaleTradesAction.do?id="+piid);
	}
	
	function CancelAudit(piid){
		showloading();
		popWin2("../aftersale/cancelAuditSaleTradesAction.do?id="+piid);
	}
	
	function Endcase(piid){
		showloading();
		popWin2("../aftersale/endcaseSaleTradesAction.do?ID="+piid);
	}
	
	function CancelEndcase(piid){
		showloading();
		popWin2("../aftersale/cancelEndcaseSaleTradesAction.do?ID="+piid);
	}
	
	function print(piid){
			popWin("../aftersale/printSaleTradesAction.do?ID="+piid,900,600);
	}

	function toaddidcode(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddSaleTradesIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin("../aftersale/toAddSaleTradesIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600);
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
          <td width="925"> 零售换货详情 </td>
          <td width="308" align="right"><table width="240"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:print('${sof.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isaudit==0}"><a href="javascript:Audit('${sof.id}');">复核</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sof.id}')">取消复核</a></c:otherwise>
              </c:choose></td>
			   <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isendcase==0}"><a href="javascript:Endcase('${sof.id}');">发货</a></c:when>
                  <c:otherwise> <a href="javascript:CancelEndcase('${sof.id}')">取消发货</a></c:otherwise>
              </c:choose></td>
			  <td width="60" align="center">
                  <c:choose>
                    <c:when test="${sof.isblankout==0}">作废</c:when>
                    <c:otherwise>
                        <font color="#FF0000">已作废</font>
                    </c:otherwise>
                </c:choose>
                </td>
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
	  	<td width="11%"  align="right">客户名称：</td>
          <td width="24%">${sof.cname}</td>
          <td width="12%" align="right">联系人：</td>
          <td width="21%">${sof.clinkman}</td>
	      <td width="12%" align="right">联系电话：</td>
	      <td width="20%">${sof.tel}</td>
	  </tr>
	  <tr>
	  	<td  align="right">入货仓库：</td>
	    <td><windrp:getname key='warehouse' value='${sof.warehouseinid}' p='d'/></td>
	    <td  align="right">出货仓库：</td>
	    <td><windrp:getname key='warehouse' value='${sof.warehouseid}' p='d'/></td>
	    <td align="right">预计取货日期：</td>
	    <td><windrp:dateformat value='${sof.tradesdate}' p='yyyy-MM-dd'/></td>
	    </tr>
      <tr>
	  	<td  align="right">换货类型：</td>
	    <td><windrp:getname key='TradesSort' value='${sof.tradessort}' p='f'/></td>
	    <td  align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td></td>
	    </tr>
	  <tr>
	    <td  align="right">送货地址：</td>
	    <td colspan="5">${sof.sendaddr}</td>
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
	    <td><windrp:getname key='organ' value='${sof.makeorganid}' p='d'/></td>
	    <td align="right">制单人：</td>
	    <td><windrp:getname key='users' value='${sof.makeid}' p='d'/> </td>
	    <td align="right">制单日期：</td>
	    <td>${sof.makedate}</td>
	    </tr>
	  <tr>
	  	<td width="11%"  align="right">是否复核：</td>
          <td width="26%"><windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f'/></td>
          <td width="10%" align="right">复核人：</td>
          <td width="20%"><windrp:getname key='users' value='${sof.auditid}' p='d'/></td>
	      <td width="11%" align="right">复核日期：</td>
	      <td width="22%">${sof.auditdate}</td>
	  </tr>
	  <tr>
	    <td  align="right">是否发货：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isendcase}' p='f'/></td>
	    <td align="right">发货人：</td>
	    <td><windrp:getname key='users' value='${sof.endcaseid}' p='d'/></td>
	    <td align="right">发货日期：</td>
	    <td>${sof.endcasedate}</td>
	    </tr>
		<tr>
	    <td  align="right">是否作废：</td>
	    <td><windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f'/></td>
	    <td align="right">作废人：</td>
	    <td><windrp:getname key='users' value='${sof.blankoutid}' p='d'/></td>
	    <td align="right">作废日期：</td>
	    <td>${sof.blankoutdate}</td>
	    </tr>
	<tr>
	    <td  align="right">作废原因：</td>
	    <td colspan="5">${sof.blankoutreason}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="110" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>换货物品清单列表</td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="9%">产品编号</td> 
          <td width="20%">产品名称</td>
          <td width="14%">规格</td>
          <td width="9%">单位</td>
          <td width="4%">序号</td>
          <td width="8%">数量</td>
		   <td width="8%">检货数量</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
         <td><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${sof.id}','${p.id}','${sof.warehouseinid}','${sof.isendcase}')" bitclick="toaddbit('${sof.id}','${p.id}','${sof.warehouseinid}','${sof.isendcase}')"/></td>
          <td>${p.quantity}</td>
		  <td>${p.takequantity}</td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>


</td>
  </tr>
</table>
</body>
</html>
