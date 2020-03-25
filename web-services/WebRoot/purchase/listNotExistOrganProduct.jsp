<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script src="../js/function.js"></script>
<script type="text/javascript" src="../js/jquery.js"></script>
</head>
<script language="javascript">

	
//表单提交事件
function formcheck(){
	var parray=new Array(); 
	$("input:checkbox").each(function(i){
		 
		if(this.name == "rid" && this.checked == true){
 			parray.push(this.value);
 		}
	}); 
	formSubmit.array.value=parray;
	if(parray.length <= 0){
		alert("请勾选机构!");
		return false;
	}else{
		showloading();
		return true;
	}
	
}
 
 
	//全选框点击事件
	function check(){ 
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
		 
	}
 	
 	function changeCheckAll(){
 		setCheckAll();
 	}
 	//设置全选框
 	function setCheckAll(){
 		var allCheckFlag = true;
 		$("#form1").find("input:checkbox").each(function(i){
 			if(this.name == "rid" && this.checked == false){
 				  allCheckFlag = false;
 			}
 		}); 
 		if(allCheckFlag == true){
 			$("#checkAll").attr('checked','checked');
 		}else{
 			$("#checkAll").removeAttr('checked');
 		}
 		
 	}
</script>
<body>
	
	<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
		<tr>
		    <td colspan="3">
		    	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
			        <tr> 
			           
			          <td width="772">未上架机构列表</td>
				    
			        </tr>
		        </table>
		      </td>
	      </tr>
	      <tr>
	      	<td colspan="3">
	      		<div style="height: 326px; overflow-y: scroll">
	      		<form id="form1" action="">
	      		<table width="100%" border="0" cellpadding="0" cellspacing="1">
	      			<tr align="center" class="title-top"> 
			    		<td width="20px"><input type="checkbox" id="checkAll" name="checkall" onClick="check();" /></td>
			    		<td>机构编号</td>
			    		<td>机构名称</td>
			    		<td>上级机构编号</td> 
			    	</tr>
			    	<c:forEach var="organ" items="${notExistOrganList}">
		        		<tr>
		        			<td><input type="checkbox" id="chk_${organ.id}" name="rid" value="${organ.id}" onclick="changeCheckAll()"/></td>
		        			<td>${organ.id }</td>
		        			<td>${organ.organname}</td>
		        			<td>${organ.parentid}</td>
		        	 	</tr>
		        	</c:forEach>
			    </table>
			    </form>
			    </div>
	      	</td>
	      </tr>
	      <tr height="35px" valign="middle" align="center">
	      	<td>
	      		<form action="../purchase/addOrganProduct.do" method="post"  name="formSubmit" onSubmit="return formcheck();">
	      			<input type="submit" name="Submit" value="上架" />
	      			<input type="button" value="取消" onClick="window.close();"/>
	      			<input type="hidden" name="array"/>
	      			<input type="hidden" name="organProductId" value="${organProductId}"/>
	              	
	      		</form>
	      	</td>
	      </tr>
	</table>
	
</body>
</html>
