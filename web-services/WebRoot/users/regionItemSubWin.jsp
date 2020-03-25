<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">

function submit() {
	if ($('input[name="areaId"]:checked').size() == 0) {
		alert('请选择要增加的区域！');
		return;
	}
	$('form#listform').submit();
}

function checkAll() {
	var ck = $('input[name="checkall"]').prop('checked');
	if (ck) {
		$('input[name="areaId"]').prop('checked', true);
	} else {
		$('input[name="areaId"]').prop('checked', false);
	}
}
function selectAll() {
	$('input[name="areaId"]').prop('checked', true);
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	 <form name="search" method="post" action="../users/userRegionPageAction.do?op=addPage">
	 	<input type="hidden" name="userId" value="${requestScope.userId}">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back">
            <td width="16%" align="right">关键字：</td>
            <td width="16%"><input type="text" name="keyword" value="${requestScope.keyword}"></td>         
            <td width="26%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
	  	  </tr>
       
      </table>
       </form>
	  <%-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		  <td style="text-align:right"><pages:pager action="../sys/toAddRegionAreaAction.do"/></td>						
		</tr>
	</table>	 --%>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 300px;" >
		 <FORM id="listform" METHOD="POST" name="listform" ACTION="../users/userRegionPageAction.do?op=add"  >    
		 		<input type="hidden" name="userId" value="${requestScope.userId}">
		      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">  
		          <tr align="center" class="title-top-lock"> 
			  	    <td width="4%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="checkAll();" ></td>
		            <td width="20%" height="25">区域编号</td>
		            <td width="30%">区域名称</td>
		          </tr>
		          <c:forEach var="item" items="${regions}"> 
				  <tr align="center" class="table-back-colorbar" > 
				  	<td> <input type="checkbox" name="areaId" value="${item.areaId}"></td>
		            <td height="20">${item.areaId}</td>
		            <td>${item.name}</td>      
		            </tr>
		          </c:forEach> 
				  
		      </table>
		</form>
	  </div>   	
	</td>
  </tr>
</table>
<br/>	
<table width="100%" border="0" cellpadding="0" cellspacing="1">
		    
            <tr>
              <td><div align="center">
  				<input type="button" name="Submit" value="确定" onClick="submit()">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();"><input type="hidden" name="speedstr">
  				&nbsp;&nbsp;<input type="button" name="button1" value="全部选中" onclick="selectAll()">&nbsp;&nbsp;
              </div></td>
            </tr>
			 
          </table>

</body>
</html>
