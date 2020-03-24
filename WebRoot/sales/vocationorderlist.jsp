<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
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
	OrderDetail();
	}

function addNew(){
	window.open("toAddVocationOrderAction.do","","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdVocationOrderAction.do?ID="+checkid,"","height=650,width=1024,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../sales/toReferSaleOrderAction.do?SaleID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleOrder(){
		if(checkid!=""){
			window.open("../sales/saleOrderAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function Complete(){
		if(checkid!=""){
			window.open("../sales/completeSaleOrderAction.do?slid="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delVocationOrderAction.do?SOID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function OrderDetail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/vocationOrderDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function CheckSaleOrder(){
		if(checkid!=""){
		window.open("toCheckSaleOrderAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectCustomer(){
	var c=showModalDialog("selectVocationOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
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
          <td width="772"> 行业零售单</td>
          <td width="772" valign="bottom">
		  <table width="120"  border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" class="back-gray"><a href="../sales/toAddVocationOrderAction.do">新增</a></td>
              <td align="center" class="back-gray"><a href="../sales/listVocationOrderAction.do">查询</a></td>
            </tr>
          </table>
		 </td>
  </tr>
</table>
<form name="search" method="post" action="listVocationOrderAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="9%"  align="right">会员名称：</td>
            <td width="20%"><input name="CID" type="hidden" id="CID" value="">
              <input name="cname" type="text" id="cname" value="" readonly>
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
            <td width="10%" align="right">是否复核：</td>
            <td width="31%">${isauditselect}</td>
            <td width="8%" align="right">是否作废： </td>
            <td width="22%">${isblankoutselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否结案： </td>
            <td>${isendcaseselect} </td>
            <td align="right">开票信息：</td>
            <td><select name="InvMsg" id="InvMsg">
              <option value="">所有</option>
              <logic:iterate id="iv" name="ivls">
                <option value="${iv.id}">${iv.ivname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">制单机构：</td>
            <td><select name="MakeOrganID" id="MakeOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
          </tr>
		  <tr class="table-back">
            <td  align="right">制单人：</td>
            <td><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
<input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12"></td>
            <td align="right">配送机构：</td>
            <td><select name="EquipOrganID" id="EquipOrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
		  </tr>		 
		  <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input type="text" name="KeyWord">
            <input type="submit" name="Submit2" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
		  </tr>		 
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="4%"  >编号</td>
            <td width="16%" >会员名称</td>
            <td width="15%" >制单机构</td>
            <td width="15%" >制单日期</td>
            <td width="6%" >总金额</td>
            <td width="8%" >是否复核</td>
            <td width="9%" >是否配送</td>
			<td width="9%" >是否作废</td>
            <td width="8%" >制单人</td>    
			<td width="10%" >检货状态</td>            
          </tr>
          <logic:iterate id="s" name="also" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');"> 
            <td  class="${s.isblankout==1?'text2-red':''}">${s.id}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.cname} <a href="../sales/listMemberAction.do?CID=${s.cid}"><img src="../images/CN/go.gif" width="10" height="10" border="0"></a></td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.makeorganidname}</td>
            <td class="${s.isblankout==1?'text2-red':''}">${s.makedate}</td>
            <td class="${s.isblankout==1?'text2-red':''}" align="right"><fmt:formatNumber value="${s.totalsum}" pattern="0.00"/></td>
            <td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isauditname}</td>
            <td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isendcasename}</td>
			<td class="${s.isblankout==1?'text2-red':''}" align="center">${s.isblankoutname}</td>
            <td class="${s.isblankout==1?'text2-red':''}" align="center">${s.makeidname} </td>  
			<td class="${s.isblankout==1?'text2-red':''}" align="center">${s.takestatusname} </td>            
          </tr>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
               <!-- <td width="60"><a href="javascript:Refer();">提交</a></td>-->
			  <!-- <td width="60"><a href="javascript:CheckSaleOrder();">核对</a></td> -->
               <!-- <td width="60"><a href="javascript:SaleOrder();">打印</a></td>-->
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listVocationOrderAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

<table width="62" border="0" cellpadding="0" cellspacing="1">
  <tr align="center" class="back-bntgray2">
    <td width="60" ><a href="javascript:OrderDetail();">详情</a></td>
    </tr>
</table>
</td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
