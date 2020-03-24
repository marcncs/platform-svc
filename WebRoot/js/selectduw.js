var strFrame; // 存放日历层的HTML代码
document
		.writeln('<iframe id=duwLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 200; height:128;  display:none ;z-index:999;"></iframe>');
strFrame = '<style>';
strFrame += '.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame += 'left:0; top:0;background-color:#FFFFFF;width:100%;height:128;';
strFrame += 'border-left:1 solid #000000;border-bottom:1 solid #000000;border-top:1 solid #000000;border-right:1 solid #000000;';
strFrame += 'SCROLLBAR-FACE-COLOR: #4682B4; ';
strFrame += 'SCROLLBAR-HIGHLIGHT-COLOR: #4682B4;';
strFrame += 'SCROLLBAR-SHADOW-COLOR: #FFFFFF;';
strFrame += 'SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;';
strFrame += 'SCROLLBAR-ARROW-COLOR: #ffffff;';
strFrame += 'SCROLLBAR-TRACK-COLOR: #ffffff;';
strFrame += 'SCROLLBAR-DARKSHADOW-COLOR: #4682B4;}';
strFrame += '.seldiv div{MARGIN-TOP: 2px;MARGIN-LEFT: 2px;';
strFrame += 'zmm: expression(onmouseover = function(){ style.background="#000066"; style.color="white"; },onmouseout = function(){ style.background="white"; style.color="black"; });}';
strFrame += '.seldiv A {FONT-SIZE: 12px;text-decoration: none;color:#000000}';
strFrame += '</style>';

strFrame += '<div id="duwdiv" class="seldiv" onselectstart="return false">';

strFrame += '</div>';
window.frames.duwLayer.document.writeln(strFrame);
window.frames.duwLayer.document.close(); // 解决ie进度条不结束的问题

// ==================================================== WEB 页面显示部分
// ======================================================
// 任意点击时关闭该控件 //ie6的情况可以由下面的切换焦点处理代替
// document.onclick=doc_onclicks;
// document.body["onclick"] = user_onclicks;
document.attachEvent("onclick", duw_onclicks);
function duw_onclicks() {
	with (window.event) {
		if (srcElement.getAttribute("Author") == null
				&& srcElement != outduwObject && srcElement != outButton) {
			closeduwLayer();
		}
	}
}

// 按Esc键关闭，切换焦点关闭
document.attachEvent("onkeyup", duw_onkeyup);
function duw_onkeyup() {
	if (window.event.keyCode == 27) {
		if (outduwObject)
			outduwObject.blur();
		closeduwLayer();
	} else if (document.activeElement)
		if (document.activeElement.getAttribute("Author") == null
				&& document.activeElement != outduwObject
				&& document.activeElement != outButton) {
			closeduwLayer();
		}
}
// 这个层的关闭
function closeduwLayer() {
	document.all.duwLayer.style.display = "none";

}

var outduwObject;
var outButton; // 点击的按钮
var hiddenobj;
var billtype;
var wibaddr;
var clearobj1;
var clearobj2;
function selectDUW(v_tt, v_hidden, v_oid, v_type, v_other, v_clear1, v_clear2) // 主调函数
{
	var dads = document.all.duwLayer.style;
	var th = v_tt;
	var ttop = v_tt.offsetTop; // TT控件的定位点高
	var thei = v_tt.clientHeight; // TT控件本身的高
	var tleft = v_tt.offsetLeft; // TT控件的定位点宽
	var ttyp = v_tt.type; // TT控件的类型
	var twidth = v_tt.offsetWidth; // TT控件的宽
	while (v_tt = v_tt.offsetParent) {
		ttop += v_tt.offsetTop;
		tleft += v_tt.offsetLeft;
	}
	dads.top = (ttyp == "image") ? ttop + thei : ttop + thei + 4;
	dads.left = tleft;
	outduwObject = th;
	outButton = null; // 设定外部点击的按钮
	// -------------------------------
	hiddenobj = v_hidden;
	wibaddr = v_other;
	clearobj1 = v_clear1;
	clearobj2 = v_clear2;
	dads.pixelWidth = twidth;
	window.frames.duwLayer.document.getElementById("duwdiv").style.pixelWidth = twidth;
	window.frames.duwLayer.document.getElementById("duwdiv").innerHTML = '';
	if (v_type == 'd') {
		getDeptByOid(v_oid);
	} else if (v_type == 'ou') {
		// 按机构获取用户
		getUsersByOid(v_oid);
	} else if (v_type == 'du') {
		// 按部门获取用户
		getUsersByDept(v_oid);
	} else if (v_type == 'w') {
		// 按机构获取仓库
		getWarehouseByOid(v_oid);
	} else if (v_type == 'vw') {
		getVisitWarehouseByOid(v_oid);
	} else if (v_type == 'rw') {
		getWarehouseByOid(v_oid, v_type);
	} else if (v_type == 'b') {
		// 按仓库获取仓位
		getWarehouseBitByWid(v_oid);
	} else if (v_type == 'dn') {
		// 按仓库获取仓位
		getAllDeptName();
	} else if (v_type == 'pn') {
		//获取产品名
		getAllProductName();
	} else if (v_type == 'w2') {
		// 按机构获取仓库
		getWarehouseByOid2(v_oid);
	} else if (v_type == 'ddu') {
		// 按明细数据获取用户
		getUsersByDetailData(v_oid, v_other);
	} else if(v_type == 'psn') {
		// 按产品名称获取规格
		getPackSizeNameByProductName(v_oid);
	} else if(v_type == 'tn') {
		// 获取erp单据导入模板编号
		getTemplateNo(v_oid);
	}else if(v_type == 'area'){
		getAreasByRegionId(v_oid);
	} else {
		alert(v_type);
		alert("参数错误!");
	}
	// --------------------------------
	dads.display = '';
	event.returnValue = false;
}

function changevalue(v_id, v_name) {
	document.getElementById(hiddenobj).value = v_id;
	outduwObject.value = v_name;
	clearValues();
	closeduwLayer();
}

function clearValues() {
	if (clearobj1 != undefined && clearobj1 != '') {
		document.getElementById(clearobj1).value = "";
	}
	if (clearobj2 != undefined && clearobj2 != '') {
		document.getElementById(clearobj2).value = "";
	}
}

function changeWarehouseValue(v_id, v_name, v_addr) {
	var oldv = document.getElementById(hiddenobj).value;
	document.getElementById(hiddenobj).value = v_id;
	outduwObject.value = v_name;
	if (wibaddr != undefined && wibaddr != '') {
		document.getElementById(wibaddr).value = v_addr;
	}
	closeduwLayer();
	if(typeof clearProductList != 'undefined' && clearProductList instanceof Function) {
		if(oldv != v_id && (hiddenobj == 'outwarehouseid' || hiddenobj == 'warehouseid' || hiddenobj == 'WarehouseID')) {
			clearProductList();
		}
	}
}

function getDeptByOid(v_oid) {
	var url = '../users/ajaxOrganDeptAction.do?oid=' + v_oid;
	var pars = '';
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showdept
	});
}
function showdept(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.deptlist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#" onclick="parent.changevalue(\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changevalue(\'' + lk[j].id
					+ '\',\'' + lk[j].deptname + '\')"><div align=left>'
					+ lk[j].deptname + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getUsersByOid(v_oid) {
	var url = '../users/ajaxOrganUsersAction.do';
	var pars = 'oid=' + v_oid;
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showusers
	});
}
function getUsersByDept(v_did) {
	var url = '../users/ajaxDeptUsersAction.do';
	var pars = 'did=' + v_did;
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showusers
	});
}
function showusers(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.userslist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#" onclick="parent.changevalue(\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changevalue(\'' + lk[j].userid
					+ '\',\'' + lk[j].realname + '\')"><div align=left>'
					+ lk[j].realname + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getWarehouseByOid(v_oid) {
	var url = '../users/ajaxOrganWarehouseAction.do';
	var pars = 'oid=' + v_oid;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showwarehouse
	});
}
function showwarehouse(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.warehouselist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeWarehouseValue(\''
					+ lk[j].id + '\',\'' + lk[j].warehousename + '\',\''
					+ lk[j].warehouseaddr + '\')"><div align=left>'
					+ lk[j].warehousename + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getWarehouseByOid(v_oid, type) {
	var url = '../users/ajaxOrganWarehouseAction.do';
	var pars = 'oid=' + v_oid + "&type=" + type;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showwarehouse2
	});
}
function showwarehouse2(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.warehouselist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeWarehouseValue(\''
					+ lk[j].id + '\',\'' + lk[j].warehousename + '\',\''
					+ lk[j].warehouseaddr + '\')"><div align=left>'
					+ lk[j].warehousename + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getVisitWarehouseByOid(v_oid) {
	var url = '../users/ajaxVisitOrganWarehouseAction.do';
	var pars = 'oid=' + v_oid;
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showvisitwarehouse
	});
}
function showvisitwarehouse(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.warehouselist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeWarehouseValue(\''
					+ lk[j].id + '\',\'' + lk[j].warehousename + '\',\''
					+ lk[j].warehouseaddr + '\')"><div align=left>'
					+ lk[j].warehousename + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getWarehouseBitByWid(v_wid) {
	var url = '../users/ajaxWarehouseBitAction.do';
	var pars = 'wid=' + v_wid;
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showwarehousebit
	});
}
function showwarehousebit(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.warehousebit;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#" onclick="parent.changevalue(\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changevalue(\'' + lk[j].wbid
					+ '\',\'' + lk[j].wbid + '\')"><div align=left>'
					+ lk[j].wbid + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function getAllDeptName() {
	var url = '../users/ajaxDeptNameAction.do';
	var pars = '';
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showdeptname
	});
}

function getAllProductName() {
	var url = '../purchase/ajaxProductNameAction.do';
	var pars = '';
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showdeptname
	});
}

function showdeptname(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.deptlist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changevalue(\'\',\'' + lk[j]
					+ '\')"><div align=left>' + lk[j] + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}


function getWarehouseByOid2(v_oid) {
	var url = '../users/ajaxOrganWarehouseAction.do';
	var pars = 'oid=' + v_oid;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showwarehouse3
	});
}
function showwarehouse3(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.warehouselist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeWarehouseValue2(\''
					+ lk[j].id + '\',\'' + lk[j].warehousename + '\',\''
					+ lk[j].warehouseaddr + '\')"><div align=left>'
					+ lk[j].warehousename + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function changeWarehouseValue2(v_id, v_name, v_addr) {
	var oldv = document.getElementById(hiddenobj).value;
	document.getElementById(hiddenobj).value = v_id;
	outduwObject.value = v_name;
	if (wibaddr != undefined && wibaddr != '') {
		document.getElementById(wibaddr).innerHTML = v_name;
	}
	closeduwLayer();
	if(typeof clearProductList != 'undefined' && clearProductList instanceof Function) {
		if(oldv != v_id && hiddenobj == 'outwarehouseid') {
			clearProductList();
		}
	}
}


function getUsersByDetailData(table, column) {
	var url = '../users/ajaxDetailDateUsersAction.do';
	var pars = 'table=' + table + "&column="+column;
	var myAjax = new Ajax.Request(url, {
		method : 'get',
		parameters : pars,
		onComplete : showddusers
	});
}
function showddusers(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.userslist;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#" onclick="parent.changevalue(\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changevalue(\'' + lk[j].userid
					+ '\',\'' + lk[j].realname + '\')"><div align=left>'
					+ lk[j].realname + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}


function getPackSizeNameByProductName(v_productName) {
	var url = '../purchase/ajaxPackSizeNameAction.do';
	var pars = 'productName=' + v_productName;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showPackSizeName
	});
}
function showPackSizeName(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.packSizeList;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changePackSizeNameValue(\''
					+ lk[j] + '\')"><div align=left>'
					+ lk[j] + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function changePackSizeNameValue(packSizeName) {
	document.getElementById(hiddenobj).value = packSizeName;
	outduwObject.value = packSizeName;
	closeduwLayer();
}


function getTemplateNo(v_organId) {
	var url = '../erp/ajaxTemplateNoAction.do';
	var pars = 'organId=' + v_organId;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showTemplateNo
	});
}
function showTemplateNo(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.templateNoList;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeTemplateNoValue(\''
					+ lk[j] + '\')"><div align=left>'
					+ lk[j] + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function changeTemplateNoValue(templateNo) {
	document.getElementById(hiddenobj).value = templateNo;
	outduwObject.value = templateNo;
	closeduwLayer();
}


function getAreasByRegionId(v_organId) {
	var url = '../keyretailer/ajaxSbonusAreaAction.do';
	var pars = 'regionId=' + v_organId;
	var myAjax = new Ajax.Request(url, {
		method : 'post',
		parameters : pars,
		onComplete : showAreasList
	});
}
function showAreasList(originalRequest) {
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.areaList;
	if (lk == undefined) {
	} else {
		var duwdiv = window.frames.duwLayer.document.getElementById("duwdiv");
		var sst = "";
		// sst+='<a href="#"
		// onclick="parent.changeWarehouseValue(\'\',\'\',\'\')"><div
		// align=left>&nbsp;</div></a>';
		for ( var j = 0; j < lk.length; j++) {
			sst += '<a href="#" onclick="parent.changeAreasValue(\''
					+ lk[j].id + '\',\''+lk[j].name+'\')"><div align=left>'
					+ lk[j].name + '</div></a>';
		}
		duwdiv.innerHTML = sst;
	}
}

function changeAreasValue(areaId,areaName) {
	document.getElementById(hiddenobj).value = areaId;
	outduwObject.value = areaName;
	closeduwLayer();
}