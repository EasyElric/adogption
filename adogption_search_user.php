<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	if(isset($_POST['username']) || isset($_POST['password']))
    {
      
            $sql = 'SELECT username, password FROM accounts';

            $retval = mysql_query( $sql, $dbhandle );
  
            while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
            {
				$userFound = false;
                if($_POST['username'] == $row['username'])
                {
					$userFound = true;
                    if($_POST['password'] == $row['password'])
					{
						$array = [];
						$array["login"] = $row['username'];
						$array["success"] = "1";
						print(json_encode($array));
					}
					else
					{
						$array["success"] = "0";
						print(json_encode($array));
					}
                    break;
                }
				if($userFound == false)
				{
					$array["success"] = "0";
					print(json_encode($array));
				}
            }
           
    } 

	
	?>