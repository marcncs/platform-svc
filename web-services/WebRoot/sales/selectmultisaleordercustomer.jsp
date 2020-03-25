<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script language="javascript">
	var checkid=0;
	var checkname="";
	var specializedept=0;
	var specializeid=0;
	var paymentmode=0;
	var transportmode=0;
	var mobile="";
	var officetel="";
	var tickettitle="";
	var integral=0;
	function CheckedObj(obj,objid,objname,objmobile,objofficetel,objspecializeid,objpaymentmode,objtransportmode,objtickettitle,objintegral){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 mobile=objmobile;
	 officetel=objofficetel;
	 specializeid=objspecializeid;
	 paymentmode=objpaymentmode;
	 transportmode=objtransportmode;
	 tickettitle= objtickettitle;
	 integral=objintegral;
	}

	function Affirm(){
		if(checkid!=""){
		var c={cid:checkid,cname:checkname,mobile:mobile,officetel:officetel,saleid:specializeid,paymentmode:paymentmode,transportmode:transportmode,tickettitle:tickettitle,integral:integral};
		window.returnValue=c;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

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


function addNew(){
	window.open("../sales/toAddMemberAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function Update(){
		if(checkid!=""){
		window.open("../sales/toUpdMemberAction.do?cid="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"			);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
		//--------------------------------start -----------------------	
function getRowObj(obj) 
{ 
var i = 0; 
while(obj.tagName.toLowerCase() != "tr"){ 
obj = obj.parentNode; 
if(obj.tagName.toLowerCase() == "table")return null; 
} 
return obj; 
} 

//根据得到的行对象得到所在的行数 
function getRowNo(obj){ 
var trObj = getRowObj(obj); 
var trArr = trObj.parentNode.children; 
for(var trNo= 0; trNo < trArr.length; trNo++){ 
if(trObj == trObj.parentNode.children[trNo]){ 
return trNo+1; 
} 
} 
}  
	
	function setvalue(obj){
		var ri = getRowNo(obj);
 
		var pl= document.listform.cid;
		if ( !pl.length ){
			var cid=document.listform.item("cid").value;
			var cname=document.listform.item("cname").value; 
			var mobile=document.listform.item("mobile").value;
			var officetel = document.listform.item("officetel").value;
			var specializeid=document.listform.item("specializeid").value; 
			var paymentmode=document.listform.item("paymentmode").value; 
			var transportmode = document.listform.item("transportmode").value; 
			var tickettitle = document.listform.item("tickettitle").value; 
			var integral = document.listform.item("integral").value; 
		
			var c={cid:cid,cname:cname,mobile:mobile,officetel:officetel,saleid:specializeid,paymentmode:paymentmode,transportmode:transportmode,tickettitle:tickettitle,integral:integral};
			opener.window.addItemValue(c);
		}else{
			var cid=document.listform.item("cid")[ri-2].value;
			var cname=document.listform.item("cname")[ri-2].value; 
			var mobile=document.listform.item("mobile")[ri-2].value;
			var officetel = document.listform.item("officetel")[ri-2].value;
			var specializeid=document.listform.item("specializeid")[ri-2].value; 
			var paymentmode=document.listform.item("paymentmode")[ri-2].value; 
			var transportmode = document.listform.item("transportmode")[ri-2].value; 
			var tickettitle = document.listform.item("tickettitle").value; 
			var integral = document.listform.item("integral")[ri-2].value; 
		var c={cid:cid,cname:cname,mobile:mobile,officetel:officetel,saleid:specializeid,paymentmode:paymentmode,transportmode:transportmode,tickettitle:tickettitle,integral:integral};
			opener.window.addItemValue(c);
		}
	}
//--------------------------------end -----------------------	

</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择客户</td>
        </tr>
      </table> 
      <form name="search" method="post" action="../sales/selectMultiSaleOrderCustomerAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
         <tr class="table-back">
          <td width="11%"  align="right">省份：</td>
            <td width="19%"><select name="Province"  id="province" onChange="getResult('province','city');">
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}">${c.areaname}</option>
              </logic:iterate>
            </select></td>
            <td width="14%" align="right">城市：</td>
            <td width="28%"><select name="City" onChange="getResult('city','areas');">
              <option value="">-城市-</option>
            </select></td>
            <td width="13%" align="right">地区：</td>
            <td width="15%"><select name="Areas" id="areas" >
              <option value="">-地区-</option>
            </select></td>
          </tr>
		   <tr class="table-back">
            <td  align="right">是否激活：</td>
            <td>${isactivateselect}</td>
            <td align="right">客户来源：</td>
            <td>${sourcename}</td>
            <td align="right">性别：</td>
            <td>${sexname}</td>
          </tr>
		  <tr class="table-back">
            <td  align="right">制单机构：</td>
            <td><select name="MakeOrganID" id="MakeOrganID">
              <option value="">所有机构</option>
              <logic:iterate id="ol" name="ols">
                <option value="${ol.id}">
                <c:forEach var="i" begin="1" end="${fn:length(ol.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ol.organname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">制单人：</td>
            <td><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="d" name="uls">
                <option value="${d.userid}">${d.realname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">手机是否空：</td>
            <td><select name="tel">
              <option value="">选择</option>
			  <option value="0">否</option>
			  <option value="1">是</option>			  			  
            </select></td>
          </tr>
		   <tr class="table-back">
            <td  align="right">登记日期：</td>
            <td><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="12">
-
<input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="12"></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="">
             <input type="submit" name="Submit2" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
		  </tr>
       
      </table>
       </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="4%" >编号</td>
            <td width="14%">名称</td>
           <td width="6%">姓别</td>
			<td width="9%">手机</td>
			<td width="9%">电话</td>
			<td width="10%">会员级别</td>
			<td width="14%">机构</td>
			<td width="24%">区域</td>
			<td width="6%">选择</td>
          </tr>
          <logic:iterate id="c" name="sls" > 
          <tr class="table-back" onClick="CheckedObj(this,'${c.cid}','${c.cname}','${c.mobile}','${c.officetel}',${c.specializeid},${c.paymentmode},${c.transportmode},'${c.tickettitle}','${c.integral}');" onDblClick="Affirm();"> 
            <td ><input type="hidden" name="cid" value="${c.cid}">${c.cid}</td>
            <td><input type="hidden" name="cname" value="${c.cname}">${c.cname}</td>
            <td>${c.membersexname}</td>
			<td><input type="hidden" name="mobile" value="${c.mobile}">${c.mobile}</td>
			<td><input type="hidden" name="officetel" value="${c.officetel}">${c.officetel}</td>
			<td><input type="hidden" name="specializeid" value="${c.specializeid}"><input type="hidden" name="paymentmode" value="${c.paymentmode}"><input type="hidden" name="transportmode" value="${c.transportmode}"><input type="hidden" name="tickettitle" value="${c.tickettitle}"><input type="hidden" name="integral" value="${c.integral}">${c.ratename}</td>
			<td>${c.makeorganidname}</td>
			<td>${c.provincename}${c.cityname}${c.areasname}</td>
			<td><a href="#" onClick="setvalue(this)">选择</a></td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/sales/selectMultiSaleOrderCustomerAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
