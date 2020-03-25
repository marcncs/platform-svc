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
	AdsumGoodsDetail();
	}
	
	function addNew(){
	window.open("toAddAdsumGoodsAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function ToInput(){
	window.open("selectPurchaseBillAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../purchase/toUpdAdsumGoodsAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function AdsumGoodsDetail(){
		if(checkid!=""){
			document.all.submsg.src="adsumGoodsDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../purchase/toReferAdsumGoodsAction.do?AGID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function SelectProvide(){
	var p=showModalDialog("selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	

	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
		window.open("../purchase/delAdsumGoodsAction.do?ID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
		}else{
			alert("请选择你要操作的记录!");
		}
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
          <td width="772"> 到货通知 </td>
        </tr>
      </table>
      <form name="search" method="post" action="listAdsumGoodsAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%" align="right">供应商：</td>
            <td width="27%" ><input name="PID" type="hidden" id="PID">
                <input name="ProvideName" type="text" id="ProvideName" size="40">
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="8%" align="right">是否复核：</td>
            <td width="10%">${isauditselect}</td>
            <td width="10%" align="right">到货日期：</td>
            <td width="36%"><input name="BeginDate" type="text" id="BeginDate" value="${begindate}" size="12" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" value="${enddate}" size="12" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="7%" >编号</td>
            <td width="35%" >供应商</td>
            <td width="9%" > 金额</td>
            <td width="24%" >到货日期</td>
            <td width="11%" >是否复核</td>
          </tr>
          <logic:iterate id="p" name="alpb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}');"> 
            <td ><a href="javascript:Update();">${p.id}</a></td>
            <td>${p.pidname}</td>
            <td>${p.totalsum}</td>
            <td>${p.receivedate}</td>
            <td>${p.isauditname}</td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table width="180"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <!--<td width="60"><a href="javascript:Refer();">提交</a></td>-->
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/purchase/listAdsumGoodsAction.do"/>          </td>
        </tr>
      </table>
      <table width="87"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:AdsumGoodsDetail();">到货通知详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
