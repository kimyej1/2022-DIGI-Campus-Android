<?php

	session_save_path("./sess");
	session_start();

	include "db.php";
	$conn = connectDB();

	$userAgent = $_SERVER["HTTP_USER_AGENT"];
	$android = strpos($userAgent, "Android");	// strpos : string position
	$ios = strpos($userAgent, "Mac OS");		// userAgent 에 "Android, Mac OS" 라는 글자가 있는지?

	if($android) {

		$id = $_POST["id"];
		$pass = $_POST["pass"];

		$sql = "SELECT * FROM user_table WHERE id='$id' and pass='$pass'";
		$result = mysqli_query($conn, $sql);
		$data = mysqli_fetch_array($result);

		$array = array();

		if($data) {
			$_SESSION["sess_id"] = $data["id"];
			$_SESSION["sess_name"] = $data["name"];
			$_SESSION["sess_level"] = $data["level"];
			$_SESSION["sess_memo"] = $data["memo"];

			$array["result"] = "1";
			$array["id"] = $data["id"];
			$array["name"] = $data["name"];
			$array["level"] = $data["level"];
			$array["memo"] = $data["memo"];
		} else {
			$array["result"] = "-1";
		}

		header("Content-type: application/json; charset=utf-8");
		$json = json_encode($array, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
		echo $json;		// LoginActivity -> onResponse()의 파라미터 response에 이 $json이 들어감

	} else {	// web으로 접속했으면..

		if(isset($_POST["id"])) {
			// insert DB
			$id = $_POST["id"];
			$pass = $_POST["pass"];

			$sql = "SELECT * FROM user_table WHERE id='$id' and pass='$pass'";
			$result = mysqli_query($conn, $sql);
			$data = mysqli_fetch_array($result);

			if($data) {
				$_SESSION["sess_id"] = $data["id"];
				$_SESSION["sess_name"] = $data["name"];
				$_SESSION["sess_level"] = $data["level"];
				$_SESSION["sess_memo"] = $data["memo"];

				$msg = "Login Success!";

			} else {
				$msg = "Login Failed..";
			}
			?>

			<script>
				alert('<?php echo $msg; ?>');
				location.href = 'login.php';
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
					<div class="col">Login</div>
					<div class="col">
						<?php
							if(isset($_SESSION["sess_id"]) and $_SESSION["sess_id"]) {	// logged in
								?>
								<button type="button" class="btn btn-danger">Logout</button>
								<?php
							} else {	// not logged in

							}
						?>
					</div>
				</div>

				<form method="post" action="login.php">
				<div class="row">
					<div class="col-3">ID</div>
					<div class="col">
						<input type="text" name="id" class="form-control">
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
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</div>
			</div>
		</body>
		</html>
<?php
	}

	closeDB($conn);
?>