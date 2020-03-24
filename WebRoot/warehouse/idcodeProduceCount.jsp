<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>  <div id="div1"><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>仓库管理>>条码统计</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="idcodeProduceCountAction.do" >
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr class="table-back" style="height: 30px"> 
            <td align="right">条码生产日期：</td>
            <td align="left"><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}"
            onFocus="selectDate(this);"  size="10" readonly="readonly">
				-
				  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" 
				  onFocus="selectDate(this);"  size="10" readonly="readonly">
				  </td>
              <td class="SeparatePage" style="width: 60%"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
         <tr class="title-func-back"> 
          <td width="50">
		</td>
		<td width="1"></td>
		<td width="50">
		</td>
		<td width="1"></td>
		<td class="SeparatePage"></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light" style="height: 25px">
          <td align="right">条码总数合计： </td>
          <td align="left" colspan="2" style="width: 86%">${count} 条</td>
        </tr>
      </table>
	 </div>
    </td>
  </tr>
</table>
</body>
</html>
