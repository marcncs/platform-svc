   /************** �϶� *********************/
 function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
 function MM_dragLayer(objName,x,hL,hT,hW,hH,toFront,dropBack,cU,cD,cL,cR,targL,targT,tol,dropJS,et,dragJS) { //v4.01
  //Copyright 1998 Macromedia, Inc. All rights reserved.
  var i,j,aLayer,retVal,curDrag=null,curLeft,curTop,IE=document.all,NS4=document.layers;
  var NS6=(!IE&&document.getElementById), NS=(NS4||NS6); if (!IE && !NS) return false;
  retVal = true; if(IE && event) event.returnValue = true;
  if (MM_dragLayer.arguments.length > 1) {
    curDrag = MM_findObj(objName); if (!curDrag) return false;
    if (!document.allLayers) { document.allLayers = new Array();
      with (document) if (NS4) { for (i=0; i<layers.length; i++) allLayers[i]=layers[i];
        for (i=0; i<allLayers.length; i++) if (allLayers[i].document && allLayers[i].document.layers)
          with (allLayers[i].document) for (j=0; j<layers.length; j++) allLayers[allLayers.length]=layers[j];
      } else {
        if (NS6) { var spns = getElementsByTagName("span"); var all = getElementsByTagName("div"); 
          for (i=0;i<spns.length;i++) if (spns[i].style&&spns[i].style.position) allLayers[allLayers.length]=spns[i];}
        for (i=0;i<all.length;i++) if (all[i].style&&all[i].style.position) allLayers[allLayers.length]=all[i]; 
    } }
    curDrag.MM_dragOk=true; curDrag.MM_targL=targL; curDrag.MM_targT=targT;
    curDrag.MM_tol=Math.pow(tol,2); curDrag.MM_hLeft=hL; curDrag.MM_hTop=hT;
    curDrag.MM_hWidth=hW; curDrag.MM_hHeight=hH; curDrag.MM_toFront=toFront;
    curDrag.MM_dropBack=dropBack; curDrag.MM_dropJS=dropJS;
    curDrag.MM_everyTime=et; curDrag.MM_dragJS=dragJS;
    curDrag.MM_oldZ = (NS4)?curDrag.zIndex:curDrag.style.zIndex;
    curLeft= (NS4)?curDrag.left:(NS6)?parseInt(curDrag.style.left):curDrag.style.pixelLeft; 
    if (String(curLeft)=="NaN") curLeft=0; curDrag.MM_startL = curLeft;
    curTop = (NS4)?curDrag.top:(NS6)?parseInt(curDrag.style.top):curDrag.style.pixelTop; 
    if (String(curTop)=="NaN") curTop=0; curDrag.MM_startT = curTop;
    curDrag.MM_bL=(cL<0)?null:curLeft-cL; curDrag.MM_bT=(cU<0)?null:curTop-cU;
    curDrag.MM_bR=(cR<0)?null:curLeft+cR; curDrag.MM_bB=(cD<0)?null:curTop+cD;
    curDrag.MM_LEFTRIGHT=0; curDrag.MM_UPDOWN=0; curDrag.MM_SNAPPED=false; //use in your JS!
    document.onmousedown = MM_dragLayer; document.onmouseup = MM_dragLayer;
    if (NS) document.captureEvents(Event.MOUSEDOWN|Event.MOUSEUP);
  } else {
    var theEvent = ((NS)?objName.type:event.type);
    if (theEvent == 'mousedown') {
      var mouseX = (NS)?objName.pageX : event.clientX + document.body.scrollLeft;
      var mouseY = (NS)?objName.pageY : event.clientY + document.body.scrollTop;
      var maxDragZ=null; document.MM_maxZ = 0;
      for (i=0; i<document.allLayers.length; i++) { aLayer = document.allLayers[i];
        var aLayerZ = (NS4)?aLayer.zIndex:parseInt(aLayer.style.zIndex);
        if (aLayerZ > document.MM_maxZ) document.MM_maxZ = aLayerZ;
        var isVisible = (((NS4)?aLayer.visibility:aLayer.style.visibility).indexOf('hid') == -1);
        if (aLayer.MM_dragOk != null && isVisible) with (aLayer) {
          var parentL=0; var parentT=0;
          if (NS6) { parentLayer = aLayer.parentNode;
            while (parentLayer != null && parentLayer.style.position) {             
              parentL += parseInt(parentLayer.offsetLeft); parentT += parseInt(parentLayer.offsetTop);
              parentLayer = parentLayer.parentNode;
          } } else if (IE) { parentLayer = aLayer.parentElement;       
            while (parentLayer != null && parentLayer.style.position) {
              parentL += parentLayer.offsetLeft; parentT += parentLayer.offsetTop;
              parentLayer = parentLayer.parentElement; } }
          var tmpX=mouseX-(((NS4)?pageX:((NS6)?parseInt(style.left):style.pixelLeft)+parentL)+MM_hLeft);
          var tmpY=mouseY-(((NS4)?pageY:((NS6)?parseInt(style.top):style.pixelTop) +parentT)+MM_hTop);
          if (String(tmpX)=="NaN") tmpX=0; if (String(tmpY)=="NaN") tmpY=0;
          var tmpW = MM_hWidth;  if (tmpW <= 0) tmpW += ((NS4)?clip.width :offsetWidth);
          var tmpH = MM_hHeight; if (tmpH <= 0) tmpH += ((NS4)?clip.height:offsetHeight);
          if ((0 <= tmpX && tmpX < tmpW && 0 <= tmpY && tmpY < tmpH) && (maxDragZ == null
              || maxDragZ <= aLayerZ)) { curDrag = aLayer; maxDragZ = aLayerZ; } } }
      if (curDrag) {
        document.onmousemove = MM_dragLayer; if (NS4) document.captureEvents(Event.MOUSEMOVE);
        curLeft = (NS4)?curDrag.left:(NS6)?parseInt(curDrag.style.left):curDrag.style.pixelLeft;
        curTop = (NS4)?curDrag.top:(NS6)?parseInt(curDrag.style.top):curDrag.style.pixelTop;
        if (String(curLeft)=="NaN") curLeft=0; if (String(curTop)=="NaN") curTop=0;
        MM_oldX = mouseX - curLeft; MM_oldY = mouseY - curTop;
        document.MM_curDrag = curDrag;  curDrag.MM_SNAPPED=false;
        if(curDrag.MM_toFront) {
          eval('curDrag.'+((NS4)?'':'style.')+'zIndex=document.MM_maxZ+1');
          if (!curDrag.MM_dropBack) document.MM_maxZ++; }
        retVal = false; if(!NS4&&!NS6) event.returnValue = false;
    } } else if (theEvent == 'mousemove') {
      if (document.MM_curDrag) with (document.MM_curDrag) {
        var mouseX = (NS)?objName.pageX : event.clientX + document.body.scrollLeft;
        var mouseY = (NS)?objName.pageY : event.clientY + document.body.scrollTop;
        newLeft = mouseX-MM_oldX; newTop  = mouseY-MM_oldY;
        if (MM_bL!=null) newLeft = Math.max(newLeft,MM_bL);
        if (MM_bR!=null) newLeft = Math.min(newLeft,MM_bR);
        if (MM_bT!=null) newTop  = Math.max(newTop ,MM_bT);
        if (MM_bB!=null) newTop  = Math.min(newTop ,MM_bB);
        MM_LEFTRIGHT = newLeft-MM_startL; MM_UPDOWN = newTop-MM_startT;
        if (NS4) {left = newLeft; top = newTop;}
        else if (NS6){style.left = newLeft; style.top = newTop;}
        else {style.pixelLeft = newLeft; style.pixelTop = newTop;}
        if (MM_dragJS) eval(MM_dragJS);
        retVal = false; if(!NS) event.returnValue = false;
    } } else if (theEvent == 'mouseup') {
      document.onmousemove = null;
      if (NS) document.releaseEvents(Event.MOUSEMOVE);
      if (NS) document.captureEvents(Event.MOUSEDOWN); //for mac NS
      if (document.MM_curDrag) with (document.MM_curDrag) {
        if (typeof MM_targL =='number' && typeof MM_targT == 'number' &&
            (Math.pow(MM_targL-((NS4)?left:(NS6)?parseInt(style.left):style.pixelLeft),2)+
             Math.pow(MM_targT-((NS4)?top:(NS6)?parseInt(style.top):style.pixelTop),2))<=MM_tol) {
          if (NS4) {left = MM_targL; top = MM_targT;}
          else if (NS6) {style.left = MM_targL; style.top = MM_targT;}
          else {style.pixelLeft = MM_targL; style.pixelTop = MM_targT;}
          MM_SNAPPED = true; MM_LEFTRIGHT = MM_startL-MM_targL; MM_UPDOWN = MM_startT-MM_targT; }
        if (MM_everyTime || MM_SNAPPED) eval(MM_dropJS);
        if(MM_dropBack) {if (NS4) zIndex = MM_oldZ; else style.zIndex = MM_oldZ;}
        retVal = false; if(!NS) event.returnValue = false; }
      document.MM_curDrag = null;
    }
    if (NS) document.routeEvent(objName);
  } return retVal;
}

 function PickerControl(){
  var picker=this;
  this.pickerPad=null;
  this.pickerClose=null;
  this.pickerAbout=null;
  this.head=null;
  this.body=null;
  this.targetID;
  this.targetName;
  this.targetValue;
  this.targetCode;
  this.targetModel;
  this.targetPrice;
  this.targetUnit;
  this.source;
  /************** ����װ弰��Ӱ *********************/
  this.addPickerPad=function(){
   document.write("<div id='divPickerpad' style='position:absolute;top:100;left:0;width:400;height:270;display:none; z-index:1000;filter:progid:DXImageTransform.Microsoft.Shadow(color=#777777, Direction=135, Strength=3) alpha(Opacity=100);cursor: move ' onmousedown=MM_dragLayer('divPickerpad','',0,0,0,0,true,false,-1,-1,-1,-1,false,false,0,'',false,'')>");
   document.write("<table border=0 height=19 onmousedown=MM_dragLayer('divPickerpad','',0,0,0,0,true,false,-1,-1,-1,-1,false,false,0,'',false,'')><tr><td><b><img src=../images/drawit.gif onmousedown=MM_dragLayer('divPickerpad','',0,0,0,0,true,false,-1,-1,-1,-1,false,false,0,'',false,'')></b></td></tr></table>");
   document.write("<iframe id='PickerFrame' scrolling ='no' src='' frameborder=1 height=270 width=400></iframe>");
//   document.write("<table border=0 height=168 width=255></table>");
//   document.write("<div style='position:absolute;top:4;left:4;width:248;height:164;background-color:#336699;'></div>");
   
   document.write("</div>");
   picker.pickerPad=document.all.divPickerpad;
   picker.pickerFrm=document.all.PickerFrame;
  }
  /****************** Show Picker *********************/
  this.show=function(defaultURL,targetName,targetID,targetValue,width,height,scrolling,sourceObject,targetCode,targetModel,targetPrice,targetUnit){

   if(picker.pickerPad.style.display=="") { picker.hide();return false;}
   if(targetName==undefined) {
   // alert("δ����Ŀ�����. \n����: Picker.show(string ����ҳ��URL,obj Ŀ�����,obj Ŀ��ID,obj Ŀ��Value,Width,Height,obj �������);\n\n����ҳ��URL:���ѡ��ҳ��.\nĿ�����:���ܷ��ص����.\nĿ��ID:���ܷ��ص�ID.\nĿ��Value:���ܷ��ص�ֵ.\n�������:���������󵯳�picker,Ĭ��ΪĿ�����.\n");
    return false;
   }else{  
   		picker.targetName=targetName;
   	}
   //if(sourceObject==undefined) picker.source=picker.targetName;
   //		else picker.source=sourceObject;
   	picker.source=picker.targetName;
	picker.pickerFrm.src="about:blank";picker.pickerFrm.src=defaultURL;
	if(targetID)picker.targetID=targetID;
	if(targetValue)picker.targetValue=targetValue;
	if(targetModel)picker.targetModel=targetModel;
	if(targetPrice)picker.targetPrice=targetPrice;
	if(targetUnit)picker.targetUnit=targetUnit;
	if(targetCode)picker.targetCode=targetCode;
	
	if(width || height){
		picker.pickerFrm.width=300;
		picker.pickerFrm.height=222;
		}
		else{
			picker.pickerFrm.width=600;
			picker.pickerFrm.height=270;
			}

	picker.pickerFrm.scrolling="no";
	picker.pickerPad.style.display="";
   //****************����λ��**************//
   var offsetPos=picker.getAbsolutePos(picker.source);//��������λ��;
   
   if((document.body.offsetHeight-(offsetPos.y+picker.source.offsetHeight-document.body.scrollTop))<picker.pickerPad.style.pixelHeight){
    var calTop=offsetPos.y-picker.pickerPad.style.pixelHeight;
   }
   else{
	 
    var calTop=offsetPos.y+picker.source.offsetHeight;
  }
   if((document.body.offsetWidth-(offsetPos.x+picker.source.offsetWidth-document.body.scrollLeft))>picker.pickerPad.style.pixelWidth){
    var calLeft=offsetPos.x ;
   }
  else{
   var calLeft=document.body.offsetWidth-picker.pickerPad.style.pixelWidth;//picker.source.offsetLeft+picker.source.offsetWidth
   }
   //alert(offsetPos.x);
   picker.pickerPad.style.pixelLeft=calLeft;
   picker.pickerPad.style.pixelTop=calTop-30;
  }
  /****************** ��������λ�� *************************/
  this.getAbsolutePos = function(el) {
   var r = { x: el.offsetLeft, y: el.offsetTop };
   if (el.offsetParent) {
    var tmp = picker.getAbsolutePos(el.offsetParent);
    r.x += tmp.x;
    r.y += tmp.y;
   }
   return r;
  };
 
  /************** ������� *********************/
  this.hide=function(){
   picker.pickerPad.style.display="none";  
  }
  /************** ������� *********************/
  this.pick=function(name,id,value,code,model,price,unitt){
   if(name!=null&&picker.targetName!=null)picker.targetName.value=name;
   if(id!=null&&picker.targetID!=null)picker.targetID.value=id;
   if(value!=null&&picker.targetValue!=null)picker.targetValue.value=value;
   if(code!=null&&picker.targetCode!=null)picker.targetCode.value=code;
   if(model!=null&&picker.targetModel!=null)picker.targetModel.value=model;
   if(price!=null&&picker.targetPrice!=null)picker.targetPrice.value=price;
   if(unitt!=null&&picker.targetUnit!=null)picker.targetUnit.value=unitt;
   picker.hide();
  }
  /************** �����￪ʼ *********************/
  this.setup=function(defaultDate){
   picker.addPickerPad();
  }
  picker.setup();
 }
