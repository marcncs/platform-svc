
function createIdcode(headerobj, tempobj, idcodeobj){
		var header = document.getElementById(headerobj).value;
		var temp = document.getElementById(tempobj).value;
		var numtemp = new Number(temp);
		var idcodes = document.all(idcodeobj);
		var padlength=21-header.length;
		if ( !/^[-\+]?\d+$/.test(temp) ){
			alert("起始号必需是数字!");
			return;
		}
		if (idcodes.length){
			for ( i=0; i<idcodes.length; i++){
				var str = header+""+leftPad(parseInt(numtemp+i), padlength, '0');
				idcodes[i].value=str;
			}
		}else{
			var str = header+""+leftPad(parseInt(numtemp), padlength, '0');
				idcodes.value=str;
		}
		
	}

function leftPad(val, size, ch) {
	var result = new String(val);
	if(ch === null || ch === undefined || ch === '') {
	ch = " ";
	}         
	while (result.length < size) {
		result = ch + result;
	}         
	return result;     
}      