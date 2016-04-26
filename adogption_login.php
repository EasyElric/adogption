<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	//check post, create account

            $sql = 'SELECT username, password, type FROM users';
            $retval = mysql_query( $sql, $dbhandle );
			
			$array["success"]=0;
           
            while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
            {
                if($_POST['username'] == $row['username'])
                {
					if($_POST['username'] == $row['username'])
					{
						$array["success"]=1;
						$array["type"]=$row['type'];
					}
					
                }
            }
         
			print(json_encode($array));
   
	?>