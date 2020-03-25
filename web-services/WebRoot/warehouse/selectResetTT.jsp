<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="javascript" src="../js/jquery.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"> </script>
<script type="text/javascript">
	 var checkLeftid = "";
	 var leftProductName = "";
	 var leftQuantity = "";
	 var checkRightid = "";
	 var rightProductName = "";
	 var rightQuantity = "";
	 function CheckedObjLeft(obj,id,name,quantity){
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event"; 
		 checkLeftid = id;
		 leftProductName = name;
		 leftQuantity = quantity;
	 }
	 function CheckedObjRight(obj,id,name,quantity){
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event";
		 checkRightid = id;
		 rightProductName = name;
		 rightQuantity = quantity;
	 }
	 function submitTT(){
	 	if(checkLeftid == "" || checkRightid == ""){
	 		alert("请两边各选择一个单据进行匹配!");
	 		return;
	 	}
	 	 
	 	if(leftProductName != rightProductName){
	 		alert("选中的产品不匹配,请重新选择!");
	 		return;
	 	}
	 	if(leftQuantity != leftQuantity){
	 		alert("选中的产品总数不匹配,请重新选择!");
	 		return;
	 	}
	 	
	 	if(window.confirm(checkLeftid + " 和 " + checkLeftid +" 确认要匹配么?")){
	 		showloading();
	 		var action = "../warehouse/replaceTTidcode.do";
		 	$.getJSON(action, {checkLeftid: checkLeftid, checkRightid: checkRightid},function(json){
		 		var data = json.result;
		 		if(data == "success"){
		 			openwindow("../msgBox.do?result=databases.refer.success","MsgWindow",500,250);
		 		}else{
		 		}
		 		window.location.href = "../warehouse/selectAndResetTT.do"
			});
	 	}else{
	 		return;
	 	}
	 	
	 }
</script>
</head>

<body> 
	<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td colspan="3">
    	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
	        <tr> 
	          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
	          <td width="772"> 重置检货小票号</td>
		    
	        </tr>
        </table>
      </td>
      
      </tr>
      <tr>
	      <td width="58%" height="465px" valign="top">
	       		      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		        
		          <tr class="title-func-back">
		            <td width="22%"  align="left">
		              	现系统存在的检货小票</td>
		            
			  	  </tr>
		        
		      </table>
	     <div style="height: 440px; overflow-y: scroll">
	      <table width="100%" border="0" cellpadding="0" cellspacing="1">
	        
	          <tr align="center" class="title-top"> 
	            <td width="26%" >检货小票编号</td>
	            <td width="34%">客户名称</td> 
	            <td width="28%">产品名称</td> 
	            <td width="75px">产品总数</td> 
	          </tr>
	    	  <c:forEach var="leftTT" items="${existTT}">
	    	  	<tr class="table-back-colorbar" onClick="CheckedObjLeft(this,'${leftTT.id}','${leftTT.productNames}','${leftTT.totalQuantity}');">
	    	  		<td>${leftTT.id }</td>
	    	  		<td>${leftTT.oname}</td>
	    	  		<td>${leftTT.productNames}</td>
	    	  		<td>${leftTT.totalQuantity}</td>
	    	  	</tr>
	    	  </c:forEach>
	      </table>
	     </div>
		</td>
		<td width="6%" align="center">
			<input type="button" style="width: 40px;height: 40px;" value=" 重新&#10;匹配" onclick="submitTT();"/>
		</td>
		<td width="36%"  valign="top">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		        
		          <tr class="title-func-back">
		            <td width="22%"  align="left"><input name="pid" type="hidden" id="pid" value="${pid}">
		              上传入系统的错误单号检货小票</td>
		            
			  	  </tr>
		        
		      </table>
	      <div style="height: 440px; overflow-y: scroll">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
		        
		          <tr align="center" class="title-top"> 
		            <td width="43%" >检货小票编号</td> 
		            <td width="36%" >产品名称</td> 
		            <td width="75px" >产品总数</td> 
		            
		          </tr>
		     	  <c:forEach var="rightTT" items="${notExistTT}">
		    	  	<tr class="table-back-colorbar" onClick="CheckedObjRight(this,'${rightTT.id}','${rightTT.productNames}','${rightTT.totalQuantity}');">
		    	  		<td>${rightTT.id}</td>
		    	  		<td>${rightTT.productNames}</td>
		    	  		<td>${rightTT.totalQuantity}</td>
		    	  	</tr>
		    	  </c:forEach>
		      </table>
	      </div>
		</td>
	
</tr> 
</table>

</body>

</html>
