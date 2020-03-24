<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<html>
	<head>
		<title>检货小票</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<script language="JavaScript">



var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 Detail();
	}
	var token = "<%=(String) request.getSession().getAttribute(
							Globals.TRANSACTION_TOKEN_KEY)%>";
	
	function Audit(ajaxid){
		
		
		showloading();
		popWin2("../warehouse/auditTakeTicketAction.do?id="+ajaxid+"&org.apache.struts.taglib.html.TOKEN="+token);
	}
	function CancelAudit(){
		if(checkid == ""){
			alert("请选中一行后再进行取消复核操作");
			return;
		}
		showloading();
		popWin("../warehouse/cancelAuditTakeTicketAction.do?id="+checkid+"&org.apache.struts.taglib.html.TOKEN="+token);
	}
	function addNew(){
		popWin("toAddTakeTicketAction.do",1000,650);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdTakeTicketAction.do?ID="+checkid,1000,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			popWin2("../warehouse/delTakeTicketAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		
		if(checkid!=""){
			//document.all.submsg.src="../warehouse/takeTicketDetailAction.do?ID="+checkid;
			popWin("../warehouse/takeTicketDetailAction.do?ID="+checkid+"&token="+token,1000,650);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
 var tip={$:function(ele){ 
  if(typeof(ele)=="object") 
    return ele; 
  else if(typeof(ele)=="string"||typeof(ele)=="number") 
    return document.getElementById(ele.toString()); 
    return null; 
  }, 
  mousePos:function(e){ 
    var x,y; 
    var e = e||window.event; 
    return{x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft, 
		y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop}; 
  }, 
  start:function(obj){ 
  	 
    var self = this; 
    var t = self.$("mjs:tip"); 
    obj.onmousemove=function(e){ 
      var mouse = self.mousePos(e);   
      t.style.left = mouse.x + 10 + 'px'; 
      t.style.top = mouse.y + 10 + 'px'; 
      //t.innerHTML = "双击可看详细";
      t.innerHTML = obj.getAttribute("tips"); 
      t.style.display = ''; 
    }; 
    obj.onmouseout=function(){ 
      t.style.display = 'none'; 
    }; 
  } 
  } 
  
  
  
  
  	 function toAudit(){
  	 if(checkid == ""){
			alert("请选中一行后再进行复核操作");
			return;
		}
	$.post("../warehouse/ajaxAuditTakeTicketAction.do?id="+checkid,
  function(result){
   var data = eval("("+ result + ")");
	if(data.state=="overQuantity"){
		if(data.tttype!=2){
		alert("库存不足不能出库");
			return;
		}
		else if(data.warehouseType!=3){
		alert("库存不足不能出库");
			return;
		}
		else{
			var con=confirm("是否要负库存出货？");
			if(con==true){
				Audit(data.id);
			}
			if(con==false){
				return;
			}
		}
		}
	else{
		Audit(data.id);
		}
  }); 
	}	
		
		
		
	
  
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td>
								<img src="../images/CN/spc.gif" height="8">
							</td>
							<td width="10%" align="left">
								检货小票
							</td>
							<td width="80%"></td>
							<td width="10%" align="right" style="font-weight: normal">
							<%--<a id="auditBtn" href="javascript:toAudit();" style="display: none;">复核</a>
								<a id="cancalAuditBtn" href="javascript:CancelAudit()"
									style="display: none;">取消复核</a> --%>
							</td>
						</tr>
					</table>
					<form name="search" method="post"
						action="../warehouse/listTakeTicketAction.do">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr class="table-back">
								<td align="right">
									检货人：
								</td>
								<td width="10%">
									<windrp:users name="TakeID" value="${TakeID}" p="y" />
								</td>
								<td width="9%" align="right">
									是否复核：
								</td>
								<td width="9%" width="10%">
									<windrp:select key="YesOrNo" name="IsAudit" p="y|f"
										value="${IsAudit}" />
								</td>
								<td width="9%" align="right">
									制单日期：
								</td>
								<td width="37%">
									<input name="BeginDate" type="text"
										onFocus="javascript:selectDate(this)" size="12"
										value="${BeginDate}" readonly>
									-
									<input name="EndDate" type="text"
										onFocus="javascript:selectDate(this)" value="${EndDate}"
										size="12" readonly>
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" title="查询">
								</td>
							</tr>

						</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="title-func-back">
							<td class="SeparatePage">
								<pages:pager action="../warehouse/listTakeTicketAction.do" />
							</td>
						</tr>
					</table>
					<table class="sortable" width="100%" border="0" cellpadding="0"
						cellspacing="1">
						<tr align="center" class="title-top">
							<td width="13%">
								编号
							</td>
							<td width="15%">
								出货仓库
							</td>
							<td width="11%">
								车号
							</td>
							<td width="14%">
								单据编号
							</td>
							<td width="12%">
								制单人
							</td>
							<td width="18%">
								制单日期
							</td>
							<td width="9%">
								是否复核
							</td>
						</tr>
						<logic:iterate id="s" name="also">
							<tr class="table-back-colorbar" onmouseover="tip.start(this);"
								tips="单击行可进行复核操作<br/>双击行可看小票明细"
								ondblclick="CheckedObj(this,'${s.id}');">
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.id}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									<windrp:getname key='warehouse' value='${s.warehouseid}' p='d' />
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.busNo}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.billno}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									<windrp:getname key='users' value='${s.makeid}' p='d' />
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									${s.makedate}
								</td>
								<td class="${s.isblankout==1?'text2-red':''}">
									<windrp:getname key='YesOrNo' value='${s.isaudit}' p='f' />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
			</tr>
		</table>
		<div id="mjs:tip" class="tip"
			style="position: absolute; left: 0; top: 0; display: none; font-size: xx-small; color: black; BORDER-RIGHT: 3px outset; BORDER-TOP: 3px outset; BACKGROUND: #ffffff; BORDER-LEFT: 3px outset; BORDER-BOTTOM: 3px outset;">

		</div>

		<table width="62" border="0" cellpadding="0" cellspacing="1">
			<tr align="center" class="back-bntgray2">
				<!--<td width="60" ><a href="javascript:Detail();">检货详情</a></td>-->
			</tr>
		</table>
	</body>
</html>
