<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>

<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	OutlayDetail();
	}
	
	function addNew(){
	window.open("../finance/toAddOutlayAction.do","","height=550,width=850,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Refer(){
		if(checkid!=""){
		window.open("../finance/toReferOutlayAction.do?OID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Update(){
		if(checkid!=""){
			window.open("toUpdOutlayAction.do?ID="+checkid,"","height=550,width=850,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function IsCreate(){
		if(checkid!=""){
		window.open("../finance/makePayableAction.do?OID="+checkid,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../finance/delOutlayAction.do?id="+checkid,"newwindow",		"height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function OutlayDetail(){
		if(checkid!=""){
		document.all.submsg.src="../finance/outlayDetailAction.do?ID="+checkid;
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
    <td width="772">账务管理>>费用申请/报销</td>
  </tr>
</table>
  <form name="search" method="post" action="listOutlayAction.do">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">

   <tr class="table-back"> 
            <td width="12%"  align="right">是否复核：</td>
            <td width="18%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>      
			<td width="9%" align="right">是否结款：</td>
            <td width="20%"><windrp:select key="YesOrNo" name="IsEndcase" p="y|f" value="${IsEndcase}"/></td>
            <td width="13%" align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td>&nbsp;</td>
   </tr>
    <tr class="table-back"> 
            <td width="12%"  align="right">制单部门：</td>
            <td width="18%"><input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
              <input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" value="${deptname}" readonly></td>
            <td width="9%" align="right">制单人：</td>
            <td width="20%"><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}"> <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
            <td width="13%" align="right">制单日期：</td>
            <td width="24%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
-
  <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="4%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0" align="right"></td>
    </tr>
  
</table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del()"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td style="text-align: right;"><pages:pager action="../finance/listOutlayAction.do"/></td>
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
            <td width="10%">编号</td>
            <td width="12%" >报销人</td>
            <td width="13%">核算部门</td>
            <td width="10%">总费用</td>
            <td width="8%">制单机构</td>
            <td width="8%">制单人</td>
            <td width="8%">制单日期</td>
            <td width="6%">是否复核</td>
            <td width="6%">是否结款</td>           
          </tr>
          <logic:present name="usList"> 
		  <logic:iterate id="o" name="usList" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${o.id}');"> 
            <td ><a href="javascript:Update();">${o.id}</a></td>
            <td><windrp:getname key='users' value='${o.outlayid}' p='d'/></td>
            <td><windrp:getname key='dept' value='${o.castdept}' p='d'/></td>
            <td align="right"><windrp:format value='${o.totaloutlay}' p="###,##0.00"/></td>
            <td><windrp:getname key='organ' value='${o.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${o.makeid}' p='d'/></td>
            <td><windrp:dateformat value='${o.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='YesOrNo' value='${o.isaudit}' p='f'/></td>
            <td><windrp:getname key='YesOrNo' value='${o.isendcase}' p='f'/></td>            
          </tr>
          </logic:iterate> </logic:present> 
      </table>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:OutlayDetail();"><span>费用详情</span></a></li>
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
