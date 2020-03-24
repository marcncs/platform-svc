<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html> 
<head>
<title>角色列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var checkid=0;
function CheckedObj(obj,objid){

 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 
  submenu = getCookie("roleMenu");
	  switch(submenu){
		case "1":
			if(document.getElementById("ListUsersUrl")){
				ListUsers();break;
			}
		//case "0":ListUsers(); break;
		
		default:
			if(document.getElementById("ListUsersUrl")){
				ListUsers();
			}
	 }

}

function AddNew(){
	popWin("../users/toAddUserGroupAction.do",500,250);
}

function UpdUserGroup(){
	if(checkid>0){
		popWin("../users/toUpdUserGroupAction.do?groupId="+checkid,500,250);
	}else{
		alert("请选择你要修改的记录!");
	}
}


function ListUsers(){
	setCookie("roleMenu","1");
	if(checkid>0){
		document.all.submsg.src="../users/listUserGroupAppAction.do?groupId="+checkid;
	}else{
		alert("请选择你要查询的记录!");
	}
}


function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin("../users/delUserGroupAction.do?groupId="+checkid,500,250);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">${menusTrace }</td>
      </tr>
    </table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<ws:hasAuth operationName="/users/toAddUserGroupAction.do">
			<td width="50" align="center">
				<a href="javascript:AddNew()"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/toUpdUserGroupAction.do">
			<td width="50" align="center">
				<a href="javascript:UpdUserGroup();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/delUserGroupAction.do">
			<td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			</ws:hasAuth>
		  <td class="SeparatePage"><pages:pager action="../users/listUserGroupAction.do"/></td>
		</tr>
	</table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		 <FORM METHOD="POST" name="listform" ACTION="">
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
         
            <tr align="center" class="title-top-lock">
              <td>编号</td>
              <td>组名称</td>
              <td>描述</td>
              <td>角色</td>
              <td>日期</td>
            </tr>
              <logic:iterate id="r" name="groupList" >
                <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${r.id});">
                  <td align="left">${r.id}</td>
                  <td align="left">${r.groupName}</td>
                  <td align="left">${r.description}</td>
                  <td align="left">${r.roleName}</td>
                  <td align="left"><windrp:dateformat value='${r.makeDate}' p='yyyy-MM-dd' /></td>
                </tr>
              </logic:iterate>
          
      </table>
      
     </form> 
	  <br>
    <div style="width:100%">
        <div id="tabs1">
          <ul>
            <%--
          	<ws:hasAuth operationName="/users/listOrganRoleAction.do">
          	<li><a href="javascript:ListOrgan();"><span>拥有机构</span></a></li>
          	</ws:hasAuth>
          	 --%>
          	<ws:hasAuth operationName="/users/listUserGroupAction.do">
            <li><a id="ListUsersUrl" href="javascript:ListUsers();"><span>用户类型</span></a></li>
            </ws:hasAuth>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
	</div>
      </td>
  </tr>
</table>
</body>
</html>

