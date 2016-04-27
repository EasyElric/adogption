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
	
	$favorites = $row['favorites'] . $_POST['id'];
	
	$sql = "UPDATE users SET favorites='$favorites'
			WHERE username = '$_POST[account]'";
            $retval = mysql_query( $sql, $dbhandle );
			
			$array["success"]=1;
			print(json_encode($array));
	
	
	//$favorites = explode(",",$favoritesALL);
	
	
	?>