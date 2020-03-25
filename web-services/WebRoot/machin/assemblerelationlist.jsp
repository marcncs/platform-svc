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
		Detail();
	}

	function addNew(){
	window.open("../machin/toAddAssembleRelationAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdAssembleRelationAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="assembleRelationDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function excput(){
		search.target="";
		search.action="../machin/excPutAssembleRelationAction.do";
		search.submit();
	}
	function oncheck(){
		search.target="";
		search.action="../machin/listAssembleRelationAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../machin/printListAssembleRelationAction.do";
		search.submit();
	}

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 组装关系吗？如果删除将永远不能恢复!")){
			window.open("../machin/delAssembleRelationAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleBill(){
		if(checkid!=""){
			window.open("../sales/saleBillAction.do?ID="+checkid,"newwindow","height=600,width=900,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
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

</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">生产组装>>组装关系设置 </td>
  </tr>
</table>
<form name="search" method="post" action="../machin/listAssembleRelationAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="10%"  align="right">制单机构：</td>
            <td width="18%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" value="${oname}" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="10%" align="right">制单部门：</td>
            <td width="25%"><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
              <input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" value="${deptname}" readonly></td>
            <td width="9%" align="right">制单人：</td>
            <td width="28%" colspan="2"><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
              <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
          </tr>
          <tr class="table-back">
            <td  align="right">是否复核：</td>
            <td><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
            <td align="right">制单日期：</td>
            <td><input type="text" name="BeginDate" size="12" value="${BeginDate}" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" value="${EndDate}" readonly onFocus="javascript:selectDate(this)"></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td class="SeparatePage"><input name="Submit" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td width="50">
		<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
	    </td>
	    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
          <td width="50">
				<a href="javascript:print();"><img
						src="../images/CN/print.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;打印</a>
		  </td>
		  <td width="1"><img src="../images/CN/hline.gif" width="1" height="14"></td>

          <td class="SeparatePage"><pages:pager action="../machin/listAssembleRelationAction.do"/></td>
        </tr>
      </table>
	  </div>
					</td>
				</tr>
				<tr>
					<td>
	<div id="listdiv" style="overflow-y: auto; height: 650px;" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="10%" >序号</td>
            <td width="13%" >组装产品编码</td>
            <td width="23%" >组装产品名称</td>
            <td width="14%" >规格</td>
            <td width="10%" >单位</td>
            <td width="8%" >数量</td>
            <td width="9%" >是否复核</td>
          </tr>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td >${s.arproductid}</td>
            <td >${s.arproductname}</td>
            <td >${s.arspecmode}</td>
            <td ><windrp:getname key='CountUnit' value='${s.arunitid}' p='d'/></td>
            <td >${s.arquantity}</td>
            <td ><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            </tr>
		  </logic:iterate>
      </table>
	  <br>
	  <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>组装关系详情</span></a></li>
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
