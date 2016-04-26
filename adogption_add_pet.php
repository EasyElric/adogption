
<?php
    $hostname = "173.194.237.216";
    $username = "test";
    $password = "test";

    // Create connection
    $dbhandle = mysql_connect($hostname, $username, $password) ;

    //select a database to work with
    $selected = mysql_select_db("Pets",$dbhandle) ;


//check post, add pet
    if(isset($_POST['name']) || isset($_POST['age']))
    {
	
		
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
		
                $sql = "INSERT INTO pets (name,age,animal,breed,location,description,aid)
                         VALUES ('$_POST[name]', '$_POST[age]','$_POST[animal]','$_POST[breed]',
						 '$_POST[location]','$_POST[description]','$accountID')";
                $retval = mysql_query( $sql, $dbhandle );
				
				$response["success"]=$_POST['account'];
				print(json_encode($response));
    } 
?>
