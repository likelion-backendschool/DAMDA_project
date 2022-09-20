$(function() {

  rome(input_from, {
	  dateValidator: rome.val.beforeEq(input_to),
	  time: false
	});

	rome(input_to, {
	  dateValidator: rome.val.afterEq(input_from),
	  time: false
	});


});