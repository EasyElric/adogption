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
	
	//insert into request table
	 $sql = "INSERT INTO requests (sender, receiver, message)
                         VALUES ('$accountID', '$_POST[receiver]','$_POST[message]')";
                $retval = mysql_query( $sql, $dbhandle );
	
	$array["success"]=1;
	print(json_encode($array));
	?>