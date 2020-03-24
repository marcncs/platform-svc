
var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=unitLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width:50; height:80;  display:none"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#FFFFFF;width:100%;height:80;';
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

strFrame+='<div id="unitdiv" class="seldiv" onselectstart="return false">';

strFrame+='</div>';
window.frames.unitLayer.document.writeln(strFrame);
window.frames.unitLayer.document.close();  //解决ie进度条不结束的问题

//==================================================== WEB 页面显示部分 ======================================================
//任意点击时关闭该控件 //ie6的情况可以由下面的切换焦点处理代替
//document.attachEvent("onclick",unit_onclicks);
function unit_onclicks(){ 
  with(window.event){ if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
}

//按Esc键关闭，切换焦点关闭
//document.attachEvent("onkeyup",unit_onkeyup);
function unit_onkeyup(){
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
function selectUnit(tt,obj,v_cid,v_oid,objtype) //主调函数
{
 var dads  = document.all.unitLayer.style;
 var th = tt;
 var ttop  = tt.offsetTop;     //TT控件的定位点高
 var thei  = tt.clientHeight;  //TT控件本身的高
 var tleft = tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = tt.type;          //TT控件的类型
 var twidth= tt.offsetWidth ;  //TT控件的宽
while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
 //dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+4;
 dads.left = tleft;
  var cur_top=0;
 if ( document.body.scrollHeight<event.clientY+100){
	cur_top=event.clientY-80;
 }else{
	 cur_top=document.body.scrollTop + event.clientY+10;
 }
 dads.top=cur_top;

 outObject = th;
 outButton = null; //设定外部点击的按钮
 billtype=objtype;
 //-------------------------------
 dads.pixelWidth=twidth;
 window.frames.unitLayer.document.getElementById("unitdiv").style.pixelWidth=twidth;
 window.frames.unitLayer.document.getElementById("unitdiv").innerHTML='';
  getProductUnit(obj,v_cid,v_oid,objtype);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;

}

function formatCurrency(num) {
	if (num==undefined){
		num=0;
	}
	 num = num.toString().replace(/\$|\,/g,'');
	   if(isNaN(num))
	   num = "0";
	   sign = (num == (num = Math.abs(num)));
	   num = Math.floor(num*100+0.50000000001);
	   cents = num%100;
	   num = Math.floor(num/100).toString(); 
	   if(cents<10)
	   cents = "0" + cents;
	   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	   num.substring(num.length-(4*i+3));
	   return (((sign)?'':'-') + num + '.' + cents);
	}

function changeunit(unitid, unitname,xquantity,objpeddleprice,objprice){
	var price = formatCurrency(objprice);
	var peddleprice = formatCurrency(objpeddleprice);
	var rows = getRowNo(outObject);
	var pobj=eval('(' +$('pobj'+rows).value+ ')'); 	
	if ( billtype == '1' ){	
		if ( $('stockpile'+rows) != null ){
			var stockpile =$('stockpile'+rows).value;
			$('spanstockpile'+rows).innerText =(stockpile/xquantity).toFixed(2);		
		}
		//零售价
		$('spanpeddleprice'+rows).innerText =peddleprice;
		//会员价
		$('spanprice'+rows).innerText =price;
		pobj.peddleprice=peddleprice;
		pobj.price=price;	
		pobj.taxprice=price;
	}else if ( billtype == '2' ){
		var stockpile =$('stockpile'+rows).value;
		$('spanstockpile'+rows).innerText =(stockpile/xquantity).toFixed(2);		
	}else if ( billtype == '3' ){			
		if ( $('stockpile'+rows) != null ){
			var stockpile =$('stockpile'+rows).value;
			$('spanstockpile'+rows).innerText =(stockpile/xquantity).toFixed(2) ;		
		}
		//价格
		//$('spanprice'+rows).innerText =price;
		//pobj.unitprice=price;	
	}else if (billtype == '4'){
		if ( $('stockpile'+rows) != null ){
			var stockpile =$('stockpile'+rows).value;
			$('spanstockpile'+rows).innerText =(stockpile/xquantity).toFixed(2);		
		}
		//订购价格
		$('spanpeddleprice'+rows).innerText =peddleprice;
		//销售价格
		$('spanprice'+rows).innerText =price;
		pobj.peddleprice=peddleprice;
		pobj.price=price;	
		pobj.taxprice=price;
	}else if ( billtype == '5' ){
		$('spanprice'+rows).innerText =price;
		pobj.unitprice=price;		
	}	
	pobj.unitid=unitid;
	pobj.unitidname=unitname;
	var t = obj2str(pobj);
	$('pobj'+rows).value=obj2str(pobj);
	//单位
	outObject.value=unitname;
	closeLayer();
	//改变单位
	changeSelectValue(outObject);
}

function getProductUnit(v_pid,v_cid,v_oid,v_billtype){
	var url = '../purchase/ajaxFUnitAction.do?pid='+v_pid+'&cid='+v_cid+'&oid='+v_oid+'&billtype='+v_billtype;
	var pars = '';
	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showunit}
				);	
}

function showunit(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.fulist;
	if ( lk == undefined ){
	}else{
		var unitdiv = window.frames.unitLayer.document.getElementById("unitdiv");
		var sst="";	
		for(var j=0; j<lk.length; j++){
			sst+='<a href="#" onclick="parent.changeunit('+lk[j].unitid+',\''+lk[j].unitname+'\','+lk[j].quantity+','+lk[j].peddleprice+','+lk[j].price+')"><div align=center>'+lk[j].unitname+'</div></a>'; 
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

function obj2str(o){
    var r = [];
    if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
    if(typeof o == "object"){
        if(!o.sort){
            for(var i in o)
                r.push(i+":"+obj2str(o[i]));
            if(!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)){
                r.push("toString:"+o.toString.toString());
            }
            r="{"+r.join()+"}";
        }else{
            for(var i =0;i<o.length;i++)
                r.push(obj2str(o[i]));
            r="["+r.join()+"]";
        }
        return r;
    }
    return o.toString();
}

