function formatCurrency(num) {
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
       //num = num.substring(0,num.length-(4*i+3))+','+
       num.substring(num.length-(4*i+3));
       return (((sign)?'':'-') + num + '.' + cents);
}

function ForDight(Dight,How) { 	  //-1 以元为单位
	var rmb;
	var yuan;
	if(Dight.indexOf(".")>0){
	yuan=5-Dight.substr(Dight.length-4,4);
	}else{
	yuan=5-Dight.substr(Dight.length-1,1);
	}
	Dight = parseFloat(Dight);
	//alert(yuan);
	if(yuan>=0&&yuan<5){
		rmb = Dight + parseFloat(yuan);
		//alert("dight="+Dight);
		//alert("a=="+rmb);
	}else{
		rmb = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);
		//alert("b=="+rmb);
	}
	return rmb; 
}