var edit;	//selectRang
var RangeType;
var selection;
var filterScript = false;
var charset="UTF-8";
var bLoad=false;
var pureText=true;
var bTextMode=1	;		//默认为Design模式
	
//预览
function InitDocument(hiddenid, charset)
{	
	if (charset!=null)
	charset=charset;
		var bodyTag="<style type=text/css>.quote{margin:5px 20px;border:1px solid #CCCCCC;padding:5px; background:#F3F3F3 }\nbody{boder:0px}.HtmlCode{margin:5px 20px;border:1px solid #CCCCCC;padding:5px;background:#FDFDDF;font-size:14px;font-family:Tahoma;font-style : oblique;line-height : normal ;font-weight:bold;}\nbody{boder:0px}</style></head><BODY bgcolor=\"#FFFFFF\">";
	
	if (navigator.appVersion.indexOf("MSIE 6.0",0)==-1){
	IframeID.document.designMode="On";
	}
	IframeID.document.open();
	IframeID.document.write ('<html><head>');
	if (bIsIE5){
	IframeID.document.write ('<script language="javascript">');
	IframeID.document.write ('var ispost=0;');
	IframeID.document.write ('	function ctlent(eventobject)');
	IframeID.document.write ('	{');
	IframeID.document.write ('		if(event.ctrlKey && window.event.keyCode==13&&ispost==0)');
	IframeID.document.write ('		{');
	IframeID.document.write ('			ispost=1;');
	IframeID.document.write ('			parent.CopyData("'+hiddenid+'"); ');
	IframeID.document.write ('			parent.document.ob_form.Submit.disabled=true;');
	IframeID.document.write ('			parent.document.ob_form.Submit2.disabled=true;');
	IframeID.document.write ('			parent.document.ob_form.submit();');
	IframeID.document.write ('		}');
	IframeID.document.write ('	}');
	IframeID.document.write ('<\/script>');
	}
	IframeID.document.write(bodyTag);
	IframeID.document.write("</body>");
	IframeID.document.write("</html>");
	IframeID.document.close();
	IframeID.document.body.contentEditable = "True";
	IframeID.document.charset=charset;
	bLoad=true;
	setStyle();
}

function setTab(n)
{
	//html和design按钮的样式更改
	var mhtml=document.getElementById("TabHtml");
	var mdesign=document.getElementById("TabDesign");
	if (n==1)
	{
		mhtml.className="TabOff";
		mdesign.className="TabOn";		
	}
	else if (n==2)
	{
		mhtml.className="TabOn";
		mdesign.className="TabOff";
	}
	else if (n==3)
	{
		mhtml.className="TabOff";
		mdesign.className="TabOff";
	}
}

function setStyle()
{
	//var bs = IframeID.document.body.runtimeStyle;
	var bs = IframeID.document.body.style;
	//根据mode设置iframe样式表	
	if (bTextMode==2) {
		bs.fontFamily="Arial";
		bs.fontSize="10pt";
	}else{
		bs.fontFamily="Arial";
		bs.fontSize="10.5pt";
	}
	bs.scrollbar3dLightColor= '#D4D0C8';
	bs.scrollbarArrowColor= '#000000';
	bs.scrollbarBaseColor= '#D4D0C8';
	bs.scrollbarDarkShadowColor= '#D4D0C8';
	bs.scrollbarFaceColor= '#D4D0C8';
	bs.scrollbarHighlightColor= '#808080';
	bs.scrollbarShadowColor= '#808080';
	bs.scrollbarTrackColor= '#D4D0C8';
	bs.border='0';
}

function validateMode()
{
	if (bTextMode!=2) return true;
	alert("请取消“查看HTML源代码”选项再使用系统编辑功能或者提交!");
	IframeID.focus();
	return false;
}

function CleanCode()
{
	var editor=IframeID;
	editor.focus();
	if (bIsIE5){
	// 0bject based cleaning
		var body = editor.document.body;
		for (var index = 0; index < body.all.length; index++) {
			tag = body.all[index];
		//*if (tag.Attribute["className"].indexOf("mso") > -1)
			tag.removeAttribute("className","",0);
			tag.removeAttribute("style","",0);
		}
	// Regex based cleaning
		var html = editor.document.body.innerHTML;
		html = html.replace(/\<p>/gi,"[$p]");
		html = html.replace(/\<\/p>/gi,"[$\/p]");
		html = html.replace(/\<br>/gi,"[$br]");
		html = html.replace(/\<[^>]*>/g,"");        ///过滤其它所有"<...>"标签
		html = html.replace(/\[\$p\]/gi,"<p>");
		html = html.replace(/\[\$\/p\]/gi,"<\/p>");
		html = html.replace(/\[\$br\]/gi,"<br>");
		editor.document.body.innerHTML = html;
	}else
	{
		var html = IframeID.document.body.ownerDocument.createRange();
		html.selectNodeContents(IframeID.document.body);
		IframeID.document.body.innerHTML = html.toString();
	}
}

function ChekEmptyCode(html)
{
	html = html.replace(/\<[^>]*>/g,"");        ///过滤其它所有"<...>"标签
	html = html.replace(/&nbsp;/gi, "");
	html = html.replace(/o:/gi, "");
	html = html.replace(/\s/gi, "");
	return html;
}

var colour;
function FormatText(command, option)
{
var codewrite;
if (bIsIE5){
		if (option=="removeFormat"){
		command=option;
		option=null;}
		IframeID.focus();
	  	IframeID.document.execCommand(command, false, option);
		pureText = false;
		IframeID.focus();
		
}else{
		if ((command == 'forecolor') || (command == 'backcolor')) {
			parent.command = command;
			buttonElement = document.getElementById(command);
			IframeID.focus();
			document.getElementById("colourPalette").style.left = getOffsetLeft(buttonElement) + "px";
			document.getElementById("colourPalette").style.top = (getOffsetTop(buttonElement) + buttonElement.offsetHeight) + "px";
		
			if (document.getElementById("colourPalette").style.visibility=="hidden")
				{document.getElementById("colourPalette").style.visibility="visible";
			}else {
				document.getElementById("colourPalette").style.visibility="hidden";
			}
		
			//get current selected range
			var sel = IframeID.document.selection; 
			if (sel != null) {
				colour = sel.createRange();
			}
		}
		else{
		IframeID.focus();
	  	IframeID.document.execCommand(command, false, option);
		pureText = false;
		IframeID.focus();
		}
	}

}

function setColor(color)
{
	IframeID.focus();
	IframeID.document.execCommand(parent.command, false, color);
	IframeID.focus();
	document.getElementById("colourPalette").style.visibility="hidden";
}

//----------------
function FilterScript(content)
{
	content = rCode(content, 'javascript:', '<b>javascript</b> :');
	var RegExp = /<script[^>]*>(.*)<\/script>/gi;
	content = content.replace(RegExp, "<div class=HtmlCode>&lt;!-- Script 代码开始 --&gt;<br>$1<br>&lt;!-- Script 代码结束 --&gt;</div>");
	RegExp = /<P>&nbsp;<\/P>/gi;
	content = content.replace(RegExp, "");
	return content;
}

function rCode(s,a,b,i){
	//s原字串，a要换掉pattern，b换成字串，i是否区分大小写
	a = a.replace("?","\\?");
	if (i==null)
	{
		var r = new RegExp(a,"gi");
	}else if (i) {
		var r = new RegExp(a,"g");
	}
	else{
		var r = new RegExp(a,"gi");
	}
	return s.replace(r,b); 
}
//InitDocument("Body","GB2312");