/** 厂家广告的显示功能 */
function getAdBannerByProductId(productId) {
	jQuery.ajax({url: "/api/products/" + productId + "/banners", type: "GET", dataType: "json",
		async: true, data: null, success: function(result) {
			adBannerCallback(result.data); 
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				throw new Error(result.code, result.msg)
			}
		}
	});
}

function adBannerCallback(banner) {
	if (banner) {
		var html = [];
		var isLink = false;
		if (banner.related_url && banner.related_url !== "undefined") {
			isLink = true;
		}
		html.push('<div id="bottomBanner">');
		if (banner.type === 'PIC') {
			if (isLink) {
				html.push('<a href="' + banner.related_url + '">');
			} else {
				html.push('<a href="javascript:void(0)">');
			}
			html.push('<img src="' + banner.pic_url + '?imageView2/2/w/960">');
			html.push('</a>');
			html.push('<div class="bannerClosed" onclick="adClose()">关闭</div>');
		} else if (banner.type === 'TEXT') {
			if (isLink) {
				html.push('<a href="' + banner.related_url + '">');
			} else {
				html.push('<a href="javascript:void(0)">');
			}
			html.push('<div class="bottomText">');
			html.push('<h1>' + banner.title + '</h1>');
			html.push('<h2>' + banner.sub_title + '</h2>');
			html.push('</div>');
			html.push('</a>');
			html.push('<div class="bannerClosed-01" onclick="adClose()">关闭</div>');
		}
		html.push('</div>');
		
		jQuery("body").append(html.join(""));
	}
}

function adClose() {
	jQuery("#bottomBanner").hide();
}