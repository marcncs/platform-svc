<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
var checkid="";
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
		popWin("toAddMemberGradeAction.do",700,400);
	}
	
	function Update(){
		if(checkid!=""){
			popWin("toUpdMemberGradeAction.do?ID="+checkid,700,400);
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="memberGradeDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
				popWin2("../sys/delMemberGradeAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Visit(){
		if(checkid!=""){
				popWin("toVisitMemberGradeAction.do?ID="+checkid,700,400);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}	
</script>
		
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									会员级别列表
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50" align="center">
									<a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50" align="center">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
                                <td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50" align="center">
									<a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50" align="center">
									<a href="javascript:Visit();"><img
											src="../images/CN/xuke.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;许可</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td class="SeparatePage">
									<pages:pager action="../sys/listMemberGradeAction.do" />
								</td>

							</tr>
						</table>
					</div>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="18%">
									编号
								</td>
								<td width="39%">
									会员级别名称
								</td>
								<td width="12%">
									享受价格
								</td>
								<td width="31%">
									积分比例
								</td>
							</tr>
							<logic:iterate id="w" name="wls"><tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${w.id}');">
									<td height="20">
										${w.id}
									</td>
									<td>
										${w.gradename}
									</td>
									<td>
										${w.policyidname}
									</td>
									<td><windrp:format value='${w.integralrate}'/>
										
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
                        <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>会员级别详情</span></a></li>
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
