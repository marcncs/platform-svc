function PrivilegeEle(pID){
	this.ID=pID;
	children=new Array();
}


function toggleNode(pPath){
	var tmpContainer=document.all(pPath+"_cont");
	tmpIndicator=document.all(pPath+"_indicator");
	tmpIcon=document.all(pPath+"_icon");
	//alert("toggel:"+pPath+"\r\n"+tmpContainer);

	if(tmpContainer!=null){
		if(tmpContainer.style.display=="block"){
			//transition to disable
			tmpContainer.style.display="none";
			if(pPath=="/"){
				tmpIndicator.src=document.all("/images/RootTplus.png").src;

			}else if(tmpIndicator.src.indexOf("T")!=-1){
				tmpIndicator.src=document.all("/images/Tplus.png").src;
			}else{
				tmpIndicator.src=document.all("/images/Lplus.png").src;

			}
			tmpIcon.src=document.all("/images/foldericon.png").src;
			
		}else{
			tmpContainer.style.display="block";

			if(pPath=="/"){
				tmpIndicator.src=document.all("/images/RootTminus.png").src;

			}else if(tmpIndicator.src.indexOf("T")!=-1){
				tmpIndicator.src=document.all("/images/Tminus.png").src;
			}else{
				tmpIndicator.src=document.all("/images/Lminus.png").src;

			}
			tmpIcon.src=document.all("/images/openfoldericon.png").src;
		}
	}
	
}




//privilege path element name
var privilegePathEleName="privilegePath";

function switchState(pPath,pEnable){
	var tmpEle=getPrivilegePathEle(pPath);
	if(tmpEle==null){
		return;
	}
	tmpEle.disabled=!pEnable;
	var tmpEles=getSubPriviligePathEle(pPath);
	for(var tmpi=0;tmpi<tmpEles.length;tmpi++){
		tmpEle=tmpEles[tmpi];
		tmpEle.disabled=!pEnable;
	}
	
}

function checkPrivilegePath(pEle){
	checkSubPrivilegePath(pEle);
	checkParentPrivilegePath(pEle);
}

function checkParentPrivilegePath(pEle){
	//alert("checked parent of '"+pEle.value+"'");
	if(!pEle.checked){
		return ;
	}
	if(pEle.value==null||pEle.value=="/"){
		return ;
	}
	
	var tmpStartIdx=pEle.value.lastIndexOf("/");
	var tmpParentPath;
	if(tmpStartIdx==0){
		tmpParentPath="/";
	}else{
		tmpParentPath=pEle.value.substring(0,tmpStartIdx);
	}
	var tmpParentEle;
	tmpParentEle=getPrivilegePathEle(tmpParentPath);
	if(tmpParentEle==null){
		alert("error ! \r\nCan't locate the element by '"+tmpParentPath+"'");
	}
	tmpParentEle.checked=true;
	checkParentPrivilegePath(tmpParentEle);
}

function getPrivilegePathEle(pPath){
	//alert("get element by '"+pPath+"'");
	var tmpEles=document.all(privilegePathEleName);
	var tmpPrivilegeEle;
	if(tmpEles!=null){
		//alert(tmpEles+" ; length:"+tmpEles.length+";value:"+tmpEles.value);
		if(tmpEles.length==null){
			//alert("element : "+tmpEles.value);
			if(tmpEles.value==pPath){
				tmpPrivilegeEle=tmpEles;
			}
		}else{
			for(var tmpi=0;tmpi<tmpEles.length;tmpi++){
				//alert(tmpEles(tmpi)+":"+tmpEles(tmpi).value);
				if(tmpEles(tmpi).value==pPath){
					tmpPrivilegeEle=tmpEles(tmpi);
					break;
				}
				
			}
		}
	}
	//alert("find "+tmpPrivilegeEle+" ; value:"+tmpPrivilegeEle.value);
	return tmpPrivilegeEle;

}



function checkSubPrivilegePath(pEle){
	//alert("check:"+pEle.value);
	var tmpCurEleChecked=pEle.checked;
	var tmpSubEles=getSubPriviligePathEle(pEle.value);
	for(var tmpi=0;tmpi<tmpSubEles.length;tmpi++){
		var tmpEle=tmpSubEles[tmpi];
		tmpEle.checked=tmpCurEleChecked;
		//checkPrivilegePath(tmpEle);
	}

}

function getSubPriviligePathEle(pParentPath){
	var tmpSubPrivEles=new Array();
	var tmpEles=document.all(privilegePathEleName);
	if(tmpEles!=null){
		if(tmpEles.length==null){
			if(isSubPrivilegeEle(pParentPath,tmpEles.value)){
				tmpSubPrivEles[tmpSubPrivEles.length]=tmpEles;
			}
		}
		else{
			for(var tmpi=0;tmpi<tmpEles.length;tmpi++){
				var tmpEle=tmpEles(tmpi);
				if(isSubPrivilegeEle(pParentPath,tmpEle.value)){
					tmpSubPrivEles[tmpSubPrivEles.length]=tmpEle;
				}
			}
		}
	}
	return tmpSubPrivEles;
}

function isSubPrivilegeEle(pParentPath,pElePath){
	var tmpIsSubPrivEle=false;
	var tmpParentPath=pParentPath;
	if(tmpParentPath!="/"){
		tmpParentPath=tmpParentPath+"/";
	}
	if(pParentPath!=pElePath&&pElePath.indexOf(tmpParentPath)==0){
		tmpIsSubPrivEle=true;
	}
	//alert("parent path : "+pParentPath+"sub Path : "+pElePath+"\r\nis sub:"+tmpIsSubPrivEle);
	return tmpIsSubPrivEle;
}




