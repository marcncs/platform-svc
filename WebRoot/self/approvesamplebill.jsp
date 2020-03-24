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

<body >

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 样品清单详情</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
      
          <tr> 
            <td height="156" align="right"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr class="table-back"> 
                  <td width="13%"  align="right">                  出货仓库：</td>
                  <td width="40%">${aw}</td>
                  <td  align="right">客户：</td>
                  <td>${cname}</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">联系人：</td>
                  <td><label>${sb.linkman}</label></td>
                  <td align="right">邮政编码：</td>
                  <td>${sb.postcode}</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">送货地址：</td>
                  <td><label>${sb.receiveaddr}</label></td>
                  <td align="right">发货日期：</td>
                  <td><label>${sb.shipmentdate}</label></td>
                </tr>
                <tr class="table-back">
                  <td  align="right">样品制作者：</td>
                  <td><label>${sb.makeuser}</label></td>
                  <td  align="right">是否提交：</td>
                  <td>${isrefer}</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">是否审阅：</td>
                  <td>${approvestatus}</td>
                  <td  align="right">制单人：</td>
                  <td>${makeuser}</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">制单日期：</td>
                  <td>${sb.makedate}</td>
                  <td  align="right">是否已发货：</td>
                  <td>${isshipment}</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">发货人：</td>
                  <td>${shipmentuser}</td>
                  <td align="right">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr class="table-back">
                  <td  align="right">备注：</td>
                  <td colspan="3">${sb.remark}</td>
                </tr>
                
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="772"> 样品产品列表</td>
  </tr>
</table>
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td > 产品</td>
                <td> 单位</td>
                <td> 单价                  </td>
                <td> 数量</td>
                <td> 金额</td>
              </tr>
				<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr align="center" class="table-back">
                <td >${p.productname}</td>
                <td>${p.unitname}</td>
                <td>${p.unitprice}</td>
                <td>${p.quantity}</td>
                <td>${p.subsum}</td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="7%" >&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计">：</td>
                <td width="15%">${sb.totalsum}</td>
              </tr>
            </table>
			<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
				  <tr>
					<td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
					<td width="772"> 审阅信息</td>
				  </tr>
			  </table>
			<form name="addform" method="post" action="approveSampleBillAction.do">	
				  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
					
					  <tr class="table-back"> 
						<td  align="right"><input name="sbid" type="hidden" id="sbid" value="${sb.id}">
					    审阅状态：</td>
						<td>${subapprovestatus}</td>
					  </tr>
					  <tr class="table-back"> 
						<td width="13%" align="right" valign="top"> 审阅内容：</td>
						<td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
					  </tr>
					  <tr class="table-back"> 
						<td  align="right" valign="top">&nbsp;</td>
						<td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
						  <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
					  </tr>
					
			  </table>
			  </form>
			</td>
          
     
      </table>
    </td>
  </tr>
</table>
</body>
</html>
