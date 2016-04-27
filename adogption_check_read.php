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
			
	$sql = "SELECT * FROM requests WHERE receiver = '$accountID'";
    $retval = mysql_query( $sql, $dbhandle );
	
	$array["new"] = 0;
	$one = 1;
	
	while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
    {
		$rid = $row['id'];
		if($row['readcheck']==0)
		{
		$array["new"] = 1;
			$sql2 = "UPDATE requests SET readcheck = '$one' WHERE id = '$rid'";
			$retval2 = mysql_query( $sql2, $dbhandle );
		}
		
	}
				

	$array["success"]=1;
	print(json_encode($array));
	
	
	?>