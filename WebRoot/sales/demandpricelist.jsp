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
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
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
	window.open("toAddDemandPriceAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdDemandPriceAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delDemandPriceAction.do?DPID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/demandPriceDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectCustomer(){
	var c=showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.search.CID.value=c.cid;
	document.search.cname.value=c.cname;
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
          <td width="772"> 零售报价</td>
  </tr>
</table>
<form name="search" method="post" action="listDemandPriceAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="9%"  align="right">客户名称：</td>
            <td width="22%"><input name="CID" type="hidden" id="CID" value="">
              <input name="cname" type="text" id="cname" value="">
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
            <td width="9%" align="right">是否复核：</td>
            <td width="14%">${isauditselect}</td>
            <td width="9%" align="right">制单日期：</td>
            <td width="37%"><input type="text" name="BeginDate" onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" onFocus="javascript:selectDate(this)"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">所属用户：</td>
            <td><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select>
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
 </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="5%"  >编号</td>
            <td width="28%" >客户名称</td>
            <td width="14%" >报价单名称</td>
            <td width="16%" >总金额</td>
            <td width="12%" >是否复核</td>
            <td width="13%" >制单人</td>
            <td width="12%" >制单日期</td>
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td>${s.cidname} <a href="../sales/listCustomerAction.do?CID=${s.cid}"><img src="../images/CN/go.gif" width="10" height="10" border="0"></a></td>
            <td>${s.demandname}</td>
            <td>${s.totalsum}</td>
            <td>${s.isauditname}</td>
            <td>${s.makeidname}</td>
            <td>${s.makedate} </td>
            </tr>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listDemandPriceAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:Detail();">报价详情</a></td>
    </tr>
</table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
