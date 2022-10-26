<?php
    include_once 'db-connect.php';
    
    class Student{
        
        private $db;
        
        private $db_table = "student";
        
        public function __construct(){
            $this->db = new DbConnect();
        }
        
        public function newStudent($tensv, $gt, $lop, $anhsv){
            if($anhsv!=""){
                $query = "INSERT INTO `".$this->db_table."`(`tensv`, `gt`, `lop`, `anhsv`) VALUES ('$tensv','$gt','$lop','$anhsv')";
                $inserted = mysqli_query($this->db->getDb(), $query);
                if($inserted == 1){
                    $json['success'] = 1;
                    $json['message'] = "Successfully add new student";
                }else{  
                    $json['success'] = 0;
                    $json['message'] = "Error in add new student. Probably the masv already exists"; 
                }
            }else{
                $json['success'] = 0;
                $json['message'] = "Please choose another photo."; 
            }
            

            mysqli_close($this->db->getDb());
            return $json;
        }
        
        public function editStudent($masv, $tensv, $gt, $lop, $anhsv){
            if($anhsv!=""){
                $query = "UPDATE `".$this->db_table."` SET `tensv`='$tensv',`gt`='$gt',`lop`='$lop',`anhsv`='$anhsv' WHERE `masv`='$masv'";
                $updated = mysqli_query($this->db->getDb(), $query);
                if($updated == 1){
                    $json['success'] = 1;
                    $json['message'] = "Successfully edit this student info";
                }else{  
                    $json['success'] = 0;
                    $json['message'] = "Error in edit this student info. Probably the masv isnt exists"; 
                }
            }else{
                $json['success'] = 0;
                $json['message'] = "Please choose another photo."; 
            }

            mysqli_close($this->db->getDb());
            return $json;
        }
        
        public function delStudent($masv){
            
            $query = "DELETE FROM `".$this->db_table."` WHERE `masv`='$masv'";
            $deleted = mysqli_query($this->db->getDb(), $query);
            if($deleted == 1){
                $json['success'] = 1;
                $json['message'] = "Successfully delete this student info";
            }else{  
                $json['success'] = 0;
                $json['message'] = "Error in delete this student info. Probably the masv isnt exists"; 
            }

            mysqli_close($this->db->getDb());
            return $json;
        }
        
        public function getListStudent($page, $records){
            
            $json = array();
            $mang = array();
            $conn = $this->db->getDb();
            $currentPage=$page*$records;

            $query = "SELECT * FROM `".$this->db_table."` WHERE 1 LIMIT $currentPage, $records";
            $result = mysqli_query($conn, $query);
            
            for (; $row = mysqli_fetch_array($result); ){
                $currentRow = array();
                // $masv = $row['masv'];
                $currentRow['masv'] = $row['masv'];
                $currentRow['tensv'] = $row['tensv'];
                $currentRow['gt'] = $row['gt'];
                $currentRow['lop'] = $row['lop'];
                $currentRow['anhsv'] = $row['anhsv'];
                // $mang[$masv]=$currentRow;
                array_push($mang, $currentRow);
            }

            $sql=mysqli_query($conn,"SELECT COUNT(*) as total FROM `".$this->db_table."` WHERE 1");
            $countRow=mysqli_fetch_array($sql);
            $json['total'] =(int)$countRow['total'];
            $json['items'] =$mang;

            mysqli_close($conn);
            return $json;
        }

        public function uploadImage($currentName, $tempName){
            $fileName = mt_rand();
            $extension=substr($currentName,strpos($currentName,"."));
            $success = (boolean) move_uploaded_file($tempName, './upload/file/'.$fileName.$extension);
            if($success){
                return 'http://192.168.1.48/sinhvien/upload/file/'.$fileName.$extension;
            }else{
                return "";
            }
        }
    }
    // $userObject=new Student();
    // echo json_encode($userObject->uploadImage($_FILES['photo']['name'], $_FILES['photo']['tmp_name']));

    // echo json_encode($userObject->newStudent("000","nam", "nam", "1", "a"));
?>