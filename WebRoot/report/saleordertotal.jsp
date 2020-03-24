<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectSingleProduct(){
	var p=showModalDialog("../warehouse/toSelectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	document.searchform.productid.value=p.id;
	document.searchform.productname.value=p.productname;
	}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售汇总表</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="saleOrderTotalAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr class="table-back">
            <td width="8%"  align="right">产品：</td>
            <td width="29%"><input name="productid" type="hidden" id="productid">
                <input id="productname" name="productname">
              <a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
            <td width="9%" align="right">制单机构：</td>
            <td width="23%"><select name="makeorganid" id="makeorganid">
              <option value="">请选择...</option>
              <logic:iterate id="o" name="alos">
                <option value="${o.id}" ${o.id==equiporganid?"selected":""}>
                  <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">制单人：</td>
            <td width="22%"><select name="makeid" id="makeid">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);"  size="10">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" onFocus="selectDate(this);"size="10">
  <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
          <td  width="11%" align="center" >产品编号</td>
          <td width="20%" align="center" >产品名称</td>
          <td width="18%" align="center" >规格</td>
          <td width="6%" align="center" >单位</td>
          <td width="10%" align="center" >数量</td>
          <td width="11%" align="center" >金额</td>
		  <td width="11%" align="center" >成本</td>
		  <td width="13%" align="center" >毛利</td>
        </tr>
		<c:set var="totalcount" value="0"/>
<c:forEach items="${str}" var="d">
        <tr class="table-back">
          <td  >${d.productid}</td>
          <td >${d.productname}</td>
          <td >${d.specmode}</td>
          <td >${d.unitidname}</td>
          <td align="right"><windrp:format p="###,##0.00" value="${d.quantity}" /></td>
          <td align="right">${d.subsum}</td>
		  <td align="right">${d.cost}</td>
		  <td align="right">${d.gain}</td>
        </tr>
</c:forEach> 
        <tr class="back-gray-light">
          <td  align="right">本页合计：</td>
		  <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="right"><windrp:format p="###,##0.00" value='${totalqt}' /></td>
          <td align="right">${totalsum}</td>
          <td align="right">${totalcost}</td>
          <td align="right">${totalgain}</td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
		  <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="right">${allqt}</td>
          <td align="right">${allsum}</td>
          <td align="right">${allcost}</td>
          <td align="right">${allgain}</td>
        </tr>
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr> 
		  	<td width="50%">&nbsp;</td>
            <td width="50%" align="right">
			<presentation:pagination target="/report/saleOrderTotalAction.do"/>	
            </td>
          </tr>
       
      </table>
	  
	  
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="57%" >&nbsp;</td>
          <td width="43%">&nbsp;</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
