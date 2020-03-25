
function cookieAccessToken(loginInfo) {
//	jQuery.cookie("individual-access-token", loginInfo,  {expires: 1});
	var expiresDate= new Date();
	expiresDate.setTime(expiresDate.getTime() + (1 * 60 * 60 * 1000));
	jQuery.cookies.set("individual-access-token", loginInfo,  {expiresAt: expiresDate});
}

function getAccessToken() {
	var accessToken = jQuery.cookies.get("individual-access-token");
	if (accessToken === null || accessToken === '' || accessToken === 'null' || typeof accessToken === 'undefined') accessToken = '';
	return accessToken;
}

function removeAccessTokenCookie() {
	cookieAccessToken(null);
}

function cookieProductId(productId) {
	jQuery.cookies.set("last_product_id", productId);
}

function cookieManufacturerId(manufacturerId) {
	jQuery.cookies.set("last_manufacturer_id", manufacturerId);
}

function getLastProductId() {
	return jQuery.cookies.get("last_product_id");
}
function getLastCookieManufacturerId() {
	return jQuery.cookies.get("last_manufacturer_id");
}

function ajaxJson(url, type, data, async, successCallback, errorCallback) {
	jQuery.ajax({
		url: url,
		type: type,
		data: data,
		dataType: "json",
		async: async,
		headers: {
			"token": getAccessToken()
		},
		success:successCallback,
		error: function(result) {
			var responseText = result.responseText;
			var responseObj = jQuery.parseJSON(responseText);
			if (responseObj.code == "WZ130001") {
				window.location.href="/elabel/signin.jsp";
			} else {
				if (errorCallback) {
					errorCallback(responseObj);
				}
			}
		}
	});
}