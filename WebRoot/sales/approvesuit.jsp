<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<head>
<title>WINDRP-分销系统</title>
<script language="javascript">
	function Approve(fid){
			window.open("../sales/toDealSuitAction.do?ID="+fid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	function Deal(sid){
			window.open("../sales/toDealSuitAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function CancelDeal(sid){
			window.open("../sales/cancelDealSuitAction.do?SID="+sid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="15"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="964"> 审核抱怨投诉</td>
              <td width="264" align="right"><table width="120"  border="0" cellpadding="0" cellspacing="0">
                <tr>
				  <td width="60" align="center">&nbsp;</td>
                  <td width="60" align="center">&nbsp;</td>
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
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="13%"  align="right">投诉种类：</td>
          <td width="23%">${pf.suitcontentname}</td>
          <td width="10%" align="right">投诉方式：</td>
          <td width="21%">${pf.suitwayname}</td>
	      <td width="8%" align="right">投诉时间：</td>
	      <td width="25%">${pf.suitdate}</td>
	  </tr>
	  <tr>
	    <td  align="right">客户名称：</td>
	    <td colspan="5">${pf.cidname}</td>
	    </tr>
	  <tr>
	    <td  align="right">投诉人联系方式：</td>
	    <td colspan="5">${pf.suittools}</td>
	    </tr>
	  <tr>
	    <td  align="right">投诉内容：</td>
	    <td colspan="5">${pf.suitstatus}</td>
	    </tr>
	  <tr>
	    <td  align="right">备注：</td>
	    <td colspan="5">${pf.memo}</td>
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
	  	<td width="12%"  align="right">制单机构：</td>
          <td width="24%">${pf.makeorganidname}</td>
          <td width="11%" align="right">制单人：</td>
          <td width="21%">${pf.makeidname}</td>
	      <td width="8%" align="right">制单日期：</td>
	      <td width="24%">${pf.makedate}</td>
	  </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>处理信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">审阅状态：</td>
          <td width="24%">${pf.approvestatusname}</td>
          <td width="11%" align="right">是否处理：</td>
          <td width="21%">${pf.isdealname}</td>
	      <td width="8%" align="right">处理方式：</td>
	      <td width="24%">${pf.dealwayname}</td>
	  </tr>
	  <tr>
	    <td  align="right">处理人：</td>
	    <td>${pf.dealusername}</td>
	    <td align="right">处理日期：</td>
	    <td>${pf.dealdate}</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  <tr>
	    <td  align="right">处理内容：</td>
	    <td colspan="5">${pf.dealcontent}</td>
	    </tr>
	  <tr>
	    <td  align="right">处理结果：</td>
	    <td colspan="5">${pf.dealfinal}</td>
	    </tr>
	  </table>
	</fieldset>
	
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>审阅信息</td>
        </tr>
      </table>
	  </legend>
 <form name="addform" method="post" action="approveSuitAction.do">
      <table width="100%" height="200"  border="0" cellpadding="0" cellspacing="1">
       
			<input name="aflid" type="hidden" id="aflid" value="${aflid}">
          <tr class="table-back"> 
            <td  align="right"><input name="billno" type="hidden" id="billno" value="${pf.id}">
            审阅意见：</td>
            <td>${approvestatus}</td>
          </tr>
          <tr class="table-back">
            <td  align="right"><input name="actid" type="hidden" id="actid" value="${actid}">
            动作：</td>
            <td>${stractid}</td>
          </tr>
          <tr class="table-back"> 
            <td width="13%"  align="right" valign="top"> 审阅内容：</td>
            <td width="87%"><textarea name="approvecontent" cols="80" rows="10" id="approvecontent"></textarea></td>
          </tr>
          <tr class="table-back"> 
            <td  align="right" valign="top">&nbsp;</td>
            <td><input type="submit" name="Submit2" value="确定"> &nbsp;&nbsp; 
              <input type="button" name="Submit2" value="取消" onClick="javascript:history.back();"></td>
          </tr>
      
      </table>
        </form>
      </fieldset>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
