/*
 *autor:丁智谋;
 *content:移动端适配;
 *date：2018.3.31
 */
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            docEl.style.fontSize = 100 * (clientWidth / 1242) + 'px';
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);


$(document).ready(function() {
	$("#btn").click(toIndex); 
	$("#btn_faq").click(toFaq);
	$("#btn_return").click(closePage);
});
function toIndex() {
	window.location.href="../qr";
}
function toFaq() {
	window.location.href="../qr/faq";
}
function closePage() {
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger") {
		WeixinJSBridge.call('closeWindow');
	} else if(ua.indexOf("alipay")!=-1) {
		AlipayJSBridge.call('closeWebview');
	} else {
		alert("请关闭当前页面，然后重新扫描");
	}
}
