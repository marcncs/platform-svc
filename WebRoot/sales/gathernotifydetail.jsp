<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
function Audit(piid){
			window.open("../sales/endcaseGatherNotifyAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(piid){
			window.open("../sales/cancelEndcaseGatherNotifyAction.do?id="+piid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function PurchaseIncome(piid){
			window.open("../warehouse/purchaseIncomeAction.do?ID="+piid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function toaddidcode(pidid, batch, piid){
			window.open("../aftersale/toAddWithdrawIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function blankout(wid){
		if(window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")){
			window.open("../aftersale/blankoutWithdrawAction.do?id="+wid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
	}
	
	
</script>

</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="925"> 打款通知详情 </td>
          <td width="308" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:PurchaseIncome('${sof.id}');">打印</a></td>
              <td width="60" align="center"><c:choose>
                  <c:when test="${sof.isendcase==0}"><a href="javascript:Audit('${sof.id}');">结案</a></c:when>
                  <c:otherwise> <a href="javascript:CancelAudit('${sof.id}')">取消结案</a></c:otherwise>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">客户名称：</td>
          <td width="27%">${sof.cname}</td>
          <td width="8%" align="right">联系人：</td>
          <td width="22%">${sof.clinkman}</td>
	      <td width="10%" align="right">联系电话：</td>
	      <td width="22%">${sof.tel}</td>
	  </tr>
	  <tr>
	  	<td  align="right">零售人员：</td>
	    <td>${sof.saleidname}</td>
	    <td  align="right">结算方式：</td>
	    <td>${sof.paymentmodename}</td>
	    <td align="right">入账银行账号：</td>
	    <td>${sof.bankaccount}</td>
	    </tr>
	  <tr>
	  	<td  align="right">零售部门：</td>
	    <td>${sof.saledeptname}</td>
	    <td  align="right">相关单据：</td>
	    <td>${sof.billno}</td>
	    <td align="right">金额：</td>
	    <td>${sof.totalsum}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${sof.memo}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">制单人：</td>
          <td width="26%">${sof.makeidname}</td>
          <td width="8%" align="right">制单日期：</td>
          <td width="22%">${sof.makedate}</td>
	      <td width="11%" align="right">&nbsp;</td>
	      <td width="22%">&nbsp;</td>
	  </tr>
	<tr>
	    <td  align="right">是否结案：</td>
	    <td>${sof.isendcasename}</td>
	    <td align="right">结案人：</td>
	    <td>${sof.endcaseidname}</td>
	    <td align="right">结案日期：</td>
	    <td>${sof.endcasedate}</td>
	    </tr>
	  </table>
	</fieldset>
	
	


</td>
  </tr>
</table>
</body>
</html>
