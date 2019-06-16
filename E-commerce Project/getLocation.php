<?php 
$zip = $_GET["zip"];
$city = null;
$state = null;
$file = fopen('zip_codes.csv', 'r');
while (($data = fgetcsv($file)) !== false) {
	if ($zip == $data[0]) {
		list($zip_code, $zip_state, $zip_city) = $data;
		$city = $zip_city;
		$state = $zip_state;
		break;
	}
}
fclose($file);
if (isset($city) && isset($state)) {
	print $city . ", " . $state;
}
else {
	print " , ";
}

?>