<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="../common/tag.jsp"%>
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>WINDRP-分销系统</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js">
	
</SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js">
	
</SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>
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
function Detail() {
	if(checkid!=""){
		document.all.submsg.src="../yun/productFeedbackDetailAction.do?ID="+checkid;
	}else{
		alert("请选择你要操作的记录!");
	}
}
	function addReply(ppid) {
		popWin("../yun/toAddReplyProductFeedBackAction.do?PPID=" + ppid, 600,200);
	}

	function editFeedback(ppid, auditStatus) {
		/* popWin("../yun/editProductFeedBackAuditStatusAction.do?PPID="+ppid + "&auditStatus=" +auditStatus); */
		var url = '../yun/editProductFeedBackAuditStatusAction.do';
		var pars = "PPID=" + ppid + "&auditStatus=" + auditStatus;
		var myAjax = new Ajax.Request(url, {
			method : 'post',
			parameters : pars,
			onComplete : function(data) {
				location.reload(true);
			}
		});
	}

	function deleteFeedback(ppid) {
		if (ppid) {
			if (confirm("你确认要删除编号为:" + ppid + "的评论吗?")) {
				/* popWin2("../yun/delProductFeedBackAction.do?PPID="+ppid); */
				var url = '../yun/delProductFeedBackAction.do';
				var pars = "PPID=" + ppid;
				var myAjax = new Ajax.Request(url, {
					method : 'get',
					parameters : pars,
					onComplete : function(data) {
						location.reload(true);
					}
				});
			}
		} else {
			alert("请选择评论!");
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">云单页>>评论管理
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
      <form name="search" method="post" action="../yun/listProductFeedbackAction.do">
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
  
  <tr class="table-back"> 
  	<td width="8%"  align="right">关键字：</td>
      <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="30"></td>
      <td width="9%" align="right"></td>
       <td width="11%">
  </td>
  <td width="9%" align="right"></td>
       <td width="11%">
  </td>
      
	<td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
</table> 
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<td class="SeparatePage"> <pages:pager action="../yun/listProductFeedbackAction.do"/> 
	</td>
	</tr>
</table>
	  </div>
	</td>
</tr>
		<tr>
			<td>
				<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<table class="sortable" width="100%" border="0" cellpadding="0"
						cellspacing="1">
						<tr align="center" class="title-top">
							<!-- <td width="16px" class="sorttable_nosort"><input
								type="checkbox" id="checkAll" onclick="checkAll()"></td> -->
							<td style="width: auto;">编号</td>
							<td style="width: auto;">产品编号</td>
							<td style="width: auto;">产品名称</td>
							<td style="width: auto;">评论内容</td>
							<td style="width: auto;">回复</td>
							<td style="width: auto;">评分</td>
							<td style="width: auto;">是否显示</td>
							<!-- <td style="width: auto;">登记证号</td> -->
							<td style="width: auto;">评论日期</td>
							<td style="width: auto;">操作</td>
						</tr>
						<logic:iterate id="p" name="productFeedbacks">
							<tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}');">
								<%-- <td width="16px"><input type="checkbox" value="${p.id}"
									name="pid"></td> --%>
								<td>${p.id}</td>
								<td>${p.productId }</td>
								<td>${p.productName }</td>
								<td>${p.content }</td>
								<td>${p.reply}</td>
								<td>${p.starScore}</td>
								<td><windrp:getname key='YesOrNo' value='${p.auditStatus}' p='f'/></td>
								<%-- <td>${p.certification}</td> --%>
								<td>${p.createTime}</td>
								<td><a width="100" href="javascript:addReply('${p.id}');">回复</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a width="100" href="javascript:editFeedback('${p.id}' , 0);">隐藏</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a width="100" href="javascript:editFeedback('${p.id}' , 1);">显示</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a width="100" href="javascript:deleteFeedback('${p.id}');">删除</a>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
				      <div style="width:100%">
				        <div id="tabs1">
				          <ul>
				            <li><a href="javascript:Detail();"><span>评论图片</span></a></li>
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