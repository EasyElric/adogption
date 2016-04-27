<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	
	
	$sql = "SELECT * FROM pets WHERE ID = '$_POST[id]'";
    $retval = mysql_query( $sql, $dbhandle );
	$row = mysql_fetch_array($retval);
	$array["name"]=$row['Name'];
	$array["age"]=$row['Age'];
	$array["description"]=$row['Description'];
	$array["location"]=$row['Location'];
	$array["breed"]=$row['Breed'];
	$array["animal"]=$row['Animal'];
	$array["aid"]=$row['aid'];
	$aid = $row['aid'];
	
			
	
	$sql = "SELECT * FROM users WHERE id = '$aid'";
    $retval = mysql_query( $sql, $dbhandle );
	$row = mysql_fetch_array($retval);
	$array["oname"]=$row['name'];	
	$array["olocation"]=$row['location'];
	$array["odescription"]=$row['description'];	
			
		
			$array["success"]=1;
			print(json_encode($array));

	
	?>