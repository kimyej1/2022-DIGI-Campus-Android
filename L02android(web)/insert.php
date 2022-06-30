<?php
	include "db.php";
	$conn = connectDB();

	$userAgent = $_SERVER["HTTP_USER_AGENT"];
	$android = strpos($userAgent, "Android");	// strpos : string position
	$ios = strpos($userAgent, "Mac OS");		// userAgent 에 "Android, Mac OS" 라는 글자가 있는지?

	if($android) {
		if(isset($_POST["id"]))
			$log = "POST ID = ".$_POST["id"];
		if(isset($_GET["id"]))
			$log = "GET ID = ".$_GET["id"];
		
		$sql = "INSERT INTO log_table (memo) VALUES ('$log')";
		$result = mysqli_query($conn, $sql);	

		// Validation Check... remove whitespaces.. (regex, rtrim/ltrim 등 여러 방법 있음)
		if(isset($_POST["id"]))
			$id = $_POST["id"];
		if(isset($_POST["name"]))
			$name = $_POST["name"];
		if(isset($_POST["pass"]))
			$pass = $_POST["pass"];

		$id = str_replace(" ", "", $id);
		$sql = "INSERT INTO user_table (id, name, pass, level) VALUES ('$id', '$name', '$pass', '1')";
		$result = mysqli_query($conn, $sql);
		// 원래는 $result 는 쿼리 문법이 맞는지 아닌지만 검사한거고,
		// 진짜 성공/실패는 affected_count 이용해서 확인해야 함!

		$array = array();

		// Ajax처럼 echo로 날린것만 받아가서 화면에 뿌려준다.
		if($result) {
			echo "Successfully Registered!";
			$array["result"] = "1";
			$array["reason"] = "success";
		} else {
			echo "Fail to Register..";
			$array["result"] = "-1";
			$array["reason"] = "fail";
		}

		header("Content-type: application/json; charset=utf-8");
		$json = json_encode($array, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
		echo $json;

	} else {	// web으로 접속했으면..

		if(isset($_POST["id"])) {
			// insert DB
			$id = $_POST["id"];
			$name = $_POST["name"];
			$pass = $_POST["pass"];

			$sql = "INSERT INTO user_table (id, name, pass, level) VALUES ('$id', '$name', '$pass', '1')";
			$result = mysqli_query($conn, $sql);
			?>

			<script>
				alert("Check DB");
				location.href = 'insert.php';
			</script>

			<?php
		}

		// http://1.2.3.4/android/insert.php?id=abcd&pass=1111&name=홍길동
		
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
					<div class="col">Register</div>
				</div>

				<form method="post" action="insert.php">
				<div class="row">
					<div class="col-3">ID</div>
					<div class="col">
						<input type="text" name="id" class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="col-3">Name</div>
					<div class="col">
						<input type="text" name="name" class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="col-3">Password</div>
					<div class="col">
						<input type="text" name="pass" class="form-control">
					</div>
				</div>

				<div class="row">
					<div class="col text-center">
						<button type="submit" class="btn btn-primary">Register</button>
					</div>
				</div>
			</div>
		</body>
		</html>
<?php
	}

	closeDB($conn);
?>