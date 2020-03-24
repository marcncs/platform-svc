<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>关联销售人员列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript">
var checkid="";
function CheckedObj(obj,objid){
	for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	obj.className="event";
 	checkid=objid;
}
this.onload = function abc(){
	//document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px" ;
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
									关联销售人员
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listOrganSalesmanAction.do">
						<input type="hidden" name="OID" value="${OID}">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="table-back">
								<td width="9%" align="right">
									关键字:
								</td>
								<td width="20%">
									<input type="text" name="KeyWord" value="${KeyWord}" maxlength="60">
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td align="right">
								</td>
								<td>
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
						</table>
						</form>
						<form name="exportExcel" method="post" action="excPutSBonusSettingAction.do">
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody><tr class="title-func-back">
								<td class="SeparatePage"> 
									<pages:pager action="../keyretailer/listOrganSalesmanAction.do" />
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 100%;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							
							<tr align="center" class="title-top">
								<td>
									用户编号
								</td>
								<td>
									用户名
								</td>
								<td>
									真实姓名
								</td>
								<td>
									销售类型
								</td>
							</tr>
							<logic:iterate id="pj" name="organSalesman">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${pj.userid}');">
									<td>
										${pj.userid}
									</td>
									<td>
										${pj.loginname}
									</td>
									<td>
										${pj.realname}
									</td>
									<td>
										<windrp:getname key='SalesUserType' value='${pj.usertype}' p='d'/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
					</div>

				</td>
			</tr>
	</table>
  </body>
</html>
