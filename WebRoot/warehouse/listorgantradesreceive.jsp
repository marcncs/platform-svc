<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 OrganTradesDetail();
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=9", 500, 250);
	}
	
	function DownTxt(){
		excputform.action="txtPurOrganTradesPAction.do";
		excputform.submit();
	}
	
	function OrganTradesDetail(){
		if(checkid!=""){
			document.all.submsg.src="../warehouse/detailOrganTradesReceiveAction.do?ID="+checkid+"&IsAudit=${IsAudit}";
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutOrganTradesReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOrganTradesReceiveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListOrganTradesReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listOrganTradesReceiveAction.do";
	}

	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	
	function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");

		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}
	function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.POrganID.value=p.id;
			document.search.poname.value=p.organname;
	}
	function oncheck(){
		if('${IsAudit}'=='no'){
			search.action="listOrganTradesAction.do";
			search.submit();
		}else{
			search.action="listRatifyOrganTradesAction.do";
			search.submit();
		}
	}
	
</script>
	</head>
		<body >
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
								<td>仓库管理 >>入库>>渠道换货供方签收
								</td>
							</tr>
						</table>
						<form name="search" method="post"
								action="../warehouse/listOrganTradesReceiveAction.do" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
									<input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img 
									src="../images/CN/find.gif" width="18" height="18" 
									border="0" align="absmiddle"></a>

								</td>
								<td align="right">制单部门：</td>
								<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>
								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
									供货机构：
								</td>
								<td >
									<input name="POrganID" type="hidden" id="POrganID" value="${POrganID}">
									<input name="poname" type="text" id="poname" value="${poname}" size="30" readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
								</td>
								<td width="12%" align="right">
									制单日期：
								</td>
								<td width="22%"><input name="BeginDate" type="text" id="BeginDate" onFocus="javascript:selectDate(this)" size="10" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" id="EndDate" onFocus="javascript:selectDate(this)" value="${EndDate}" size="10" readonly>
								</td>
								<td width="8%" align="right">
									是否签收：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="PIsReceive" value="${PIsReceive}" p="y|f" />
								</td >
								<td class="SeparatePage">&nbsp;</td>
							</tr>
                            <tr class="table-back">
								<td align="right">关键字：</td>
								<td ><input type="text" name="KeyWord" value="${KeyWord}" maxlength="60"></td>
								<td width="12%" align="right">&nbsp;</td>
								<td width="22%">&nbsp;</td>
								<td width="8%" align="right">&nbsp;</td>
								<td>&nbsp;</td >
								<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr class="title-func-back">
							<td width="50"><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
							<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
							  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
							  <td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
							  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
							<td class="SeparatePage"><pages:pager action="../warehouse/listOrganTradesReceiveAction.do" /></td>
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
								<td width="12%">编号</td>
								<td width="12%">供货机构</td>
								<td width="10%">联系人</td>
								<td width="12%">制单机构</td>
								<td width="8%">制单人</td>
								<td width="10%">制单日期</td>								
								<td width="7%">是否签收</td>
							</tr>
							<logic:iterate id="o" name="list">
								
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${o.id}');">
									<td class="${o.isblankout==1?'td-blankout':''}">
										${o.id}
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${o.porganid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										${o.plinkman }
									</td>									
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='organ' value='${o.makeorganid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='users' value='${o.makeid}' p='d' />
									</td>
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:dateformat value="${o.makedate}" p="yyyy-MM-dd"/>
									</td>								
									<td class="${o.isblankout==1?'td-blankout':''}">
										<windrp:getname key='YesOrNo' value='${o.pisreceive}' p='f' />
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
                        <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:OrganTradesDetail();"><span>渠道换货详情</span></a></li>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="POrganID" id ="POrganID" value="${POrganID}">
<input type="hidden" name="PIsReceive" id ="PIsReceive" value="${PIsReceive}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
	</body>
</html>
