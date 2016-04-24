<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;

	//check for account id
	
			$sql = 'SELECT username, id FROM users';
            $retval = mysql_query( $sql, $dbhandle );
          
            while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
            {
                if($_POST['account'] == $row['username'])
                {
                   $accountID = $row['id'];
				   break;
                }
            }
			
	//search for pets with account id		
	$sql = "SELECT * FROM pets WHERE aid = '$accountID'";
    $retval = mysql_query( $sql, $dbhandle );
	$array = [];
				
	while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
    {
		$array[pets][] = $row	;
	}
				
	$array["success"]=1;
	print(json_encode($array));
			
	?>