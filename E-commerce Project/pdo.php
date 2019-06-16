<?php  
include '../dbconfig.php';
$hostname = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$dbname = DB_DATABASE;

try {
	$pdo = new PDO("mysql:host=$hostname;dbname=$dbname", $username, $password);
	//$pdo = new PDO("mysql:host=localhost; dbname=phone_table", "root", "75644982");
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}
catch (PDOException $e) {
	$e->getMessage();
}
?>