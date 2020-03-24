<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>机构列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

var checkid="";
function CheckedObj(obj,objid){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
  pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	case "0":Detail(); break;
	 	case "1":LinkMan(); break;
		default:Detail();
	 }
 
}

function Detail(){
	setCookie("oCookie","0");
		if(checkid!=""){
			document.all.basic.src="../ditch/organDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function LinkMan(){
		setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="../ditch/listOlinkmanAction.do?OID="+checkid;
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
	
		function print(){
				excputform.target="_blank";
				excputform.action="../ditch/printOrganAction.do";
				excputform.submit();
		}

	function DownTxt(){
		excputform.action="txtPutOrganAction.do";
		excputform.submit();
	}
	function ImportOrgan(){
		popWin("../ditch/toImportFirOrganAction.do",500,300);
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
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="search" method="post" action="listOrganAction.do">
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
											<option value="${c.id}" ${c.id==Province?"selected":""}>${c.areaname}</option>
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
										</option>
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
								<td></td>
							</tr>
							<tr class="table-back">
								
									<td align="right">
								联系人：
								</td>
								<td>
								<input type="text" name="LinkMan" value="${LinkMan }" maxlength="5"/>
								</td>
								<td align="right">关键字：</td>
								<td><input type="text" name="KeyWord" maxlength="30" value="${KeyWord}"/></td>
								<td align="right" >
									<label style="display:none">是否撤消：</label>
								</td>
								<td >
									<div style="display:none"><windrp:select key="YesOrNo" name="IsRepeal" p="y|f" value="0" /></div>
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
									<a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
                                <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
                                <%--<td width="100"><a href="javascript:ImportOrgan();"><img src="../images/CN/import.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导入客户信息</a></td> --%>
								<td class="SeparatePage">
									<pages:pager action="../ditch/listOrganAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					<FORM METHOD="POST" name="listform" ACTION="">
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							
							<tr align="center" class="title-top-lock">
								<td width="10%">
									编号
								</td>
								<td width="12%">
									内部编码
								</td>
								<td width="20%">
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
								<td width="6%">
									是否撤消
								</td>
							</tr>
							<logic:iterate id="d" name="dpt">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${d.id}');">
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
										<windrp:getname key='YesOrNo' value='${d.isrepeal}' p='f' />
									</td>									
								</tr>
							</logic:iterate>
							
						</table>
						</form>
						<br>
				      <div style="width:100%">
				        <div id="tabs1">
				          <ul>
				            <li><a href="javascript:Detail();"><span>详情</span></a></li>
				            <li><a href="javascript:LinkMan();"><span>联系人</span></a></li>
				          </ul>
				        </div>
				        <div> <IFRAME id="basic" style="WIDTH: 100%; HEIGHT:100%" name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no onload="setIframeHeight(this)"></IFRAME></div>
				      </div>		
					</div>

				</td>
			</tr>
		</table>
		<form name="excputform" method="post" action="excPutOrganAction.do">
			<input type="hidden" name="Province" id="Province"
				value="${Province}">
			<input type="hidden" name="City" id="City" value="${City }">
			<input type="hidden" name="Areas" id="Areas" value="${Areas }">
			<input type="hidden" name="EndDate" id="EndDate" value="${EndDate }">
			<input type="hidden" name="BeginDate" id="BeginDate"
				value="${BeginDate }">
			<input type="hidden" name="IsRepeal" id="IsRepeal"
				value="${IsRepeal }">
			<input type="hidden" name="KeyWord" id="KeyWord" value="${KeyWord }">
		</form>
	</body>
</html>

