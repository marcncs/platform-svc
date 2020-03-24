var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=showsqLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 250; height:145;  display:none ;z-index:999;"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#ffffcc;width:250;height:145;';
strFrame+='border-left:1 solid #96965e;border-bottom:1 solid #96965e;border-top:1 solid #96965e;border-right:1 solid #96965e;';
strFrame+='SCROLLBAR-FACE-COLOR: #4682B4; ';
strFrame+='SCROLLBAR-HIGHLIGHT-COLOR: #4682B4;'; 
strFrame+='SCROLLBAR-SHADOW-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-ARROW-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-TRACK-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-DARKSHADOW-COLOR: #4682B4;}';
strFrame+='.seldiv div{MARGIN-TOP: 0px;MARGIN-LEFT: 2px;}';
strFrame+='.seldiv A {FONT-SIZE: 12px;text-decoration: none;color:#000000}';
strFrame+='.title-back{color: #263C5D;HEIGHT: 24px;background-image: url(../images/CN/top-back3.gif);font-weight:bold;font-size:12px;}';
strFrame+='.hprice{font-size:10px;}';
strFrame+='</style>';

strFrame+='<div id="sqdiv" class="seldiv">';
strFrame+='<table width="100%" height="80" border="0" cellpadding="0" cellspacing="0">';
strFrame+='<tr class="title-back"><td width="45%" height="30"> 仓库</td><td width="30%">批号</td><td width="35%">可用数量</td></tr>';
strFrame+='<tr><td colspan="3"><div id="stock"></div></td></tr>';
strFrame+='</table>';
strFrame+='</div>';
window.frames.showsqLayer.document.writeln(strFrame);
window.frames.showsqLayer.document.close();  //解决ie进度条不结束的问题


document.attachEvent("onclick",sq_onclicks);
function sq_onclicks(){ 
  with(window.event){if (srcElement.getAttribute("Author")==null && srcElement != outduwObject && srcElement != outButton){
    closeshowsqLayer();}
  }
}

//按Esc键关闭，切换焦点关闭
document.attachEvent("onkeyup",sq_onkeyup);
function sq_onkeyup(){
  if (window.event.keyCode==27){
  	if(outduwObject)outduwObject.blur();
  	closeshowsqLayer();
 	}else if(document.activeElement)
	  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outduwObject && document.activeElement != outButton)
	  {
	   closeshowsqLayer();
	  }
} 
//这个层的关闭
function closeshowsqLayer(){
   document.all.showsqLayer.style.display="none";
   
}

var outduwObject;
var outButton;  //点击的按钮
var billtype;
function ShowSQ(v_tt,v_productid) //主调函数
{
 //var dads  = document.all.showsqLayer.style;
 var dads  = document.getElementById("showsqLayer").style;
 var th = v_tt;
 var ttop  = v_tt.offsetTop;     //TT控件的定位点高
 var thei  = v_tt.clientHeight;  //TT控件本身的高
 var tleft = v_tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = v_tt.type;          //TT控件的类型
 var twidth= v_tt.offsetWidth ;  //TT控件的宽
 while (v_tt = v_tt.offsetParent){ttop+=v_tt.offsetTop; tleft+=v_tt.offsetLeft;}
 //dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+20;
  var cur_top=0;
 if ( document.body.scrollHeight<event.clientY+100){
	cur_top=event.clientY-145;
 }else{
	 cur_top=document.body.scrollTop + event.clientY+10;
 }
 dads.top=cur_top;
 dads.left = tleft+20; 
 outduwObject = th;
 outButton = null; //设定外部点击的按钮
 //-------------------------------
 getStockByOid(v_productid);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}


function HiddenSQ(){
	closeshowsqLayer();
}

function getStockByOid(v_productid){
	var url = '../warehouse/getStockQuantityAjax.do?productid='+v_productid;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprice}
				);	
}
function showprice(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.pslist;
	if ( lk == undefined ){
	}else{
		var hprice = window.frames.showsqLayer.document.getElementById("stock");
		var sst='<table width="100%" border="0" cellpadding="1" cellspacing="0" class="hprice">';	
		for(var j=0; j<lk.length; j++){
			sst+='<tr><td width="45%">'+lk[j].wname+'</td><td width="30%" align="left">'+lk[j].batch+' </td><td width="35%" align="center">'+lk[j].stockpile+' </td></tr>'; 
		}
		sst+='<tr><td width="45%"></td><td width="30%" align="right">合计：</td><td width="35%" align="center">'+data.total+'</td></tr>';
		sst+='</table>';
		hprice.innerHTML=sst ;
	}
}




