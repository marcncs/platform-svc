<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}
	
	function ToSet(){
			popWin("toSetSafetyAwakeAction.do?OID=${OID}",700,400);
	}
	
function Delete(){
	if(checkid!=""){
		if(window.confirm("您确认要删除 编号为："+checkid+" 的记录吗？如果删除将永远不能恢复!")){
			popWin2("../users/delOrganAwakeAction.do?ID="+checkid);
		}			
	}else{
		alert("请选择你要操作的记录!");
	}
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 报警提醒人</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">
				<td width="50" align="center">
					<a href="javascript:ToSet();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;设置</a></td>
			  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td width="50"><a href="javascript:Delete();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
			  <td style="text-align:right"><pages:pager action="../users/listOrganAwakeAction.do"/></td>
								
			</tr>
		</table>
		 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
           <td  width="70">编号</td>
            <td  >用户编号</td>
            <td >用户名称</td>
          </tr>
          <logic:iterate id="p" name="alpl" >
		  
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${p.id});"> 
         	<td>${p.id}</td>
            <td >${p.userid}</td>
            <td><windrp:getname key='users' value='${p.userid}' p='d'/></td>
            </tr>
          </logic:iterate> 
        
      </table>
      </form>
      </td>
  </tr>
</table>
</body>
</html>
