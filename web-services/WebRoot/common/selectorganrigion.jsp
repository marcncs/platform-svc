<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="tag.jsp"%>
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
var organ={id:"",oecode:"",organname:"",otel:"",ofax:"",oemail:"",omobile:"",oaddr:""};
var organrigion={id:"",organname:""};
function CheckedObj(obj,objid,objoecode,objorganname,objotel,objofax,objoemail,objomobile,objoaddr){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 organ.id=objid;
 organ.oecode=objoecode;
 organ.organname=objorganname;
 organ.otel=objotel;
 organ.ofax=objofax;
 organ.oemail=objoemail;
 organ.omobile=objomobile;
 organ.oaddr=objoaddr;
 
 organrigion.id=objid;
 organrigion.organname=objorganname;
 //     var vche = obj.firstChild.firstChild;
//		 if(vche.checked){
//		 	vche.checked = false;
//		 }else{
//		 	vche.checked = true;
//		  }

}

function  CheckedObj_organ(obj,objid,objorganname){


           if(obj.checked){
             obj.parentNode.parentNode.className="table-back-colorbar";
           }
       
         
         obj.className="event";
         checkid=objid;
         organrigion.id=objid;
         organrigion.organname=objorganname;
}


function Check(){
		var pid = document.all("che");
		var checkall = document.all("checkall");
		if (pid==undefined){return;}
		if (pid.length){
			for(i=0;i<pid.length;i++){
					pid[i].checked=checkall.checked;
				   //trObj=pid[i].parentNode.parentNode;
			}
		}else{
			pid.checked=checkall.checked;
		}	
		
		
		
	}
function Affirm(){
		if(checkid!=""){
		window.returnValue=organ;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}
	
 function AffirmComfin(){  
  var organrigion;
  var organrigionArray =new Array();
	var che = document.getElementsByName("che");
	
	var cheall=document.getElementById("cheall");
	
	if(cheall.checked){
	    checkid='1'; 
	}
	
		if(checkid>0){
			  for(var i=0;i<che.length;i++){
			    if(che[i].checked){
			   organrigion = {id : document.getElementsByName("organid")[i].value ,organname :document.getElementsByName("organname")[i].value};
			   organrigionArray.push(organrigion);
		      }
  	      }
			  window.returnValue=organrigionArray;
			  window.close();
		}else{
		   alert("请选择你要操作的记录!");
		}
}	
	
var cobj ="";
function getResult(getobj,toobj){
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									选择机构
								</td>

							</tr>
						</table>
						<form name="search" method="post"
							action="../common/selectOrganRigionAction.do?OID=${OID }">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">

								<tr class="table-back">
									<td width="9%" align="right">
										省份：
									</td>
									<td width="20%">
										<select name="Province"
											id="province" onChange="getResult('province','city');">
											<option value="">
												-省份-
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
										<select name="City" onChange="getResult('city','areas');">
											<option value="">
												-城市-
											</option>
											<option value="${City}" ${City==null?"":"selected"}>
												<windrp:getname key='countryarea' value='${City}' p='d' />
											</option>
										</select>
									</td>
									<td width="10%" align="right">
										地区：
									</td>
									<td width="25%">
										<select name="Areas" id="areas">
											<option value="">
												-地区-
											</option>
											<option value="${Areas}" ${Areas==null?"":"selected"}>
												<windrp:getname key='countryarea' value='${Areas}' p='d' />
											</option>
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
									<td align="right">
										&nbsp;

									</td>
									<td>
										&nbsp;

									</td>
									<td align="right">
										&nbsp;

									</td>
									<td>
										&nbsp;

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
								<td width="50">
									<a href="#" onClick="javascript:AffirmComfin();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;取消</a>
								</td>
								<td class="SeparatePage">
									<pages:pager action="../common/selectOrganRigionAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 400px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top-lock">

									<td width="10%">
										<input type="checkbox" id="cheall"  name="checkall" onClick="Check();">
									</td>
									<td width="10%">
										编号
									</td>
									<td width="10%">
										内部编码
									</td>
									<td width="16%">
										机构名称
									</td>
									<td width="8%">
										省
									</td>
									<td width="8%">
										市
									</td>
									<td width="8%">
										区
									</td>
									<td width="18%">
										上级机构
									</td>
									<td width="12%">
										手机
									</td>
								</tr>
								<logic:iterate id="d" name="dpt">
									<tr align="center" class="table-back-colorbar"
										onClick="CheckedObj(this,'${d.id}','${d.oecode}','${d.organname}','${d.otel}','${d.ofax}','${d.oemail}','${d.omobile}','${d.oaddr}');">
										<td>
											<input type="checkbox" name="che" value="${d.id}"
												${d.isCheck==1? "checked":"" }  />
											<input type="hidden" name="organid" value="${d.id}">
											<input type="hidden" name="oecode" value="${d.oecode}">
											<input type="hidden" name="organname" value="${d.organname}">
											<input type="hidden" name="provincename"
												value="${d.provincename}">
											<input type="hidden" name="cityname" value="${d.cityname}">
											<input type="hidden" name="areasname" value="${d.areasname}}">
											<input type="hidden" name="parentidname"
												value="${d.parentidname}">
											<input type="hidden" name="omobile" value="${d.omobile}">
										</td>
										<td>
											${d.id}
										</td>
										<td>
											${d.oecode}
										</td>
										<td>
											${d.organname}
										</td>
										<td>
											${d.provincename}
										</td>
										<td>
											${d.cityname}
										</td>
										<td>
											${d.areasname}
										</td>
										<td>
											${d.parentidname}
										</td>
										<td>
											${d.omobile}
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
