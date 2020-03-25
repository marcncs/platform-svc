<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery.tablegrid.js"></script>
		<script type="text/javascript"> 
		
		$(document).ready(function(){
		    $("#grid").tablegrid();
		})
		
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
				popWin("../self/toAddServiceAgreementAction.do",1000,600);
		}
	
		function Update(){
			if(checkid>0){
				popWin("toUpdServiceAgreementAction.do?id="+checkid,1000,600);
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function Del(){
			if(checkid>0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的服务预约吗？如果删除将永远不能恢复!")){
					popWin2("../self/delServiceAgreementAction.do?ID="+checkid);
				}
			}else{
			alert("请选择你要操作的记录!");
			}
		}
		
		function Detail(id){
			if(checkid!=""){
				document.all.submsg.src="../self/serviceAgreementDetailAction.do?id="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function Allot(){
			if(checkid>0){
				popWin("../self/toAllotServiceAction.do?ID="+checkid,470,250);
			}else{
			alert("请选择你要操作的记录!");
			}
		}
		function SelectName(){
			
				var objsort = document.search.ObjSort;
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
		
		
		function SelectOrgan(){
				var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
				if ( p==undefined){return;}
					document.search.MakeOrganID.value=p.id;
					document.search.oname.value=p.organname;
			}
		
	
		this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}
	
</script>

	</head>

	<body>

		<div id="div1">
			<table width="100%" height="40" border="0" cellpadding="0"
				cellspacing="0" class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td width="772">
						我的办公桌>>服务预约
					</td>
				</tr>
			</table>
<form name="search" method="post"
					action="listServiceAgreementAllAction.do">
			<table width="100%" height="60" border="0" cellpadding="0"
				cellspacing="0">
				
				<tr class="table-back">
					<td align="right">
						对象类型：
					</td>
					<td>
						<windrp:select key="ObjSort" name="ObjSort" p="y|f" value="${ObjSort}" />
					</td>
					<td  align="right">
						对象名称：
					</td>
					<td >
						<input name="CID" type="hidden" id="CID" value="${CId}">
						<input name="cname" type="text" id="cname" value="${cname}"><a href="javascript:SelectName();"><img align="absmiddle"
								src="../images/CN/find.gif" width="18" height="18" border="0">
						</a>
					</td>
					<td  align="right">
						服务种类：
					</td>
					<td >
						<windrp:select key="HapContent" name="SContent" p="y|d" value="${SContent}" />
					</td>
				</tr>
				<tr class="table-back">
					<td align="right">
						服务状态：
					</td>
					<td style="padding-right: 5px;">
						<windrp:select key="SStatus" name="SStatus" p="y|d" value="${SStatus}" />
					</td>
					<td align="right">
						等级：
					</td>
					<td>
						<windrp:select key="Rank" name="Rank" p="y|d" value="${Rank}" />
					</td>
					<td align="right">
						是否分配：
					</td>
					<td>
						<windrp:select key="IsAllot" name="IsAllot" p="y|f"  value="${IsAllot}"/>
					</td>

				</tr>
				<tr class="table-back">
					<td align="right">
						制单机构：
					</td>
					<td>
						<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30"  value="${oname}"
									readonly><a href="javascript:SelectOrgan();"><img
								src="../images/CN/find.gif" width="18" height="18" border="0"
								align="absmiddle"> </a>
					</td>
					
					<td align="right">
						约定时间：
					</td>
					<td >
						<input type="text" name="BeginDate" value="${BeginDate}" size="10"
							onFocus="javascript:selectDate(this)" readonly="readonly">
						-
						<input type="text" name="EndDate" value="${EndDate}" size="10"
							onFocus="javascript:selectDate(this)" readonly="readonly">
					</td>
					<td class="SeparatePage" colspan="2">
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
								src="../images/CN/addnew.gif" width="16" height="16" border="0"
								align="absmiddle">&nbsp;新增</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td width="50">
						<a href="javascript:Update();"><img
								src="../images/CN/update.gif" width="16" height="16" border="0"
								align="absmiddle">&nbsp;修改</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td width="50">
						<a href="javascript:Del()"><img src="../images/CN/delete.gif"
								width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a>
					</td>
					<td width="1">
						<img src="../images/CN/hline.gif" width="2" height="14">
					</td>
					<td class="SeparatePage">
						<pages:pager action="../self/listServiceAgreementAllAction.do" />
					</td>
				</tr>
			</table>
		</div>
		<div id="abc"  style="overflow-y: auto; height: 600px; width: 100%">
			<div style="overflow-x: auto; width: 100%; padding-bottom: 20px;">
				<table id="grid" width="100%" border="0" cellpadding="0"
					cellspacing="1" class="sortable">
					<tr align="center" class="title-top">
						<td>
							编号
						</td>
						<td>
							对象类型
						</td>
						<td>
							对象名称
						</td>
						<td>
							服务种类
						</td>
						<td>
							服务状态
						</td>
						<td>
							等级
						</td>
						<td>
							约定服务时间
						</td>
						<td>
							制单机构
						</td>
						<td>
							制单人
						</td>
					</tr>
					<logic:iterate id="p" name="hList">
						<tr class="table-back-colorbar"
							onClick="CheckedObj(this,${p.id});">
							<td>
								${p.id}
								<c:if test="${p.isaffirm==0}">
									<font color="#FF0000" size="-4">新</font>
								</c:if>
							</td>
							<td>
								<windrp:getname key='ObjSort' value='${p.objsort}' p='f' />
							</td>
							<td>
								${p.cname}
							</td>
							<td>
								<windrp:getname key='SContent' value='${p.scontent}' p='d' />
							</td>
							<td>
								<windrp:getname key='SStatus' value='${p.sstatus}' p='d' />
							</td>
							<td>
								<windrp:getname key='Rank' value='${p.rank}' p='d' />

							</td>
							<td>
								<windrp:dateformat value='${p.sdate}' p='yyyy-MM-dd' />
							</td>
							<td>
								<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
							</td>
							<td>
								<windrp:getname key='users' value='${p.makeid}' p='d' />
							</td>
						</tr>
					</logic:iterate>
				</table>
				</div>

					<br>
					<div style="width: 100%">
						<div id="tabs1">
							<ul>
								<li>
									<a href="javascript:Detail();"><span>服务预约详情</span>
									</a>
								</li>
							</ul>
						</div>
						<div>
							<IFRAME id="submsg"
								style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%"
								name="submsg" src="../sys/remind.htm" frameBorder="0"
								scrolling="no" onload="setIframeHeight(this);"></IFRAME>
						</div>
					</div>
				</div>
	</body>
</html>
