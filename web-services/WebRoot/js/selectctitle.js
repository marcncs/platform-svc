var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=titleLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 200; height:128;  display:none"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#FFFFFF;width:100%;height:128;';
strFrame+='border-left:1 solid #000000;border-bottom:1 solid #000000;border-top:1 solid #000000;border-right:1 solid #000000;';
strFrame+='SCROLLBAR-FACE-COLOR: #4682B4; ';
strFrame+='SCROLLBAR-HIGHLIGHT-COLOR: #4682B4;'; 
strFrame+='SCROLLBAR-SHADOW-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-ARROW-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-TRACK-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-DARKSHADOW-COLOR: #4682B4;}';
strFrame+='.seldiv div{MARGIN-TOP: 4px;MARGIN-LEFT: 2px;';
strFrame+='zmm: expression(onmouseover = function(){ style.background="#000066"; style.color="white"; },onmouseout = function(){ style.background="white"; style.color="black"; });}';
strFrame+='.seldiv A {FONT-SIZE: 12px;text-decoration: none;color:#000000}';
strFrame+='</style>';

strFrame+='<div id="titlediv" class="seldiv" onselectstart="return false">';

strFrame+='</div>';
window.frames.titleLayer.document.writeln(strFrame);
window.frames.titleLayer.document.close();  //解决ie进度条不结束的问题

//==================================================== WEB 页面显示部分 ======================================================
//任意点击时关闭该控件 //ie6的情况可以由下面的切换焦点处理代替
//document.onclick=doc_onclicks;
//document.body["onclick"] = user_onclicks;
document.attachEvent("onclick",duw_onclicks);
function duw_onclicks(){ 
  with(window.event){if (srcElement.getAttribute("Author")==null && srcElement != outtitleObject && srcElement != outButton){
    closetitleLayer();}
  }
}

//按Esc键关闭，切换焦点关闭
document.attachEvent("onkeyup",duw_onkeyup);
function duw_onkeyup(){
  if (window.event.keyCode==27){
  	if(outtitleObject)outtitleObject.blur();
  	closetitleLayer();
 	}else if(document.activeElement)
	  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outtitleObject && document.activeElement != outButton)
	  {
	   closetitleLayer();
	  }
} 
//这个层的关闭
function closetitleLayer(){
   document.all.titleLayer.style.display="none";
   
}

var outtitleObject;
var outButton;  //点击的按钮
var billtype;
function selectCTitle(v_tt,v_oid,v_type) //主调函数
{
 var dads  = document.all.titleLayer.style;
 var th = v_tt;
 var ttop  = v_tt.offsetTop;     //TT控件的定位点高
 var thei  = v_tt.clientHeight;  //TT控件本身的高
 var tleft = v_tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = v_tt.type;          //TT控件的类型
 var twidth= v_tt.offsetWidth ;  //TT控件的宽
 while (v_tt = v_tt.offsetParent){ttop+=v_tt.offsetTop; tleft+=v_tt.offsetLeft;}
 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+4;
 dads.left = tleft; 
 outtitleObject = th;
 outButton = null; //设定外部点击的按钮
 //-------------------------------
 dads.pixelWidth=twidth;
 window.frames.titleLayer.document.getElementById("titlediv").style.pixelWidth=twidth;
 window.frames.titleLayer.document.getElementById("titlediv").innerHTML='';
 if ( v_type =='1' ){
 	getProductUnit(v_oid);
 }else {
	//按机构获取用户
	getCidTitle(v_oid); 
 }
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}

function changetitle(unitname){
	outtitleObject.value=unitname;
	closetitleLayer();
}

function getCidTitle(objpid){
	var url = '../sales/ajaxGetCTitleAction.do?cid='+objpid;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showtitle}
				);	
}

function showtitle(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.ctlist;
	if ( lk == undefined ){
	}else{
		var titlediv = window.frames.titleLayer.document.getElementById("titlediv");
		var sst="";	
		for(var j=0; j<lk.length; j++){
			sst+='<a href="#" onclick="parent.changetitle(\''+lk[j].ctitle+'\')"><div align=left>'+lk[j].ctitle+'</div></a>'; 
		}
		titlediv.innerHTML=sst ;
	}
}

function getProductUnit(objpid){
	var url = '../sales/ajaxGetCAddrAction.do?cid='+objpid;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showunit}
				);	
}

function showunit(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.calist;
	if ( lk == undefined ){
	}else{		
		var unitdiv = window.frames.titleLayer.document.getElementById("titlediv");		
		var sst="";	
		for(var j=0; j<lk.length; j++){
			sst+='<a href="#" onclick="parent.changetitle(\''+lk[j].caddr+'\')"><div align=left>'+lk[j].caddr+'</div></a>'; 
		}
		unitdiv.innerHTML=sst ;
	}
}

