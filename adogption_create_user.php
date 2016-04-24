<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;
	
	//check post, create account

            $sql = 'SELECT username, password FROM users';
            $retval = mysql_query( $sql, $dbhandle );
           

            $taken = false;
            while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
            {
                if($_POST['username'] == $row['username'])
                {
                    $taken = true;
					$array["success"]=0;
					print(json_encode($array));
                    break;
                }
            }
            if($taken == false)
            {
                $sql2 = "INSERT INTO users (username,password,type)
                         VALUES ('$_POST[username]', '$_POST[password]', '$_POST[type]')";
                $retval2 = mysql_query( $sql2, $dbhandle );
				$array["success"]=1;
				print(json_encode($array));
            }
	
	?>