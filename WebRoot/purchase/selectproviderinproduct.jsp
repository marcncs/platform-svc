<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onLoad="defaultCheck()">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 相关产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectProviderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="11%"  align="right">
            <input name="pvid" type="hidden" id="pvid" value="${pvid}">
            产品类别：</td>
            <td width="22%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/></td> 
	  	    <td width="13%" align="right">品牌：</td>
	  	    <td width="15%"><windrp:select key="Brand" name="unitid" p="y|d"/></td>
	  	    <td width="12%" align="right">关键字：</td>
	  	    <td width="20%"><input type="text" name="KeyWord">
              </td>
              <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp; 关闭</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../purchase/selectProviderProductAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable" id="ptable">
        
          <tr align="center" class="title-top"> 
            <td width="9%" >产品编号</td>
            <td width="20%">产品名称</td>
            <td width="14%">规格</td>
            <td width="20%">单位</td>
            <td width="20%">单价</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr align="center" class="table-back-colorbar" > 
            <td >${p.productid}</td>
            <td><input type="hidden" name="productname" value="${p.pvproductname}">
            ${p.pvproductname}</td>
            <td><input type="hidden" name="specmode" value="${p.pvspecmode}">
            ${p.pvspecmode}</td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="unitname" value="${p.unitname}">
              ${p.unitname}</td>
            <td><input type="hidden" name="price" value="${p.price}">
              ${p.price}</td>
		  </tr>
          </logic:iterate> 
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
