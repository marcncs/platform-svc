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
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script language="JavaScript">
	var checkid="";
	var queryid="";
	function CheckedObj(obj,objid,objqueryid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 queryid=objqueryid;
	}

function excput(){
	$.ajax({
		type : "POST",
		url : "../assistant/ajaxGetTotalNumberAction.do",
		data : $("#form").serializeArray(),
		dataType: "json",
		async: false,
		success : function(date) {
			if(date.returnCode == 1){
				alert("一次只能导出十万条数据，请重新选择查询条件，分批导出");
			}else{
				search.action="exportPointsNumberAction.do";
				search.submit();
				search.action="../assistant/listPointsNumberAction.do";
			}
		}
	});

}


this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
          <td width="772"> 防伪防窜货管理&gt;&gt;积分码查询</td>
        </tr>
      </table>
      <form id="form" name="search" method="post" action="../assistant/listPointsNumberAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr class="table-back"> 
		    <td width="20%" align="right">积分（防伪）数据：</td>
            <td width="30%"><input type="text" name="pointNumber" maxlength="16" value="${pointNumber }"></td>
            <td width="9%"  align="right">生成日期：</td>
            <td width="23%"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate }" size="10" onFocus="javascript:selectDate(this)">
         -
         <input name="EndDate" type="text" id="EndDate" value="${EndDate }" size="10" onFocus="javascript:selectDate(this)">
         </td>
            
            <td width="9%" align="right">&nbsp;</td>
            <td width="31%" class="SeparatePage">&nbsp;</td>
          </tr>
          
          <tr class="table-back"> 
		    <td width="20%" align="right">是否激活：</td>
            <td width="30%"><windrp:select key="YesOrNo" name="isactive" p="y|f" value="${isactive}" /></td>
            <td width="9%"  align="right">激活日期：</td>
            <td width="23%"><input name="beginActiveDate" type="text" id="BeginDate" value="${beginActiveDate }" size="10" onFocus="javascript:selectDate(this)">
         -
         <input name="endActiveDate" type="text" id="EndDate" value="${endActiveDate }" size="10" onFocus="javascript:selectDate(this)">
         </td>
            
            <td width="9%" align="right">&nbsp;</td>
            <td width="31%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back"> 
			<td width="50">
				<a href="javascript:excput();"><img
						src="../images/CN/downpda.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;导出</a>
			</td>
			<td class="SeparatePage">
				<pages:pager action="../assistant/listPointsNumberAction.do" />
			</td>
		</tr>
      </table>
      </div>
	</td>
	</tr>
	
	<tr>
	<td>
	<div id="listdiv" style="overflow: auto; height: 600px;width: 100%;">
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1" >
        
          <tr align="center" class="title-top-lock">
            <td width="6%" >积分（防伪）数据</td> 
            <td width="7%"  >产品物流编码</td>           
            <td width="7%" > 外箱物流编码</td>
            <td width="5%" > 生成日期</td>
            <td width="4%" >防伪查询次数</td>
            <td width="6%" >防伪首次查询时间</td>			
			<td width="5%" >积分时间</td>
            <td width="5%" >是否积分</td>
            <td width="5%" >是否激活</td>
            <td width="5%" >激活时间</td>
          </tr>
          <logic:iterate id="pi" name="list" > 
          <tr class="table-back-colorbar" > 
            <td>${pi.pointsNumber}</td>
            <td >${pi.proBarcode}</td>
            <td>${pi.cartonBarcode}</td>
            <td>${pi.addDT}</td>
            <td>${pi.fwfindCount}</td>
            <td>${pi.fwfindDT}</td>			
			<td>${pi.pointsDT}</td>
            <td><windrp:getname key='YesOrNo' value='${pi.isPoints}' p='f' /></td>
            <td><windrp:getname key='YesOrNo' value='${pi.isActivated}' p='f' /></td>
            <td>${pi.activatedDT}</td>
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
