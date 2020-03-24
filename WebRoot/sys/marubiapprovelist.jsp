<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>机构列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript">
			this.onload =function onLoadDivHeight(){
				document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
			}
			var checkid="";
			function CheckedObj(obj,objid){
			 for(i=0; i<obj.parentNode.childNodes.length; i++)
			 {
				   obj.parentNode.childNodes[i].className="table-back-colorbar";
			 }
			 
			 obj.className="event";
			 checkid=objid;
			}
			function approve(){
				if(checkid!=""){
					if(confirm("确认要审批该记录吗?")){
						popWin("../sys/isApproveMarubiBarcodeLogging.do?ID="+checkid,500,300);
					}
				}else{
					alert("请选择你要操作的记录!");
				}  	
			}
			function openDownFile(clientname,clientcode,date,productname){
				if(clientname != "" && clientcode != "" && date != "" && productname != ""){
					popWin("../sys/winsafeFwmGuangMingGuoNeiDownAction.do?clientname="+clientname+"&clientcode="+clientcode+"&date="+date+"&productname="+encodeURIComponent(encodeURIComponent(productname)),610,200);
				}else{
					alert("系统正在升级,下载稍候,请与管理员联系!");
				}
			}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									物流码管理&gt;&gt;物流码审批
								</td>

							</tr>
						</table>
						<form name="search" method="post" action="../sys/toIsApproveMarubiBarcodeLogging.do"
								onsubmit="return oncheck();">
						  <table width="100%" border="0" cellpadding="0" cellspacing="0">
					          <tr class="table-back">
					            <td width="10%"  align="right">申请编号：</td>
					            <td width="15%">
					            <input name="codeSearch" type="text" id="codeSearch" value="${codeSearch}"></td>
					            <td width="10%"  align="right">企业名称：</td>
					            <td width="15%">
					            <input name="clientnameSearch" type="text" id="clientnameSearch" value="${clientnameSearch}"></td>
					          	 <td width="10%" align="right">生成数量：</td>
					            <td width="15%"><input id="fwmnumSearch" name="fwmnumSearch" value="${fwmnumSearch}"></input></td>
					            <td width="10%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" align="right"></td>
					          </tr>
					      </table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
				          <tr class="title-func-back">
				        	<td width="50" id="addfwm"><a href="#" onClick="javascript:approve();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;审批</a> </td>
				       		<td class="SeparatePage">
								<pages:pager action="../sys/toIsApproveMarubiBarcodeLogging.do"/>
							</td>
				          </tr>
				        </table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
					          <tr align="center" class="title-top"> 
					          	<td width="12%">申请编号</td>
					            <td width="13%" >企业名称</td>
								<td width="15%">产品名称</td>
								<td width="8%">生成数量</td>
								<td width="12%">生成时间</td>
								<td width="7%">生成格式</td>
								<td width="8%">生成状态</td>
								<td width="8%">是否申请</td>
								<td width="8%">是否审批</td>
					          </tr>
					          <logic:iterate id="c" name="fwmList">
						          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${c.id}');"> 
						            <td align="center">${c.fwmCodeFile}</td>
						            <td align="center">${c.fwmClientName}</td>
						            <td align="center">${c.fwmProductName}</td>
						            <td align="center">${c.fwmNum}</td>
						            <td align="center">
						            	<bean:write name="c" property="fwmCreateDate" format="yyyy-MM-dd HH:mm:ss"/>
						            </td>
						            <td align="center">${c.fwmDaoFormat}</td>
						             <td align="center">
						            	<c:if test="${c.isDeal eq 0}">未生成</c:if>
						            	<c:if test="${c.isDeal eq 1}">生成中..</c:if>
						            	<c:if test="${c.isDeal eq 2}">已生成</c:if>
						            </td>
						            <td align="center">
						            	<c:if test="${c.isApply eq 0}">未申请</c:if>
						            	<c:if test="${c.isApply eq 1}">已申请</c:if>
						            </td>
						            <td align="center">
						            	<c:if test="${c.isApprove eq 0}">未审批</c:if>
						            	<c:if test="${c.isApprove eq 1}">已审批</c:if>
						            </td>
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

