
var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=unitLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 100; height:140;  display:none"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#F7FAFC;width:100;height:140;';
strFrame+='border-left:1 solid #C0C0C0;border-bottom:1 solid #C0C0C0;border-top:1 solid #C0C0C0;border-right:1 solid #C0C0C0;';
strFrame+='SCROLLBAR-FACE-COLOR: #4682B4; ';
strFrame+='SCROLLBAR-HIGHLIGHT-COLOR: #4682B4;'; 
strFrame+='SCROLLBAR-SHADOW-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-ARROW-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-TRACK-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-DARKSHADOW-COLOR: #4682B4;}';
strFrame+='.seldiv div{MARGIN-TOP: 4px;}';
strFrame+='.seldiv A {FONT-SIZE: 14px;text-decoration: none;}';
strFrame+='</style>';

strFrame+='<div id="unitdiv" class="seldiv" onselectstart="return false">';

strFrame+='</div>';
window.frames.unitLayer.document.writeln(strFrame);
window.frames.unitLayer.document.close();  //解决ie进度条不结束的问题

//==================================================== WEB 页面显示部分 ======================================================
//任意点击时关闭该控件 //ie6的情况可以由下面的切换焦点处理代替
document.onclick=function (){ 
  with(window.event){ if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
}

//按Esc键关闭，切换焦点关闭
document.onkeyup=function (){
  if (window.event.keyCode==27){
  	if(outObject)outObject.blur();
  	closeLayer();
 	}else if(document.activeElement)
	  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
	  {
	   closeLayer();
	  }
}
//这个层的关闭
function closeLayer(){
   document.all.unitLayer.style.display="none";
}

var outObject;
var outButton;  //点击的按钮
var billtype;
function selectUnit(tt,obj,objtype) //主调函数
{
 //if (arguments.length >  2){alert("对不起!传入本控件的参数太多!");return;}
 //if (arguments.length == 0){alert("对不起!您没有传回本控件任何参数!");return;}
 var dads  = document.all.unitLayer.style;
 var th = tt;
 var ttop  = tt.offsetTop;     //TT控件的定位点高
 var thei  = tt.clientHeight;  //TT控件本身的高
 var tleft = tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = tt.type;          //TT控件的类型
 while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+4;
 dads.left = tleft;
 //outObject = (arguments.length == 1) ? th : obj;
 //outButton = (arguments.length == 1) ? null : th; //设定外部点击的按钮
 outObject = th;
 outButton = null; //设定外部点击的按钮
 billtype=objtype;
 //-------------------------------
 getProductUnit(obj);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}


function changeunit(unitid, unitname){
	var rows = getRowNo(outObject);	
	$('warehouseid'+rows).value=unitid;	
	outObject.value=unitname;
	closeLayer();
}

function getProductUnit(objpid){
	var url = '../sales/ajaxWarehouseAction.do?eqid='+objpid;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showunit}
				);	
}

function showunit(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.whlist;
	if ( lk == undefined ){
	}else{
		var unitdiv = window.frames.unitLayer.document.getElementById("unitdiv");
		var sst="";	
		for(var j=0; j<lk.length; j++){
			sst+='<div align=center><a href="javascript:parent.changeunit('+lk[j].id+',\''+lk[j].warehousename+'\')">'+lk[j].warehousename+'</a></div>'; 
		}
		unitdiv.innerHTML=sst ;
	}
}

//得到行对象 
function getRowObj(obj) { 
	while(obj.tagName.toLowerCase() != "tr"){ 
		obj = obj.parentNode; 
		if(obj.tagName.toLowerCase() == "table")return null; 
	} 
	return obj; 
} 

//根据得到的行对象得到所在的行数 
function getRowNo(obj){ 
	var trObj = getRowObj(obj); 
	var trArr = trObj.parentNode.children; 
	for(var trNo= 0; trNo < trArr.length; trNo++){ 
		if(trObj == trObj.parentNode.children[trNo]){ 
			return trNo+1; 
		} 
	} 
}  
