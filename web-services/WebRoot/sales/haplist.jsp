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
		popWin("../sales/toAddHapAction.do",1050,550);
	}

	function Update(){
		if(checkid>0){
			popWin("toUpdHapAction.do?id="+checkid,1050,550);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid>0){
		document.all.submsg.src="../sales/hapDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
				popWin2("../sales/delHapAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
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
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									会员管理>>销售机会
								</td>
							</tr>
						</table>
<form name="search" method="post" action="listHapAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="11%" align="right">
									机会种类：
								</td>
								<td width="23%">
									${hapcontentselect}
								</td>
								<td width="10%" align="right">
									机会性质：
								</td>
								<td width="20%">
									${hapkindselect}
								</td>
								<td width="10%" align="right">
									机会状态：
								</td>
								<td width="26%">
									${hapstatusselect}
									<input type="submit" name="Submit" value="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="60">
									<a href="#" onClick="javascript:addNew();">新增</a>
								</td>
								<td width="60">
									<a href="javascript:Update();">修改</a>
								</td>
								<td width="60">
									<a href="javascript:Del();">删除</a>
								</td>

								<td style="text-align: right;">
									<pages:pager action="../sales/listHapAction.do" />
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
							class="sortable">

							<tr align="center" class="title-top">
								<td width="4%">
									编号
								</td>
								<td width="23%">
									对象名称
								</td>
								<td width="13%">
									机会种类
								</td>
								<td width="11%">
									机会性质
								</td>
								<td width="12%">
									机会状态
								</td>
								<td width="7%">
									预计金额
								</td>
								<td width="10%">
									机会开始时间
								</td>
								<td width="10%">
									机会消失时间
								</td>
								<td width="10%">
									录入用户
								</td>
							</tr>
							<c:set var="count" value="0" />
							<logic:iterate id="h" name="hList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,${h.id});">
									<td>
										${h.id}
									</td>
									<td>
										${h.cidname}
									</td>
									<td>
										${h.hapcontentname}
									</td>
									<td>
										${h.hapkindname}
									</td>
									<td>
										${h.hapstatusname}
									</td>
									<td><windrp:format value='${h.intend}'/>
										
									</td>
									<td>
										${h.hapbegin}
									</td>
									<td>
										${h.hapend}
									</td>
									<td>
										${h.makeidname}
									</td>
								</tr>
								<c:set var="count" value="${count+1}" />
							</logic:iterate>

						</table>
						<br>
								<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>销售机会详情</span></a></li>
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
