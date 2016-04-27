<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	$sql = "SELECT * FROM users WHERE username = '$_POST[account]'";
    $retval = mysql_query( $sql, $dbhandle );
	$row = mysql_fetch_array($retval);
	
	$favoritesAll = $row['favorites'];
	$favoritesAll = substr($favoritesAll, 1);
	$temp = explode(',',$favoritesAll);
	$favorites = implode(",",$temp);
	$favorites = "(".$favorites.")";
	
	$sql = "SELECT * FROM pets WHERE ID in $favorites";
    $retval = mysql_query( $sql, $dbhandle );
	$array = [];
				
	while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
    {
		$array["pets"][] = $row	;
	}
				

	$array["success"]=1;
	print(json_encode($array));

	
	?>