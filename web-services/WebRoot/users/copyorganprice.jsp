<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>修改机构</title>
<script src="../js/prototype.js"></script>
<script type="text/javascript">
var cobj ="";
    function getResult(getobj,toobj)
    {
		//alert(getobj);
		cobj = toobj;
        var areaID = $F(getobj);
        //var y = $F('lstYears');
        var url = '../sales/listAreasAction.do';
        var pars = 'parentid=' + areaID;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );

    }

    function showResponse(originalRequest)
    {
        //put returned XML in the textarea
		var city = originalRequest.responseXML.getElementsByTagName("area"); 
						//alert(city.length);
						var strid = new Array();
						var str = new Array();
						for(var i=0;i<city.length;i++){
							var e = city[i];
							str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
							//alert(str);
						}
						buildSelect(str,document.getElementById(cobj));//赋值给city选择框
        //$('cc').value = originalRequest.responseXML.getElementsByTagName("area");
    }

	function buildSelect(str,sel) {//赋值给选择框
	//alert(str.length);

		sel.options.length=0;
		sel.options[0]=new Option("选择","")
		for(var i=0;i<str.length;i++) {
			//alert(str[i]);	
				sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
		}
}


function check(){
	if(document.addRoleForm.tagoid.value==""){
		alert("机构不能为空!");
		addRoleForm.tagoid.focus();
		return false;
	}
	
	var oname =addRoleForm.tagoid.options[addRoleForm.tagoid.selectedIndex].text;
	if ( !window.confirm("克隆将会删除'"+oname+"'机构原先的价格，你确定要克隆吗！")){
		return false;
	}
	return true;
}


</script>
</head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 机构价格克隆</td>
      </tr>
    </table>
	<form name="addRoleForm" method="post" action="copyOrganPriceAction.do" onSubmit="return check();">
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right">
	  	  从：</td>
          <td width="34%"><input name="id" type="hidden" id="id" value="${organ.id}">
            <input name="organname" type="text" id="organname" value="${organ.organname}" size="35" readonly/>
            机构价格</td>
          <td width="8%"  align="right">克隆到：</td>
          <td width="22%"><select name="tagoid" id="tagoid">
                <option value="">请选择...</option>
                <logic:iterate id="p" name="als">
                  <option value="${p.id}" ${p.id==d.parentid?"selected":""}>${p.organname}</option>
                </logic:iterate>
              </select>
            机构</td>
          <td width="7%"  align="right">&nbsp;</td>
          <td width="20%">&nbsp;</td>
	  </tr>
	  <tr>
	    <td  align="right">&nbsp;</td>
	    <td >&nbsp;</td>
	    <td  align="right">&nbsp;</td>
	    <td >&nbsp;</td>
	    <td >&nbsp;</td>
	    <td >&nbsp;</td>
	  </tr>
	  </table>
	</fieldset>
	
        <table width="100%" border="0" cellpadding="0" cellspacing="1">

            <tr align="center">
              <td><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
              <input type="button" name="Submit2" value="取消" onclick="window.close();"></td>
            </tr>
      </table>
	</form></td>
  </tr>
</table>
</body>
</html>
