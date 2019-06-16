<!DOCTYPE html>
<html>
<head>
	<title>Phones</title>
	<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div class="phones">	

	<div class="topnav">
  		<a href="home.html">Home</a>
  		<a class="active" href="phones.php">Phones</a>
  		<a href="order.php">Order</a>
	</div>

	<h1 style="color: #87CEEB;">Current Phone Inventory</h1>
	<table style="width:100%" class="phone-table">
	<?php 
	//echo "<pre>\n";
	require_once "pdo.php";

	$stmt = $pdo->query("SELECT Model, Picture, Price, Colors, Accessories FROM Phones ORDER BY ID");
	//$stmt = $pdo->query("SELECT * FROM Phones");

	$count = 0;
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
		if ($count == 0) {
			echo "<tr><th colspan='4'>iPhones</th></tr><tr>";
		}

		if ($count == 4) {
			echo "</tr><tr><th colspan='4'>Android Phones</th></tr><tr>";
		}

		if ($count == 8) {
			echo "</tr><tr><th colspan='4'>Flip Phones</th></tr><tr>";
		}

		echo "<td>";
		echo "<h3>" . $row['Model'] . "</h3>";
		echo "<a href='" . $row['Model'] . ".php'><img class='product' src='data:image/jpeg;base64," . base64_encode($row['Picture']) . "' height='225' width='225' alt='" . $row['Model'] . "'></a>"; 
		echo "<p>$" . $row['Price'] . "</p>";
		echo "<p>Available in " . $row['Colors'] . "</p>";
		echo "<p>" . $row['Accessories'] . " Included</p>";
		echo "</td>";

		$count++;
	}
	echo "</tr>";
	//echo "</pre>\n";
	?>

	</table>

</div>
</body>
</html>
