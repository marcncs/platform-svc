<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}

function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.organId.value=p.id;
	document.search.oname.value=p.organname;
}

function Import(){		
	popWin("../sys/toImportCustomerMatchOrderAction.do",500,300);
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
    <td width="772"> 系统设置&gt;&gt;用户管理</td>
  </tr>
</table>
 <form name="search" method="post" action="listCustomerMatchOrderAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          
          <tr class="table-back">
            <td width="8%" align="right">机构：</td>
            <td width="26%"><input name="organId" type="hidden" id="organId" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          	<td width="8%" align="right">关键字： </td>
            <td width="25%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
        	<td colspan="5" class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50">
				<a href="javascript:Import()"><img
						src="../images/CN/import.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;导入 </a>
			</td>
		  <td class="SeparatePage"><pages:pager action="../sys/listCustomerMatchOrderAction.do"/>	</td>
							
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
            <td width="10%">编号</td>
            <td width="20%">管家婆名称</td>
            <td width="10%">产品线</td>
            <td width="20%">公司名称</td>
            <td width="10%">是否VIP</td>
            <td width="10%">拣货顺序</td>
            <td style="width: auto;">出货顺序</td>
          </tr>
		  <logic:iterate id="u" name="cmoList" >
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
            <td  align="left">${u.id}</td>
            <td align="left"> ${u.siname }</td>
            <td align="left">${u.productline}</td>
            <td align="left"><windrp:getname key='organ' value='${u.organid}' p='d'/></td>
			<td align="left"><c:if test="${u.customerlevel==null || u.customerlevel==0}">否</c:if>
	    	<c:if test="${u.customerlevel==1}">是</c:if></td>
			<td align="left"> ${u.matchorder }</td>
            <td align="left">${u.outorder}</td>
          </tr>
          </logic:iterate> 
       
      </table>
 </form>
	</div>
</td>
  </tr>
</table>


</body>
</html>
