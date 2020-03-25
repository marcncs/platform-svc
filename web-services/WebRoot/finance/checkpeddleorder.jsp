<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">

	function Prints(soid){
			window.open("../sales/printPeddleOrderAction.do?ID="+soid,"newwindow","height=600,width=970,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Blankout(soid){
		if(window.confirm("您确认要作废该记录吗？如果作废将不能恢复!")){
			window.open("../sales/blankoutPeddleOrderAction.do?id="+soid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}
	
	function toaddidcode(pidid, batch, piid){
			window.open("../sales/toAddPeddleOrderIdcodeAction.do?pidid="+pidid+"&batch="+batch+"&piid="+piid,"newwindow","height=400,width=600,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	
	
</script>
<style type="text/css">
<!--

#sq {
	position:absolute;
	left:0px;
	top:0px;
	width:200px;
	height:auto;
	z-index:1;
	visibility:hidden;
}
-->
</style>
</head>

<div id="sq">
  <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" class="GG">
    <tr>
      <td width='40%' height="32" class="title-back"> 仓库</td>
	  <td width='25%' class="title-back">批次</td>
      <td width='35%' class="title-back">可用数量</td>
    </tr>
    <tr>
      <td colspan="3">
       <div id="stock">	
        	
      </div> 	
	  </td>
    </tr>
  </table>
</div>

<body>
<form name="" method="post" action="checkPeddleOrderAction.do">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

<input type="hidden" name="poids" value="${poids}">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
        <td width="958"> 零售对帐 </td>
        <td width="275" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
          <tr>
          <!--  <td width="60" align="center"><a href="javascript:Prints('0');">打印</a></td> -->         
          </tr>
        </table></td>
      </tr>
    </table>
        <fieldset align="center">
        <legend> </legend>
          <table width="50" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>基本信息</td>
          </tr>
        </table>
          <table width="100%"  border="0" cellpadding="0" cellspacing="1">
          <tr>
            <td width="9%"  align="right">制单日期：</td>
            <td width="22%">${BeginDate} -- ${EndDate}</td>
            <td width="12%"  align="right">制单机构：</td>
            <td width="20%">${MakeOrgan}</td>
            <td width="9%" align="right">制单人：</td>
            <td width="28%">${MakeUser}</td>
          </tr>
        </table>
      </fieldset>
      <fieldset align="center">
        <legend> </legend>
        <table width="91" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="91">款项列表</td>
          </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="5%">序号</td>
            <td width="41%" >付款方式</td>
            <td width="30%">金额</td>
            <td width="24%">收款去向</td>
          </tr>
			<tr class="table-back">
			  <td>1</td>
			  <td >现金</td>
			  <td><input type="hidden" name="incomesum" value="${xianjin}"><fmt:formatNumber value="${xianjin}" pattern="0.00"/></td>
			  <td><select name="fundattach" id="fundattach">
          <logic:iterate id="u" name="cblist">
            <option value="${u.id}">${u.cbname}</option>
          </logic:iterate>
        </select></td>
			</tr>
			<tr class="table-back">
			  <td>2</td>
			  <td >刷卡</td>
			  <td><input type="hidden" name="incomesum" value="${shuaka}"><fmt:formatNumber value="${shuaka}" pattern="0.00"/></td>
			  <td><select name="fundattach" id="fundattach">
          <logic:iterate id="u" name="cblist">
            <option value="${u.id}">${u.cbname}</option>
          </logic:iterate>
        </select></td>
			</tr>

        </table>
      </fieldset></td>
  </tr>
  <tr>
  <td  align="center"><input type="Submit" name="Submit"  value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="返回" onClick="history.go(-1);"></td>
          </tr>

</table>
</form>
</body>
</html>
