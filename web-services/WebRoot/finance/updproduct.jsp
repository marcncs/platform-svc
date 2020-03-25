<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script type="text/javascript">
function yulan()
{
var fileext=document.validateProduct.picture.value.substring(document.validateProduct.picture.value.lastIndexOf("."),document.validateProduct.picture.value.length)
        fileext=fileext.toLowerCase()
    
        if ((fileext!='.jpg')&&(fileext!='.gif')&&(fileext!='.jpeg')&&(fileext!='.png')&&(fileext!='.bmp'))
        {
            alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 ！");
             document.validateProduct.picture.focus();
        }
        else
        {
        //alert(''+document.form1.UpFile.value)//把这里改成预览图片的语句
  document.getElementById("preview").innerHTML="<img src='"+document.validateProduct.picture.value+"' align=center width=230 style='border:6px double #ccc'>"
        }


}
</script>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 修改产品</td>
  </tr>
</table>

        <form action="updProductAction.do" method="post"  >
          <fieldset align="center">
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="9%"  align="right">产品编号：</td>
              <td width="26%">
                ${p.id}     <input type="hidden" name="id" id="id" value="${p.id}" />         </td>
              <td width="9%" align="right">产品名称：</td>
              <td width="20%">
                ${p.productname}              </td>
              <td width="13%" align="right">产品类别：</td>
              <td width="23%">
			  ${ulsname}                </td>
            </tr>
			<tr>
              <td  align="right">品牌：</td>
              <td>${brand}</td>
              <td align="right">&nbsp;</td>
              <td>&nbsp;</td>
              <td align="right">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
          </fieldset>
          <fieldset align="center">
          <legend>
          <table width="75" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>价格库存信息</td>
            </tr>
          </table>
		  </legend>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="9%"  align="right">标准采购价：</td>
              <td width="26%"><input name="standardpurchase" type="text" value="${p.standardpurchase}" id="standardpurchase" maxlength="8"></td>
              <td width="9%" align="right">标准销售价：</td>
              <td width="22%">
                <input name="standardsale" type="text" id="standardsale" value="${p.standardsale}" maxlength="8">              </td>
              <td width="9%" align="right">最低销售价：</td>
              <td width="25%">
                <input name="leastsale" type="text" id="leastsale" value="${p.leastsale}" maxlength="8">              </td>
            </tr>
			<tr>
              <td  align="right">A类终短价：</td>
              <td><input name="pricei" type="text" id="pricei" value="${p.pricei}" maxlength="8" >              </td>
              <td align="right">B类终端价：</td>
              <td><input name="priceii" type="text" id="priceii" value="${p.priceii}" maxlength="8" >              </td>
              <td align="right">C类终端价：</td>
              <td><input name="priceiii" type="text" id="priceiii" value="${p.priceii}" maxlength="8" >              </td>
            </tr>
			<tr>
              <td  align="right">二级批发商价：</td>
              <td><input name="pricewholesale" type="text" id="pricewholesale" value="${p.pricewholesale}" maxlength="8" >              </td>
              <td align="right">4S价：</td>
              <td><input name="priceivs" type="text" id="priceivs" value="${p.priceivs}" maxlength="8" >              </td>
              <td align="right">加盟价：</td>
              <td><input name="priceuni" type="text" id="priceuni" value="${p.priceuni}" maxlength="8" >              </td>
            </tr>
          </table>
          </fieldset>
		  
          <fieldset align="center">
          <legend>          </legend>
          </fieldset>
          <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="history.back();">
              </div></td>
            </tr>
          </table>
        </form>    </td>
  </tr>
</table>
</body>
</html>
