<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation,com.winsafe.drp.dao.Customer"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script language="javascript">
function Activate(cid){
			window.open("../sales/activateCustomerAction.do?CID="+cid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function NoActivate(cid){
			window.open("../sales/cancelActivateCustomerAction.do?CID="+cid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="12"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="647"> 客户详细信息</td>
        <td width="322" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" align="center"><c:choose>
                <c:when test="${cf.isactivate==0}"><a href="javascript:Activate('${cf.cid}');">激活</a></c:when>
                <c:otherwise> <a href="javascript:NoActivate('${cf.cid}')">停用</a></c:otherwise>
            </c:choose></td>
            <!--  <td width="60" align="center"><c:choose>
                  <c:when test=""><a href="javascript:Ratify('')">批准</a></c:when>
                  <c:otherwise><a href="javascript:CancelRatify('')">取消批准</a></c:otherwise>
              </c:choose></td>  -->
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	    <tr>
          <td width="11%"  align="right">客户编号：</td>
	      <td width="22%">${cf.cid}</td>
	      <td width="11%" align="right">客户名称：</td>
	      <td width="24%">${cf.cname}</td>
	      <td width="32%" rowspan="10" align="right"><table width="100%" border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td height="150" align="center"><div id="preview"><img src="../${cf.cphoto}" width="150" style="border:6px double #ccc"></div></td>
              </tr>
              <tr>
                <td><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                    <tr>
                      <td width="29%" align="right">客户照片：</td>
                      <td width="71%">&nbsp;</td>
                    </tr>
                </table></td>
              </tr>
          </table></td>
	    </tr>
		<tr>
	      <td  align="right">付款期限：</td>
	      <td>${cf.prompt}</td>
	      <td align="right">信用额度：</td>
	      <td>${cf.creditlock}</td>
	    </tr>
		<tr>
	      <td  align="right">分类：</td>
	      <td>${cf.sortname}</td>
	      <td align="right">价格级别：</td>
	      <td>${cf.ratename}</td>
	    </tr>
		<tr>
	      <td  align="right">折扣率：</td>
	      <td>${cf.discount}%</td>
	      <td align="right">税率：</td>
	      <td>${cf.taxrate}%</td>
	    </tr>
	    <tr>
	      <td  align="right">客户行业：</td>
	      <td>${cf.vocationname}</td>
	      <td align="right">ABC类别：</td>
	      <td>${cf.customertypename}</td>
	      </tr>
	    <tr>
          <td  align="right">客户状态：</td>
	      <td>${cf.customerstatusname}</td>
	      <td align="right">活跃率：</td>
	      <td>${cf.yauldname}</td>
	    </tr>
	    <tr>
          <td  align="right">客户来源：</td>
	      <td>${cf.sourcename}</td>
	      <td align="right">渠道：</td>
	      <td>${cf.ditchidname}</td>
	    </tr>
	    <tr>
          <td  align="right">签约日期：</td>
	      <td>${cf.signdate}</td>
	      <td align="right">专管人员：</td>
	      <td>${cf.specializeidname}</td>
	    </tr>
	    <tr>
          <td  align="right">录入人员：</td>
	      <td>${cf.makeidname}</td>
	      <td align="right">最后联系日期：</td>
	      <td>${cf.lastcontact}</td>
	    </tr>
	    <tr>
          <td  align="right">下次联系日期：</td>
	      <td>${cf.nextcontact}</td>
	      <td align="right">邮编：</td>
	      <td>${cf.postcode}</td>
	    </tr>
	    <tr>
	      <td  align="right">省份：</td>
	      <td>${cf.provincename}</td>
	      <td align="right">城市：</td>
	      <td>${cf.cityname}</td>
	    </tr>
	    <tr>
          <td  align="right">地区：</td>
	      <td>${cf.areasname}</td>
	      <td align="right">是否激活：</td>
	      <td>${cf.isactivatename}</td>
	    </tr>
		 <tr>
          <td  align="right">激活人：</td>
	      <td>${cf.activateidname}</td>
	      <td align="right">激活日期：</td>
	      <td>${cf.activatedate}</td>
	    </tr>
		 <tr>
	      <td  align="right">经管部门：</td>
	      <td colspan="3">${cf.specializedeptname}</td>
	      </tr>
	    <tr>
	      <td  align="right">详细地址：</td>
	      <td colspan="3">${cf.detailaddr}</td>
	      </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>联系资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="11%"  align="right">办公电话：</td>
          <td width="24%">${cf.officetel}</td>
          <td width="9%" align="right">手机：</td>
          <td width="22%">${cf.mobile}</td>
	      <td width="9%" align="right">传真：</td>
	      <td width="25%">${cf.fax}</td>
	  </tr>
	  <tr>
	    <td  align="right">网址：</td>
	    <td>${cf.homepage}</td>
	    <td align="right">邮箱：</td>
	    <td>${cf.email}</td>
	    <td align="right">备注：</td>
	    <td>${cf.remark}</td>
	    </tr>
	  </table>
</fieldset>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>账务信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	<c:forEach var="bank" items="${banklist}">
	  <tr>
	  	<td width="11%"  align="right">开户银行：</td>
          <td width="24%">${bank.bankname}</td>
          <td width="9%" align="right">户名：</td>
          <td width="22%">${bank.doorname}</td>
	      <td width="9%" align="right">账号：</td>
	      <td width="25%">${bank.bankaccount}</td>
	  </tr>
	</c:forEach>
	  </table>
</fieldset>
	
	</td>
  </tr>
</table>	
    </td>
  </tr>
</table>

</body>
</html>
