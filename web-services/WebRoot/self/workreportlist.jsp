<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="javascript">
		
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
		WorkReportDetail();
		}
		
		function addNew(){
			popWin("../self/toAddNewWorkReportAction.do",1000,600);
		}


		function SubmitWorkReport(){
			popWin("../common/referInfoAction.do",1000,600);
		}

		function Update(){
			if(checkid>0){
			popWin("../self/updWorkReportAction.do?ID="+checkid,1000,600);
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
		function WorkReportDetail(){
				if(checkid>0){
				document.all.submsg.src="../self/workReportDetailAction.do?ID="+checkid;
				}else{
				alert("请选择你要操作的记录!");
				}
			}

		function Del(){
			if(checkid>0){
				if(window.confirm("您确认要删除编号为："+checkid+" 的工作报告吗？如果删除将永远不能恢复!")){
					popWin2("../self/delWorkReportAction.do?ID="+checkid);
				}
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		


</script>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" class="title-back" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									我的办公桌>>工作报告
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="listWorkReportAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									类别：
								</td>
								<td>
									<windrp:select key="ReportSort" name="ReportSort" p="y|f" value="${ReportSort}"/>
								</td>
								<td align="right">
									时间段：
								</td>
								<td>
									<input type="text" name="BeginDate" value="${BeginDate}"
										size="10" onFocus="javascript:selectDate(this)" readonly="readonly">
									-
									<input type="text" name="EndDate" value="${EndDate}" size="10"
										onFocus="javascript:selectDate(this)" readonly="readonly">
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">

								</td>
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
								<!-- 
								<td width="60">
									<a href="javascript:SubmitWorkReport();">提交报告</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								 -->
								<td class="SeparatePage">
									<pages:pager action="../sys/listWorkReportAction.do" />
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
							class="sortable" style="table-layout:fixed;overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
							
							<tr align="center" class="title-top">
								<td width="6%">
									编号
								</td>
								<td width="30%">
									内容
								</td>
								<td style="width: 70px;">
									报告分类
								</td>
								<td style="width: 120px;">
									制单机构
								</td>
								<td style="width: 80px;">
									制单人
								</td>
								<td style="width: 80px;">
									制单日期
								</td>
								<td style="width: 70px;">
									是否审阅
								</td>								
							</tr>
							<logic:iterate id="wr" name="arls">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${wr.id});">
									<td>
										${wr.id}
										<c:if test="${wr.approve==0}">
											<font color="#FF0000" size="-4">新</font>
										</c:if>
									</td>

									<td><div title="${wr.reportcontent}" style="width:250; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${wr.reportcontent}</div>
										
									</td>
									<td>
										<windrp:getname key='ReportSort' value='${wr.reportsort}' p='f' />
									</td>
									<td>
										<windrp:getname key='organ' value='${wr.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='users' value='${wr.makeid}' p='d' />
									</td>
									<td>
										<windrp:dateformat value='${wr.makedate }' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='ApproveStatus' value='${wr.approvestatus}'
											p='f' />
									</td>									
								</tr>
							</logic:iterate>
							
						</table>
						</form>
						<br />
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:WorkReportDetail();"><span>工作报告详情</span></a></li>
   
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
