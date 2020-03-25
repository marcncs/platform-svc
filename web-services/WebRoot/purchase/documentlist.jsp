<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}
	
	function addNew(){
		popWin("toAddDocumentAction.do",900,600);
	}
	
	function Del(){
		if(checkid >=0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的相关文档吗？如果删除将永远不能恢复!")){
				popWin2("../purchase/delDocumentAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	

</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								相关文档
							</td>
						</tr>
					</table>
<form name="search" method="post" action="listDocumentAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td width="8%" align="right">
								<input type="hidden" name="Cid" value="${cid}">
								关键字：
							</td>
							<td>
								<input type="text" name="DocumentName" value="${DocumentName}">

							</td>
							<td align="right">&nbsp;
								
							</td>
							<td  class="SeparatePage">
								<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
							</td>
						</tr>
						
					</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td width="60">
								<a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/upload.gif" width="16" height="16"
											border="0" align="absmiddle"></a> <a href="javascript:addNew();">上传</a>
					    </td>
                        <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
                        <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td   class="SeparatePage">
								<pages:pager action="../purchase/listDocumentAction.do" />
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top">
							<td width="40">
								编号
							</td>
							<td>
								文档名
								
							</td>
							<td>描述</td>
							<td>
								更新日期
							</td>
							<td>
								制单机构
							</td>
							<td>
								制单人
							</td>

						</tr>
						<logic:iterate id="c" name="usList">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,${c.id});">
								<td>
									${c.id}
								</td>
								<td>
								<img src="../images/CN/beizheng.gif" border="0">
								<a href="../common/downloadfile.jsp?filename=${c.realpathname}">${c.realpathname}</a>
								</td>
								<td>
								${c.description }
								</td>
								<td>
									
									<windrp:dateformat value="${c.makedate}" p="yyyy-MM-dd"/>
								</td>
								<td>
									<windrp:getname key='organ' value='${c.makeorganid}' p='d'/>
								</td>
								<td>
									<windrp:getname key='users' value='${c.makeid}' p='d'/> 
								</td>
							</tr>
						</logic:iterate>

					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
