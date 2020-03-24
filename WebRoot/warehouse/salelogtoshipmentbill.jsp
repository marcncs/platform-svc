<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 销售订单转为出货单</td>
        </tr>
      </table>
      <form name="affirmform" method="post" action="affirmInputShipmentAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr class="table-back"> 
            <td width="15%"  align="right"><input name="slid" type="hidden" id="slid" value="${slid}"> 
				<input name="cid" type="hidden" id="cid" value="${cid}">
            收货方：</td>
            <td width="35%"> 
              <input name="cname" type="text" id="cname" value="${cname}" readonly> </td>
            <td width="14%" align="right">联系人：</td>
            <td width="36%"><input name="linkman" type="text" id="linkman" value="${linkman}"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">电话：</td>
            <td><input name="tel" type="text" id="tel" value="${tel}"></td>
            <td align="right">发票号：</td>
            <td><input name="invoicecode" type="text" id="invoicecode" value="${invoicecode}"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">发货仓库：</td>
            <td><select name="warehouseid">
                <logic:iterate id="w" name="alw" >
                  <option value="${w.id}">${w.warehousename}</option>
                </logic:iterate>
              </select></td>
            <td align="right">运输方式：</td>
            <td>${transportmodeselect}</td>
          </tr>
          <tr class="table-back"> 
			<td  align="right">收货地址：</td>
            <td><textarea name="receiveaddr" cols="35" rows="3" id="receiveaddr">${address}</textarea></td>
            <td align="right">备注：</td>
            <td><textarea name="textarea" cols="35" rows="3"></textarea></td>
          </tr>
          <tr> 
            <td  align="right">&nbsp;</td>
            <td><input type="submit" name="Submit" value="确定"> <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table> 
       </form>
      </td>
  </tr>
</table>
</body>
</html>
