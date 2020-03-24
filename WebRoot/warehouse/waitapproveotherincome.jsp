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
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
var actid=0;
	function CheckedObj(obj,objid,oactid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 actid = oactid;
	
	}


	function Approve(){
		if(checkid!=""){	
			window.open("../warehouse/toApproveOtherIncomeAction.do?ID="+checkid+"&actid="+actid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function CancelApprove(){
		if(checkid!=""){
			window.open("../warehouse/cancelApproveOtherIncomeAction.do?OIID="+checkid+"&actid="+actid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772">盘盈单</td>
        </tr>
      </table>
              <form name="search" method="post" action="../warehouse/waitProductIncomeAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back"> 
            <td width="9%"  align="right">审阅状态：</td>
            <td width="17%" >${approveselect}</td>
            <td width="10%"  align="right">申请时间段：</td>
            <td width="64%" > 
              <input type="text" name="BeginDate" onFocus="javascript:selectDate(this)">
-
<input type="text" name="EndDate" onFocus="javascript:selectDate(this)">
              <input type="submit" name="Submit" value="查询">
            </td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="6%" >编号</td>
            <td width="15%">入货仓库</td>
            <td width="16%">入库类别</td>
            <td width="18%">入库部门</td>
            <td width="19%">入库日期</td>
            <td width="12%">审阅动作</td>
            <td width="14%">是否审阅</td>
          </tr>
          <logic:iterate id="pi" name="arpb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${pi.id}','${pi.actid}');"> 
            <td  ><a href="../warehouse/otherIncomeDetailAction.do?ID=${pi.id}">${pi.id}</a></td>
            <td>${pi.warehouseidname}</td>
            <td>${pi.incomesortname}</td>
            <td>${pi.incomedeptname}</td>
            <td>${pi.incomedate}</td>
            <td>${pi.actidname}</td>
            <td>${pi.approvestatusname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="42%">
<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Approve();">审阅</a></td>
                <td width="60"><a href="javascript:CancelApprove();">取消审阅</a></td>
                <td width="60">&nbsp;</td>
              </tr>
            </table></td>
          <td width="58%" align="right"> <presentation:pagination target="/warehouse/waitProductIncomeAction.do"/>          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
