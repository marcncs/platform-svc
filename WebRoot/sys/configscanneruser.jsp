<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>采集器用户设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	
	function addNew(){
		var uid = document.getElementById("uid").value;
		popWin("toAddScannerUserAction.do?uid="+uid+"&type=ADD",900,600);
	}
	
	function Update(){
		var uid = document.getElementById("uid").value;
		if(checkid!=""){
			popWin("toAddScannerUserAction.do?suid="+checkid+"&type=EDIT&uid="+uid,900,600);
		}else{
			alert("请选择你要操作的记录");
		}
	}
	function Delete(){
		var uid = document.getElementById("uid").value;
		if(checkid!=""){
			if(window.confirm("确定删除该条记录吗？")){
				popWin("toAddScannerUserAction.do?suid="+checkid+"&type=DELETE&uid="+uid,900,600);
			}
			
		}else{
			alert("请选择你要操作的记录");
		}
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">采集器用户设置</td>
        </tr>
      </table>
	 		 <table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50" align="center">
									<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
								<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
								<td width="50" align="center">
									<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
								<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
								<td width="50" align="center">
								<a href="javascript:Delete();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
				            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
								<td>&nbsp;</td>		
							</tr>
				</table>
		</div>
	 </td>
	</tr>
	<tr>
	 <td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
	<input id="uid" type="hidden" value="${uid }">
	<form method="post" name="listform" action="">
      <table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
           <td width="20%">用户编号</td>
            <td width="20%"  >用户名称</td>            
            <td width="20%" >采集器编号</td>
          </tr>
          <logic:iterate id="su" name="scanusers" >
            <tr  align="center"  class="table-back-colorbar" onClick="CheckedObj(this,'${su.id}');">           
			  <td>${su.userid }</td>
			 <td>${uname}</td>
             <td>${su.scanner}</td>
            </tr>
          </logic:iterate> 
      </table>      
     </form>
	  </div>
	  </td>
  </tr>
</table>

</body>
</html>
