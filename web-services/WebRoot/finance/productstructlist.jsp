<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>

<script src="../js/prototype.js"></script>
<script type="text/javascript">
function selectnode(obj){
	if(document.getElementById("treecon").getElementsByTagName("div")[0].style.display=="block")
	document.getElementById("parentadd").value = obj.innerHTML;
	if(document.getElementById("treecon").getElementsByTagName("div")[1].style.display=="block")
	document.getElementById("parentadd2").value = obj.innerHTML;
}
function selectdo(obj){
	if(obj.innerHTML=="增加产品类别")
	{
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].style.border="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].getElementsByTagName("div")[0].style.display="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].style.border="1px solid #ccc";
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].getElementsByTagName("div")[0].style.display="block";
	}else{
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].style.border="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].getElementsByTagName("div")[0].style.display="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].style.border="1px solid #ccc";
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].getElementsByTagName("div")[0].style.display="block";
	}
}
</script>
<style>
*{ padding:0px; margin:0px}



#treelist {	
	width: 180px;
	height:300px;
	overflow: auto;
	border: 1px solid #999999;
}
#treelist ul {
	margin-left:20px;
	display:block;
}
#treelist ul li{
	margin-top:2px; 
	margin-bottom:2px;
	list-style-type:none;
	display:block;

}
#treelist ul li div{ float:left; width:150px;}
#treelist ul a { 
	color: #cc0080; text-decoration: none; 
}
#treelist ul span{
	background-image: url(../images/tree.gif);
	background-repeat: no-repeat;
	padding-left:15px;
	cursor:hand;
	height:15px;
}
.yuandian{
	background-position: 0px 2px;
}
.jiahao{
	background-position: 0px -52px;
}
.jianhao{
	background-position: 0px -25px;
}


html,body{ height:100%; border:none; overflow:hidden}
*{ padding:0; margin:0px;}
body,input,select{ font-size:11px; font-family:Tahoma;}
#control{
	position:absolute; 
	bottom:0px; 
	height:70px; 
	border-top:4px solid #999; 
	width:100%;	
	background-image: url(../images/infobg.gif);
	background-repeat: repeat-x;
	background-position: left top;
	padding:3px;
}
#control td { padding:5px;}
#treecon { height:150px; margin-bottom:80px; border-top:4px solid #999}
fieldset { margin:5px 2px 2px 2px;padding-bottom:5px;}
fieldset legend { cursor:hand}
fieldset legend { margin-bottom:0px;}
fieldset p { margin-top:8px;}
fieldset a { color:#333333}
.inputtree1{ width:96px; margin-left:5px}

</style>

<script language="JavaScript">

	var str = "";
	var id1;
	var child = 1;
	var ob;
	function innerChild(acode,obj)//展开节点
	{
		ob=obj;
		if(obj.className=="jiahao"){
			if (obj.parentElement.getElementsByTagName("ul")[0]){
				obj.parentElement.getElementsByTagName("ul")[0].style.display="block";
			}else{
				getResult(acode);				
			}
			
			obj.className="jianhao";			
		}else{
			obj.className="jiahao";
			obj.parentElement.getElementsByTagName("ul")[0].style.display="none";
		}	

	}


	function selectTag(areaname,acode){//点击地区名

		if(document.getElementById("treecon").getElementsByTagName("div")[0].style.display=="block"){
			document.getElementById("parentadd0").value = acode;
			document.getElementById("parentadd").value = areaname;
		}
		if(document.getElementById("treecon").getElementsByTagName("div")[1].style.display=="block"){
			document.getElementById("parentadd2").value = areaname;
			document.getElementById("parentadd1").value=acode;
			}
		
	}

	function addParntAddr(){//增加地区
		var acode=document.getElementById("parentadd0").value ;
		//alert("acode===="+acode);
		if(acode==null||acode==""){
		alert("请选择父地点!");
			return ;
		}
		var newareaname=document.getElementById("newarea").value;
		//alert("acode==========="+acode+"-------name====="+newareaname);

		var url = '../purchase/ajaxAddProductStructAction.do';
        var pars = 'parentid=' + acode+'&areaname='+newareaname;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars}
                    );
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].style.border="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[0].getElementsByTagName("div")[0].style.display="none";
	}
	
	function updParntAddr(){//修改地区
		var acode=document.getElementById("parentadd1").value;
		var newareaname=document.getElementById("parentadd2").value;
		if(newareaname==""||newareaname=="请选择地点"){
			alert("请选择要修改的地点");
			return ;
		}
		var url = '../purchase/ajaxUpdProductStructAction.do';
        var pars = 'acode=' + acode+'&areaname='+newareaname;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars}
                    );
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].style.border="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].getElementsByTagName("div")[0].style.display="none";
	}
	
	function delParntAddr(){//删除
		var dcode=document.getElementById("parentadd1").value;
		var dname=document.getElementById("parentadd2").value;
		if(dname==""||dname=="请选择地点"){
			alert("请选择要删除的类别");
			return ;
		}
		var url = '../purchase/ajaxDelProductStructAction.do';
        var pars = 'acode=' + dcode+'&areaname='+dname;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars}
                    );
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].style.border="none";
		document.getElementById("treecon").getElementsByTagName("fieldset")[1].getElementsByTagName("div")[0].style.display="none";
	}

	
    function getResult(thisid)//获得地区　
    {
		//alert("into");
		str="";
        id1 = thisid;
        var url = '../purchase/ajaxListProductStructAction.do';
        var pars = 'parentid=' + id1;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse1,onSuccess:innerresult}
                    );
    }

    function showResponse1(originalRequest)//后执行

    {
		if(id1=="-1"){
			document.getElementById("root").innerHTML=str;
		}
		else{
			var ul, li, a; 
			str=str.substring(0,str.length-1);
			if(str!=""){
				var str1=str.split(";");
				var ulstr = "";
				for(var i=0;i<str1.length;i++){	
					ulstr += "<li><span onclick=\"innerChild('"+str1[i].split("|")[0]+"',this)\" class=\"jiahao\">&nbsp;</span>";					
					ulstr += "<nobr><a href=\"javascript:product('"+str1[i].split("|")[0]+"')\" onclick=\"selectTag('"+str1[i].split("|")[1]+"',"+str1[i].split("|")[0]+")\">"+str1[i].split("|")[1]+"&nbsp;</a></nobr>";
					ulstr += "</li>";
					}							
					ulstr = "<ul style='display:block'>"+ulstr+"</ul>";
					ob.className = "jianhao";
					ob.parentNode.innerHTML += ulstr;
					
				}else{
					ob.removeAttribute("href");
					ob.onclick=function(){};
					ob.className = "yuandian";
				}
		}
    }
	
	function innerresult(originalRequest){//这个函数先执行

		var city = originalRequest.responseXML.getElementsByTagName("tree"); 
		str="";
		for(var i=0;i<city.length;i++){
			var e = city[i];
			var id=e.getElementsByTagName("id")[0].firstChild.data;
			var areaname=e.getElementsByTagName("areaname")[0].firstChild.data;
			var acode= e.getElementsByTagName("acode")[0].firstChild.data;
			if(acode.length==1){
				str =str+"<li><span  onclick=\"innerChild('"+acode+"',this)\" class=\"jiahao\">&nbsp;</span><a href=\"javascript:product('"+acode+"')\" onclick=\"selectTag('"+areaname+"',"+acode+")\">"+areaname+"&nbsp;</a></li>";
			}	
			else
			{
			   str=str+acode+"|"+areaname+";";
			}
		}
		if(id1=="-1"){
			document.getElementById("root").innerHTML=str;
		}	
	}
	
	function product(productstructid){
		document.all.product.src="../purchase/listProductAction.do?OtherKey="+productstructid;
	}
	
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}" onLoad="getResult('-1')">
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="37" colspan="2"><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 产品资料</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="20%" valign="top">
	
			<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" style="border-right:4px solid #999">
		  <tr>
			<td id="tree" valign="top" height="60%" >
			<div id="treelist" style="width:100%; height:320px">
			<ul id="root" style="margin-left:10px;">
			<li>读取数据当中....</li>
			</ul>
			</div>
			</td>
		  </tr>
		  <tr>
			<td valign="bottom">&nbsp;</td>
		  </tr>
		</table>

    </td>
    <td width="80%"><IFRAME id="product" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="product" src="../finance/listProductAction.do" frameBorder=0 
      scrolling=auto></IFRAME></td>
  </tr>
</table>
</body>
</html>
