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
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
		popWin("toAddContactAction.do?objsort="+${objsort},800,500);
		
	}

	function Update(){
		if(checkid>0){
			popWin("toUpdContactLogAction.do?id="+checkid,800,500);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的商务联系记录吗？如果删除将永远不能恢复!")){
					popWin2("delContactLogAction.do?CLID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

function Detail(){
		if(checkid!=""){
		document.all.submsg.src="contactLogDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectName(){
		var objsort = document.search.objsort;
		if(objsort.value ==1){
			SelectCustomer();
		}else{
			SelectOrgan2();
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
	
	function SelectOrgan2(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.search.CID.value=o.id;
		document.search.cname.value=o.organname;
	} 
	this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.search.MakeOrganID.value=p.id;
		document.search.oname.value=p.organname;
		clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
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
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									<c:if test="${objsort==1 }">
									会员管理>>商务联系
								</c:if>
									<c:if test="${objsort==0 }">
									渠道管理>>商务联系
								</c:if>
								</td>
							</tr>
						</table>
<form name="search" method="post"
								action="listContactLogAction.do">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0">
							
							<tr class="table-back">
								<td width="10%" align="right">
									对象名称：
									  <input type="hidden" id="objsort" name="objsort" value="${objsort }">
								</td>
								<td>
									<input name="CID" type="hidden" id="CID" value="${CID}">
									<input name="cname" type="text" id="cname" value="${cname}" readonly="readonly" ><a href="javascript:SelectName();"><img
											align="absmiddle" src="../images/CN/find.gif" width="18" height="18" border="0">
									</a>
								</td>
								<td width="100" align="right">
									下次联系时间：
								</td>
								<td >
									<input type="text" name="BeginDate" value="${BeginDate}"
										size="10" onFocus="javascript:selectDate(this)" readonly="readonly">
									-
									<input type="text" name="EndDate" value="${EndDate}" size="10"
										onFocus="javascript:selectDate(this)" readonly="readonly">
								</td>
								<td align="right">
									联系方式：
								</td>
								<td>
									<windrp:select key="ContactMode" name="ContactMode" p="y|f" value="${ContactMode}" />
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
<input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									制单部门：
								</td>
								<td>
									<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname"
									onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"
									value="${deptname}" readonly>
								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">

									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F(MakeDeptID),'du')"
										value="${uname}" readonly>
								</td>
							</tr>
							<tr class="table-back">
								
								<td align="right">
									联系性质：
								</td>
								<td>
									<windrp:select key="ContactProperty" name="ContactProperty"
										p="y|f" value="${ContactProperty}" />
								</td>
								<td align="right">
									内容关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">
								</td>
								<td></td>
								<td class="SeparatePage" >
									<input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
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
								<td class="SeparatePage">
									<pages:pager action="../sales/listContactLogAction.do" />
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
							
							<tr align="center" class="title-top-lock"
								style="border-top: none 1px #F0F0F0;">
								<td width="50">
									编号
								</td>
								<td width="75">
									对象类型
								</td>
								<td>
									对象名称
								</td>
								<td>
									联系人
								</td>
								<td>
									联系日期
								</td>
								<td>
									联系方式
								</td>
								<td>
									联系性质
								</td>
								<td>
									下次联系时间
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单人
								</td>
							</tr>
							<c:set var="count" value="0" />
							<logic:iterate id="c" name="usList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${c.id});">
									<td>
										${c.id}
									</td>
									<td>
										<windrp:getname key="ObjSort" p="f" value="${c.objsort}" />
									</td>
									<td>
										${c.cidname}
									</td>
									<td>
										${c.linkman}
									</td>
									<td>
										${c.contactdate}
									</td>
									<td>
										<windrp:getname key='ContactMode' value='${c.contactmode}'
											p='f' />
									</td>
									<td>
										<windrp:getname key='ContactProperty'
											value='${c.contactproperty}' p='f' />
									</td>
									<td>
										${c.nextcontact}
									</td>
									<td>
										<windrp:getname key='organ' value='${c.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${c.userid}' p='d' />
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
              <li><a href="javascript:Detail();"><span>商务联系详情</span></a></li>
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
