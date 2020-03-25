<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"> </SCRIPT>
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
	window.open("../finance/toAddPayableAction.do","","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdPayableAction.do?ID="+checkid,"","height=550,width=800,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	if(checkid!=""){
			document.all.submsg.src="../finance/payableDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
	function Refer(){
		if(checkid!=""){
			window.open("../finance/toReferPayableAction.do?PAID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			window.open("../finance/delPayableAction.do?PID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Check(){
		var pid = document.all("rid");
		var checkall = document.all("checkall");
		if(pid==undefined){return;}
		if (pid.length){
			for(i=0;i<pid.length;i++){
					pid[i].checked=checkall.checked;
			}
		}else{
			pid.checked=checkall.checked;
		}		
	}
	
	function TransPaymentLog(){
		var flag=true;
		var rid=document.all("rid");
		var rids="";
		if ( rid.length >1){
			for(var i=0; i<rid.length; i++){
				if (rid[i].checked){
					flag=false;
					rids="'"+rid[i].value+"',"+rids;
				}
			}
		}else{
			if(rid.checked){
				flag=false;
				rids="'"+rid.value+"',";
			}
		}
		if (flag){
			alert("请选择你要操作的记录!");
		}else{
			window.open("../finance/toTransPaymentLogAction.do?rids="+rids,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");					
		}
	}
	
	function Destory(){
		var flag=true;
		var rid=document.all("rid");
		var rids="";
		if ( rid.length >1){
			for(var i=0; i<rid.length; i++){
				if (rid[i].checked){
					flag=false;
					rids="'"+rid[i].value+"',"+rids;
				}
			}
		}else{
			if(rid.checked){
				flag=false;
				rids="'"+rid.value+"',";
			}
		}
		if (flag){
			alert("请选择你要操作的记录!");
		}else{
			window.open("../finance/toDestoryPaymentLogAction.do?rids="+rids,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");					
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
          <td width="772">应付款结算单列表</td>
        </tr>
      </table>
      <form name="search" method="post" action="listPayableAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="9%"  align="right"><input name="POID" type="hidden" id="POID" value="${poid}">
            制单日期：</td>
            <td width="24%"><input name="BeginDate" type="text" id="BeginDate" size="10" value="${BeginDate}" onFocus="javascript:selectDate(this)" readonly>
              -
            <input name="EndDate" type="text" id="EndDate" size="10" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
            <td width="8%" align="right">关键字：</td>
            <td width="27%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="8%" align="right">&nbsp;</td>
            <td width="12%">&nbsp;</td>
            <td width="12%" align="right"><input name="Submit2" type="image" id="Submit" src="../images/CN/search.gif" border="0"></td>
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
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td width="60"><a href="javascript:TransPaymentLog()"><img src="../images/CN/tranpayment.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;转付款</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td width="50"><a href="javascript:Destory()"><img src="../images/CN/hexiao.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;核销</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager action="../finance/listPayableAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
		 
          <tr align="center" class="title-top">
		  <td width="3%" class="sorttable_nosort"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="12%" >编号</td>
            <td width="10%">应付款结算金额</td>
            <td width="11%">制单人</td>
            <td width="9%">制单日期</td>
            <td width="10%">到期日</td>
            <td width="7%">超龄(天)</td>
            <td width="12%">单据号</td>
            <td width="24%">描述</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${s.id});"> 
		  <td align="center"><input type="checkbox" name="rid" value="${s.id}" ></td>
            <td >${s.id}</td>
            <td align="right"><windrp:format value='${s.payablesum}' p="###,##0.00"/></td>
            <td>${s.makeidname}</td>
            <td><windrp:dateformat value='${s.makedate}'/></td>
            <td><windrp:dateformat value='${s.awakedate}' p='yyyy-MM-dd'/></td>
            <td>${s.overage}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td>${s.payabledescribe}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
	   
      </table>
       </form>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'submsg','listdiv');setIframeHeight(this)"></IFRAME></div>
      </div>
	   
      
</body>
</html>
