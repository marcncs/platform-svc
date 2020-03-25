function msgFormat(pMsgPattern,pReplacement){
	if(pReplacement.length==null||pReplacement.length==0){
		alert("require replacement is a array");
	}
	var tmpMsg=pMsgPattern;
	for(var tmpi=0;tmpi<pReplacement.length;tmpi++){
		tmpMsg=tmpMsg.replace("{"+tmpi+"}",pReplacement[tmpi]);
	}
	
	return tmpMsg;
	
}
