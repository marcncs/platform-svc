<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
	 
<html>
<head>
<script src="../js/prototype.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var tn = "";
	var roleid=0;
	var omid;
	
	function getMenuList2(mid,rid,mtr,dd,tblname){
	tn=tblname;
	roleid=rid;
	omid=mid;
		document.getElementById(mtr).style.display="block";
		document.getElementById("chbox"+mid).style.display="block";
		document.getElementById(dd).innerHTML="";
	   var url = "../users/ajaxLeftMenuPowerAction.do?id="+mid+"&roleid="+rid;
	   document.getElementById("result").style.display="";
	   document.getElementById("result").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse2}
                    );
		}
	
	function showResponse2(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');	
		var divs = "";
		if ( data != undefined ){			
			var menulist = data.menulist;
			for (k=0; k<menulist.length; k++){
				//二级菜单 
				var leftmenu = menulist[k].secondmenu;
				var scname="sf"+omid;
				var scid=scname+"s"+k;
				var sischecked="";
				if ( leftmenu.roleid != "0" ){
					sischecked = "checked";
				}
				var saddimg = "<img src='../images/join.gif'/>";
				if ( k== menulist.length-1){
					saddimg = "<img src='../images/joinbottom.gif'/>";
				}
				divs=divs+ "<div class='menubdiv'>"+
					"<div class='menumdiv'>"+saddimg+
					"<input type='checkbox' name='"+scname+"' id='"+scid+"' value='"+leftmenu.id+"' onClick='menuclick(this)' "+sischecked+">"+leftmenu.lmenuname+"</div>";
				//有子菜单 
				if ( leftmenu.hassonmenu == "1"){
					//三级菜单 
					var sonmenu = menulist[k].sunmenu;
					var addimg = "<img src='../images/join.gif'/>";
					for(j=0; j<sonmenu.length; j++){
						var thirdmenu = sonmenu[j].thirdmenu;						
						if ( j== sonmenu.length-1){
							addimg = "<img src='../images/joinbottom.gif'/>";
						}
						var tcname="t"+scid;
						var tcid=tcname+"s"+j;
						var tischecked="";
						if ( thirdmenu.roleid != "0" ){
							tischecked = "checked";
						}
						divs=divs+"<div class='menusdiv'>"+addimg+"<input type='checkbox' name='"+tcname+"' id='"+tcid+"' value='"+thirdmenu.id+"' onClick='menuclick(this)' "+tischecked+">"+thirdmenu.lmenuname;
						//操作列表
						var operatelist = sonmenu[j].operatelist;
						var lis = addPowderLi(operatelist, "p"+tcid);
						divs=divs+lis+"</div>";;					
					}
				}else{//没子菜单 
					//操作列表
					var operatelist = menulist[k].operatelist;
					var lis = addPowderLi(operatelist, "p"+scid);
					divs=divs+lis;					
				}
				divs=divs+"</div>";
			}			
			//document.write(divs);
			$(tn).innerHTML=divs;	
			document.getElementById("result").style.display="none";
		}
	}
	
	//添加权限checkbox
	function addPowderLi(operatelist,pname){
		if (operatelist.length == undefined ){
			return "";
		}
		var lis = "";
		for (l=0; l<operatelist.length; l++){
			var ischecked="";
			var addimg = "";
			if ( operatelist[l].roleid != "0" ){
				ischecked = "checked";
			}
			if ( l == 0 ){
				addimg = "<img src='../images/joinbottom.gif' align='top'/>";
			}
			lis=lis+"<li>"+addimg+"<input type='checkbox' name='"+pname+"' value='"+operatelist[l].id+"' onClick='powerclick(this)' "+ ischecked+">"+operatelist[l].operatename+"</li>";
		}
		return lis;
	}
	
	
//-----------------------------checkbox onclick start--------------------------
var lmids="";
var powerids="";
function menuclick(obj){
	lmids="";
	powerids="";
	
	lmids = obj.value +","+lmids;		
	//一级
	if ( obj.id.indexOf('f') == 0 ){
		selectsecond(obj.id, obj.checked);	
	//二级
	}else if ( obj.id.indexOf('s') == 0 ){
		if ( obj.checked ){
			var oname = obj.name;
			var fo =document.getElementById(oname.substring(1,oname.length));
			fo.checked=true;
			lmids = fo.value +","+lmids;	
		}
		selectthird(obj.id, obj.checked);	
	//三级
	}else if ( obj.id.indexOf('t') == 0 ){
		selectpower(obj.id, obj.checked);
		if ( obj.checked ){
			var oname = obj.name;
			var so = document.getElementById(oname.substring(1,oname.length));
			so.checked=true;
			lmids = so.value +","+lmids;	
			var fname = so.name;
			var fo=document.getElementById(fname.substring(1,fname.length));
			fo.checked=true;
			lmids = fo.value +","+lmids;	
		}	
	}
	//alert(lmids);
	//alert(powerids);
	updCheckAllPurview(powerids, obj.checked, lmids);
}

function powerclick(obj){
	lmids="";
	powerids="";
	powerids = obj.value + ","+ powerids;
	if ( obj.checked ){		
		var oname = obj.name;
		var to = document.getElementById(oname.substring(1,oname.length));
		to.checked=true;
		lmids = to.value +","+lmids;	
		var tname = to.name;
		var so = document.getElementById(tname.substring(1,tname.length));
		so.checked=true;
		lmids = so.value +","+lmids;	
		var sname = so.name;
		var fo = document.getElementById(sname.substring(1,sname.length));
		if ( fo ){
			fo.checked=true;
			lmids = fo.value +","+lmids;	
		}
		//alert(lmids);
		//alert(powerids);
	}
	updCheckAllPurview(powerids, obj.checked, lmids);
}

//选择二级菜单 
function selectsecond(objid,ischeck){
	var second = document.all("s"+objid);
	if ( second ){		
		if ( second.length ){
			for (k=0;k<second.length;k++){
				second[k].checked=ischeck;	
				lmids = second[k].value +","+lmids;		
				selectthird(second[k].id, ischeck);			
			}
		}else{
			second.checked=ischeck;
			lmids = second.value +","+lmids;					
			selectthird(second.id, ischeck);			
		}
	}
}

//选择三级菜单
function selectthird(objid, ischeck){
	var third=document.all("t"+objid);
	if ( third ){	
		if ( third.length ){	
			for (i=0;i<third.length;i++){
				third[i].checked=ischeck;
				lmids = third[i].value +","+lmids;
				selectpower(third[i].id, ischeck);
			}
		}else{
			third.checked=ischeck;
			lmids = third.value +","+lmids;
			selectpower(third.id, ischeck);
		}
	}else{
		selectpower(objid, ischeck);
	}
}


//选择权限
function selectpower(objid, ischeck){
	var p=document.all("p"+objid);
	if (p){
		if ( p.length){
			for (j=0;j<p.length;j++){
				p[j].checked=ischeck;
				powerids = p[j].value + ","+ powerids;
			}
		}else{
			p.checked=ischeck;
			powerids = p.value + ","+ powerids;
		}
	}
}
//-----------------------------checkbox onclick end----------------------------
	function hiddenMenu(htr,trdn,subtbl, boxobj){
		document.getElementById(htr).style.display="none";
		document.getElementById(boxobj).style.display="none";
		document.getElementById(trdn).innerHTML="▼";		
		$(subtbl).innerHTML="";
	}
	//更新
	function updCheckAllPurview(oids, bol, lmids){
		var updurl = "";
		if ( bol ){			
			updurl = "../users/updPurviewAjax.do?op=add&oid="+oids+"&lmid="+lmids+"&roleid="+roleid;
		}else{
			if ( roleid ==1){
				// alert("系统管理员不能删除权限！");
				// return;
			}
			updurl = "../users/updPurviewAjax.do?op=del&oid="+oids+"&lmid="+lmids+"&roleid="+roleid;
		}
		//alert(updurl);
		document.getElementById("result").style.display="";
	    document.getElementById("result").innerHTML="正在处理中...";
		var pars = '';
        var myAjax = new Ajax.Request(
                    updurl,{method: 'get', parameters: pars, onComplete: updResult}
                    );
	}
	//提示操作结果
	function updResult(updpurview){
		var result = updpurview.responseXML.getElementsByTagName("resultStr")[0].firstChild.data;
		buildText(result);//赋值给信息提示框
    }
	//显示操作结果
	function buildText(str) {//赋值给消息提示框
		document.getElementById("result").style.display="";
		document.getElementById("result").innerHTML=str;
		setTimeout("document.getElementById('result').style.display='none'",500);
	}

this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>

<title>角色列表</title>
</head>
<body>
<style>
#result {font-weight:bold; position:absolute;left:753px;top:20px; text-align:center; background-color:#ff0000;color:#fff;LEFT:expression(Math.abs(Math.round((document.body.clientWidth - result.offsetWidth)/2))); TOP:expression(Math.abs(Math.round((document.body.clientHeight)/2+document.body.scrollTop-180)))}
#result h1 {font-size:12px; margin:0px; padding:0px 5px 0px 5px};

.menu {list-style:none;width:100%; }
.menubdiv {list-style:none;width:100%; margin-top:2px;  border:0px dashed; }
.menumdiv {list-style:none;width:100%;}
.menusdiv {list-style:none;width:100%; margin-left:24px; }
.menubdiv li {float:left;  margin-left:10px;}
td {white-space:nowrap}
</style>
<div id="result"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td >
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">系统设置>>角色管理>>角色权限</td>
      </tr>
    </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td width="15%">当前角色：</td>
          <td width="85%">${currentRole.rolename}</td>
        </tr>
        <tr class="title-top">
          <td >所属模块</td>
          <td >权限</td>
        </tr>
      </table>
	   </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y:auto;height: 400px;width: 100%;" >
      <table class="sortable" width="100%" border="0" cellpadding="2" cellspacing="1" >
        <form name="listMenu" method="post" action="modifyRoleMenuAction.do" >
          <input type="hidden" name="roleid" value="${currentRole.id}">
         
<!--		    <tr>
              <td  class="table-back">基本权限(必选)</td>
              <td class="table-back"> <a href="javascript:getMenuList(-1,${currentRole.id},'basictr','basicdown','basic');"><span id="basicdown">▼</span></a></td>

            </tr>
            <tr id="basictr" style="display:none">
              <td  align="right" valign="top" class="table-back"><a href="javascript:hiddenMenu('basictr','basicdown','basic');">▲</a></td>
              <td colspan="3" class="table-back">
			  <table id="basic" width="20%" height="100%" border="0" cellpadding="0" cellspacing="0">

              </table></td>
            </tr>
-->			
		  <c:forEach var="lm" items="${lmlist}">
		   <c:if test="${lm.id != 1}">
            <tr class="table-back-colorbar">
              <td  class="table-back" width="15%"><table border="0" width="100%"><tr><td width="90" align="left">${lm.lmenuname} </td><td align="left"><a href="javascript:getMenuList2(${lm.id},${currentRole.id},'powertrs${lm.id}','downspan${lm.id}','menudivs${lm.id}');"><span id="downspan${lm.id}">▼</span></a></td></tr></table></td>
              <td class="table-back" width="85%"> </td>
            </tr>
            <tr id="powertrs${lm.id}" style="display:none" class="table-back-colorbar">
              <td  align="center" valign="top" class="table-back"><a href="javascript:hiddenMenu('powertrs${lm.id}','downspan${lm.id}','menudivs${lm.id}', 'chbox${lm.id}');">▲</a></td>
              <td  class="table-back"><span id="chbox${lm.id}" style="display:none"><input type="checkbox" id="f${lm.id}" value="${lm.id}" onClick="menuclick(this)" ${lm.roleid!=0?"checked":""}>全选</span><div class="menu" id="menudivs${lm.id}"></div></td>
            </tr>
		   </c:if>
		  </c:forEach>	
			
        </form>
    </table>
	 </div>
	</td>
  </tr>
</table>

</body>
</html>


