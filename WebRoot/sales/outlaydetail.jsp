<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Audit(oid){
			window.open("../sales/auditOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudit(oid){
			window.open("../sales/cancelAuditOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function Audittwo(oid){
			window.open("../sales/audittwoOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAudittwo(oid){
			window.open("../sales/cancelAudittwoOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function Auditend(oid){
			window.open("../sales/auditendOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelAuditend(oid){
			window.open("../sales/cancelAuditendOutlayAction.do?OID="+oid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="948"> 费用单详情 </td>
    <td width="285" align="right"><table width="180"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="60" align="center"><c:choose>
          <c:when test="${olf.isaudit==0}"><a href="javascript:Audit('${olf.id}');">初审</a></c:when>
          <c:otherwise> <a href="javascript:CancelAudit('${olf.id}')">取消初审</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${olf.isaudittwo==0}"><a href="javascript:Audittwo('${olf.id}')">复审</a></c:when>
          <c:otherwise><a href="javascript:CancelAudittwo('${olf.id}')">取消复审</a></c:otherwise>
        </c:choose></td>
        <td width="60" align="center"><c:choose>
          <c:when test="${olf.isauditend==0}"><a href="javascript:Auditend('${olf.id}')">终审</a></c:when>
          <c:otherwise><a href="javascript:CancelAuditend('${olf.id}')">取消终审</a></c:otherwise>
        </c:choose></td>
      </tr>
    </table></td>
  </tr>
</table>

<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">支出日期：</td>
          <td width="21%">${olf.paydate}</td>
          <td width="13%" align="right">客户：</td>
          <td width="21%">${olf.customeridname}</td>
	      <td width="11%" align="right">核算部门：</td>
	      <td width="25%">${olf.castdeptname}</td>
	  </tr>
	  <tr>
	    <td  align="right">核算员：</td>
	    <td>${olf.castername}</td>
	    <td align="right">总费用：</td>
	    <td>${olf.totaloutlay}</td>
	    <td align="right">备注：</td>
	    <td>${olf.remark}</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">制单人：</td>
          <td width="21%">${olf.makeidname}</td>
          <td width="13%" align="right">制单日期：</td>
          <td width="19%">${olf.makedate}</td>
	      <td width="13%" align="right">是否初审：</td>
	      <td width="25%">${olf.isauditname}</td>
	  </tr>
	  <tr>
	    <td  align="right">初审人：</td>
	    <td>${olf.auditidname}</td>
	    <td align="right">初审日期：</td>
	    <td>${olf.auditdate}</td>
	    <td align="right">是否复审：</td>
	    <td>${olf.isaudittwoname}</td>
	    </tr>
	  <tr>
	    <td  align="right">复审人：</td>
	    <td>${olf.audittwoidname}</td>
	    <td align="right">复审日期：</td>
	    <td>${olf.audittwodate}</td>
	    <td align="right">是否终审：</td>
	    <td>${olf.isauditendname}</td>
	    </tr>
	  <tr>
	    <td  align="right">终审人：</td>
	    <td>${olf.auditendidname}</td>
	    <td align="right">终审日期： </td>
	    <td>${olf.auditenddate}</td>
	    <td align="right">是否提交：</td>
	    <td>${olf.isrefername}</td>
	    </tr>
	  <tr>
	    <td  align="right">是否审阅：</td>
	    <td>${olf.approvestatusname}</td>
	    <td align="right">审阅日期：</td>
	    <td>${olf.approvedate}</td>
	    <td align="right">是否生成应付款：</td>
	    <td>${olf.iscreatename}</td>
	    </tr>
	  </table>
	</fieldset>

	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>费用明细</td>
        </tr>
      </table>
	  </legend>
	  <FORM METHOD="POST" name="listform" ACTION="">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
      
        <tr align="center" class="title-top">
          <td width="305" >费用类别</td>
          <!--<td width="135" >相关客户ID</td>-->
          <td >备注</td>
          <td >费用金额</td>
        </tr>
        <logic:iterate id="d" name="als">
          <tr class="table-back">
            <td >${d.outlayprojectidname}</td>
            <td width="707">${d.remark}</td>
            <td width="227">${d.outlaysum}</td>
          </tr>
        </logic:iterate>
      
    </table>
    </form>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审阅信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="title-top">
    <td width="9%" >是否审阅</td>
    <td width="8%">审阅者</td>
    <td width="13%">动作</td>
    <td width="70%">审阅内容</td>
  </tr>
  <logic:iterate id="r" name="rvls" >
  <tr class="table-back">
    <td >${r.approvename}</td>
    <td>${r.approveidname}</td>
    <td>${r.actidname}</td>
    <td>${r.approvecontent}</td>
  </tr>
  </logic:iterate>
</table>
</fieldset>

    </td>
  </tr>
</table>
</body>
</html>
