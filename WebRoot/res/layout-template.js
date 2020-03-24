$().ready(function() {
	$.ajax({url: "/view/include/header.htm", dataType: "html", async: false,
		success:function(data){
			$("#_header").html(data);
		},
	});
	$.ajax({url: "/view/include/nav.htm", dataType: "html", async: false,
		success:function(data){
			$("#_nav").html(data);
		},
	});
	$.ajax({url: "/view/include/footer.htm", dataType: "html", async: false,
		success:function(data){
			$("#_footer").html(data);
		},
	});
});