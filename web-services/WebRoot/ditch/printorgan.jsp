<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>机构列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/pss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
      <div align="center" class="print_title_style">机构列表</div>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td  align="right">省：</td>
            <td><windrp:getname key='countryarea' value='${Province}' p='d'/>
</td>
            <td  align="right">市： </td>
            <td><windrp:getname key='countryarea' value='${City}' p='d'/></td>
            <td  align="right">区：</td>
            <td><windrp:getname key='countryarea' value='${Areas}' p='d'/></td>
          </tr>
          <tr>
            <td  align="right">是否撤消：</td>
            <td><windrp:getname key='YesOrNo' value='${IsRepeal}' p='f'/></td>
            <td  align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td  align="right">&nbsp;</td>
            <td>&nbsp;</td>
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
        <table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="10%" >编号</td>
			  <td width="12%" >内部编码</td>
              <td width="20%">机构名称</td>
              <td width="8%">省</td>
              <td width="8%">市</td>
              <td width="8%">区</td>
              <td width="18%">上级机构</td>
			  <td width="6%">是否撤消</td>
            </tr>
              <logic:iterate id="d" name="dpt" >
                <tr align="center" class="table-back-colorbar">
                  <td >${d.id}</td>
				  <td >${d.oecode}</td>
                  <td>${d.organname}</td>
                  <td>${d.provincename}</td>
                  <td>${d.cityname}</td>
                  <td>${d.areasname}</td>
                  <td>${d.parentidname}</td>
				  <td><windrp:getname key='YesOrNo' value='${d.isrepeal}' p='f'/></td>
                </tr>
              </logic:iterate>
      </table>
      
	  </div>

</body>
</html>

