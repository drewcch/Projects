function calculateCost() {
	var total = 0;
	var model = document.forms["orderForm"]["phoneModel"].value;
	switch (model) {
		case "iPhone XS":
			total += 899.99;
			break;
		case "iPhone XS Max":
			total += 999.99;
			break;
		case "iPhone XR":
			total += 599.99;
			break;
		case "iPhone SE":
			total += 199.99;
			break;
		case "Galaxy S10":
			total += 799.99;
			break;
		case "Galaxy S10+":
			total += 899.99;
			break;
		case "Galaxy S10e":
			total += 549.99;
			break;
		case "G8 Thinq":
			total += 649.99;
			break;
		case "LG B470":
			total += 19.99;
			break;
		case "Kyocera DuraXE":
			total += 39.99;
			break;
		case "Samsung Rugby 3":
			total += 24.99;
			break;
		case "Easyfone Prime A1":
			total += 19.99;
			break;
		default:
			break;
	}
	var qty = parseInt(document.forms["orderForm"]["qty"].value);
	total = total * qty;
	var shipping = document.forms["orderForm"]["shipping"].value;
	switch (shipping) {
		case "FREE 5 Day":
			break;
		case "Expedited 2 Day":
			total += 4.99;
			break;
		case "Overnight 1 Day":
			total += 9.99;
			break;
		case "Same Day 0 Day":
			total += 19.99;
			break;
		default: 
			break;
	}
	var zip = document.forms["orderForm"]["zip"].value;
	var beforeTax = total.toFixed(2);
	document.getElementById("beforeTax").innerHTML = beforeTax;
	getTaxRate(total,zip);
}

function getColors() {
	var model = document.forms["orderForm"]["phoneModel"].value;
	switch (model) {
		case "iPhone XS":
			document.getElementById("colorChoice").options[0] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[1] = new Option("Silver","Silver");
			document.getElementById("colorChoice").options[2] = new Option("Gold","Gold");
			break;
		case "iPhone XS Max":
			document.getElementById("colorChoice").options[0] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[1] = new Option("Silver","Silver");
			document.getElementById("colorChoice").options[2] = new Option("Gold","Gold");
			break;
		case "iPhone XR":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("White","White");
			document.getElementById("colorChoice").options[2] = new Option("Red","Red");
			break;
		case "iPhone SE":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Silver","Silver");
			document.getElementById("colorChoice").options[2] = new Option("Gold","Gold");
			break;
		case "Galaxy S10":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("White","White");
			document.getElementById("colorChoice").options[2] = new Option("Blue","Blue");
			break;
		case "Galaxy S10+":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("White","White");
			document.getElementById("colorChoice").options[2] = new Option("Blue","Blue");
			break;
		case "Galaxy S10e":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("White","White");
			document.getElementById("colorChoice").options[2] = new Option("Yellow","Yellow");
			break;
		case "G8 Thinq":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Silver","Silver");
			document.getElementById("colorChoice").options[2] = new Option("Red","Red");
			break;
		case "LG B470":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[2] = new Option("White","White");
			break;
		case "Kyocera DuraXE":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[2] = new Option("White","White");
			break;
		case "Samsung Rugby 3":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[2] = new Option("White","White");
			break;
		case "Easyfone Prime A1":
			document.getElementById("colorChoice").options[0] = new Option("Black","Black");
			document.getElementById("colorChoice").options[1] = new Option("Gray","Gray");
			document.getElementById("colorChoice").options[2] = new Option("White","White");
			break;
		default:
			break;
	}
}

function getAccessories() {
	var model = document.forms["orderForm"]["phoneModel"].value;
	switch (model) {
		case "iPhone XS":
			document.getElementById("accessoryChoice").options[0] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "iPhone XS Max":
			document.getElementById("accessoryChoice").options[0] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "iPhone XR":
			document.getElementById("accessoryChoice").options[0] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "iPhone SE":
			document.getElementById("accessoryChoice").options[0] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "Galaxy S10":
			document.getElementById("accessoryChoice").options[0] = new Option("Case and Screen Protector","Case and Screen Protector");
			document.getElementById("accessoryChoice").options[1] = new Option("Case","case");
			document.getElementById("accessoryChoice").options[2] = new Option("Screen Protector","Screen Protector");
			document.getElementById("accessoryChoice").options[3] = new Option("Don't Include Accessories","none");
			break;
		case "Galaxy S10+":
			document.getElementById("accessoryChoice").options[0] = new Option("Case and Screen Protector","Case and Screen Protector");
			document.getElementById("accessoryChoice").options[1] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[2] = new Option("Screen Protector","Screen Protector");
			document.getElementById("accessoryChoice").options[3] = new Option("Don't Include Accessories","None");
			break;
		case "Galaxy S10e":
			document.getElementById("accessoryChoice").options[0] = new Option("Case and Screen Protector","Case and Screen Protector");
			document.getElementById("accessoryChoice").options[1] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[2] = new Option("Screen Protector","Screen Protector");
			document.getElementById("accessoryChoice").options[3] = new Option("Don't Include Accessories","None");
			break;
		case "G8 Thinq":
			document.getElementById("accessoryChoice").options[0] = new Option("Case and Screen Protector","Case and Screen Protector");
			document.getElementById("accessoryChoice").options[1] = new Option("Case","Case");
			document.getElementById("accessoryChoice").options[2] = new Option("Screen Protector","Screen Protector");
			document.getElementById("accessoryChoice").options[3] = new Option("Don't Include Accessories","None");
			break;
		case "LG B470":
			document.getElementById("accessoryChoice").options[0] = new Option("Extra Battery","Extra Battery");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "Kyocera DuraXE":
			document.getElementById("accessoryChoice").options[0] = new Option("Extra Battery","Extra Battery");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "Samsung Rugby 3":
			document.getElementById("accessoryChoice").options[0] = new Option("Extra Battery","Extra Battery");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		case "Easyfone Prime A1":
			document.getElementById("accessoryChoice").options[0] = new Option("Extra Battery","Extra Battery");
			document.getElementById("accessoryChoice").options[1] = new Option("Don't Include Accessories","None");
			break;
		default:
			break;
	}
}

function getLocation(zip) {
	if (window.XMLHttpRequest) {
		var xhr = new XMLHttpRequest();
	}
	else {
		var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
	}

	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;
			var location = result.split(', ');
			if (location[0] != '' && location[1] != '') {
				document.getElementById("city").value = location[0];
				document.getElementById("state").value = location[1];
			}
		}
	}

	xhr.open("GET", "getLocation.php?zip=" + zip, true);
	xhr.send();
}

function getTaxRate(total,zip) {
	if (window.XMLHttpRequest) {
		var xhr = new XMLHttpRequest();
	}
	else {
		var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
	}

	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;
			var rate = parseFloat(result);

			var tax = rate * total;
			var total_cost = total + tax;
			var rounded_cost = total_cost.toFixed(2);

			document.getElementById("orderTotal").innerHTML = rounded_cost;
			document.getElementsByName("totalCost")[0].setAttribute("value",rounded_cost);
		}
	}

	xhr.open("GET", "getTaxRate.php?zip=" + zip, true);
	xhr.send();
}