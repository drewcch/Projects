<?php 
$zip = $_GET["zip"];
$tax = null;
$file = fopen('tax_rates2.csv', 'r');
while (($data = fgetcsv($file)) !== false) {
	if ($zip == $data[1]) {
		list($state, $zip_code, $region, $rate) = $data;
		$tax = $rate;
		break;
	}
}
fclose($file);

if (isset($tax)) {
	print $rate;
}
else {
	print 0;
}

?>