<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>  
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
var member={cid:"",cname:"",mobile:"",officetel:"",saleid:"",paymentmode:"",transportmode:"",tickettitle:"",integral:"",detailaddr:""};
function CheckedObj(obj,objid,objname,objmobile,objofficetel,objspecializeid,objpaymentmode,objtransportmode,objtickettitle,objintegral,objdetailaddr){

 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 member.cid=checkid;
 member.cname = objname;
 member.mobile=objmobile;
 member.officetel=objofficetel;
 member.saleid=objspecializeid;
 member.paymentmode=objpaymentmode;
 member.transportmode=objtransportmode;
 member.tickettitle= objtickettitle;
 member.integral=objintegral;
 member.detailaddr=objdetailaddr;
}

	function Affirm(){
		if(checkid!=""){
		window.returnValue=member;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}
	
	
var cobj ="";
function getResult(getobj,toobj)
{
	cobj = toobj;
	var areaID = $F(getobj);
	var url = '../sales/listAreasAction.do';
	var pars = 'parentid=' + areaID;
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showResponse}
				);
}

function showResponse(originalRequest){
	var city = originalRequest.responseXML.getElementsByTagName("area"); 
					var strid = new Array();
					var str = new Array();
					for(var i=0;i<city.length;i++){
						var e = city[i];
						str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
					}
					buildSelect(str,document.getElementById(cobj));//赋值给city选择框
}

function buildSelect(str,sel) {//赋值给选择框
	sel.options.length=0;
	sel.options[0]=new Option("选择","")
	for(var i=0;i<str.length;i++) {
		sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
	}
}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
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
          <td width="772"> 选择会员</td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectMemberAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
         <tr class="table-back">
            <td  align="right">省份：</td>
            <td><select name="Province"  id="province" onChange="getResult('province','city');">
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}" ${c.id==Province?"selected":""}>${c.areaname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">城市：</td>
            <td><select name="City" onChange="getResult('city','areas');">
              <option value="">-城市-</option>
              <option value="${City}" ${City==null?"":"selected"}><windrp:getname key='countryarea' value='${City}' p='d'/></option>
            </select></td>
            <td align="right">地区：</td>
            <td><select name="Areas" id="areas" >
              <option value="">-地区-</option>
               <option value="${Areas}" ${Areas==null?"":"selected"}><windrp:getname key='countryarea' value='${Areas}' p='d'/></option>
            </select></td> 
			<td></td>           
          </tr>
		 
		  <tr class="table-back"> 
            <td width="13%"  align="right">关键字：</td>
            <td width="23%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="9%"  align="right">&nbsp;</td>
            <td width="24%" >&nbsp;</td>
            <td width="9%" >&nbsp;</td>
			<td></td>
             <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
		  </tr>
       
      </table>
       </form>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back"> 
		   <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td class="SeparatePage"> <pages:pager action="../common/selectMemberAction.do"/>          </td>
        </tr>
      </table>
	   </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center"  class="title-top-lock"> 
            <td width="10%" >编号</td>
            <td width="15%">名称</td>
           <td width="4%">姓别</td>
			<td width="10%">手机</td>
			<td width="10%">电话</td>
			<td width="10%">会员级别</td>
			<td width="15%">机构</td>
			<td width="10%">区域</td>
          </tr>
          <logic:iterate id="c" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${c.cid}','${c.cname}','${c.mobile}','${c.officetel}','${c.specializeid}','${c.paymentmode}','${c.transportmode}','${c.tickettitle}','${c.integral}','${c.detailaddr}');" onDblClick="Affirm();"> 
            <td >${c.cid}</td>
            <td>${c.cname}</td>
            <td>${c.membersexname}</td>
			<td>${c.mobile}</td>
			<td>${c.officetel}</td>
			<td>${c.ratename}</td>
			<td>${c.makeorganidname}</td>
			<td>${c.provincename}${c.cityname}${c.areasname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      </div>
      
    </td>
  </tr>
</table>
</body>
</html>
