<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}

function addNew(){
	window.open("toAddSampleBillAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdSampleBillAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

	function SampleBill(){
		if(checkid!=""){
			window.open("../sales/sampleBillAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delSampleBillAction.do?SBID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	//function SampleBillDetail(){
	//	if(checkid!=""){
	//	document.all.submsg.src="../sales/sampleBillDetailAction.do?ID="+checkid;
	//	}else{
	//	alert("请选择你要操作的记录!");
	//	}
	//}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/sampleBillDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 样品记录</td>
  </tr>
</table>
 <form name="search" method="post" action="listSampleBillByCustomerAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%" height="20" align="right"><input name="CID" type="hidden" id="CID" value="${cid}">
            是否复核：              </td>
            <td width="23%">${isauditselect}</td>
            <td width="11%" align="right">制单日期：</td>
            <td width="56%"><input type="text" name="BeginDate" size="12" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="6%"  height="25">订单编号</td>
            <td width="22%" >客户名称</td>
            <td width="10%" >联系人</td>
            <td width="15%" >联系电话</td>
            <td width="12%" >发货日期</td>
            <td width="8%" >是否复核</td>
            <td width="8%" >制单人</td>
            <td width="8%" >制单日期</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td height="20">${s.id}</td>
            <td>${s.cidname}</td>
            <td>${s.linkman}</td>
            <td>${s.tel}</td>
            <td>${s.shipmentdate}</td>
            <td>${s.isauditname}</td>
            <td>${s.makeidname}</td>
            <td>${s.makedate}</td>
          </tr>
		   <c:set var="count" value="${count+1}"/>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table height="25" border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
                <!--<td width="60"><a href="javascript:SaleOrder();">打印</a></td>-->
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listSampleBillByCustomerAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

    <table width="80" height="25" border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="80"><c:if test="${count>0}">
            <a href="javascript:Detail();">
           
              样品记录详情</a> </c:if></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 65%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
