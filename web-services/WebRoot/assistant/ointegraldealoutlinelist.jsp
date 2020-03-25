<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

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
	function CheckedObj(obj,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 //checkid=objid;
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
    <td width="772"> 机构积分查询 </td>
  </tr>
</table>
<form name="search" method="post" action="../assistant/listOIntegralDealOutlineAction.do">
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="10%"  align="right">机构：</td>
            <td width="24%"><select name="OID" id="OID">
              <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols" >
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="10%" align="right">关键字：</td>
            <td width="23%"><input type="text" name="KeyWord" value="">
              <input type="submit" name="Submit" value="查询"></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="25%">&nbsp;</td>
          </tr>
        
      </table>
      </form>
 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="14%"   align="center">相关单据号</td>
            <td width="20%"  align="center">机构</td>
            <td width="12%"  align="center">应得积分</td>
            <td width="16%"  align="center">已得积分</td>
            <td width="16%"  align="center">制单日期</td>
          </tr>
		<c:set var="count" value="0"/>
		  <logic:iterate id="d" name="hList" >
		 
          <tr class="table-back" onClick="CheckedObj(this,'${d.billno}');"> 
            <td >${d.billno}</td>
            <td><a href="../assistant/listOIntegralDealAllAction.do?OID=${d.oid}">${d.oidname}</a></td>
            <td>${d.dealintegral}</td>
            <td>${d.completeintegral}</td>
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
			<presentation:pagination target="/assistant/listOIntegralDealOutlineAction.do"/>	
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
