<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>发送消息</title>

<script src="../js/prototype.js"></script>
<script>
	var senduserid=0;
	var content="<li>正在读取数据中......</li>";
	var bb = setInterval("showMessage1();",5000);
	//----------------------------------------
	function showOnlieUser(){//显示在线用户
	   var url = 'testListOnLineUserAction.do';
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse1}
                    );
	}

   function showResponse1(originalRequest){
		var user = originalRequest.responseXML.getElementsByTagName("user"); 
		var str="";
		for(var i=0;i<user.length;i++){
				var a = user[i];
				var userid = a.getElementsByTagName("id")[0].firstChild.data;
				var username =a.getElementsByTagName("username")[0].firstChild.data ;
				var isonline =a.getElementsByTagName("isonline")[0].firstChild.data ;
				if(isonline==1)
					str = "<li ><a href=\"javascript:void(0)\" onclick=\"selectusername('"+username+"','"+userid+"')\" style='color:red'>"+username+"</a></li>"+str;
				else 
					str="<li><a href=\"javascript:void(0)\" onclick=\"selectusername('"+username+"','"+userid+"')\">"+username+"</a></li>"+str;
			}
			document.getElementById("onlineusers").innerHTML="<li><a href=\"javascript:void(0)\">所有用户</a></li>"+str;

			setTimout("showOnlieUser()",10000);
	}

	function selectusername(username,id)
	{
		//alert("id=="+id);
		senduserid=id;
		//alert("fff"+senduserid);
		document.getElementById("username").innerHTML=username;

	}
	//-----------------------------------------------------------

	function showMessage(){//第一次读消息
	   var url = 'testNoReadMesAction.do';
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse2}
                    );				
	}

	function showResponse2(originalRequest2){
		var messages = originalRequest2.responseXML.getElementsByTagName("messages"); 
		for(var i=0;i<messages.length;i++){
				var a = messages[i];
				var id = a.getElementsByTagName("id")[0].firstChild.data;
				var message =a.getElementsByTagName("message")[0].firstChild.data ;
				var sendusername =a.getElementsByTagName("sendusername")[0].firstChild.data ;
				var acceptusername =a.getElementsByTagName("acceptusername")[0].firstChild.data ;
				var sendtime =a.getElementsByTagName("sendtime")[0].firstChild.data ;
				
				content = "<li><span>"+sendusername+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+sendtime+")</span> &nbsp;&nbsp;&nbsp;&nbsp;"+message+"</li>"+content;
				
		    }
			document.getElementById("content").innerHTML=content;
			document.getElementById("content").scrollTop=document.getElementById("content").scrollHeight;
    }
//-------------------------------------------------------------------------------

	function showMessage1(){	//刷新消息
	//alert("==========");
	   var url = 'testReadMessageAction.do';
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse4}
                    );				
	}

	function showResponse4(originalRequest4){
		var messages = originalRequest4.responseXML.getElementsByTagName("messages"); 
		content="";
		for(var i=0;i<messages.length;i++){
				var a = messages[i];
				var id = a.getElementsByTagName("id")[0].firstChild.data;
				var message =a.getElementsByTagName("message")[0].firstChild.data ;
				var sendusername =a.getElementsByTagName("sendusername")[0].firstChild.data ;
				var acceptusername =a.getElementsByTagName("acceptusername")[0].firstChild.data ;
				var sendtime =a.getElementsByTagName("sendtime")[0].firstChild.data ;
				
				content = "<li><span>"+sendusername+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+sendtime+")</span> &nbsp;&nbsp;&nbsp;&nbsp;"+message+"</li>"+content;
				
		    }
			document.getElementById("content").innerHTML=content;
			document.getElementById("content").scrollTop=document.getElementById("content").scrollHeight;
    }
////////--------------------------------
	function sendMessage(){//发消息

		var messagecontent=$F("messagecontent");
		if (messagecontent=="")
		{
			alert("请输入发送消息内容!")
			return false;
		}  
		else if(document.getElementById("username").innerHTML.length==0){
			alert("请选择发送用户!");
			return false;
		}
		else if(senduserid==${users.userid}){
			alert("你不能给自己发送消息!");
			return false;
		}
//alert("---"+messagecontent);
	   var url = 'testSendMesAction.do';
	   
	  // alert(senduserid);
	   var username='${users.loginname}';
	   var dt=new Date();
       var pars = 'acceptdid='+senduserid+'&message='+messagecontent;
	   //alert("-=-=-"+pars);
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars, onComplete: ''}
                    );	
					
		document.getElementById("messagecontent").value="";
		content=content+"<li><span>"+username+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+DateDemo()+")</span> &nbsp;&nbsp;&nbsp;&nbsp;"+messagecontent+"</li>"
		document.getElementById("content").innerHTML=content;
		document.getElementById("content").scrollTop=document.getElementById("content").scrollHeight;
	}

	function DateDemo(){
	   var d, s = "";							// 声明变量。

	   d = new Date();							// 创建 Date 对象。

	   s += d.getFullYear()+"-";				   // 获取年份。

	   s += (d.getMonth() + 1) + "-";            // 获取月份。

	   s += d.getDate() + "-";                   // 获取日。

	   s +="    "+d.getHours()+":"                   
	   s +=d.getMinutes()+":" 
	   s +=d.getSeconds()+"" 
	   return(s);                                // 返回日期。

	}



</script>
<style type="text/css">
body{margin:0px;padding:0px}
#container {
	height: 430px;
	width: 510px;
	margin: 0px;
	background-color: #5db7ff;
	font-size: 12px;
	font-family: Arial, Helvetica, sans-serif;
}
#container #title {
	height: 28px;
	background-image: url(../images/topbg.gif);
	background-repeat: repeat-x;
	line-height: 28px;
}
#container #title img {
	margin: 6px;
}
#container #content {
	height: 240px;
	width: 312px;
	background-color: #FFFFFF;
	padding: 2px;
	margin-bottom: 0px;
	margin-left: 10px;
	border-top-width: 1px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-left-style: solid;
	border-top-color: #2467C2;
	border-right-color: #2467C2;
	border-left-color: #2467C2;
	margin-top: 3px;
	clear: right;
	overflow: auto;
}
#container #tips {
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 0px;
	margin-left: 10px;
	background-image: url(../images/tipsbg.gif);
	background-repeat: repeat-x;
	height: 22px;
	line-height: 22px;
	padding-left: 5px;
	width: 311px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #2467C2;
	border-left-color: #2467C2;
	color: #17427B;
}
#container #mymsg {
	display: block;
	padding: 0px;
	width: 314px;
	height: 80px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 0px;
	margin-left: 10px;
}
#container #mymsg textarea {
	padding: 4px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	width: 308px;
	height: 75px;
	margin-top: -1px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left:0px !important;
	margin-left: -10px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #2467C2;
	border-bottom-color: #2467C2;
	border-left-color: #2467C2;
	border-top-style: none;
	overflow: auto;
}
#container #content li {
	list-style-type: none;
	margin: 5px;
	font-size: 12px;
}
#container #content li span {
	display:block;
	color:#009900;
	margin-top:10px;
}
#container #send {
	margin-left: 10px;
	width: 314px;
	text-align: right;
	margin-top: 14px !important;
	margin-top: 5px;
}
.input1 {
	background-image: url(../images/sendmsg.gif);
	background-repeat: no-repeat;
	background-position: left top;
	height: 21px;
	width: 53px;
	font-size: 12px;
	margin-right: 2px;
	margin-left: 5px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	line-height: 18px;
	text-align: center;
}
.input2 {
	background-image: url(../images/sendmsg.gif);
	background-repeat: no-repeat;
	background-position: left bottom;
	height: 21px;
	width: 53px;
	font-size: 12px;
	margin-left: 2px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	line-height: 18px;
	text-align: center;
}
#container #userlist {
	position: absolute;
	margin-left: 337px !important;
	margin-left: 327px;
	margin-top:3px !important;
	margin-top:0px;	
	width: 160px;
	border: 1px solid #2467C2;
	height: 381px !important;
	height: 378px;
	background-color: #FFFFFF;
}
#container #userlist h1 {
	font-size: 12px;
	font-weight: normal;
	margin: 0px;
	background-image: url(../images/useronlinebg.gif);
	background-repeat: repeat-x;
	background-position: left top;
	height: 19px;
	line-height: 19px;
	padding-top: 0px;
	padding-right: 0px;
	padding-bottom: 0px;
	padding-left: 10px;
}
#container #userlist #onlineusers li {
	list-style-type: none;
	padding: 0px;
	margin: 0px;
}
#container #userlist #onlineusers {
	margin: 0px;
	padding-top: 5px;
	padding-right: 0px;
	padding-bottom: 5px;
	padding-left: 0px;
	height: 348px;
	overflow: auto;
}
#container #userlist #onlineusers a {
	text-decoration: none;
	color: #333333;
	background-image: url(../images/miniIco1.gif);
	background-repeat: no-repeat;
	background-position: 6px 2px;
	padding-left: 20px;
	margin: 5px;
	padding-top: 0px;
	padding-right: 0px;
	padding-bottom: 0px;
	width: 128px;
}
#container #userlist #onlineusers a:hover {
	background-image: url(../images/miniIco2.gif);
	background-repeat: no-repeat;
	background-position: 8px 2px;
}
#container #userlist img {
	margin-bottom: -3px;
	margin-top: 2px;
}

</style>
</head>

<body onload="showOnlieUser();showMessage();">
<div id="container">
 <div id="title"><img src="../images/title.gif" /></div>
 <div id="userlist">
 <h1><img src="../images/onlineusers2.gif" alt="在线用户" width="16" height="16" />&nbsp;&nbsp;在线用户</h1>
 <ul id="onlineusers">
 	
 </ul>
 </div>
  <ul id="content">
	  	  
  </ul>
  <div id="tips">我对 <span id="username"></span> 说</div><div id="mymsg"><textarea name="messagecontent"></textarea></div>
  <div id="send"><input type="button" class="input1" value="关闭(C)" onclick="window.close()">
  <input type="button" class="input2" value="发送(S)" onclick="sendMessage();">
  </div>
</div>
</body>
</html>
