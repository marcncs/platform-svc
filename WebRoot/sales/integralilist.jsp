<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid="";
var checkosort="";
var checkbillno="";
var checkisort="";
	function CheckedObj(obj,objid,objosort,objbillno){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkosort= objosort;
	 checkbillno= objbillno;
	Detail();
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="../sales/listIntegralDetailAction.do?OID="+checkid+"&OSort="+checkosort+"&BillNo="+checkbillno;
		}else{
			alert("请选择你要操作的记录");
		}
	}
	
function SelectCustomer(){
		var os = document.search.OSort.value;
		if(os==0){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( o==undefined){return;}
		document.search.OID.value=o.id;
		document.search.cname.value=o.organname;
		}
		if(os==1){
		var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( m==undefined){return;}
		document.search.OID.value=m.cid;
		document.search.cname.value=m.cname;
		}
		if(os==2){
		var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		document.search.OID.value=p.pid;
		document.search.cname.value=p.pname;
		}
	}
	
	function SelectEOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.EquipOrganID.value=p.id;
			document.search.ename.value=p.organname;
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.OrganID.value=p.id;
			document.search.oname.value=p.organname;
	}
	

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

function excput(){
		search.target="";
		search.action="../sales/excPutIntegralIAction.do";
		search.submit();
		search.action="listIntegralIAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../sales/printIntegralIAction.do";
		search.submit();
		search.target="";
		search.action="listIntegralIAction.do";
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
          <td width="772">会员管理>>积分收入</td>
        </tr>
      </table>
        <form name="search" method="post" action="listIntegralIAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      
          <tr class="table-back">
            <td  align="right">对象类型：</td>
            <td><windrp:select key="ObjectSort" name="OSort" p="y|f" value="${OSort}"/></td>
            <td align="right">选择对象：</td>
            <td><input name="OID" type="hidden" id="OID" value="${OID}">
              <input name="cname" type="text" id="cname" value="${cname}" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">配送机构：</td>
            <td><input name="EquipOrganID" type="hidden" id="EquipOrganID" value="${EquipOrganID}">
              <input name="ename" type="text" id="ename" value="${ename}" size="30" readonly><a href="javascript:SelectEOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">&nbsp;</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">所属机构：</td>
            <td><input name="OrganID" type="hidden" id="OrganID" value="${OrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">制单日期：</td>
            <td ><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" size="10" onFocus="javascript:selectDate(this)" readonly>
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" size="10" onFocus="javascript:selectDate(this)" readonly></td>
            <td align="right">积分类别：</td>
            <td ><windrp:select key="ISort" name="ISort" p="y|d" value="${ISort}"/></td>
            <td  align="right">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td class="SeparatePage"><input name="Submit2" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
		<td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
				width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			<td width="51">
				<a href="javascript:print();"><img
						src="../images/CN/print.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;打印</a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
          <td class="SeparatePage"><pages:pager action="../sales/listIntegralIAction.do"/></td>
        </tr>
      </table>
      </div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="listdiv" style="overflow-y: auto; height: 650px;" >
					 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
		    <td width="5%">编号</td>
		    <td width="5%">对象类型</td>
		    <td width="7%">对象编号</td>
		    <td width="13%">对象名称</td>
            <td width="13%">单据号</td>
            <td width="8%">积分类别</td>
            <td width="6%">应收</td>
            <td width="7%">已收</td>
            <td width="10%">制单日期</td>
            <td width="14%">配送机构</td>
            <td width="12%">所属机构</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="iils" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.oid}','${s.osort}','${s.billno}');"> 
            <td>${s.id}</td>
            <td><windrp:getname key='ObjectSort' value='${s.osort}' p='f'/></td>
            <td>${s.oid}</td>
            <td>${s.oname}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td><windrp:getname key='ISort' value='${s.isort}' p='d'/></td>
            <td align="right"><windrp:format value="${s.rincome}"/></td>
            <td align="right"><windrp:format value="${s.aincome}" /></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.equiporganid}' p='d'/></td>
            <td><windrp:getname key='organ' value='${s.organid}' p='d'/></td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
        
      </table>
      </form>
      <br>
      
<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>收入明细</span></a></li>
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
