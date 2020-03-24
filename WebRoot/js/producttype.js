var strFrame;  //存放日历层的HTML代码
document.writeln('<iframe id=producttypeLayer scrolling="no" Author=jelli frameborder=0 style="position: absolute; width: 180; height:300;  display:none ;z-index:999;"></iframe>');
strFrame='<style>';
strFrame+='.seldiv{position:absolute;z-index:1000;overflow-x:auto;overflow-y:auto;';
strFrame+='left:0; top:0;background-color:#FFFFFF;width:180;height:300;font-size:12px;';
strFrame+='border-left:1 solid #C0C0C0;border-bottom:1 solid #C0C0C0;border-top:1 solid #C0C0C0;border-right:1 solid #C0C0C0;';
strFrame+='SCROLLBAR-FACE-COLOR: #4682B4; ';
strFrame+='SCROLLBAR-HIGHLIGHT-COLOR: #4682B4;'; 
strFrame+='SCROLLBAR-SHADOW-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-3DLIGHT-COLOR: #FFFFFF;'; 
strFrame+='SCROLLBAR-ARROW-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-TRACK-COLOR: #ffffff;'; 
strFrame+='SCROLLBAR-DARKSHADOW-COLOR: #4682B4;}';
strFrame+='#treelist ul{margin-left:20px;display:block;}';
strFrame+='#treelist ul li{margin-top:2px; margin-bottom:2px;list-style-type:none;display:block;}';
strFrame+='#treelist ul a {text-decoration: none; color:#000000;}';
strFrame+='#treelist ul span{background-image: url(../images/tree.gif);background-repeat: no-repeat;padding-left:15px;cursor:hand;height:15px;}';
strFrame+='.yuandian{background-position: 0px 2px;}';
strFrame+='.jiahao{background-position: 0px -52px;}';
strFrame+='.jianhao{background-position: 0px -25px;}';
strFrame+='</style>';

strFrame+='<div id="treelist" class="seldiv" >';
strFrame+='<ul id="root" style="margin-left:10px;">';
strFrame+='<li>loading...</li>';
strFrame+='</ul></div>';
window.frames.producttypeLayer.document.writeln(strFrame);
window.frames.producttypeLayer.document.close();  //解决ie进度条不结束的问题

//document.attachEvent("onclick",duw_onclicks);
function duw_onclicks(){ 
  with(window.event){if (srcElement.getAttribute("Author")==null && srcElement != outduwObject && srcElement != outButton){
    closeproducttypeLayer();}
  }
}

//按Esc键关闭，切换焦点关闭
//document.attachEvent("onkeyup",duw_onkeyup);
function duw_onkeyup(){
  if (window.event.keyCode==27){
  	if(outduwObject)outduwObject.blur();
  	closeproducttypeLayer();
 	}else if(document.activeElement)
	  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outduwObject && document.activeElement != outButton)
	  {
	   closeproducttypeLayer();
	  }
} 
//这个层的关闭
function closeproducttypeLayer(){
   document.all.producttypeLayer.style.display="none";
   
}

var outduwObject;
var outButton;  //点击的按钮
var hiddenobj;
var billtype;
function selectptype(v_tt,v_hidden) //主调函数
{
 var dads  = document.all.producttypeLayer.style;
 var th = v_tt;
 var ttop  = v_tt.offsetTop;     //TT控件的定位点高
 var thei  = v_tt.clientHeight;  //TT控件本身的高
 var tleft = v_tt.offsetLeft;    //TT控件的定位点宽
 var ttyp  = v_tt.type;          //TT控件的类型
 var twidth= v_tt.offsetWidth ;  //TT控件的宽
 while (v_tt = v_tt.offsetParent){ttop+=v_tt.offsetTop; tleft+=v_tt.offsetLeft;}
 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+4;
 dads.left = tleft; 
 outduwObject = th;
 outButton = null; //设定外部点击的按钮
 //-------------------------------
 hiddenobj=v_hidden;
 getResult(1);
 //--------------------------------

 dads.display = '';
 event.returnValue=false;
}

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
	
function getResult(thisid){
		str="";
        id1 = thisid;
        var url = '../purchase/ajaxListProductStructAction.do';
        var pars = 'parentid=' + id1;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse1,onSuccess:innerresult}
                    );
    }

function showResponse1(originalRequest){
	if(id1=="1"){
			window.frames.producttypeLayer.document.getElementById("root").innerHTML=str;
		}
		else{
			var ul, li, a; 
			str=str.substring(0,str.length-1);
			if(str!=""){
				var str1=str.split(";");
				var ulstr = "";
				for(var i=0;i<str1.length;i++){	
					ulstr += "<li><span onclick=\"parent.innerChild('"+str1[i].split("|")[0]+"',this)\" class=\"jiahao\">&nbsp;</span>";					
					ulstr += "<nobr><a href=\"javascript:parent.cvalue('"+str1[i].split("|")[1]+"',"+str1[i].split("|")[0]+")\">"+str1[i].split("|")[1]+"&nbsp;</a></nobr>";
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
 
function innerresult(originalRequest){ 
	 var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.pslist;
	if ( lk == undefined ){
	}else{
		str="";
		for(var i=0;i<lk.length;i++){
			var id=lk[i].id;
			var areaname=lk[i].areaname;
			var acode= lk[i].acode;
			if(acode.length==3){
				str =str+"<li><span  onclick=\"parent.innerChild('"+acode+"',this)\" class=\"jiahao\">&nbsp;</span><a href=\"javascript:parent.cvalue('"+areaname+"',"+acode+")\">"+areaname+"&nbsp;</a></li>";
			}	
			else
			{
			   str=str+acode+"|"+areaname+";";
			}
		}
		if(id1=="1"){
			str ="<li><a href=\"javascript:parent.cvalue('ALL',1)\">ALL&nbsp;</a></li>"+str;
			 window.frames.producttypeLayer.document.getElementById("root").innerHTML=str;
		}	
	}
}

function cvalue(v_name, v_id){
	document.getElementById(hiddenobj).value=v_id;
	outduwObject.value=v_name;
	closeproducttypeLayer();
}



