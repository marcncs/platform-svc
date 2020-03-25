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
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
PurchaseIncomeDetail();
	}
	
	function addNew(){
	window.open("../purchase/toAddBorrowIncomeAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function ToInput(){
	window.open("../purchase/selectPurchaseOrderAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../purchase/toUpdBorrowIncomeAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function PurchaseIncomeDetail(){
		if(checkid!=""){
			document.all.submsg.src="borrowIncomeDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function TransWithdraw(){
		if(checkid!=""){
			window.open("../purchase/transWithdrawBorrowIncomeAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
			window.open("../purchase/delBorrowIncomeAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function Refer(){
		if(checkid!=""){
			window.open("../purchase/toReferPurchaseIncomeAction.do?PIID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SelectProvide(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.search.CID.value=getCookie("cid");
	document.search.providename.value=getCookie("cname");
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 借入单</td>
        </tr>
      </table>
       <form name="search" method="post" action="../purchase/listBorrowIncomeAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right">客户：</td>
            <td width="34%"><input name="CID" type="hidden" id="CID">
              <input name="providename" type="text" id="providename" size="35">
              <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="7%" align="right">仓库：</td>
            <td width="21%"><select name="WarehouseID" id="WarehouseID">
              <option value="" selected>所有仓库</option>
              <logic:iterate id="w" name="alw" >
                <option value="${w.id}">${w.warehousename}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">是否复核：</td>
            <td width="19%">${isauditselect}</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">入库日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)"></td>
            <td align="right">&nbsp;</td>
            <td><input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
      
      </table>
        </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="6%"  >编号</td>
            <td width="16%" >入库仓库</td>
            <td width="11%" > 采购订单编号</td>
            <td width="24%" > 客户</td>
            <td width="13%" >入库日期</td>
            <td width="8%" >是否复核</td>
            <td width="12%" >制单人</td>
          </tr>
          <logic:iterate id="pi" name="alpi" > 
          <tr class="table-back" onClick="CheckedObj(this,'${pi.id}');"> 
            <td >${pi.id}</td>
            <td>${pi.warehouseidname}</td>
            <td>${pi.poid}</td>
            <td>${pi.cname}</td>
            <td>${pi.incomedate}</td>
            <td>${pi.isauditname}</td>
            <td>${pi.makeidname}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <!--<td width="60"><a href="javascript:Refer();">提交</a></td>-->
                <!--<td width="60">打印</td>-->
                <td width="60"><a href="javascript:Del();">删除</a></td>
				<td width="60"><a href="javascript:TransWithdraw();">转退货</a></td>
              </tr>
            </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/purchase/listBorrowIncomeAction.do"/>          </td>
        </tr>
      </table>      
      <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:PurchaseIncomeDetail();">借入单详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
