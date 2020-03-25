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
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	StuffShipmentDetail();
	}

	function addNew(){
	window.open("../warehouse/toAddStuffShipmentBillAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdStuffShipmentBillAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function StuffShipmentDetail(){
		if(checkid!=""){
			document.all.submsg.src="stuffShipmentBillDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../warehouse/toReferStuffShipmentBillAction.do?SSID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
			window.open("../warehouse/delStuffShipmentBillAction.do?SSID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleBill(){
		if(checkid!=""){
			window.open("../sales/saleBillAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772"> 材料出库单 </td>
  </tr>
</table>
<form name="search" method="post" action="../warehouse/listStuffShipmentBillAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="12%"  align="right">是否复核：</td>
            <td width="37%">${isauditselect}</td>
            <td width="11%" align="right">是否提交：</td>
            <td width="40%">${isreferselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">需求日期： </td>
            <td><input type="text" name="BeginDate" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="6%" >编号</td>
            <td width="17%" >出货仓库</td>
            <td width="19%" >出库类别</td>
            <td width="19%" >出库部门</td>
            <td width="19%" >需求日期</td>
            <td width="10%" >是否复核</td>
            <td width="10%" >是否提交</td>
          </tr>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td ><a href="javascript:Update();">${s.id}</a></td>
            <td >${s.warehouseidname}</td>
            <td >${s.shipmentsortname}</td>
            <td >${s.shipmentdeptname}</td>
            <td >${s.requiredate}</td>
            <td >${s.isauditname}</td>
            <td >${s.isrefername}</td>
            </tr>
		  </logic:iterate>
        
      </table>
      </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <!--<td width="60"><a href="javascript:Refer();">提交</a></td>-->
				<td width="60"><a href="javascript:Del();">删除</a></td>
                <!--<td width="60"><a href="javascript:SaleBill();">打印</a></td>-->
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/warehouse/listStuffShipmentBillAction.do"/>
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

<table width="87"  border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="85"><a href="javascript:StuffShipmentDetail();">材料出库详情</a></td>
  </tr>
</table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
