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
			$sql = 'SELECT username, id, type FROM users';
            $retval = mysql_query( $sql, $dbhandle );
          
		  $response["success"] = 0;
            while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
            {
                if($_POST['account'] == $row['username'])
                {
				$response["success"] = 1;
                   $accountID = $row['id'];
				   break;
                }
            }
			
			$sql = "UPDATE users SET name='$_POST[name]', location='$_POST[location]', description ='$_POST[description]' 
			WHERE id = '$accountID'";
            $retval = mysql_query( $sql, $dbhandle );
			
		
			print(json_encode($response));
	}
			

	
	?>