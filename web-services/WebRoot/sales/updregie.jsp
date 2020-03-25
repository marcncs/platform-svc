<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<script language="javascript">
function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.addform.cid.value=getCookie("cid");
	document.addform.regienob.value=getCookie("cname");
}
</script>
<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改专卖店</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
     <form name="addform" method="post" action="updRegieAction.do" >
    <table width="100%" height="209"  border="0" cellpadding="0" cellspacing="1">
     
        <tr class="table-back">
          <td width="11%"  align="right"><input name="id" type="hidden" id="id" value="${r.id}">
            专卖店名称：</td>
          <td width="38%"><input name="regiename" type="text" id="regiename" value="${r.regiename}"></td>
          <td width="13%" align="right">店面类型：</td>
          <td width="38%">${regietypename}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">面积大小：</td>
          <td><input name="acreage" type="text" id="acreage" value="${r.acreage}">
平米</td>
          <td align="right">现状：</td>
          <td>${actualityname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">店长：</td>
          <td><input name="regienob" type="text" id="regienob" value="${r.regienob}"><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          <td align="right">开店日期：</td>
          <td><input name="shopdate" type="text" id="shopdate" onFocus="javascript:selectDate(this)" value="${r.shopdate}"></td>
        </tr>
        <tr class="table-back">
          <td  align="right">专管人员：</td>
          <td><select name="specializeid" id="specializeid">
             <logic:iterate id="u" name="als">
               <option value="${u.userid}">${u.realname}</option>
             </logic:iterate>
           </select></td>
          <td align="right">省份：</td>
          <td><select name="province"  onChange="ChangeCountry(this);">
              <option value="">-省份-</option>
              <logic:present name="cls"> <logic:iterate id="c" name="cls">
              <option value="${c.id}" <c:if test="${c.id==r.province}">
                    <c:out value="selected"/>
                  </c:if>>${c.areaname}</option>
              </logic:iterate> </logic:present>
          </select></td>
        </tr>
        <tr class="table-back">
          <td  align="right">城市：</td>
          <td><select name="city">
              <option value="">-城市-</option>
              <logic:present name="city"> <logic:iterate id="ct" name="city">
              <option value="${ct.id}" <c:if test="${ct.id==r.city}"><c:out value="selected"/></c:if>>${ct.areaname}</option>
              </logic:iterate> </logic:present>
          </select></td>
          <td align="right">是否可用：</td>
          <td>${isuseselect}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">地址：</td>
          <td><input name="regieaddr" type="text" id="regieaddr" value="${r.regieaddr}"></td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td colspan="3"><textarea name="remark" cols="140" rows="5">${r.remark}</textarea></td>
        </tr>
        <tr class="table-back">
          <td  align="right">&nbsp;</td>
          <td><input type="submit" name="Submit" value="确定">
              <input type="reset" name="cancel" onClick="javascript:history.back()" value="取消"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
     
     
    </table> </form></td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
