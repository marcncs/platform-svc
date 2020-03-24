<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
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
              <td width="60" align="center"><c:choose>
                <c:when test="${of.isrepeal==0}"><a href="javascript:Repeal('${of.id}');">撤消</a></c:when>
                <c:otherwise> <a href="javascript:CancelRepeal('${of.id}')">取消撤消</a></c:otherwise>
              </c:choose></td>
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	<form name="validateCustomer" method="post" action="../sales/addMemberAction.do" >
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	  <tr>
	  	<td width="13%"  align="right">编号：</td>
          <td width="23%">${of.id}</td>
          <td width="12%" align="right">企业内部编码：</td>
          <td width="19%">${of.oecode}</td>
          <td width="10%" align="right">名称：</td>
	      <td width="23%">${of.organname}</td>
	  </tr>
	  <c:if test="${of.organType != 1}">
	  <tr>
	    <td  align="right">电话：</td>
	    <td>${of.otel}</td>
	    <td align="right">手机：</td>
	    <td>${of.omobile}</td>
	    <td align="right">传真：</td>
	    <td>${of.ofax}</td>
	  </tr>
	  <tr>
	   <td  align="right">EMail：</td>
	    <td>${of.oemail}</td>
	    <td align="right">上级机构：</td>
	    <td><windrp:getname key='organ' value='${of.parentid}' p='d'/></td>
	    <td rowspan="2"  align="right" valign="top">Logo：</td>
	    <td  rowspan="2">
	    <c:if test="${not empty of.logo}">
	    	<img src="../${logo }" width="123" height="55"></img>
	    </c:if>
	    </td>
	  </tr>
	  <tr>
	    <td align="right">区域：</td>
	    <td colspan="3">${province}-${city}-${areas}</td>
	    
	    </tr>
        <tr>
	    <td  align="right">地址：</td>
	    <td colspan="5">${of.oaddr}</td>
	    </tr>
	    <tr>
	   </c:if>
		<td align="right">机构类型： </td>
		<td ><windrp:getname key='OrganType' value='${of.organType}' p='f'/></td>
		<c:if test="${of.organType == 1}">
		<td align="right">类别： </td>
		<td ><windrp:getname key='PlantType' value='${of.organModel}' p='f'/></td>
		</c:if>
		<c:if test="${of.organType == 2}">
		<td align="right">类别： </td>
		<td ><windrp:getname key='DealerType' value='${of.organModel}' p='f'/></td>
		</c:if>
       </tr>
        <c:if test="${users.makeorganid == '00000001' && of.organType == 2 && of.organModel != 1}">
				<tr>
	  			  <td align="right">营业执照：</td>
				  <td>${of.license}</td>
	 			  <td align="right">客户级别：</td>
	              <td><windrp:getname key='CustomerLevel' value='${of.customerlevel}' p="d"/> </td>
	 		   </tr>
		</c:if>
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
	    <td width="15%" align="right">创建日期：</td>
	    <td width="20%">${of.creationTime}</td>
	  </tr>
	    <c:choose>
		    <c:when test="${ of.modificationTime!=null&&of.modificationTime!=''}">
		    <tr>
		     <td width="15%" align="right">修改日期：</td>
	    <td width="20%">${of.modificationTime}</td>
	    </tr>
		    </c:when>
		    </c:choose>
	 
	  <tr>
	    <td width="10%"  align="right">是否撤消：</td>
	    <td width="20%"><windrp:getname key='YesOrNo' value='${of.isrepeal}' p='f'/></td>
	    <td width="10%" align="right">撤消人：</td>
	    <td width="25%"><windrp:getname key='users' value='${of.repealid}' p='d'/> </td>
	    <td width="15%" align="right">撤消日期：</td>
	    <td width="20%">${of.repealdate}</td>
	    </tr>
	    <c:choose>
		    <c:when test="${ (users.makeorganid == '00000001' && of.organType == 2 && of.organModel != 1) or users.userType==2 or users.userType==3 or users.userType==4}">
				<tr>
	  			  <td width="10%"  align="right">是否审核：</td>
				  <td width="20%"><windrp:getname key='YesOrNo' value='${of.validatestatus}' p='f'/></td>
	 			  <td width="10%" align="right">审核人：</td>
	              <td width="25%"><windrp:getname key='users' value='${of.validateuserid}' p='d'/> </td>
	  			  <td width="15%" align="right">审核日期：</td>
	 		      <td width="20%">${of.validatedate}</td>
	 		   </tr>
			</c:when>
		</c:choose>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>匹配信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
	   <tr>
	    <td width="13%"  align="right">公司名称：</td>
	    <td width="23%">${matchInfo.name}</td>
	    <td width="12%" align="right">法人名称：</td>
	    <td width="19%">${matchInfo.leg}</td>
	    <td width="10%" align="right">成立日期：</td>
	    <td width="23%">${matchInfo.c_date}</td>
	    </tr>
	 
	  <tr>
	    <td width="10%"  align="right">注册号：</td>
	    <td width="20%">${matchInfo.re_code}</td>
	    <td width="10%" align="right">企业状态：</td>
	    <td width="25%">${matchInfo.status}</td>
	    <td width="15%" align="right"></td>
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
