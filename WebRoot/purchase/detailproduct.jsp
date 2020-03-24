<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">产品详情</td>
    <td width="364" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"></td>
            </tr>
          </table></td>
  </tr>
</table>

          <fieldset align="center">
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
            <tr>
              <td  align="right">产品类别：</td>
              <td>${psidname}</td>
              <td  align="right">类别英文名：</td>
              <td>${psidnameen}</td>
              <td height="20" align="right">物料号：</td>
			   <td width="21%">${p.mCode}</td>
            </tr>
            <tr>
              <td width="11%"  align="right">产品名称：</td>
              <td width="21%">${p.productname}</td>
              <td width="12%" align="right">产品英文名：</td>
              <td width="18%">${p.productnameen}</td>
              <td width="13%" align="right">规格明细：</td>
              <td width="21%">${p.packSizeName}</td>
            </tr>
            <tr>
			   <td align="right">物料中文描述：</td>
			   <td width="21%">${p.matericalChDes}</td>
			   <td align="right">物料英文描述：</td>
			   <td width="21%">${p.matericalEnDes}</td>
			   <td align="right">保质期(天)：</td>
			   <td width="21%">${p.expiryDays}</td>
		      </tr>
		      <tr>
		       <td align="right">产品编号：</td>
               <td>${p.id}</td>
			   <td height="20" align="right">包装规格：</td>
			   <td width="25%">${p.specmode}</td>
			   <td align="right">包装规格英文：</td>
			   <td width="21%">${p.packSizeNameEn}</td>
		      </tr>
		      <!--<tr> 
		      	<td align="right">箱码打印：</td>
		      	<td><windrp:getname key='YesOrNo' value='${p.cartonPrintFlag}' p='f'/></td>
				<td align="right">小包装打印：</td>
				<td><windrp:getname key='YesOrNo' value='${p.primaryPrintFlag}' p='f'/></td>
				<td align="right">箱码扫描：</td>
				<td><windrp:getname key='YesOrNo' value='${p.cartonScanning}' p='f'/></td>
		      </tr>-->
			</table>
			</fieldset>
    </td>
  </tr>
</table>

</body>
</html>
