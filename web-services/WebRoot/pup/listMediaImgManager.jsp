<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WINDRP-分销系统</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>
<script language="JavaScript">
function addNew(){
	popWin("../yun/toAddMediaImgAction.do?");
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
		<%-- <td>
			<div id="bodydiv">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="title-func-back">
						<td class="SeparatePage"><pages:pager action="../yun/listMediaImgManagerAction.do" /></td>
					</tr>
				</table>
			</div>
		</td> --%>
		</tr>
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
							<tr>
								<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
								<td width="772">图片管理 </td>
							</tr>
						</table>
						<form name="search" method="post" action="listMediaImgManagerAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/yun/toAddMediaImgAction.do">
									<td width="100"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0"	align="absmiddle">&nbsp;新增图片</a></td>
									<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
								</ws:hasAuth>
								<td class="SeparatePage"><pages:pager action="../yun/listMediaImgManagerAction.do" /></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr align="center" class="title-top">
								<!-- <td width="16px" class="sorttable_nosort"><input type="checkbox" id="checkAll" onclick="checkAll()"></td> -->
								<td style="width: auto;">编号</td>
								<td style="width: auto;">图片标题</td>
								<!-- <td width="800px">视频简介</td> -->
								<td style="width: auto;">图片大小</td>
								<!-- <td style="width: auto;">评论状态</td> -->
								<!-- <td style="width: auto;">是否发布</td> -->
								<td style="width: auto;">上传日期</td>
								<td style="width: auto;">操作</td>
							</tr>
							<logic:iterate id="p" name="mediaImgs">
							<tr class="table-back-colorbar" >
									<%-- <td width="16px"><input type="checkbox" value="${p.id}" name="pid"></td> --%>
									<td>${p.id}</td>
									<td>${p.title }</td>
									<%-- <td width="800px">${p.digest }</td> --%>
									<td>${p.size}</td>
									<%-- <td>${p.auditStatusName}</td> --%>
									<%-- <td>
										<c:if test="${p.isPublish == false}">未发布</c:if>
										<c:if test="${p.isPublish == true}">已发布</c:if>
									</td> --%>
									<td>${p.createTime}</td>
									<td>
									<ws:hasAuth operationName="/yun/toEditMediaImgAction.do">
										<a width="100" href="javascript:editMV('${p.id}');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
									</ws:hasAuth>
									<ws:hasAuth operationName="/yun/publishMediaImgAction.do">
										<c:if test="${p.isPublish == false}">
											<a width="100" href="javascript:publishMV('${p.id}' , 'true');">发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
										<c:if test="${p.isPublish == true}">
											<a width="100" href="javascript:publishMV('${p.id}' , 'false');">取消发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
										</c:if>
									</ws:hasAuth>
									<ws:hasAuth operationName="/yun/deleteMediaImgAction.do">
										<a width="100" href="javascript:deleteMV('${p.id}');">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</ws:hasAuth>
									<ws:hasAuth operationName="/yun/toPlayMediaImgAction.do">
										<a width="100" href="javascript:playMV('${p.id}');">播放视频</a>
									</ws:hasAuth>
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
					</div>
				</td>
			</tr>
		</table>
	</table>
</body>
</html>