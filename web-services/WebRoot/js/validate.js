//IsInteger: 用于判断一个数字型字符串是否为整形， 
//还可判断是否是正整数或负整数，返回值为true或false 
//string: 需要判断的字符串 
//sign: 若要判断是正负数是使用，是正用&acute;+&acute;，负&acute;-&acute;，不用则表示不作判断 

//var a = "&acute;123&acute;"; 
function IsInteger(string ,sign) 
{ 
	//alert("into IsInteger function()!!!!!!!!"); 
	var integer; 
	if ((sign!=null) && (sign!="&acute;-&acute;") && (sign!="&acute;+&acute;")) 
	{ 
		//alert("afdf"); 
		return false; 
	} 
	integer = parseInt(string); 
	if (isNaN(integer)) 
	{ 
		return false; 
	} 
	else if (integer.toString().length==string.length) 
	{ 
		if ((sign==null) || (sign=="&acute;-&acute;" && integer<0) || (sign=="&acute;+&acute;" && integer>0)) 
		{ 
			return true; 
		} 
		else 
		{
			return false;
		}
	} 
	else 
	{
		return false;
	}
} 

//IsNumber: 用于判断一个数字型字符串是否为数值型， 
//还可判断是否是正数或负数，返回值为true或false 
//string: 需要判断的字符串 
//sign: 若要判断是正负数是使用，是正用&acute;+&acute;，负&acute;-&acute;，不用则表示不作判断 
//Author: PPDJ 
//sample: 
//var a =" &acute;123&acute;"; 
//if (IsNumber(a)) 
//{ 
//	alert(&acute;a is a number&acute;); 
//} 
//if (IsNumber("a,&acute;+&acute;")) 
//{ 
//	alert("a is a positive number"); 
//} 
//if (IsNumber("a,&acute;-&acute;")) 
//{ 
//	alert("&acute;a is a negative number&acute;"); 
//} 

  

function IsNumber(string,sign) 
{ 
	var number; 
	if (string==null) return false; 
	if ((sign!=null) && (sign!="&acute;-&acute;") && (sign!="&acute;+&acute;")) 
	{ 
		return false; 
	} 
	number = new Number(string); 
	if (isNaN(number)) 
	{ 
		return false; 
	} 
	else if ((sign==null) || (sign=="&acute;-&acute;" && number<0) || (sign=="&acute;+&acute;" && number>0)) 
	{ 
		return true; 
	} 
	else 
	{
		return false; 
	}
} 

 
//函数名：fucCheckTEL
//功能介绍：检查是否为电话号码
//参数说明：要检查的字符串
//返回值：true为是合法，false为不合法
function fucCheckTEL(TEL)
{
	 var i,j,strTemp;
	 strTemp="0123456789-()# ";
	 if(TEL.length<7||TEL.length>13)
	{
		 return false;
	}
	 for (i=0;i<TEL.length;i++)
	 {
		  j=strTemp.indexOf(TEL.charAt(i)); 
		  if (j==-1)
		  {
		  //说明有字符不合法
		   return false;
		  }
	 }
	 //说明合法
	 return true;
}


