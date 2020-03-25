<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDateTime.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
		this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}
		
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 submenu = getCookie("CookieProvide");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":TaskDetail(); break;
		case "2":AfficheExecuter(); break;
		default:TaskDetail();
	 }
	
	}
	
	function addNew(){
		popWin("../self/toaddTaskAction.do",1000,600);
	}

	function AllotTask(){
		if(checkid>0){
			popWin("../self/toAllotTaskAction.do?TaskID="+checkid,500,250);
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Update(){
		if(checkid>0){
			popWin("../self/toUpdTaskAction.do?ID="+checkid,1000,600);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的任务吗？如果删除将永远不能恢复!")){
				popWin2("../self/delTaskAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function TaskDetail(){
	setCookie("CookieProvide","1");
		if(checkid>0){
		document.all.submsg.src="../self/listTaskDetialAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function AfficheExecuter(){
	setCookie("CookieProvide","2");
		if(checkid>0){
		document.all.submsg.src="../self/listTaskExecuterAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function keypre(){
	return !(window.event && window.event.keyCode == 13);	
	}
	
	function SelectName(){
		
			var objsort = document.search.ObjSort;
			if(objsort.value ==1){
				SelectCustomer();
			}else{
				SelectOrgan();
			}
		}
	
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( c == undefined ){
				return;
			}
			document.search.CID.value=c.cid;
			document.search.CName.value=c.cname;
		}

		function SelectOrgan(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.search.CID.value=o.id;
			document.search.CName.value=o.organname;
		} 
	function SelectOrgan2(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.search.MakeOrganID.value=o.id;
			document.search.oname.value=o.organname;
			clearUser("MakeID","uname");
		} 
	
	</script>

	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									我的办公桌&gt;&gt;任务
								</td>
							</tr>
						</table>
<form name="search" method="post" action="listTaskAction.do">	
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="table-back">
													
							<tr>
								<td align="right" >状态：</td>
								<td><windrp:select key="TaskPlanStatus" name="Status" p="y|f" value="${Status}" /></td>
								<td align="right">完成状况：</td>
								<td><windrp:select key="OverStatus" name="OverStatus" p="y|d"  value="${OverStatus}"/></td>
								<td align="right">优先级： </td>
								<td ><windrp:select key="Priority" name="Priority" p="y|d" value="${Priority}" /></td>
							  </tr>
							  <tr class="table-back">
								<td align="right">
									制单机构：</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30"  value="${oname}"
									readonly><a href="javascript:SelectOrgan2();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>								</td>
								<td align="right">
									制单人：								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')"
										 readonly>								</td>
								<td align="right" >关键字： </td>
							  <td><input type="text" name="KeyWord" value="${KeyWord}"
										onKeyPress="return keypre();"></td>
								
							</tr>
                            <tr>
								<td width="8%" align="right">结束日期：</td>
								<td width="17%"><input type="text" name="ConclusionDate" size="10" value="${ConclusionDate}"
										onFocus="javascript:selectDate(this)" readonly="readonly">
-
  <input name="EndTime" type="text" id="EndTime" size="10" value="${EndTime}"
										onFocus="selectTime(this);" readonly="readonly"></td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="22%">&nbsp;</td>
								<td align="right">&nbsp;</td>
								
								<td class="SeparatePage">
								  <input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
								</td>
							  </tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr class="title-func-back">
								<td width="50">
									<a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../sys/listTaskAction.do" />
								</td>
						  </tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable" >
							<tr align="center" class="title-top">
								<td width="5%">
									编号
								</td>
								<!--<td style="width: 8%;">
									对象类型
								</td>
								<td style="width: 15%;">
									客户名称
								</td>-->
								<td>
									标题
								</td>
								<td style="width: 10%;">
									结束日期
								</td>
								<td style="width: 6%;">
									优先级
								</td>
								<td style="width: 8%;">
									完成状况
								</td>
								<td style="width: 15%;">
									制单机构
								</td>
								<td style="width: 10%;">
									制单人
								</td>
							</tr>
							<logic:iterate id="tp" name="tpfls">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${tp.id});">
									<td>
										${tp.id}
										<c:if test="${tp.isaffirm==0}">
											<font color="#FF0000" size="-4">新</font>
										</c:if>
									</td>
									<!--<td>
										<windrp:getname key="ObjSort" value="${tp.objsort}" p="f" />
									</td>
									<td>
										${tp.cname}
									</td>-->
									<td><div title="${tp.tptitle}" style="width:200; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${tp.tptitle}</div>
											
									</td>
									<td>
										<windrp:dateformat value='${tp.conclusiondate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='Priority' value='${tp.priority}' p='d' />
									</td>
									<td>
										<windrp:getname key='OverStatus' value='${tp.overstatus}' p='d' />
									</td>
									<td>
										<windrp:getname key='organ' value='${tp.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${tp.makeid}' p='d' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
						
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:TaskDetail();"><span>任务详情</span></a></li>
               <li><a href="javascript:AfficheExecuter();"><span>执行者</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg"
							style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
							name="submsg" src="../sys/remind.htm" frameBorder="0"
							scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
