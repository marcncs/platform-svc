<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
function SelectLoanObject(){
		showModalDialog("toSelectLoanObjectAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.addform.uid.value=getCookie("uid");
		document.addform.uidname.value=getCookie("uname");
	}
	
	function Audit(lid){
			window.open("../finance/auditLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(lid){
			window.open("../finance/cancelAuditLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Endcase(lid){
			window.open("../finance/endcaseLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelEndcase(lid){
			window.open("../finance/cancelEndcaseLoanAction.do?LID="+lid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>

</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="15"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="942"> 个人借款详情</td>
        <td width="286" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
          <tr>
           <!-- <td width="60" align="center"><a href="javascript:PrintLoan('${lf.id}');">打印</a></td>-->
            <td width="60" align="center"><c:choose>
              <c:when test="${lf.isaudit==0}"><a href="javascript:Audit('${lf.id}');">复核</a></c:when>
              <c:otherwise> <a href="javascript:CancelAudit('${lf.id}')">取消复核</a></c:otherwise>
            </c:choose></td>
			<td width="60" align="center"><c:choose>
              <c:when test="${lf.isendcase==0}"><a href="javascript:Endcase('${lf.id}');">结款</a></c:when>
              <c:otherwise> <a href="javascript:CancelEndcase('${lf.id}')">取消结款</a></c:otherwise>
            </c:choose></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="addform" method="post" action="addLoanAction.do" onSubmit="return Check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="11%"  align="right">借款对象：</td>
          <td width="24%">${lf.uidname}</td>
          <td width="10%" align="right">借款日期：</td>
          <td width="22%">${lf.loandate}</td>
          <td width="10%" align="right">借款金额：</td>
	      <td width="23%"><windrp:format value='${lf.loansum}'/></td>
	  </tr>
	  <tr>
	    <td  align="right">付款来源：</td>
	    <td>${lf.fundsrcname}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">借款用途：</td>
	    <td colspan="5">${lf.purpose}</td>
	    </tr>
	  <tr>
	    <td  align="right">单位意见：</td>
	    <td colspan="5">${lf.companyidea}</td>
	  </tr>
	  <tr>
	    <td  align="right">核算中心意见：</td>
	    <td colspan="5">${lf.hubidea}</td>
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
	    <td width="11%"  align="right">制单机构：</td>
	    <td width="24%"><windrp:getname key='organ' value='${lf.makeorganid}' p='d'/></td>
	    <td width="10%" align="right">制单部门：</td>
	    <td width="22%"><windrp:getname key='dept' value='${lf.makedeptid}' p='d'/></td>
	    <td width="10%" align="right">制单人：</td>
	    <td width="23%">${lf.makeidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">制单日期：</td>
	    <td>${lf.makedate}</td>
	    <td align="right">是否复核：</td>
	    <td>${lf.isauditname}</td>
	    <td align="right">复核人：</td>
	    <td>${lf.auditidname}</td>
	  </tr>
	  <tr>
	    <td  align="right">复核日期：</td>
	    <td>${lf.auditdate}</td>
	    <td align="right">是否结款：</td>
	    <td>${lf.isendcasename}</td>
	    <td align="right">结款人：</td>
	    <td>${lf.endcaseidname}</td>
	    </tr>
		<tr>
	    <td  align="right">结款日期：</td>
	    <td>${lf.endcasedate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>

	 </form>
	 </td>
  </tr>
</table>
</td>
  </tr>
</table>

</body>
</html>
