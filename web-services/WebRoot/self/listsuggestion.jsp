<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>投诉意见</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../javascripts/prototype.js"></script>
		<script type="text/javascript" src="../javascripts/capxous.js"></script>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link rel="stylesheet" type="text/css" href="../styles/capxous.css" />
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
	 	obj.className="event";
	 	checkid=objid;
	 	/*
	 	submenu = getCookie("CookieProvide");
	 switch(submenu){
		case "1":AfficheDetail(); break;
		case "2":AfficheBrowser(); break;
		default:AfficheDetail();
	 }
		 */
		
	}
	
	function addNew(){
		popWin("../self/toAddAfficheAction.do",1000,600);
	}

	function Update(){
		if(checkid>0){
		popWin("toUpdAfficheAction.do?id="+checkid,1000,600);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid>0){
			if(window.confirm("您确认要删除编号为："+checkid+" 的公告吗？如果删除将永远不能恢复!")){
				popWin2("../self/delAfficheAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function AfficheDetail(){
	setCookie("CookieProvide","1");
		if(checkid>0){
		document.all.submsg.src="afficheDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function AfficheBrowser(){
	setCookie("CookieProvide","2");
		if(checkid>0){
		document.all.submsg.src="listAfficheBrowseAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	this.onload = function abc(){
		//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace}
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listSuggestionAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<%--<td align="right">
									公告类型：
								</td>
								<td>
									<windrp:select key="AfficheType" name="AfficheType" p="y|f" value="${AfficheType}"/>
								</td>
								--%>
									<td align="right">
										投诉日期：
									</td>
									<td>
										<input name="BeginDate" type="text" id="BeginDate" size="10"
											value="${BeginDate}" onFocus="selectDate(this);" readonly="readonly">
										-
										<input name="EndDate" type="text" id="EndDate" size="10"
											value="${EndDate}" onFocus="selectDate(this);" readonly="readonly">
									</td>
									<td align="right">
										投诉内容：
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
								<td class="SeparatePage">
									<pages:pager action="../self/listSuggestionAction.do" />
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
								<td>
									编号
								</td>
								<td>
									投诉内容
								</td>
								<td>
									投诉时间
								</td>
								<td>
									IP
								</td>
							</tr>
							<logic:iterate id="s" name="aaList">

								<tr class="table-back-colorbar" onClick="CheckedObj(this,${s.id});">
									<td>
										${s.id}
									</td>
									<td>
										<div title="${s.suggestionMsg}"
											style="width: 350; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
											${s.suggestionMsg}
										</div>
									</td>
									<td>
										<windrp:dateformat value="${s.makeDate}" p="yyyy-MM-dd" />
									</td>
									<td>${s.ip }</td>
								</tr>
							</logic:iterate>

						</table>
						<br />
						<%--
						
						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:AfficheDetail();"><span>公告详情</span></a></li>
               <li><a href="javascript:AfficheBrowser();"><span>浏览者</span></a></li>
           </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
						
					--%>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
