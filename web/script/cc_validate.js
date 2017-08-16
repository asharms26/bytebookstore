/**
 * 		validate credit card form is not empty
 */

function validateForm() {
	
	var c_number = document.forms["cc_validate"]["cardnumber"].value;
	var c_expiry = document.forms["cc_validate"]["expiry"].value;
	
	if (c_number == "") {
		alert("Card number cannot be blank");
		return false;
	}
	
	if (c_expiry == "") {
		alert("Expiry cannot be blank");
		return false;
	}
	
	if(!ValidateCreditCardNumber())
		return false;	
}

function ValidateCreditCardNumber() {

	  var ccNum = document.forms["cc_validate"]["cardnumber"].value;
	  var cType = document.querySelector('input[name="cardtype"]:checked').value;
	  var cDate = document.forms["cc_validate"]["expiry"].value;
	  
	  var visaRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;
	  var mastercardRegEx = /^(?:5[1-5][0-9]{14})$/;
	  var discovRegEx = /^(?:6(?:011|5[0-9][0-9])[0-9]{12})$/;

	  var dateRegEx = /^(0[1-9]|1[0-2]|[1-9])\/\d{2}$/;
	  
	  if (cType == "VISA") {
		  if(!visaRegEx.test(ccNum)) {
			  alert("Please provide a valid VISA number.");
			  return false;
		  }
	  }
	  
	  if (cType == "MasterCard") {
		  if(!mastercardRegEx.test(ccNum)){
			  alert("Please provide a valid Master Card number.");
			  return false;
		  }
	  }

	  if (cType == "Discover") {
		  if(!discovRegEx.test(ccNum)){
			  alert("Please provide a valid Discover Card number.");
			  return false;
		  }
	  }

	  if(!dateRegEx.test(cDate)){
		  alert("Please provide a valid date in the format of MM/YY");
		  return false;
	  }
	  
	  return true;
	}