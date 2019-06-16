<?php  
require_once "pdo.php";
if (isset($_POST['firstName']) && isset($_POST['lastName']) && 
	isset($_POST['address']) && isset($_POST['city']) &&
	isset($_POST['zip']) && isset($_POST['phoneNumber']) &&
	isset($_POST['email']) && isset($_POST['cardNumber']) && 
	isset($_POST['cvv']) && isset($_POST['cardName']) && 
	$_POST['totalCost'] != 0.00) { 
		$sql = "INSERT INTO Orders (FirstName, LastName, Model, Color, Accessories, Quantity, Address, ShipMethod, City, State, Zip, PhoneNumber, Email, CardType, CardNumber, CVV, CardName, CardMonth, CardYear, Cost, TimeOrder) VALUES (:FirstName, :LastName, :Model, :Color, :Accessories, :Quantity, :Address, :ShipMethod, :City, :State, :Zip, :PhoneNumber, :Email, :CardType, :CardNumber, :CVV, :CardName, :CardMonth, :CardYear, :Cost, :TimeOrder)";

		date_default_timezone_set('America/Los_Angeles');

		$stmt = $pdo->prepare($sql);

		$stmt->execute(array(
			':FirstName' => $_POST['firstName'],
			':LastName' => $_POST['lastName'],
			':Model' => $_POST['phoneModel'],
			':Color' => $_POST['color'],
			':Accessories' => $_POST['accessories'],
			':Quantity' => $_POST['qty'],
			':Address' => $_POST['address'],
			':ShipMethod' => $_POST['shipping'],
			':City' => $_POST['city'],
			':State' => $_POST['state'],
			':Zip' => $_POST['zip'],
			':PhoneNumber' => $_POST['phoneNumber'],
			':Email' => $_POST['email'],
			':CardType' => $_POST['cardType'],
			':CardNumber' => $_POST['cardNumber'],
			':CVV' => $_POST['cvv'],
			':CardName' => $_POST['cardName'],
			':CardMonth' => $_POST['month'],
			':CardYear' => $_POST['year'],
			':Cost' => $_POST['totalCost'],
			':TimeOrder' => date("Y-m-d H:i:s")));

		header("Location: confirmation.php");
	}
?>


<!DOCTYPE html>
<html>
<head>
	<title>Order</title>
	<link rel="stylesheet" type="text/css" href="main.css">
	<script type="text/javascript" src="main.js"></script>
</head>
<body>
<div class="order-form">

	<div class="topnav">
  		<a href="home.html">Home</a>
  		<a href="phones.php">Phones</a>
  		<a class="active" href="order.php">Order</a>
	</div>

	<h1 style="color: #87CEEB;">Order Form</h1>

	<fieldset>
		<legend>
			Order Information
		</legend>
		<form name="orderForm" method="post">
			<br>
			First Name: <input type="text" name="firstName" placeholder="John" required>
			Last Name: <input type="text" name="lastName" placeholder="Doe" required><br><br>
			Phone Model: <select name="phoneModel" onchange="getColors(); getAccessories();">
				<option value="iPhone XS" selected="selected">iPhone XS</option>
				<option value="iPhone XS Max">iPhone XS Max</option>
				<option value="iPhone XR">iPhone XR</option>
				<option value="iPhone SE">iPhone SE</option>
				<option value="Galaxy S10">Galaxy S10</option>
				<option value="Galaxy S10+">Galaxy S10+</option>
				<option value="Galaxy S10e">Galaxy S10e</option>
				<option value="G8 Thinq">G8 Thinq</option>
				<option value="LG B470">LG B470</option>
				<option value="Kyocera DuraXE">Kyocera DuraXE</option>
				<option value="Samsung Rugby 3">Samsung Rugby 3</option>
				<option value="Easyfone Prime A1">Easyfone Prime A1</option>
			</select> 
			Color: <select name="color" id="colorChoice">
				<option value="Gray">Gray</option>
				<option value="Silver">Silver</option>
				<option value="Gold">Gold</option>
			</select> <br><br>
			Accessories: <select name="accessories" id="accessoryChoice">
				<option value="Case">Case</option>
				<option value="None">Don't Include Accessories</option>
			</select>
			Quantity (Limit 4 per customer): <input type="number" name="qty" min="1" max="4" value="1" required> <br><br>
			Shipping Address: <input type="text" name="address" placeholder="111 Pine Street" required>
			Shipping Method: <select name="shipping">
				<option value="FREE 5 Day" selected="selected">FREE 5 Day</option>
				<option value="Expedited 2 Day">Expedited $4.99</option>
				<option value="Overnight 1 Day">Overnight $9.99</option>
				<option value="Same Day 0 Day">Same Day $19.99</option>
			</select> <br><br>
			City: <input type="text" id="city" name="city" placeholder="Los Angeles" required>
			State: <select name="state" id = "state">
				<option value="AL">AL</option>
				<option value="AK">AK</option>
				<option value="AR">AR</option>	
				<option value="AZ">AZ</option>
				<option value="CA" selected="selected">CA</option>
				<option value="CO">CO</option>
				<option value="CT">CT</option>
				<option value="DC">DC</option>
				<option value="DE">DE</option>
				<option value="FL">FL</option>
				<option value="GA">GA</option>
				<option value="HI">HI</option>
				<option value="IA">IA</option>	
				<option value="ID">ID</option>
				<option value="IL">IL</option>
				<option value="IN">IN</option>
				<option value="KS">KS</option>
				<option value="KY">KY</option>
				<option value="LA">LA</option>
				<option value="MA">MA</option>
				<option value="MD">MD</option>
				<option value="ME">ME</option>
				<option value="MI">MI</option>
				<option value="MN">MN</option>
				<option value="MO">MO</option>	
				<option value="MS">MS</option>
				<option value="MT">MT</option>
				<option value="NC">NC</option>	
				<option value="NE">NE</option>
				<option value="NH">NH</option>
				<option value="NJ">NJ</option>
				<option value="NM">NM</option>			
				<option value="NV">NV</option>
				<option value="NY">NY</option>
				<option value="ND">ND</option>
				<option value="OH">OH</option>
				<option value="OK">OK</option>
				<option value="OR">OR</option>
				<option value="PA">PA</option>
				<option value="RI">RI</option>
				<option value="SC">SC</option>
				<option value="SD">SD</option>
				<option value="TN">TN</option>
				<option value="TX">TX</option>
				<option value="UT">UT</option>
				<option value="VT">VT</option>
				<option value="VA">VA</option>
				<option value="WA">WA</option>
				<option value="WI">WI</option>	
				<option value="WV">WV</option>
				<option value="WY">WY</option>
			</select>
			ZIP: <input type="tel" name="zip" placeholder="11111" pattern="[0-9]{5}" onblur="getLocation(this.value)" required> <br><br>
			Phone Number: <input type="tel" name="phoneNumber" placeholder="111-222-3333" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required>
			Email: <input type="email" name="email" placeholder="bob@example.com" required> <br><br>
			Credit Card Type: <select name="cardType">
				<option value="VISA" selected="selected">VISA</option>
				<option value="Mastercard">Mastercard</option>
				<option value="American Express">American Express</option>
				<option value="Discover">Discover</option>
			</select> <br><br>
			Credit Card Number: <input type="tel" name="cardNumber" placeholder="1111-2222-3333-4444" pattern="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}" required>
			CVV: <input type="tel" name="cvv" placeholder="111" pattern="[0-9]{3}" required> <br><br>
			Name on Card: <input type="text" name="cardName" placeholder="John Doe" required>
			Card Expiration Date: <input type="number" name="month" min="1" max="12" value="4" required> <select name="year">
				<option value="2019" selected="selected">2019</option>
				<option value="2020">2020</option>
				<option value="2021">2021</option>
				<option value="2022">2022</option>
				<option value="2023">2023</option>
				<option value="2024">2024</option>
			</select> <br><br><br>
		    <h4 id="display-order" onmouseover="calculateCost()" style="">Please Hover Over to Display Updated Order Total Before Submitting Order</h4> <br><br><br>
		    Total before Taxes: <p id="beforeTax" style="font-weight: bold;"></p>
		    Total after Tax: <p id="orderTotal" style="font-weight: bold;"></p>
		    <input type="submit" value="Submit Order" onclick="calculateCost()"> <br>
			<input type="text" name="totalCost" style="visibility: hidden;">
		</form>
	</fieldset>

</div>
</body>
</html>