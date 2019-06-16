<!DOCTYPE html>
<html>
<head>
	<title>Confirmation</title>
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div class="confirmation">	

	<div class="topnav">
  		<a href="home.html">Home</a>
  		<a href="phones.php">Phones</a>
  		<a href="order.php">Order</a>
	</div>
	
	<h1>Your Order has been Submitted!</h1>

	<?php  
	require_once "pdo.php";
	$stmt = $pdo->query("SELECT * FROM Orders ORDER BY ID DESC LIMIT 1"); 
	$row = $stmt->fetch(PDO::FETCH_ASSOC);

	echo "<h3>Order ID</h3>";
	echo "<p>" . $row['ID'] . "</p>";
	echo "<h3>Name</h3>";
	echo "<p>" . $row['FirstName'] . " " . $row['LastName'] . "</p>";
	echo "<h3>Model</h3>";
	echo "<p>" . $row['Model'] . "</p>";
	echo "<h3>Color</h3>";
	echo "<p>" . $row['Color'] . "</p>";
	echo "<h3>Accessories</h3>";
	echo "<p>" . $row['Accessories'] . "</p>";
	echo "<h3>Quantity</h3>";
	echo "<p>" . $row['Quantity'] . "</p>";
	echo "<h3>Shipping Address</h3>";
	echo "<p>" . $row['Address'] . "</p>";
	echo "<h3>Shipping Method</h3>";
	echo "<p>" . $row['ShipMethod'] . "</p>";
	echo "<h3>Location</h3>";
	echo "<p>" . $row['City'] . " , " . $row['State'] . " , " . $row['Zip'] . "</p>";
	echo "<h3>Phone Number</h3>";
	echo "<p>" . $row['PhoneNumber'] . "</p>";
	echo "<h3>Email</h3>";
	echo "<p>" . $row['Email'] . "</p>";
	echo "<h3>Credit Card Type</h3>";
	echo "<p>" . $row['CardType'] . "</p>";
	echo "<h3>Name on Card</h3>";
	echo "<p>" . $row['CardName'] . "</p>";
	echo "<h3>Credit Card Number</h3>";
	echo "<p>" . $row['CardNumber'] . " --- " . $row['CVV'] . "</p>";
	echo "<h3>Credit Card Expiration</h3>";
	echo "<p>" . $row['CardMonth'] . " / " . $row['CardYear'] . "</p>";
	echo "<h3>Total Cost (including tax)</h3>";
	echo "<p>$" . $row['Cost'] . "</p>";
	echo "<h3>Time of Order</h3>";
	echo "<p>" . $row['TimeOrder'] . "</p>";
	?>

</div>
</body>
</html>