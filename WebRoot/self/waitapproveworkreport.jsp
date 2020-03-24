<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	WorkReportDetail();
	}
	
	function WorkReportDetail(){
		if(checkid>0){
		document.all.submsg.src="../self/workReportDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Approve(){
		if(checkid>0){
			document.all.submsg.src="../self/toApproveWorkReportAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}


</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 工作报告</td>
        </tr>
      </table>
       <form name="search" method="post" action="waitApproveWorkReportAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="9%"  align="right">是否审阅：</td>
            <td width="17%" >${approveselect}</td>
            <td width="10%"  align="right">关键字：</td>
            <td width="64%" > 
              <input type="text" name="KeyWord" value="${keyword}">
            </td>
          </tr>
          <tr class="table-back">
            <td  align="right">报告分类：</td>
            <td >${sortselect}</td>
            <td  align="right">&nbsp;</td>
            <td ><input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="10%">报告人</td>
            <td width="48%">内容</td>
            <td width="11%">报告分类</td>
            <td width="9%">报告日期</td>
            <td width="9%">是否审阅</td>
          </tr>
          <logic:iterate id="wr" name="arwr" > 
          <tr class="table-back" onClick="CheckedObj(this,${wr.id});"> 
            <td >${wr.id}</td>
            <td>${wr.createuser}</td>
            <td><a href="../self/workReportDetailAction.do?ID=${wr.id}">${wr.reportcontent}</a></td>
            <td>${wr.reportsort}</td>
            <td>${wr.referdate}</td>
            <td>${wr.approvestatus}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="42%">
		  </td>
          <td width="58%" align="right"> <presentation:pagination target="/self/waitApproveWorkReportAction.do"/>          </td>
        </tr>
      </table>
      <table width="157" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="92" ><a href="javascript:WorkReportDetail();">工作报告详情</a></td>
		  <td><a href="javascript:Approve();">审阅</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
