<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>机构列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript">

var checkid="";
var organ={id:"",oecode:"",organname:"",organModel:""};
function CheckedObj(obj,objid,objoecode,objorganname,objorganModel){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 organ.id=objid;
 organ.oecode=objoecode;
 organ.organname=objorganname;
 organ.organModel=objorganModel;
}

function Affirm(){
		if(checkid!=""){
		window.returnValue=organ;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}


var cobj ="";
function getResult(getobj,toobj){
	if(getobj=='province'){
		buildSelect("",document.getElementById("areas"));
	}
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
		sel.options[0]=new Option("-选择-","")
		for(var i=0;i<str.length;i++) {
			sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
		}
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}

function oncheck(){
		var type = $j("#type").val();
		var oid = $j("#OID").val();
		search.target="";
		search.action="../sap/selectPlantAction.do?type="+type+"&OID="+oid;
		search.submit();
	}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
<table width="100%" border="0" cellpadding="0"
	cellspacing="0" class="title-back">
	<tr>
		<td width="10">
			<img src="../images/CN/spc.gif" width="10" height="1">
		</td>
		<td width="772">
			${msgb }
		</td>
		
	</tr>
</table>
	<form name="search" method="post"
		onsubmit="return oncheck();">
		<input type="hidden"  id="type"  value="${type }"/>
		<input type="hidden"  id="OID"  value="${OID }"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">

<tr class="table-back">
	<td width="9%" align="right">
		省份：
	</td>
	<td width="20%">
		<select name="Province"
			id="province" onChange="getResult('province','city');">
			<option value="">
				-选择-
			</option>
			<logic:iterate id="c" name="cals">
				<option value="${c.id}" ${c.id==Province?"selected":""}>
					${c.areaname}
				</option>
			</logic:iterate>
			</select>
		</td>
		<td width="10%" align="right">
			城市：
		</td>
		<td width="26%">
			<select id="city" name="City" onChange="getResult('city','areas');">
				<option value="">
					-选择-
				</option>s
				<logic:iterate id="c" name="Citys">
				<option value="${c.id}" ${c.id==City?"selected":""}>${c.areaname}</option>
				</logic:iterate>
			</select>
		</td>
		<td width="10%" align="right">
			地区：
		</td>
		<td width="25%">
			<select id="areas" name="Areas" id="areas">
				<option value="">
					-选择-
				</option>
				<logic:iterate id="c" name="Areass">
				<option value="${c.id}" ${c.id==Areas?"selected":""}>${c.areaname}</option>
				</logic:iterate>
			</select>
		</td>
		<td>
		</td>
	</tr>
	<tr class="table-back">
		<td align="right">
			关键字：
		</td>
		<td>
			<input type="text" name="KeyWord" value="${KeyWord}">
		</td>
		<td align="right">&nbsp;
			
		</td>
		<td>&nbsp;
			
		</td>
		<td align="right">&nbsp;
			
		</td>
		<td>&nbsp;
			
		</td>
		<td class="SeparatePage">
			<input name="Submit" type="image" id="Submit"
				src="../images/CN/search.gif" border="0" title="查询">
		</td>
	</tr>
	
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr class="title-func-back">
  <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
        <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
  <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>							
     <td class="SeparatePage"><pages:pager action="../sap/selectPlantAction.do"/></td>
	</tr>
</table>
	 </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
        <table class="sortable"  width="100%" border="0" cellpadding="0" cellspacing="1">
          
            <tr align="center" class="title-top-lock">
              <td width="10%" >编号</td>
			  <td width="12%" >内部编码</td>
              <td width="26%">经销商名称</td>
              <td width="8%">省</td>
              <td width="8%">市</td>
              <td width="8%">区</td>
              <td width="18%">上级机构</td>
              <td width="12%">类型</td>
            </tr>
              <logic:iterate id="d" name="dpt" >
                <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${d.id}','${d.oecode}','${d.organname}','${d.organModel}');" onDblClick="Affirm();">
                  <td >${d.id}</td>
				  <td >${d.oecode}</td>
                  <td>${d.organname}</td>
                  <td>${d.provincename}</td>
                  <td>${d.cityname}</td>
                  <td>${d.areasname}</td>
                  <td>${d.parentidname}</td>
                  <td>
					<windrp:getname key="${d.organType==1?'PlantType':'DealerType' }" value='${d.organModel}' p='f'/>
				</td>
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

