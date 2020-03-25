<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<script language="javascript">
	function ToSet(){
		popWin("../purchase/toSetProductPriceAction.do",650,500);
	}
	
	function ToApp(){
		popWin("../purchase/toAppLctProductPriceAction.do",700,600);
	}
	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 零售产品价格</td>
  </tr>
</table>

       	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">
				<td width="50">
					<a href="javascript:ToSet();"><img
							src="../images/CN/addnew.gif" width="16" height="16"
							border="0" align="absmiddle">&nbsp;设置</a>
				</td>
				<td width="80">
					<a href="javascript:ToApp();"><img
							src="../images/CN/addnew.gif" width="16" height="16"
							border="0" align="absmiddle">&nbsp;应用到机构</a>
				</td>									
				<td class="SeparatePage">
					
				</td>
			</tr>
		</table>
		  <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="9%">序号</td>
              <td width="32%" >产品名称</td>
              <td width="11%">单位</td>
              <td width="13%">价格政策</td>
              <td width="13%">价格</td>
            </tr>
            <logic:iterate id="p" name="alpl" >
              <tr class="table-back-colorbar">
                <td>${p.id}</td>
                <td  title="点击查看详情">${p.productidname}</td>
                <td>${p.unitidname}</td>
                <td>${p.policyidname}</td>
                <td><windrp:format value='${p.unitprice}'/></td>
              </tr>
            </logic:iterate>
          </table>
         
          
    </td>
  </tr>
</table>
</body>
</html>
