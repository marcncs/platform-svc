<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
	//alert(getCookie("id"));
	//document.searchform.CID.value=getCookie("cid");
	document.searchform.KeyWord.value=getCookie("cname");
	}
	
	
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
		var province = document.searchform.Province.value;
		if ( province == "" ){
			alert("请选择省份!");
			return false;
		}
		return true;
	}
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售单区域分析</td>
        </tr>
      </table>
      <form name="searchform" method="post" action="saleOrderCustomerAction.do" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		<tr class="table-back">
            <td  align="right">省份：</td>
            <td><select name="Province"  id="province" onChange="getResult('province','city');">
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}">${c.areaname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">城市：</td>
            <td width="28%"><select name="City" onChange="getResult('city','areas');">
              <option value="">-城市-</option>
            </select></td>
            <td width="11%" align="right">地区：</td>
            <td width="22%"><select name="Areas" id="areas" >
              <option value="">-地区-</option>
            </select></td>            
          </tr>
          <tr class="table-back"> 
            <td width="10%"  align="right">制单机构：</td>
            <td width="15%"><!--<input name="CID" type="hidden" id="CID" value="${CID}">-->
              <select name="MakeOrganID" id="MakeOrganID">
                <option value="">请选择...</option>
                <logic:iterate id="o" name="alos">
                  <option value="${o.id}" ${o.id==equiporganid?"selected":""}>${o.organname}</option>
                </logic:iterate>
              </select></td>
            <td width="14%" align="right">制单人：</td>
            <td ><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
  <td width="11%" align="right">制单日期：</td>
            <td ><input name="BeginDate" type="text" id="BeginDate" value="${BeginDate}" onFocus="selectDate(this);"  size="10">
-
  <input name="EndDate" type="text" id="EndDate" value="${EndDate}" onFocus="selectDate(this);"  size="10"></td></tr>
          <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input id="KeyWord" size="20" name="KeyWord">
              <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        <tr class="title-top-lock">
		  <td width="4%"  align="center" >序号</td>
          <td width="12%" align="center" >省份</td>
          <td width="10%" align="center" >城市</td>
          <td width="14%" align="center" >地区</td>
          <td width="12%"  align="center" >会员名称</td>
          <td width="11%" align="center" >零售单号</td>
          <td width="9%" align="center" >制单机构</td>
          <td width="9%" align="center" >制单人</td>    
		  <td width="5%" align="center" >制单日期</td>        
          <td width="14%" align="center">金额</td>
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
