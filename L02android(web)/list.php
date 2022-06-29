<?php
	include "db.php";
	$conn = connectDB();

	$userAgent = $_SERVER["HTTP_USER_AGENT"];
	$android = strpos($userAgent, "Android");	// strpos : string position
	$ios = strpos($userAgent, "Mac OS");		// userAgent 에 "Android, Mac OS" 라는 글자가 있는지?

	if($android or 2>1) {

        $sql = "SELECT * FROM user_table ORDER BY name ASC";
        $result = mysqli_query($conn, $sql);
        $data = mysqli_fetch_array($result);

        $array = array();

        // 얘는 있고/없고 상관 없어서 if/else 생략하고 바로 while로 (없으면 그냥 건너뛰고 빈 JSON 보낸다)
        while($data)
        {
            // array()로 array 만들어서 $array에 때려넣기
            array_push($array, array("idx" => $data["idx"], "id" => $data["id"], "name" => $data["name"], "level" => $data["level"]) );

            $data = mysqli_fetch_array($result);
        }
		// header("Content-type: application/json; charset=utf-8");
		// $json = json_encode($array, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
		// echo $json;

	} else {    // web으로 접속했으면..

		?>

		<!doctype html> 
		<html lang="ko"> 
			<head> 
				<meta charset="UTF-8"> 
				<title>국민은행</title> 
				<meta name="viewport" 
					content="width=device-width, maximum-scale=3.0, user-scalable=yes"> 
				<link href="style.css" rel="stylesheet" type="text/css">  
				<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"> 
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
				<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script> 
					<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"> 
				<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script> 
		
		
			</head> 
		<body>
			<div class="container">
				<div class="row">
					<div class="col">User List</div>
				</div>

                <?php
                    // 순서, 아이디, 이름, 레벨
                    $sql = "SELECT * FROM user_table ORDER BY name ASC";
                    $result = mysqli_query($conn, $sql);
                    $data = mysqli_fetch_array($result);

                    if($data) {
                        // head
                        ?>
                        <div class="row rowLine">
                            <div class="col">Index</div>
                            <div class="col">ID</div>
                            <div class="col">Name</div>
                            <div class="col">Level</div>
                        </div>
                        <?php

                        while($data) {
                            ?>
                            <div class="row rowLine">
                                <div class="col"><?php echo $data["idx"]?></div>
                                <div class="col"><?php echo $data["id"]?></div>
                                <div class="col"><?php echo $data["name"]?></div>
                                <div class="col"><?php echo $data["level"]?></div>
                            </div>
                            <?php
                            $data = mysqli_fetch_array($result);
                        }
                    } else {

                    }
                ?>
				
			</div>
		</body>
		</html>
<?php
	}

	closeDB($conn);
?>