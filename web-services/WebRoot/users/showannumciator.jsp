<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>  
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}

	function MakePurchasePlan(){
		document.search.action="safetyMakePurchasePlanAction.do";
		search.submit();
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 库存报警 </td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">       
          <tr class="table-back"> 
            <td width="12%"  align="right">机构名称：</td>
            <td width="25%" >
            ${organidname}</td>
            <td width="19%"  align="right">&nbsp;</td>
            <td width="44%" >&nbsp;</td>
          </tr>        
      </table>
    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
        <td width="120"><a href="../users/awakeOrganAnunciatorAction.do">知道了,不要再提醒</a></td> 	          <td width="100"><a href="javascript:window.close();">稍后再提醒我</a></td> 	
          <td class="SeparatePage"> <pages:pager action="../users/showAnnumciatorAction.do"/>          </td>
        </tr>
      </table>
       </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 300px;" >
		<form name="search" method="post" action="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top-lock"> 
            <td width="13%" >产品编号</td>
            <td width="21%">产品名称</td>
            <td width="17%">规格</td>
            <td width="8%">单位</td>
            <td width="8%">当前库存</td>
            <td width="7%">最低库存</td>
            <td width="8%">最高库存</td>
            <td width="18%">报警数量</td>
          </tr>
          <logic:iterate id="pi" name="als" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${pi.productid}');"> 
            <td ><input name="productid" type="hidden" id="productid" value="${pi.productid}">
              ${pi.productid}</td>
            <td><input name="productname" type="hidden" id="productname" value="${pi.productidname}">
              ${pi.productidname}</td>
            <td><input name="specmode" type="hidden" id="specmode" value="${pi.specmode}">
              ${pi.specmode}</td>
            <td><input name="unitid" type="hidden" id="unitid" value="${pi.unitid}">
              ${pi.unitidname}</td>
            <td>${pi.curquantity}</td>
            <td>${pi.safetyl}</td>
            <td>${pi.safetyh}</td>
            <td><input name="quantity" type="hidden" id="quantity" value="<c:choose><c:when test='${pi.curquantity<pi.safetyl}'>${pi.safetyl-pi.curquantity}</c:when><c:otherwise>0</c:otherwise></c:choose>" size="8">
            <c:choose><c:when test='${pi.curquantity<pi.safetyl}'>${pi.safetyl-pi.curquantity}</c:when><c:otherwise>0</c:otherwise></c:choose>
            </td>
            </tr>
          </logic:iterate> 
          
      </table></form>
      </div>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="4%">&nbsp;</td>
          <td width="96%" align="right"></td>
        </tr>
      </table>      
     <!-- <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><a href="../users/awakeOrganAnunciatorAction.do">知道了,不要再提醒</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:MakePurchasePlan();">生成采购计划</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.close();">稍后再提醒我</a></td>
        </tr>
      </table>-->
      
      </td>
  </tr>

</table>
</body>
</html>
