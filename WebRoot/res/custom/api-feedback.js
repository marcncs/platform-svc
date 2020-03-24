/**
 * 该js文件主要是提供各种产品评论的功能
 */

function deleteFeedback(feedbackId) {
	jQuery.ajax({url: "/mfr_admin/product/feedbacks/" + feedbackId, type: "DELETE", dataType: "json",
		async: false, data: null, success: function(result) {
			return true;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var result = jQuery.parseJSON(XMLHttpRequest.responseText);
			if (result) {
				if (result.code == 'WZ130001') {
					window.location.href="/mfr_admin/signin.jsp";
				} else {
					throw new Error(result.code, result.msg) 
				}
			}
		}
	});
}