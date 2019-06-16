<!DOCTYPE html>
<html>
<head>
	<title>iPhone XS Max</title>
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div class="detailed-descriptions">

	<div class="topnav">
  		<a href="home.html">Home</a>
  		<a href="phones.php">Phones</a>
  		<a href="order.php">Order</a>
	</div>
	
	<?php 
	require_once "pdo.php";

	$stmt = $pdo->query("SELECT * FROM Phones WHERE Model='iPhone XS Max'");
	$row = $stmt->fetch(PDO::FETCH_ASSOC);

	echo "<h1>" . $row['Model'] . "</h1>";
	echo "<h2>$" . $row['Price'] . "</h2>";
	echo "<h3>" . $row['Accessories'] . " Included</h3>";
	echo "<h3><a href='order.php'><u>Order Now</u></a></h3>";
	echo "<img src='data:image/jpeg;base64," . base64_encode($row['Picture']) . "' height='500' width='500' alt='" . $row['Model'] . "'>"; 
	echo "<h3>Colors</h3>";
	echo "<p>" . $row['Colors'] . "</p>";
	echo "<h3>Storage</h3>";
	echo "<p>" . $row['Storage'] . "</p>";
	echo "<h3>Size</h3>";
	echo "<p>" . $row['Size'] . "</p>";
	echo "<h3>Weight</h3>";
	echo "<p>" . $row['Weight'] . "</p>";
	echo "<h3>Display</h3>";
	echo "<p>" . $row['Display'] . "</p>";
	echo "<h3>Camera</h3>";
	echo "<p>" . $row['Camera'] . "</p>";
	echo "<h3>Processor</h3>";
	echo "<p>" . $row['Processor'] . "</p>";
	echo "<h3>Battery</h3>";
	echo "<p>" . $row['Battery'] . "</p>";
	?>

</div>
</body>
</html>

