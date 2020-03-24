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
			document.search.makeOrganId.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","makeId","uname");

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
       <form name="search" method="post" action="../sys/listOrganUploadLogAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
         <tr class="table-back"> 
            <td width="9%"  align="right">制单机构：</td>
            <td width="25%"><input name="makeOrganId" type="hidden" id="makeOrganId" value="${makeOrganId}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>			</td>
            <td width="8%" align="right">制单人：</td>
            <td ><input type="hidden" name="makeId" id="makeId" value="${makeId}">
<input type="text" name="uname" id="uname" onClick="selectDUW(this,'makeId','OrganUploadLog','ddu','makeId')" value="${uname}" readonly></td>
			<td width="5%"></td>
			<td width="5%"></td>
			<td width="5%"></td>
          </tr>
          <tr class="table-back">
            <td  align="right">处理状态：</td>
            <td><windrp:select key="SapUploadLogStatus" name="isDeal" p="y|f"
											value="${isDeal}" /></td>
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
					
		  <td class="SeparatePage"><pages:pager action="../sys/listOrganUploadLogAction.do"/></td>
							
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
            <td width="7%">有效数量</td>
			<td width="7%" >无效数量</td>
            <td width="6%" >原文件</td>
			<td width="6%" >日志文件</td>
			<td width="14%" >制单机构</td>
			<td width="9%" >制单人</td>
			<td width="12%" >制单日期</td>
			<td width="7%" >处理状态</td>
			<%--			
			<ws:hasAuth operationName="/warehouse/reProcessIdcodeUploadAction.do">
			<td width="5%">
				重新处理
			</td>			
			</ws:hasAuth>--%>
          </tr>
          <logic:iterate id="u" name="alpi" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${u.id});"> 
            <td>${u.id}</td>
            <td>${u.fileName}</td>
			<td>${u.totalCount - u.errorCount}</td>
			<td>${u.errorCount}</td>
            <td title="${u.filePath}"><a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.filePath}' />">下载</a></td>
			<td title="${u.logFilePath}">
				<c:choose>
				   <c:when test="${u.isDeal == 0 or u.isDeal == 1}">下载</c:when>
				   <c:otherwise><a href="../common/downloadSapfile.jsp?filename=<windrp:encode key='${u.logFilePath}' />">下载</a></c:otherwise>
				</c:choose>
			</td>
			<td><windrp:getname key='organ' value='${u.makeOrganId}' p='d'/></td>
			<td><windrp:getname key='users' value='${u.makeId}' p='d'/></td>
			<td><windrp:dateformat value='${u.makeDate}'/></td>
			<td>
			${u.dealStatus}
			</td>
			<%--			
			<ws:hasAuth operationName="/warehouse/reProcessIdcodeUploadAction.do">
			<td>
				<c:if test="${u.isdeal == -1}">
					<input type="button" onclick="reProcess(${u.id})" value="重新处理"></input>
				</c:if>
			</td>
			</ws:hasAuth>--%>
			
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
