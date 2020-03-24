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


this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}	

function ProductIncomeDetail(piid){
	popWin("../warehouse/productIncomeDetailAction.do?ID="+piid+"&type=2",1000,650);
}

$(function(){
	checkOne();
	checkTwo();
});


function checkOne(){
	if($("#pnccode").val()!="" || $("#packdate").val()!="" || $("#code").val()!=""){
		//$("#code").attr("readonly","readonly");
		$("#boxcode").attr("disabled","disabled");
	}else{
		//$("#code").removeAttr("readonly");
		$("#boxcode").removeAttr("disabled");
	}
}

function checkTwo(){
	if($("#boxcode").val()!=""){
		$("#pnccode").attr("disabled","disabled");
		$("#packdate").attr("disabled","disabled");
		$("#code").attr("disabled","disabled");
	}else{
		$("#pnccode").removeAttr("disabled");
		$("#packdate").removeAttr("disabled");
		$("#code").removeAttr("disabled");
	}
}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
A {
	FONT-SIZE: 14px;
	text-decoration: underline;
}
</style>
</head>
<body style="overflow: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772" style="font-size: 12px"> 防伪防窜货管理&gt;&gt;物流码流向查询</td>
        </tr>
      </table>
      <form id="form" name="search" method="post" action="../assistant/listViewIdcodeTrendAction.do?type=2">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"><%--
        <tr class="table-back"> 
      	 <td colspan="4"> <br/></td>
       </tr>
		  <tr class="table-back"> 
		    <td width="20%" align="right">产品编码：</td>
            <td width="20%"><input type="text" name="pnccode"  value="${pnccode }" size="30">
             </td>
             <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
          </tr>
          <tr class="table-back"> 
		    <td width="20%" align="right">分装日期：</td>
             <td width="20%"><input type="text" name="packdate"  value="${packdate }" size="30">
              </td>
           <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
          </tr>
           <tr class="table-back"> 
		    <td width="20%" align="right">基础包装码：</td>
            <td width="20%"><input type="text" name="code" value="${code }" size="30">
            </td>
          <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
          </tr>
           <tr class="table-back"> 
		    <td width="20%" align="right">物流箱码：</td>
            <td width="20%"><input type="text" name="boxcode"  value="${boxcode }" size="30">
            <span><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></span>
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
           <tr class="table-back"> 
      	 <td colspan="4"> <br/></td>
       </tr>
          <tr class="table-back"> 
		    <td width="20%" align="right">&nbsp;</td>
            <td width="20%"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="查询" style="width: 70"/></td>
           <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
          </tr>
      --%>
      
      <tr class="table-back"> 
		    <td width="20%" align="right">产品编码：</td>
            <td width="30%"><input type="text" name="pnccode" id="pnccode"  value="${pnccode }" size="30" onkeyup="checkOne()"></td>
            <td width="9%"  align="right">分装日期：</td>
            <td width="23%"><input type="text" name="packdate" id="packdate"  value="${packdate }" size="30" onkeyup="checkOne()"> </td>
            
            <td width="9%" align="right">&nbsp;</td>
            <td width="31%" class="SeparatePage">&nbsp;</td>
          </tr>
          
          <tr class="table-back"> 
		    <td width="20%" align="right">基础包装码：</td>
            <td width="30%"><input type="text" name="code" id="code" value="${code }" size="30" onkeyup="checkOne()"></td>
            <td width="9%"  align="right">物流箱码：</td>
            <td width="23%"><input type="text" name="boxcode" id="boxcode"  value="${boxcode }" size="30" onkeyup="checkTwo()"> </td>
            
            <td width="9%" align="right">&nbsp;</td>
            <td width="31%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      
      
      </table>
       </form>
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back"> 
			<td width="50">
				&nbsp;
			</td>
			<td class="SeparatePage">
				&nbsp;
			</td>
		</tr>
      </table>
       <table width="100%" height="100px" border="0" cellpadding="0" cellspacing="0">
		<tr class="table-back"> 
		   <td width="20%" align="right" style="font-size: 14px"><b>查询结果：</b></td>
            <td width="20%">
           &nbsp;<c:if test="${noResult=='noResult'}">无结果</c:if>
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
		</tr>
      </table>
      </div>
	</td>
	</tr>
	
	<tr>
	<td>
	<div id="listdiv" style="overflow: auto; height: 600px;width: 100%;" class="table-back">
	<FORM METHOD="POST" name="listform" ACTION="">
      <table   width="100%"  border="0" cellpadding="0" cellspacing="0" >
        <c:if test="${viewform.code!='hidden'}">
          <tr class="table-back" height="30px"> 
		    <td width="20%" align="right" style="font-size: 14px">基础码：</td>
            <td width="20%" style="font-size: 14px">${viewform.code }
<%--            <textarea rows="2" cols="40" style="height: 60px" readonly="readonly"></textarea>--%>
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
           </c:if>
           <tr class="table-back" height="30px"> 
		    <td width="20%" align="right" style="font-size: 14px">物流箱码：</td>
            <td width="20%" style="font-size: 14px">${viewform.boxcode }
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
            <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">客户编号：</td>
            <td width="20%" style="font-size: 14px">${viewform.organNo }
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
            <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">客户名称：</td>
            <td width="20%" style="font-size: 14px">${viewform.organName }
            </td>
           <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
            <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">产品名称：</td>
            <td width="20%" style="font-size: 14px">${viewform.pname }
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
           <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">规格：</td>
            <td width="20%" style="font-size: 14px">${viewform.specmode }
            </td>
          <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
            <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">批次：</td>
            <td width="20%" style="font-size: 14px">${viewform.batch }
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
            <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">生产日期：</td>
            <td width="20%" style="font-size: 14px">${viewform.produceDate }
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
           <tr class="table-back"> 
		    <td width="20%" align="right" style="font-size: 14px">生产订单号：</td>
            <td width="20%" style="font-size: 14px"><c:if test="${viewform.billNo ne null}"><a href="javascript:ProductIncomeDetail('${viewform.billNo}');"><span>${viewform.billNo}</span></a></c:if>
            </td>
            <td width="10%" >&nbsp;</td>
            <td width="50%" >&nbsp;</td>
           </tr>
      </table>
       </form>
      <br>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
