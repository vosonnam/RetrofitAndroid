<?php
    
    require_once 'user.php';
    require_once 'userlog.php';
    require_once 'upload/student.php';
    session_start();

    function getAPI($key){
    	return (isset($_GET[$key])?$_GET[$key]:"");
    }
    
    function postAPI($key){
    	return (isset($_POST[$key])?$_POST[$key]:"");
    }

    $event=postAPI('event');
    switch ($event) {
    	case 'getstudent':
    		
		    $page=postAPI('page');
		    $records=postAPI('records');

		    $userObject=new Student();

		    echo json_encode($userObject->getListStudent($page,$records));
    		break;
    	case 'editstudent':
    		
		    $masv=postAPI('masv');
		    $tensv=postAPI('tensv');
		    $gt=postAPI('gt');
		    $lop=postAPI('lop');

		    $userObject=new Student();
            $anhsv= $userObject->uploadImage($_FILES['photo']['name'], $_FILES['photo']['tmp_name']);
            echo $_FILES['photo']['size']."\n";
		    echo json_encode($userObject->editStudent($masv, $tensv, $gt, $lop, $anhsv));
    		break;
    	case 'addstudent':
    		
		    $tensv=postAPI('tensv');
		    $gt=postAPI('gt');
		    $lop=postAPI('lop');

		    $userObject=new Student();
            $anhsv= $userObject->uploadImage($_FILES['photo']['name'], $_FILES['photo']['tmp_name']);

		    echo json_encode($userObject->newStudent($tensv, $gt, $lop, $anhsv));
    		break;
    	case 'delstudent':
    		
		    $masv=postAPI('masv');

		    $userObject=new Student();

		    echo json_encode($userObject->delStudent($masv));
    		break;
    	case 'getlog':
    		
		    $page=postAPI('page');
		    $records=postAPI('records');

		    $userObject=new LogUser();

		    echo json_encode($userObject->getListLogUser($page,$records));
    		break;
    	case 'addlog':
    		
		    $username=postAPI('username');
		    $login=postAPI('login');
		    $logout=postAPI('logout');
		    $status=postAPI('status');

		    $userObject=new LogUser();

		    echo json_encode($userObject->newLog($username,$login,$logout,$status));
    		break;
    	case 'login':
    		
		    $username=postAPI('username');
		    $password=postAPI('password');

		    $userObject=new User();

		    echo json_encode($userObject->loginUsers($username,$password));
    		break;
    	case 'register':
    		
		    $username=postAPI('username');
		    $password=postAPI('password');

		    $userObject=new User();

		    echo json_encode($userObject->createNewRegisterUser($username,$password));
    		break;
    	
    	default:
    		echo "event key not match: $event";
    		break;
    }
?>