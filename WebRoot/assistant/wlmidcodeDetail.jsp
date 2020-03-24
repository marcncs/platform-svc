<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function Repeal(siid){
			popWin2("../users/repealOrganAction.do?ID="+siid);
	}
	
	function CancelRepeal(siid){
			popWin2("../users/cancelRepealOrganAction.do?ID="+siid);
	}

</script>

<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">详情</td>
		<td width="276" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center">
              <%-- 
              <c:choose>
                <c:when test="${of.isrepeal==0}"><a href="javascript:Repeal('${of.id}');">撤消</a></c:when>
                <c:otherwise> <a href="javascript:CancelRepeal('${of.id}')">取消撤消</a></c:otherwise>
              </c:choose>
              --%>
              </td>
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
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	 	  <td width="12%"  align="right">小包装码：</td>
          <td width="13%">${pc.primaryCode}</td>
	  	  <td width="12%"  align="right">暗码：</td>
          <td width="13%">${pc.covertCode}</td>
	  	  <td width="12%"  align="right">箱码：</td>
          <td width="13%">${pc.cartonCode}</td>
          <td width="12%" align="right">包装规格：</td>
          <td width="13%">${pg.packSize}</td>
	  </tr>
	  <tr>
	    <td width="12%"  align="right">产品规格：</td>
	    <td width="13%">${p.specmode}</td>
	    <td width="12%"  align="right">批号：</td>
	    <td width="13%">${pg.batchNumber}</td>
	    <td width="12%"  align="right">生产日期：</td>
	    <td width="13%">${ttid.producedate}</td>
	    <td width="12%"  align="right">装箱日期：</td>
	    <td width="13%">${ttid.vad}</td>
	  </tr>
	  <tr>
	    <td width="12%"  align="right">工厂名：</td>
	    <td width="13%">${pg.plantName}<br></td>
	    <td width="12%"  align="right">机构名：</td>
	    <td width="13%">${tt.oname}</td>
		<td width="12%"  align="right">收获人代码： </td>
		<td width="13%">${tt.inOid}</td>
		<td width="12%"  align="right">收获人名字： </td>
		<td width="13%">${tt.inOname}</td>
      </tr>
      <tr>
	    <td width="12%"  align="right">到期日期：</td>
	    <td width="13%">${pg.expiryDate}</td>
	  </tr>
	  </table>
</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>状态信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	    <td width="10%"  align="right">是否撤消：</td>
	    <td width="20%"><windrp:getname key='YesOrNo' value='' p='f'/></td>
	    <td width="10%" align="right">撤消人：</td>
	    <td width="25%"><windrp:getname key='users' value='' p='d'/> </td>
	    <td width="15%" align="right">撤消日期：</td>
	    <td width="20%"></td>
	    </tr>
	  </table>
	</fieldset>
	

    </form>
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
