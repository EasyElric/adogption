<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	//check for account id
	if(isset($_POST['name']))
    {
			
			$sql = "UPDATE pets SET Name='$_POST[name]', Location='$_POST[location]', Description ='$_POST[description]', 
			Age='$_POST[age]', Breed='$_POST[breed]' WHERE ID = '$_POST[id]'";
            $retval = mysql_query( $sql, $dbhandle );
			
		
			$array["success"]=1;
			print(json_encode($array));
	}

	
	?>