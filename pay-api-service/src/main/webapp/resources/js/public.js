function showLoading(){
	$("body").append('<div class="loading"><span class="mui-spinner"></span></div>');
}
function hideLoading(){
	$("body").find(".loading").remove();
}
$("body").ajaxStart(function(){
	showLoading();
}).ajaxStop(function(){
	hideLoading();
});