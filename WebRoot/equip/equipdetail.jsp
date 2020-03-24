<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="javascript">
	
	function PrintEquip(id){
		window.open("../equip/printEquipAction.do?ID="+id,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="958"> 配送单详情 </td>
          <td width="275" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <!--<td width="60" align="center"><a href="javascript:PrintEquip('${sof.id}');">打印</a></td>-->
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
	  	<td width="9%"  align="right"> <input name="cid" type="hidden" id="cid" value="${sof.cid}">
	  	   对象名称：</td>
          <td width="28%">${sof.cname}</td>
          <td width="12%" align="right">托运单号：</td>
          <td width="20%">${sof.transportnum}</td>
	      <td width="9%"  align="right">运输方式：</td>
	      <td width="22%"><windrp:getname key='TransportMode' value='${sof.transportmode}' p='d'/></td>
	  </tr>
	  <tr>
	    <td  align="right">联系人：</td>
	    <td>${sof.clinkman}</td>
	    <td align="right">收货地址：</td>
	    <td>${sof.addr}</td>
	    <td height="18" align="right">联系电话：</td>
	    <td>${sof.tel}</td>
	  </tr>
	  <tr>
	    <td height="18" align="right">件数：</td>
	    <td><windrp:format value='${sof.piece}' p="###,##0.00" /></td>
	    <td align="right">货运部：</td>
	    <td><windrp:getname key='Transit' value='${sof.transit}' p='d'/></td>
	    <td align="right">司机：</td>
	    <td colspan="3"><windrp:getname key='users' value='${sof.motorman}' p='d'/></td>
	    </tr>
	  <tr>
	    <td height="18" align="right">车牌：</td>
	    <td>${sof.carbrand}</td>
	    <td align="right">配送日期：</td>
	    <td><windrp:dateformat value='${sof.equipdate}' p='yyyy-MM-dd'/></td>
	    <td align="right"><!--代收总金额：--></td>
	    <td colspan="3"><!--${sof.eratotalsum}--></td>
	    </tr>
		<!-- <tr>
	    <td height="18" align="right">冲加描述：</td>
	    <td>${sof.rushdesc}</td>
	    <td align="right">冲加金额：</td>
	    <td>${sof.rushsum}</td>
	    <td align="right">&nbsp;</td>
	    <td colspan="3">&nbsp;</td>
	    </tr>-->
	  </table>
</fieldset>

 

<fieldset align="center"> <legend>
      <table width="100" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>配送单详情列表</td>
        </tr>
      </table>
	  </legend>
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">         
          <td width="18%" >送货单号</td>
		  <td width="9%">付款方式</td> 
		  <td width="9%">开票信息</td>
		  <td width="9%">单据金额</td> 
         <!-- <td width="17%">应代收金额</td>-->
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td >${p.sbid}</td>
		  <td>${p.paymentmodename}</td> 
		  <td>${p.invmsgname}</td>
		  <td align="right"><windrp:format value="${p.billsum}" /></td> 
         <!-- <td>${p.erasum}</td>-->
        </tr>
        </logic:iterate> 
      </table>
</fieldset>

</td>
  </tr>
</table>
</body>
</html>
