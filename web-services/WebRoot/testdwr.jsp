<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<script src='dwr/interface/TestDWR.js'></script>
<script src='dwr/engine.js'></script>
<script src='dwr/util.js'></script>
<script src='js/prototype.js'></script>
<script src='js/validate.js'></script>
<script src='js/page.js'></script>

<script language="javascript">
	function listResult(){
		alert("abcde");
		DWRUtil.removeAllRows("newarea");
		var map={ AreaName:$F("AreaName"), Rank:$F("Rank") };

		TestDWR.getAreaByDWR(fillTable,map,currPage,pageSize);
		document.getElementById("currentpage").innerHTML=currPage;
	}
	
	var callfunction=[
   //function(unit) { return unit.id },
   function(unit) { return unit.areaname},
   function(unit) { return unit.parentid},
   function(unit) { return unit.rank}
   //function(unit) { return "<tr><td height='1'></td><td height='1' colspan='3' background='images/CN/index_mid07.gif'></td><td height='1'></td></tr>"}
	];

  function fillTable(apartment) {
	//  alert("ffff"+DWRUtil.getValue("abc"));
    DWRUtil.addRows("newarea", apartment[0], callfunction);//更新数据 

	
	recordCount=apartment[1];						//分页
	recordCount=parseInt(recordCount);
	if (recordCount > 0) {
			pagenum = parseInt(recordCount / pageSize);
			if (recordCount % pageSize > 0) {
				pagenum++;
			}
		}	

		//alert("pagenum========="+pagenum);
		document.getElementById("count").innerHTML=recordCount;
		document.getElementById("pagenum").innerHTML=pagenum;

  }
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>WINDRP-分销系统</title>
</head>

<body onload="listResult();setTimeout('page1()',100);">
<form id="form1" name="form1" method="post" action="">
<table width="50%" height="26" border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td width="18%">名称：</td>
    <td width="20%"><INPUT TYPE="text" NAME="AreaName"></td>
    <td width="21%">级别：</td>
    <td width="15%"><SELECT NAME="rank">
	<option value="0">国</option>
	<option value="1">省</option>
	<option value="2">市</option>
	</SELECT></td>
    <td width="26%">
      <input type="button" name="Submit" value="提交" onclick="listResult();" />
   
    </td>
  </tr>
  
</table>
</form>
<table width="50%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="32">活动名</td>
    <td>电话</td>
    <td>次数</td>
  </tr>
	<tbody id="newarea" class="zi">

	</tbody>
</table>
<table width="50%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td id="first"></td>
    <td id="prev"></td>
    <td id="next"></td>
    <td id="last"></td>
    <td>共<span id="count"></span>条记录,第<span id="currentpage"></span>页/共<span id="pagenum"></span>页&nbsp;&nbsp;  跳到第<input type="text" name="pageno" size="1" > 页

    <input type="submit" name="Submit2" value="Go"  onclick="pageno()"/></td>
  </tr>
</table>
</body>
</html>
