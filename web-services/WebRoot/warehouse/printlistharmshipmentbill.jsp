<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<link href="../css/pss.css" rel="stylesheet" type="text/css">
		<style media=print> 
		.Noprint{display:none;} 
		</style> 
		</head>
		
		<body style="overflow: auto;">
		<center class="Noprint" > 
		<div class="printstyle">
		<img src="../images/print.gif" onClick="javascript:window.print();"></img>
		</div><hr align="center" width="100%" size="1" noshade> 
		</center> 
		<div id="abc">
		<div align="center" class="print_title_style">报损出库单</div>
				      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr> 
            <td width="8%"  align="right">出货仓库：</td>
            <td width="26%">
			<windrp:getname key='warehouse' value='${WarehouseID}' p='d'/></td>
            <td width="12%" align="right">是否复核：</td>
            <td width="24%"><windrp:getname key="YesOrNo" p="f" value="${IsAudit}" /></td>
            <td width="9%" align="right">是否作废：</td>
            <td width="17%"><windrp:getname key="YesOrNo" p="f" value="${IsBlankOut}" /></td>
           
          </tr>
          <tr>
            <td  align="right">制单机构：</td>
            <td>${oname}</td>
            <td align="right">制单部门：</td>
            <td>${deptname}</td>
            <td align="right">制单人：</td>
            <td>${uname}</td>
            
          </tr>
		   <tr >
		   <td width="10%" align="right">是否结案：</td>
            <td width="19%"><windrp:getname key="YesOrNo"  p="f" value="${IsEndcase}"/></td>
            <td  align="right">领用日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>

	      </tr>
		  <tr>
			  <td width="13%"  align="right">打印机构：</td>
			  <td width="21%">${porganname}</td>
			   <td width="10%"  align="right">打印人：</td>
			  <td width="23%">${pusername}</td>
			   <td width="9%"  align="right">打印时间：</td>
			  <td width="24%">${ptime}</td>
			</tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">

          <tr align="center" class="title-top"> 
            <td width="13%" >编号</td>
            <td width="11%">出货仓库</td>
			<td width="13%" >出库部门</td>
            <td width="11%" >报损日期</td>
            <td width="21%" >制单机构</td>
            <td width="13%" >制单人</td>
            <td width="6%" >是否复核</td>
            <td width="6%" >是否结案</td>
            <td width="6%" >是否作废</td>            
          </tr>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back-colorbar" > 
            <td>${s.id}</td>
            <td><windrp:getname key='warehouse' value='${s.warehouseid}' p='d'/></td>
            <td><windrp:getname key='dept' value='${s.shipmentdept}' p='d'/></td>
            <td><windrp:dateformat value='${s.harmdate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${s.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isblankout}' p='f'/></td>           
          </tr>
		  </logic:iterate>
      </table>
	</div>
</body>
</html>
