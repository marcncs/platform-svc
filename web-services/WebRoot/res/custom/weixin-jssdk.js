/**
 * 微信JS的相关函数
 */

//初始化微信JSSDK的配置信息
function initWeixinConfig() {
	var data = {
		sign_url : location.href.split('#')[0]
	};
	jQuery.ajax({ url : "http://henkel.n369.com/api/weixin/jssdk/jsconfig", type : "POST", data : data, dataType : "json", async : false,
		success : function(d) {
			var config = d.data;
			wx.config({
				debug: false,
				appId: config.appId,
				timestamp: config.timestamp,
				nonceStr: config.nonceStr,
				signature: config.signature,
				jsApiList: [
				            'onMenuShareTimeline',
				            'onMenuShareAppMessage',
							'scanQRCode'
				            ]
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				alert(result.code + "," + result.msg);
			}
		}
	});
}

function wxShare(title, desc, imgUrl) {
	//分享
	wx.ready(function () {
		var shareData = {
		    title: title,
		    desc: desc,
		    imgUrl: imgUrl
		};
		//分享给朋友
		wx.onMenuShareAppMessage(shareData);
		//分享到朋友圈
		wx.onMenuShareTimeline(shareData);
	});
}