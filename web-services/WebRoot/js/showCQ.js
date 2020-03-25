var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=showsqLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 90; height:90;  display:none ;z-index:999;"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#ffffcc;width:90;height:90;';
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
strFrame+='<tr class="title-back"><td height="30"> 条码总数</td></tr>';
strFrame+='<tr><td><div id="codeQty"></div></td></tr>';
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
function ShowCQ(v_tt,v_productid, v_billno, v_bsort) //主调函数
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
 getCodeQuantity(v_productid, v_billno, v_bsort);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}


function HiddenCQ(){
	closeshowsqLayer();
}

function getCodeQuantity(v_productid, v_billno, v_bsort){
	var url = '../warehouse/getCodeQuantityAjax.do?productId='+v_productid+'&billNo='+v_billno+'&bSort='+v_bsort;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprice}
				);	
}
function showprice(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var hprice = window.frames.showsqLayer.document.getElementById("codeQty");
	var sst='<table width="100%" border="0" cellpadding="1" cellspacing="0" class="hprice">';	
	sst+='<tr><td align="center">合计：'+data.codeQty+' 个</td></tr>';
	sst+='</table>';
	hprice.innerHTML=sst ;	
}

function ShowTCQ(v_tt,v_productid, v_billno) //主调函数
{
 var dads  = document.all.showsqLayer.style;
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
 getTCodeQuantity(v_productid, v_billno);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}

function getTCodeQuantity(v_productid, v_billno){
	var url = '../warehouse/getTakeTicketIdCodeQuantityAjax.do?productId='+v_productid+'&billNo='+v_billno;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprice}
				);	
}



function ShowBCQ(v_tt,v_productid, v_billno,v_batch) //主调函数
{
 var dads  = document.all.showsqLayer.style;
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
 getBCodeQuantity(v_productid, v_billno,v_batch);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}

function getBCodeQuantity(v_productid, v_billno,v_batch){
	var url = '../warehouse/getBarcodeQuantityAjax.do?productId='+v_productid+'&billNo='+v_billno+'&batchNo='+v_batch;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprice}
				);	
}

