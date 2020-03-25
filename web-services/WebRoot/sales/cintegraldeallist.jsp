<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	function CheckedObj(obj,objid,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 
	}

	function addNew(){
		window.open("../sales/toAddDitchAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdDitchAction.do?id="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}



function SelectCustomer(){
	var c=showModalDialog("toSelectSaleOrderCustomerAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
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
    <td width="772">单据积分明细 </td>
  </tr>
</table>
 <form name="search" method="post" action="listCIntegralDealAllAction.do">
      <table width="100%" height="44"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right">会员：</td>
            <td width="24%"><input name="CID" type="hidden" id="CID" value="">
              <input name="cname" type="text" id="cname" value="" readonly>
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"> </a></td>
            <td width="10%" align="right">机构：</td>
            <td width="23%"><select name="OrganID" id="OrganID">
			 <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">积分类别：</td>
            <td width="25%">${isortselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="">
              <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="10%"   align="center">相关单据号</td>
            <td width="14%"  align="center">机构</td>
            <td width="11%"  align="center">会员</td>
			 <td width="13%"  align="center">手机</td>
            <td width="13%"  align="center">积分类别</td>
            <td width="11%"  align="center">应得积分</td>
            <td width="11%"  align="center">已得积分</td>
			<td width="17%"  align="center">制单日期</td>
          </tr>
		<c:set var="count" value="0"/>
		  <logic:iterate id="d" name="hList" >
		 
          <tr class="table-back" onClick="CheckedObj(this,'${d.id}','${d.billno}');"> 
            <td >${d.billno}</td>
            <td>${d.organidname}</td>
            <td>${d.cidname}</td>
			<td>${d.mobile}</td>
            <td>${d.isortname}</td>
            <td><fmt:formatNumber value="${d.dealintegral}" pattern="0.00"/></td>
            <td><fmt:formatNumber value="${d.completeintegral}" pattern="0.00"/></td>
			 <td>${d.makedate}</td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%">&nbsp;</td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listCIntegralDealAllAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

    </td>
  </tr>
</table>
</body>
</html>
