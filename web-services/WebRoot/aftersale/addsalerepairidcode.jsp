<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	function affirm(){
		var idcodes = document.listform.idcode;		
		var result = true;
		for(i=0;i<idcodes.length;i++){
			if (idcodes[i].value =="" ){
				alert("请录入序号!");
				result = false;
				break;				
			}
		}
		return result;
	}
	

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 录入序号</td>
	    
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back">
            <td width="8%"  align="left">
            产品ID：</td>
            <td width="13%">${pid.productid}</td>
			<td width="12%"   align="left">
            产品名称：</td>
            <td width="67%">${pid.productname}</td>
          </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="../aftersale/addSaleRepairIdcodeAction.do">
		
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <input name="piid" type="hidden"  value="${piid}">
		<input name="productid" type="hidden"  value="${pid.productid}">
		<input name="batch" type="hidden"  value="${batch}">
          <tr align="center" class="title-top">
		  	<td >编号</td> 
            <td >序号</td>                    
          </tr>
		 
		  
		  	<c:forEach items="${idcode}" var="pii" varStatus="vs">
            <tr align="center" class="table-back" >
			  <td>${vs.count}</td>
              <td ><input name="idcode" type="text" id="idcode" value="${pii}"></td>              
            </tr>
          	</c:forEach>
		 
          
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:if(affirm()) listform.submit();">提交</a></td>
                <td width="60">取消</td>
              </tr>
            </table></td>
          <td width="70%" align="right"> </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
