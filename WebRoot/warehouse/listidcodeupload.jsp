<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language="JavaScript"> 
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
function download(obj){
	document.location="../common/downloadfile.jsp?filename=upload\fail\\"+obj;
}
	
function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}

function reProcess(id) {
	document.getElementById("listform").action = "reProcessIdcodeUploadAction.do?uploadId="+id;
	document.getElementById("listform").submit();
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>${menusTrace }</td>
        </tr>
      </table>
       <form name="search" method="post" action="../warehouse/listIdcodeUploadAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
         <tr class="table-back"> 
            <td width="9%"  align="right">制单机构：</td>
            <td width="25%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>			</td>
            <td width="8%" align="right">制单人：</td>
            <td ><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
<input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
			<td width="5%"></td>
			<td width="5%"></td>
			<td width="5%"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">单据类型：</td>
            <td><windrp:select key="BillSort" name="BillSort" p="y|f" value="${BillSort}" /></td>
			<td width="10%" align="right">日志日期：</td>
            <td width="24%"><input id="BeginDate" onFocus="javascript:selectDate(this)" size="12" name="BeginDate" value="${BeginDate}" readonly="readonly">
-
  <input id="EndDate" onFocus="javascript:selectDate(this)" size="12" name="EndDate" value="${EndDate}" readonly="readonly"></td>
            <td align="right">关键字：</td>
			<td width="19%" ><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">	
					
		  <td class="SeparatePage"><pages:pager action="../warehouse/listIdcodeUploadAction.do"/></td>
							
		</tr>
	 </table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 600px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%"  cellpadding="0" cellspacing="1"  bordercolor="#dddddd">
        
          <tr align="center" class="title-top-lock"> 
            <td width="6%" >编号</td>
            <td width="11%">文件名称</td>
			<td width="8%">单据类型</td>
            <td width="7%">有效数量</td>
			<td width="5%" >无效数量</td>
            <td width="6%" >原文件</td>
			<td width="6%" >无效文件</td>
			<td width="14%" >制单机构</td>
			<td width="9%" >制单人</td>
			<td width="12%" >制单日期</td>
			<td width="7%" >处理状态</td>
			<ws:hasAuth operationName="/warehouse/reProcessIdcodeUploadAction.do">
			<td width="5%">
				重新处理
			</td>			
			</ws:hasAuth>
          </tr>
          <logic:iterate id="u" name="alpi" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
            <td>${u.id}</td>
            <td>${u.filename}</td>
            <td><windrp:getname key="BillSort" value="${u.billsort}" p='f'/></td>
			<td>${u.valinum}</td>
			
			<td>${u.failnum + (u.warnnum ==''?0:u.warnnum)}</td>
            <td title="${u.filepath}"><a href="../common/downloadS3file.jsp?filename=<windrp:encode key='${u.filepath}' />">下载</a></td>
			<td title="${u.failfilepath}">
				<a href="../common/downloadS3file.jsp?filename=<windrp:encode key='${u.failfilepath}' />">下载</a>
<%--				
				<c:choose>
				   <c:when test="${(u.failnum == 0 and u.warnnum == 0) and u.isdeal != -1}">下载</c:when>
				   <c:otherwise><a href="../common/downloadfile.jsp?filename=${u.failfilepath}">下载</a></c:otherwise>
				</c:choose>		--%>	
			</td>
			<td><windrp:getname key='organ' value='${u.makeorganid}' p='d'/></td>
			<td><windrp:getname key='users' value='${u.makeid}' p='d'/></td>
			<td><windrp:dateformat value='${u.makedate}'/></td>
			<td><windrp:getname key='DealState' value='${u.isdeal}' p='f'/></td>
			<ws:hasAuth operationName="/warehouse/reProcessIdcodeUploadAction.do">
			<td>
				<c:if test="${u.isdeal == -1}">
					<input type="button" onclick="reProcess(${u.id})" value="重新处理"></input>
				</c:if>
			</td>
			</ws:hasAuth>
			
            </tr>
          </logic:iterate> 
        
      </table>   
      </form> 
	  </div> 
    </td>
  </tr>
</table>
<form name="excputform" method="post" action="">
			<input type="hidden" name="MakeOrganID" id="MakeOrganID"
				value="${MakeOrganID}">
			<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
			<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
			<input type="hidden" name="POrganID" id="POrganID" value="${POrganID}">
			<input type="hidden" name="IsComplete" id="IsComplete" value="${IsComplete}">
			<input type="hidden" name="BeginDate" id="BeginDate" value="${BeginDate}">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate}">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord}">
		</form>
</body>
</html>
