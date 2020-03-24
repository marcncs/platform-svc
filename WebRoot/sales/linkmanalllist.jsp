<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function addNew(){
	popWin("../sales/toAddLinkManAction.do",900,600);
	}

	function Update(){
		if(checkid>0){
			popWin("updLinkManAction.do?id="+checkid,900,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除编号为："+checkid+" 的联系人记录吗？如果删除将永远不能恢复!")){
			popWin2("../sales/delLinkmanAction.do?LID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Detail(){
		if(checkid!=""){
		document.all.submsg.src="linkManDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectCustomer(){	
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( c == undefined ){
		return;
	}
	document.search.CID.value=c.cid;
	document.search.cname.value=c.cname;
}
	
	this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									会员管理>>联系人
								</td>
							</tr>
						</table>
<form name="search" method="post"
								action="listLinkManAllAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									客户：
								</td>
								<td>
									<input name="CID" type="hidden" id="CID" value="${CID}">
									<input name="cname" type="text" id="cname"
									 value="${cname}"><a href="javascript:SelectCustomer();"><img b
											src="../images/CN/find.gif" width="18" height="18" border="0">
									</a>
								</td>
								<td align="right">
									生日：
								</td>
								<td>
									<input type="text" name="BeginDate" size="10" value="${BeginDate}"
										onFocus="javascript:selectDate(this)">
									-
									<input type="text" name="EndDate" size="10" value="${EndDate}"
										onFocus="javascript:selectDate(this)">
								</td>
								<td align="right">
									性别：
								</td>
								<td>
									<windrp:select key="Sex" name="Sex" p="y|f" value="${Sex}"/>
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${keyword}">
									
								</td>
								<td align="right">
									<input type="submit" name="Submit" value="查询">
								</td>
						
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="javascript:addNew();"><img
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
								<td style="text-align: right;">
									<pages:pager action="../sales/listLinkManAllAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							
							<tr align="center" class="title-top-lock">
								<td width="15%">
									联系人姓名
								</td>
								<td width="8%">
									联系人性别
								</td>
								<td width="25%">
									客户名称
								</td>
								<td width="9%">
									部门
								</td>
								<td width="5%">
									职务
								</td>
								<td width="12%">
									办公电话
								</td>
								<td width="16%">
									手机
								</td>
								<td width="10%">
									是否主联系人
								</td>
							</tr>
							<c:set var="count" value="0" />
							<logic:iterate id="l" name="usList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${l.id});">
									<td>
										${l.name}
									</td>
									<td>
										${l.sexname}
									</td>
									<td>
										${l.name}
										<a href="../sales/listMemberAction.do?CID=${l.cid}"><img
												src="../images/CN/go.gif" width="10" height="10" border="0">
										</a>
									</td>
									<td>
										${l.department}
									</td>
									<td>
										${l.duty}
									</td>
									<td>
										${l.officetel}
									</td>
									<td>
										${l.mobile}
									</td>
									<td>
										${l.ismainname}
									</td>
								</tr>
								<c:set var="count" value="${count+1}" />
							</logic:iterate>

							
						</table>
						</form>
						<br />
									<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>联系人详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
					</div>
				</td>
			</tr>
		</table>


	</body>
</html>
