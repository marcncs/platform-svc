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
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
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
	window.open("../finance/toAddReckoningAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdReckoningAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	if(checkid!=""){
			document.all.submsg.src="../finance/reckoningDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}

	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../finance/delReckoningAction.do?RID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">还款清算列表</td>
        </tr>
      </table>
      <form name="search" method="post" action="listReckoningAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right"><input name="POID" type="hidden" id="POID" value="${poid}">
            制单日期：</td>
            <td width="28%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
              -
            <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="11%" align="right">&nbsp;</td>
            <td width="18%">&nbsp;</td>
            <td width="10%" align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
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
          <td style="text-align: right;"><pages:pager action="../finance/listReckoningAction.do"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">

          <tr align="center" class="title-top">
            <td width="13%" >编号</td> 
            <td width="14%" >还款人</td>
			<td width="18%">借款金额</td>
            <td width="16%">还款金额</td>
            <td width="14%">制单人</td>
            <td width="13%">制单日期</td>
            <td width="12%">是否复核</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');">
            <td >${s.id}</td> 
            <td >${s.uidname}</td>
			<td><windrp:format value='${s.loansum}'/></td>
            <td><windrp:format value='${s.backsum}'/></td>
            <td>${s.makeidname}</td>
            <td>${s.makedate}</td>
            <td>${s.isauditname}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
	  <br>
      <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>详情</span></a></li>
            </ul>
          </div>
          <div>
          <IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'submsg','listdiv');setIframeHeight(this)"></IFRAME>
          </div>
       </div>
	</td>
  </tr>
</table>

</body>
</html>
