<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script src="../js/jquery-1.11.1.min.js"></script>
<script language="JavaScript">
var $j = jQuery.noConflict(true);
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0;
	var isLocked=0;
	function CheckedObj(obj,objid,objislocked){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 isLocked = objislocked;
	  submenu = getCookie("UsMenu");
	  switch(submenu){
	 	case 0 : 
		 	if(document.getElementById("DetailUrl")){
			 	Detail(); break;
			}
		case "1":
			if(document.getElementById("UserVisitUrl")){
				UserVisit(); break;
			}
		case "2":
			if(document.getElementById("MoveOrganUrl")){
				MoveOrgan();break;
			}
		case "3":
			if(document.getElementById("organVisitUrl")){
				organVisit();break;
			}
		case "5":
			if(document.getElementById("userCustomer")){
				userCustomer();break;
			}
		default:
			if(document.getElementById("DetailUrl")){
				Detail();
			}
	 }
	}
	
	function addNew(){
	popWin("../users/toAddUsersAction.do",900,600);
	}
	
	function UpdUsers(){
		if(checkid>0){
		popWin("toUpdUsersAction.do?uid="+checkid,900,600);
		}else{
		alert("请选择你要操作的记录!");
		}
		
	}
	
	function UserRole(){
		if(checkid>0){
		
		popWin("listUserRoleAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function ResetPWD(){
		if(checkid>0){
			popWin("toResetPwdAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function AssignAreas(){
		if(checkid>0){
		popWin("toAssignAreasAction.do?uid="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
		
	}
	
	function Detail(){
	setCookie("UsMenu","0");
		if(checkid>0){
			document.all.submsg.src="listUsersDetailAction.do?userid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function regionInfo() {
		setCookie("UsMenu","4");
		if (checkid>0) {
			$j('iframe#submsg').attr('src', 'userRegionPageAction.do?op=page&userid='+checkid);
		} else {
			alert("请选择你要操作的记录!");
		}
	}

	function userCustomer() {
		setCookie("UsMenu","5");
		if (checkid>0) {
			$j('iframe#submsg').attr('src', '../keyretailer/listUserCustomerAction.do?op=page&userid='+checkid);
		} else {
			alert("请选择你要操作的记录!");
		}
	}
	
	function UserVisit(){
	setCookie("UsMenu","1");
		if(checkid>0){
			document.all.submsg.src="..${ruleAuthUrl }?userid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	function MoveOrgan(){
	setCookie("UsMenu","2");
		if(checkid>0){
			document.all.submsg.src="listMoveCanuseOrganAction.do?UIDI="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function organVisit(){
		setCookie("UsMenu","3");
			if(checkid>0){
				document.all.submsg.src="..${visitAuthUrl }?userid="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
	function SetCall(){
		if(checkid>0){
			popWin("listUserCallEventAction.do?muid="+checkid,700,400);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function UnlockUsers(){
		if(checkid>0){
			if ( isLocked!=1 ){
				alert("该账号无需解锁！");
				return;
			}
			popWin2("../users/unlockUsersAction.do?ID="+checkid);
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid>0){
			if ( checkid==1 ){
				alert("对不起，系统管理员不能删除！");
				return;
			}
			if ( confirm("你确认要删除编号为:"+checkid+"的用户吗?如果删除将不能恢复!") ){
				popWin2("../users/delUsersAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.search.MakeOrganID.value=p.id;
document.search.oname.value=p.organname;
document.search.MakeDeptID.value="";
document.search.deptname.value="";

}


function ConfigScanner(){
	if(checkid>0){
		popWin("../sys/toConfigScannerUserAction.do?ID="+checkid,700,400);
	}else{
	alert("请选择要配置的用户!");
	}
}
function Import(){		
	popWin("../users/toImportUsersAction.do",500,300);
}
//加载事件
$j(function(){
	$j("#organType").change(changeOrganType);
	changeOrganType();
});

//机构类型change事件,根据机构类型显示不同的明细
function changeOrganType(){
	var organTypeVal = $j("#organType").val();
	if(organTypeVal == ""){
		// 请选择
		$j("#organModelTag").hide();
		$j("#organModel1").hide();
		$j("#organModel2").hide();
		$j("#organModel1").attr("disabled","disabled");
		$j("#organModel2").attr("disabled","disabled");
	}else if(organTypeVal == 1){
		// 工厂
		$j("#organModelTag").show();
		$j("#organModel1").show();
		$j("#organModel1").removeAttr("disabled");
		$j("#organModel2").hide();
		$j("#organModel2").attr("disabled","disabled");
	}else if(organTypeVal == 2){
		// 经销商
		$j("#organModelTag").show();
		$j("#organModel1").hide();
		$j("#organModel1").attr("disabled","disabled");
		$j("#organModel2").show();
		$j("#organModel2").removeAttr("disabled");
	}else{
		$j("#organModelTag").hide();
		$j("#organModel1").hide();
		$j("#organModel2").hide();
		$j("#organModel1").attr("disabled","disabled");
		$j("#organModel2").attr("disabled","disabled");
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
    <td width="772"> ${menusTrace }</td>
  </tr>
</table>
 <form name="search" method="post" action="listUsersAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          
          <tr class="table-back">
            <td width="8%" align="right">机构：</td>
            <td width="26%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="9%" align="right">是否可用：</td>
            <td width="24%"><windrp:select key="YesOrNo" name="Status" value="${Status}" p="y|f"/></td>
			<td align="right">
				用户类型：
			</td>
			<td>
				<select name="userType"> 
					<option value="">
						-请选择-
					</option>
					<logic:iterate id="lv" name="salesUserType">
					<option value="${lv.key}">${lv.value}</option>
					</logic:iterate>
				</select>
			</td>
			<td></td>
          </tr>
		  <tr class="table-back">
            <td width="8%" align="right">创建日期：</td>
            <td width="26%"><input type="text" name="BeginDate" value="${BeginDate}" onFocus="javascript:selectDate(this)" size="12" readonly="readonly">
-
  <input type="text" name="EndDate" value="${EndDate}" onFocus="javascript:selectDate(this)" size="12" readonly="readonly"></td>
            <td width="8%" align="right">关键字： </td>
            <td width="25%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
           
	    <td width="8%" align="right">是否锁定：</td>
        <td width="12%"><windrp:select key="YesOrNo" name="isLocked" value="${isLocked}" p="y|f"/></td>
     
			<td colspan="1"></td>
		  </tr>
		  
		  <tr class="table-back">
            <td align="right">
									机构类别：
								</td>
								<td>
									<windrp:select key="OrganType" name="organType" p="y|f"
														value="${organType}" />
								</td>
								<td id="organModelTag" style="display:none;" align="right">
									机构类型：
								</td>
								<td id="organModel1" style="display:none;">
									<windrp:select key="PlantType" name="organModel"  p="y|f" 
											value="${organModel}" />
								</td>
								<td id="organModel2" style="display:none;">
									<windrp:select key="DealerType" name="organModel" p="y|f"
										value="${organModel}" />
								</td>
     							<td align="right">是否CWID用户：</td>
       						    <td><windrp:select key="YesOrNo" name="isCwid" value="${isCwid}" p="y|f"/></td>
			<td colspan="3" class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
		  </tr>
        
      </table>
      </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<ws:hasAuth operationName="/users/toAddUsersAction.do">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/toUpdUsersAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:UpdUsers();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/delUsersAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            <td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
			</ws:hasAuth>	
			<ws:hasAuth operationName="/users/toResetPwdAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="70" align="center">
				<a href="javascript:ResetPWD();"><img src="../images/CN/setmima.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;重设密码</a></td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/listUserRoleAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="70" align="center"><a href="javascript:UserRole();"><img src="../images/CN/haveuser.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;拥有角色</a></td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/toImportUsersAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50"><a href="javascript:Import()"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导入 </a>
			</td>
			</ws:hasAuth>
			<ws:hasAuth operationName="/users/unlockUsersAction.do">
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="75" align="center">
				<a href="javascript:UnlockUsers();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;解锁账号</a></td>
			</ws:hasAuth>
			<%--
			<td width="100" align="center"><a href="javascript:SetCall();"><img src="../images/CN/setcallcent.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;呼叫查询设置</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
			<td width="90" align="center"><a href="javascript:ConfigScanner();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;配置采集器</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
			<td width="82" align="center">
				<a href="javascript:CopyOrganPrice();">机构价格克隆</a>	</td>
			--%>
		  <td class="SeparatePage"><pages:pager action="../users/listUsersAction.do"/>	</td>
							
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
        
          <tr align="center" class="title-top"> 
            <td>编号</td>
            <td>登录名</td>
            <td>真实姓名</td>
            <!-- td width="30px">性别</td -->
            <td>所属机构</td>
            <td>创建日期</td>
			<td>有效日期</td>
			<td>用户类型</td>
			<td>是否可用</td>
			<td>是否锁定</td>
			<td>是否CWID用户</td>
          </tr>
		  <logic:iterate id="u" name="usList" >
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${u.userid},${u.isLocked});"> 
            <td  align="left">${u.userid}</td>
            <td align="left">${u.loginname}</td>
            <td align="left">${u.realname}</td>
            <!-- 
            <td align="left"><windrp:getname key='Sex' value='${u.sex}' p='f'/></td>
             -->
            <td align="left"><windrp:getname key='organ' value='${u.makeorganid}' p='d'/></td>
            <td align="left"><windrp:dateformat value='${u.createdate}' /></td>
			<td align="left"><c:if test="${u.validate!=null}"><windrp:dateformat value='${u.validate}' p='yyyy-MM-dd'/></c:if>
	    	<c:if test="${u.validate==null}">永久</c:if></td>
			<td align="left">${u.userTypeName}</td>
			<td align="left"><windrp:getname key='YesOrNo' value='${u.status}' p='f'/></td>
			<td align="left"><windrp:getname key='YesOrNo' value='${u.isLocked}' p='f'/></td>
			<td align="left"><windrp:getname key='YesOrNo' value='${u.isCwid}' p='f'/></td>
          </tr>
          </logic:iterate> 
       
      </table>
 </form>
	<br>
    <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a id="DetailUrl" href="javascript:Detail();"><span>详情</span></a></li>
            <ws:hasAuth operationName="${ruleAuthUrl }">
            	<li><a id="UserVisitUrl" href="javascript:UserVisit();"><span>管辖权限</span></a></li>
            </ws:hasAuth>
            <ws:hasAuth operationName="${visitAuthUrl }">
            	<li><a id="organVisitUrl" href="javascript:organVisit();"><span>业务往来权限</span></a></li>
            </ws:hasAuth>
            <li><a id="regionUrl" href="javascript:regionInfo();"><span>关联区域</span></a></li>
            <ws:hasAuth operationName="/keyretailer/listUserCustomerAction.do">
            <li><a id="customerUrl" href="javascript:userCustomer();"><span>关联客户</span></a></li>
            </ws:hasAuth>
            <%--
            <ws:hasAuth operationName="/users/listMoveCanuseOrganAction.do">
            <li><a id="MoveOrganUrl" href="javascript:MoveOrgan();"><span>转仓机构</span></a></li>
            </ws:hasAuth>--%>
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
