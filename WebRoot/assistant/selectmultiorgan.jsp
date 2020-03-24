<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
	function CheckedObj(obj,objid,objname,objmobile,objintegral){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 mobile=objmobile;
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
 
		var pl= document.listform.oid;
		if ( !pl.length ){
			var oid=document.listform.item("oid").value;
			var oname=document.listform.item("oname").value; 
			var otel=document.listform.item("otel").value;
			var integral = document.listform.item("integral").value; 
		
			var o={oid:oid,oname:oname,otel:otel,integral:integral};
			opener.window.addItemValue(o);
		}else{
			var oid=document.listform.item("oid")[ri-2].value;
			var oname=document.listform.item("oname")[ri-2].value; 
			var otel=document.listform.item("otel")[ri-2].value;
			var integral = document.listform.item("integral")[ri-2].value; 
			var o={oid:oid,oname:oname,otel:otel,integral:integral};
			opener.window.addItemValue(o);
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
          <td width="772"> 选择机构</td>
        </tr>
      </table>
      <form name="search" method="post" action="../assistant/selectMultiOrganAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
         <tr class="table-back">
            <td  align="right">省份：</td>
            <td><select name="Province"  id="province" onChange="getResult('province','city');">
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}">${c.areaname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">城市：</td>
            <td><select name="City" onChange="getResult('city','areas');">
              <option value="">-城市-</option>
            </select></td>
            <td align="right">地区：</td>
            <td><select name="Areas" id="areas" >
              <option value="">-地区-</option>
            </select></td>            
          </tr>
		 
		  <tr class="table-back"> 
            <td width="13%"  align="right">关键字：</td>
            <td width="26%" ><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
            <td width="6%"  align="right">&nbsp;</td>
            <td width="24%" >&nbsp;</td>
            <td width="9%" >&nbsp;</td>
            <td width="22%" >&nbsp;</td>
		  </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="6%" >编号</td>
            <td width="18%">名称</td>
			<td width="9%">电话</td>			
			<td width="35%">区域</td>
			<td width="25%">地址</td>
			<td width="7%">选择</td>
          </tr>
          <logic:iterate id="c" name="sls" > 
          <tr class="table-back" > 
            <td ><input type="hidden" name="oid" value="${c.id}">${c.id}</td>
            <td><input type="hidden" name="oname" value="${c.organname}">${c.organname}</td>
			<td><input type="hidden" name="otel" value="${c.otel}">${c.otel}</td>
			<td>${c.provincename}${c.cityname}${c.areasname}</td>
			<td><input type="hidden" name="oaddr" value="${c.oaddr}"><input type="hidden" name="integral" value="${c.integral}">${c.oaddr}</td>			
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
             <!--   <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td> -->
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/assistant/selectMultiOrganAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
