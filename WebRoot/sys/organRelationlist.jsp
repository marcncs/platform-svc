<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	function addNew(){
		popWin("selectListOrganAction.do?OID="+'${OID}',700,400);
	}
	function Update(){
		if(checkid!=""){
			popWin("selectListOrganAction.do?SID="+checkid+"&OID=${OID}",700,400);
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}
	
	function Del(){
	if(checkid!=""){
		if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin2("../sys/delTransferRelation.do?ID="+checkid);
		}
	}else{
	alert("请选择你要操作的记录!");
	}
}
	
	function OutPut(){
		search.target="";
		search.action="../sys/excPutSTranferRelationAction.do?OID="+'${OID}';
		search.submit();
		search.action="../sys/listOrganRelationAction.do";
	}
	
	function Import(){ 
	  popWin("../sys/toImportSTransferRelationAction.do",500,300);
	}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 进货关系列表</td>
        </tr>
      </table>
       <form name="search" method="post" action="../sys/listOrganRelationAction.do">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
        <input type="hidden" name="OID" value="${OID}">
        <input type="hidden" name="organModel" value="${organModel}"><!--
    <tr class="table-back"> 
      <td width="8%"  align="right">关键字：</td>
      <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
      <td align="right"></td>
      <td class="SeparatePage"> <input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
  	
	--></table>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
				<td width="1">
				<img src="../images/CN/hline.gif" width="1" height="14">
			</td>	
			<td width="50">
				<a href="javascript:OutPut();"><img
						src="../images/CN/outputExcel.gif" width="16" height="16"
						border="0" align="absmiddle">导出</a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="1" height="14">
			</td>
			<td width="50">
				<a href="javascript:Import()"><img
						src="../images/CN/import.gif" width="16" height="16"
						border="0" align="absmiddle">导入 </a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="1" height="14">
			</td>
			<%--
            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
			<a href="javascript:Visit();"><img src="../images/CN/xuke.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;许可</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="90" align="center">
			<a href="javascript:ConfScanner();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;配置采集器</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			 --%>			
		  <td class="SeparatePage"><pages:pager action="../sys/listOrganRelationAction.do"/></td>						
		</tr>
	</table>
	 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
          						<td width="10%">
									编号
								</td>
            					<td width="10%">
									进货机构编号
								</td>
								<td width="12%">
									进货机构内部编码
								</td>
								<td width="17%">
									进货机构名称
								</td>
								<td width="6%">
									进货机构类别
								</td>
								<td width="6%">
									进货机构类型
								</td>
								<td width="8%">
									省
								</td>
								<td width="6%">
									市
								</td>
								<td width="6%">
									区
								</td>
								<td width="16%">
									进货机构所属上级机构
								</td>
          </tr>
          <logic:iterate id="d" name="dpt" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.sequence}');"> 
          							<td>
										${d.sequence}
									</td>
          							<td>
										${d.id}
									</td>
									<td>
										${d.oecode}
									</td>
									<td>
										${d.organname}
									</td>
									<td>
										<windrp:getname key='OrganType' value='${d.organType}' p='f'/>
									</td>
									<td>
										<windrp:getname key="${d.organType==1?'PlantType':'DealerType' }" value='${d.organModel}' p='f'/>
									</td>
									<td>
										${d.provincename}
									</td>
									<td>
										${d.cityname}
									</td>
									<td>
										${d.areasname}
									</td>
									<td>
										${d.parentidname}
									</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
</body>
</html>
