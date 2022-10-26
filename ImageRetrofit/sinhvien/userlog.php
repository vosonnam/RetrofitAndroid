<?php
    
    include_once 'db-connect.php';
    
    class LogUser{
        
        private $db;
        
        private $db_table = "userlog";
        
        public function __construct(){
            $this->db = new DbConnect();
        }
        
        public function newLog($username, $login, $logout, $status){
            
            $query = "INSERT INTO `".$this->db_table."`(`username`, `login`, `logout`, `status`) VALUES ('$username','$login','$logout','$status')";
            $inserted = mysqli_query($this->db->getDb(), $query);
            if($inserted == 1){
                $json['success'] = 1;
                $json['message'] = "Successfully add new row log";
            }else{  
                $json['success'] = 0;
                $json['message'] = "Error in add new row log"; 
            }

            mysqli_close($this->db->getDb());
            return $json;
        }
        
        public function getListLogUser($page, $records){
            
            $json = array();
            $mang = array();
            $conn = $this->db->getDb();
            $currentPage=$page*$records;

            $query = "SELECT * FROM `userlog` WHERE 1 LIMIT $currentPage, $records";
            $result = mysqli_query($conn, $query);
            
            for ($i=1; $row = mysqli_fetch_array($result); $i++){
                $currentRow = array();
                $currentRow['username'] = $row['username'];
                $currentRow['login'] = $row['login'];
                $currentRow['logout'] = $row['logout'];
                $currentRow['status'] = $row['status'];
                // $mang[$i]=$currentRow;
                array_push($mang, $currentRow);
            }

            $sql=mysqli_query($conn,"SELECT COUNT(*) as total FROM `userlog` WHERE 1");
            $countRow=mysqli_fetch_array($sql);
            $json['total'] =(int)$countRow['total'];
            $json['items'] =$mang;

            mysqli_close($conn);
            return $json;
        }
    }
?>