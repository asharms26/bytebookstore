/**
 * 		validate credit card form is not empty
 */

function validateForm() {
	
    var c_number = document.forms["cc_validate"]["cardnumber"].value;
    var c_expiry = document.forms["cc_validate"]["expiry"].value;
    var p_name = document.forms["cc_validate"]["buyerName"].value;
    var p_street = document.forms["cc_validate"]["buyerStreet"].value;
    var p_city = document.forms["cc_validate"]["buyerCity"].value;	
    var p_zip = document.forms["cc_validate"]["buyerZIP"].value;

    if (p_name == "") {
        alert("name cannot be blank");
        return false;
    }

    if (p_street == "") {
        alert("street cannot be blank");
        return false;
    }

    if (p_city == "") {
        alert("city cannot be blank");
        return false;
    }

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
    var eZIP = document.getElementByID("buyerState");
    var pZIP = eZIP.options[eZIP.selectedIndex].value;

    var visaRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;
    var mastercardRegEx = /^(?:5[1-5][0-9]{14})$/;
    var discovRegEx = /^(?:6(?:011|5[0-9][0-9])[0-9]{12})$/;

    var dateRegEx = /^(0[1-9]|1[0-2]|[1-9])\/\d{2}$/;

    var zipRegEx = /^(\\d{5})?$/;

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

    if(!zipRegEx.test(pZIP)){
        alert("Please provide a valid zip code");
		return false;
    }

    return true;
}