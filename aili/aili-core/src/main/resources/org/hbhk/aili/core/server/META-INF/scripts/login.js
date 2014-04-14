$(function() {
	
		$("#loginForm").validate({
			rules:{
			username:"required",
			password:"required",
			validateCode:"required"
			},
			messages:{
				username:core.hbhk.i18n('hbhk.user.inputusername'),
				password:core.hbhk.i18n('hbhk.user.inputpass'),
				validateCode:core.hbhk.i18n('hbhk.user.inputcode')
		    }
		});
		
});