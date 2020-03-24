var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=showhpLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 200; height:128;  display:none ;z-index:999;"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#ffffcc;width:200;height:128;';
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
strFrame+='.hprice{font-size:12px;}';
strFrame+='</style>';

strFrame+='<div id="hpdiv" class="seldiv">';
strFrame+='<table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" >';
strFrame+='<tr class="title-back"><td width="50%" >历史成交价</td><td width="50%">成交价日期</td></tr>';
strFrame+='<tr><td colspan="2"><div id="hprice"></div></td></tr>';
strFrame+='</table>';
strFrame+='</div>';
window.frames.showhpLayer.document.writeln(strFrame);
window.frames.showhpLayer.document.close();  //解决ie进度条不结束的问题


document.attachEvent("onclick",duw_onclicks);
function duw_onclicks(){ 
  with(window.event){if (srcElement.getAttribute("Author")==null && srcElement != outduwObject && srcElement != outButton){
    closeshowhpLayer();}
  }
}

//按Esc键关闭，切换焦点关闭
document.attachEvent("onkeyup",duw_onkeyup);
function duw_onkeyup(){
  if (window.event.keyCode==27){
  	if(outduwObject)outduwObject.blur();
  	closeshowhpLayer();
 	}else if(document.activeElement)
	  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outduwObject && document.activeElement != outButton)
	  {
	   closeshowhpLayer();
	  }
} 
//这个层的关闭
function closeshowhpLayer(){
   document.all.showhpLayer.style.display="none";
   
}

var outduwObject;
var outButton;  //点击的按钮
var billtype;
function showhp(v_tt,v_pid,v_productid) //主调函数
{
 //var dads  = document.all.showhpLayer.style;
 var dads  = document.getElementById("showsqLayer").style;
 var th = v_tt;
 var ttop  = v_tt.offsetTop;     //TT控件的定位点高
 var thei  = v_tt.clientHeight;  //TT控件本身的高
 var tleft = v_tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = v_tt.type;          //TT控件的类型
 var twidth= v_tt.offsetWidth ;  //TT控件的宽
 while (v_tt = v_tt.offsetParent){ttop+=v_tt.offsetTop; tleft+=v_tt.offsetLeft;}
 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+20;
 dads.left = tleft+20; 
 outduwObject = th;
 outButton = null; //设定外部点击的按钮
 //-------------------------------
 getDeptByOid(v_pid, v_productid);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}


function hiddenhp(){
	closeshowhpLayer();
}

function getDeptByOid(v_pid,v_productid){
	var url = '../purchase/getPurchaseHistoryChenAjax.do?pid='+v_pid+"&productid="+v_productid;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showprice}
				);	
}
function showprice(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.pricelist;
	if ( lk == undefined ){
	}else{
		var hprice = window.frames.showhpLayer.document.getElementById("hprice");
		var sst='<table  width="100%"  border="0" cellpadding="1" cellspacing="0" class="hprice">';	
		for(var j=0; j<lk.length; j++){
			sst+='<tr><td width="50%">'+lk[j].price+'</td><td width="50%">'+lk[j].date+'</td></tr>'; 
		}
		sst+='</table>';
		hprice.innerHTML=sst ;
	}
}




