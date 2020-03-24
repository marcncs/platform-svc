<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 MsgDetail();
	}
	
	function addNew(){
		popWin("../assistant/toAddMsgAction.do",1000,600);
	}
	
	function ToInput(){
		popWin("../purchase/selectPurchaseBillAction.do",1000,650);
	}

	function updNew(){
		if(checkid!=""){
			popWin("../assistant/toUpdMsgAction.do?ID="+checkid,1000,600);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function MsgDetail(){
		if(checkid!=""){
			document.all.submsg.src="msgDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
				popWin2("../assistant/delMsgAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
		clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 呼叫中心/短信>>短信</td>
        </tr>
      </table>
       <form name="search" method="post" action="../assistant/listMsgAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right">短信类型：</td>
            <td width="23%"><windrp:select key="MsgSort" name="MsgSort" p="y|f" value="${MsgSort}"/></td>
            <td width="8%" align="right">是否复核：</td>
            <td width="26%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
            <td width="12%" align="right">是否处理：</td>
            <td width="21%"><windrp:select key="YesOrNo" name="IsDeal" p="y|f" value="${IsDeal}"/></td>
          </tr>
         <tr class="table-back"> 
            <td  align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
            <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">制单部门：</td>
            <td><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
            <input type="text" name="deptname" id="deptname" value="${deptname}" onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')"  readonly></td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
            <input type="text" name="uname" id="uname" value="${uname}"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										readonly></td>
          </tr>
		  <tr class="table-back"> 
            <td  align="right">手机号码：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td align="right">发送日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)"></td>
            <td align="right">&nbsp;</td>
            <td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="50"><a href="javascript:updNew();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	<td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
	<td class="SeparatePage"> <pages:pager action="../assistant/listMsgAction.do"/> 
	</td>
	</tr>
</table>
      </div>
	</td>
				</tr>
				<tr>
					<td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="6%"  >编号</td>
            <td width="17%" >手机号码</td>
            <td width="4%" > 类型</td>
            <td width="30%" > 内容</td>
            <td width="13%" >发送机构</td>
            <td width="7%" >发送人</td>
            <td width="9%" >发送日期</td>
			<td width="7%" >是否复核</td>
			<td width="7%" >是否处理</td>
          </tr>
          <logic:iterate id="pi" name="alsb" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${pi.id});"> 
            <td >${pi.id}</td>
            <td title='${pi.mobileno}'>${pi.mobileno}</td>
            <td><windrp:getname key='MsgSort' value='${pi.msgsort}' p='f'/></td>
            <td title='${pi.msgcontent}'>${pi.msgcontent}</td>
            <td><windrp:getname key='organ' value='${pi.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${pi.makeid}' p='d'/> </td>
            <td><windrp:dateformat value='${pi.makedate}' p='yyyy-MM-dd'/></td>
			<td><windrp:getname key='YesOrNo' value='${pi.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${pi.isdeal}' p='f'/></td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <br>
       <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:MsgDetail();"><span>详情</span></a></li>           
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
