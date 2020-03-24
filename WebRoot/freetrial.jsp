<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<TITLE>在线演示试用商业进销存|网络进销存|B/S ERP在线试用|ERP进销存软件定制开发</TITLE>
<META name="description" content="支持多语言的客户关系管理系统,B/S进销存,汽车销售管理系统,CRM,企业管理,企业资源计划,在线演示试用,提供软件定制开发">
<META name ="keywords" content="B/S 进销存,客户关系管理,网络订单系统,CRM,汽车销售管理系统,企业管理系统,在线演示试用,在线进销存">

<link href="c/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<script language="javascript">
	function Chk(){
		companyname = document.applytrial.companyname;
		companyaddr = document.applytrial.companyaddr;
		linkman = document.applytrial.linkman;
		tel = document.applytrial.tel;
		
		if(companyname.value==null||companyname.value==""){
			alert("请输入企业名称");
			return false;
		}
		if(companyaddr.value==null||companyaddr.value==""){
			alert("请输入企业地址");
			return false;
		}
		if(linkman.value==null||linkman.value==""){
			alert("请输入联系人");
			return false;
		}
		if(tel.value==null||tel.value==""){
			alert("请输入联系电话");
			return false;
		}

		
		return true;
	}
</script>

<body>
<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="14px" height="17px" background="i/CN/box_01.gif"></td>
    <td background="i/CN/box_02.gif"></td>
    <td width="14px" height="17px" background="i/CN/box_03.gif"></td>
  </tr>
  <tr>
    <td background="i/CN/box_04.gif">&nbsp;</td>
    <td><img src="i/CN/smallbook.jpg" width="10" height="10">&nbsp;申 请 试 用 <br>
        <br>
          <form name="applytrial" action="addTrialApplyAction.do" method="post" onSubmit="return Chk();">
        <table width="100%" height="339"  cellpadding="0" cellspacing="1" bgcolor="#BFC6F0">
        
            <tr>
              <td height="20" colspan="2" align="center" bgcolor="#FFFFFF" class="K">&nbsp;</td>
            </tr>
            <tr>
              <td height="20" colspan="2" class="back-gray">注意 ： 我公司将以电话和邮件方式发放试用账号，请务必尽量详细和真实地填写下列信息。</td>
            </tr>
            <tr>
              <td width="31%" height="20" align="right" bgcolor="#FFFFFF">企业名称：</td>
              <td width="69%" height="20" bgcolor="#FFFFFF"><input name="companyname" type="text" id="companyname" size="50">
                <span class="STYLE1">*</span></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">公司地址：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="companyaddr" type="text" id="companyaddr" size="50">
                <span class="STYLE1">*</span></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">联系人：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="linkman" type="text" id="linkman">
                <span class="STYLE1">                *</span></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">联系人电话：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="tel" type="text" id="tel">
                <span class="STYLE1">*</span>(固定电话请加区号)</td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">联系人QQ：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="qq" type="text" id="qq"></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">联系人MSN：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="msn" type="text" id="msn"></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">邮件地址： </td>
              <td height="20" bgcolor="#FFFFFF"><input name="email" type="text" id="email">
                <span class="STYLE1">*</span></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">公司电话：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="companytel" type="text" id="companytel">
                <span class="STYLE1">*</span></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">公司网址：</td>
              <td height="20" bgcolor="#FFFFFF"><input name="companysite" type="text" id="companysite" size="50"></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">意向：</td>
              <td height="20" bgcolor="#FFFFFF"><select name="intent" id="intent">
                  <option value="0">客户</option>
                  <option value="1">代理商</option>
                </select>
              </td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">试用方式：</td>
              <td height="20" bgcolor="#FFFFFF"><select name="trialmode" id="trialmode">
                  <option value="0" selected>网上试用</option>
                  <option value="1">安装到本地机器</option>
                </select>
              </td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">您从何种途径知道本商品：</td>
              <td height="20" bgcolor="#FFFFFF"><select name="route" id="route">
                  <option value="0">朋友推荐</option>
                  <option value="1">Google</option>
                  <option value="2">百度</option>
                  <option value="3">新浪</option>
                  <option value="4">Sohu</option>
                  <option value="5">网上文章</option>
                  <option value="6">其它</option>
                </select>
              </td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">备注：</td>
              <td height="20" bgcolor="#FFFFFF"><textarea name="remark" cols="50" rows="3" id="remark"></textarea></td>
            </tr>
            <tr>
              <td height="20" align="right" bgcolor="#FFFFFF">&nbsp;</td>
              <td height="20" bgcolor="#FFFFFF"><input type="submit" name="Submit" value="提交">
                &nbsp;&nbsp;
                <input type="reset" name="Submit2" value="重置"></td>
            </tr>
         
        </table>
         </form>
      <br>
        <p>&nbsp;</p></td>
    <td background="i/CN/box_06.gif">&nbsp;</td>
  </tr>
  <tr>
    <td width="14px" height="17px" background="i/CN/box_07.gif"></td>
    <td background="i/CN/box_08.gif"></td>
    <td width="14px" height="17px" background="i/CN/box_09.gif"></td>
  </tr>
</table>
</body>
</html>
