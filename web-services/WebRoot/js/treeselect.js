function getL(e){
	var l=e.offsetLeft;
	while(e=e.offsetParent){
		l+=e.offsetLeft;
	}
	return l+1;
}

function getT(e){
	var t=e.offsetTop;
	while(e=e.offsetParent){
		t+=e.offsetTop;
	}
	return t;
}
		
function dropDown(){ 
 	var tDiv=document.all["TreeDiv"];
	var DivShim=document.all["DivShim"];
	hiddenAllDiv();
 
	if(tDiv.style.display=='none'){  
		tDiv.style.display='';  
		DivShim.style.display=''; 
		getPosition();  
	}else{  
		tDiv.style.display='none';	
		DivShim.style.display='none';	
	}
}


function getPosition(){ 
 	var vField=document.all["TextField"];
	var fieldSpan=document.all["ValueFieldSpan"];
	var tDiv=document.all["TreeDiv"];
	var DivShim=document.all["DivShim"];
	tDiv.style.left=getL(vField);
	DivShim.style.left=getL(vField);
	tDiv.style.top=getT(vField)+vField.offsetHeight+1;
	DivShim.style.top=tDiv.style.top;
	
	tDiv.style.pixelWidth=fieldSpan.offsetWidth;//���������ֵ��DIV�Ŀ����
	tDiv.style.pixelHeight=300;
	DivShim.style.pixelWidth=fieldSpan.offsetWidth;//���������ֵ��DIV�Ŀ����
	DivShim.style.pixelHeight=300;

	
	tDiv.style.overflowY='auto';
	tDiv.style.overflowX='auto';
}

function hiddenAllDiv(){
  var currDiv=document.getElementById('TreeDiv');
  var DivShim=document.getElementById('DivShim');
  if(currDiv.style.display!='none'){   
  		currDiv.style.display='none';  
		DivShim.style.display='none';  
	}
}

document.attachEvent("onclick",hiddenDiv);
function hiddenDiv(){
	var o=window.event.srcElement.id;
	if(o.indexOf("TextField")==0||o.indexOf("TreeDiv")==0||o.indexOf("BigButton")==0||o.indexOf("webfx-tree-object")==0||o.indexOf("SelectButton")==0){
		return;
	}else{
  		var currDiv=document.getElementById('TreeDiv');  
		var DivShim=document.getElementById('DivShim'); 
  		if(currDiv.style.display!='none'){   
  			currDiv.style.display='none';  
			DivShim.style.display='none'; 
   		}  
 	}
}


//**********ȫ�ֱ���************
//���ؿ����
var gp_hiddenname="";
//��ʾ�����
var gp_textName="";
//������
var gp_tree;
//回调方法 
var gp_callBack;

/**
 * hiddenname ���ؿ����
 * name ��ʾ�����
 * tree ��
 * DefText ��ʾ��Ĭ������
 * tdHeight div�߶�
 */
function CreateTreeSelect(hiddenname,name,tree,DefText,tdHeight,callBack) {
	
	gp_hiddenname=hiddenname;
	gp_textName=name;
	gp_tree=tree;
	gp_callBack=callBack;
	document.write('<span  id="ValueFieldSpan" style="border:0px">');
	document.write('<input type="text"   readonly onclick="this.hideFocus=true;dropDown();" value="'+DefText+'" name="'+gp_textName+'" id="TextField" class="ValueFieldClass" ><a href="javascript:this.hideFocus=true;dropDown();"><img src="../images/CN/find.gif" style="vertical-align:middle" border="0"  onMouseOver="this.style.cursor=\'hand\'"/></a></span>');
	document.write("<iframe id='DivShim' scrolling='no' frameborder='0' style='position:absolute; display:none;background-color:#F7FAFC;'></iframe>");
	document.write("<div id='TreeDiv' class='seldiv'  style='display:none;background-color:#F7FAFC;'>");
	document.write(tree);
	document.write('</div>');
}

//����ֵ
function show(objid){
		var seltext = "";
		if (gp_tree.getSelected()) { 
			seltext=gp_tree.getSelected().text; 
		}
		//alert(document.getElementById(gp_hiddenname).value);
		document.getElementById(gp_hiddenname).value=objid;
		document.getElementById("TextField").value=seltext;
		document.all["TreeDiv"].style.display='none';
		document.all["DivShim"].style.display='none';
		//回调方法
		if(gp_callBack){
			eval(gp_callBack);
		}
}