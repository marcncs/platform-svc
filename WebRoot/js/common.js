
var lk;
function getLinkmanBycid(objcid){
		//var id = $F('psid');
        var url = 'ajaxCustomerLinkmanAction.do?cid='+objcid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }

   function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		lk = data.linkman;
		if ( lk == undefined ){
			document.validateProvide.decideman.value='';
			//document.validateProvide.tel.value='';
			//document.validateProvide.transportaddr.value='';
		}else{
			document.validateProvide.decideman.value=lk.name;
			//document.validateProvide.tel.value=lk.officetel +" " +lk.mobile;
			//document.validateProvide.transportaddr.value=lk.addr;
			//setSelectValue('transit',lk.transit);	
		}
	}
	
	
	function setSelectValue(obj,ovalue){
	var thisobj=document.getElementById(obj);
	for ( i=0; i<thisobj.options.length; i++){
		if (thisobj.options[i].value==ovalue){
			thisobj.options[i].selected=true;
		}
	}
}


function getReceivemanBycid(objcid){
		//var id = $F('psid');
        var url = 'ajaxCustomerLinkmanAction.do?cid='+objcid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showReceiveman}
                    );	
    }

   function showReceiveman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		lk = data.linkman;
		if ( lk == undefined ){
			document.validateProvide.receiveman.value='';
			document.validateProvide.receivemobile.value='';
			document.validateProvide.receivetel.value='';
			document.validateProvide.transportaddr.value='';	
		}else{
			document.validateProvide.receiveman.value=lk.name;
			document.validateProvide.receivemobile.value=lk.mobile;
			document.validateProvide.receivetel.value=lk.officetel;
			document.validateProvide.transportaddr.value=lk.addr;	
			//setSelectValue('transit',lk.transit);	
		}
	}



