<?php
//�������ݿ������ļ�	
include '../connf/conn.php';
//��ȡPOST������ֵ
$u_name = $_POST['u_name'];

//�༭sql���
$sql = "
SELECT o_id,meal.m_id
	FROM orders
	JOIN user_
	ON orders.u_name = user_.u_name
	JOIN meal
	ON orders.m_id = meal.m_id 
	where orders.u_name = '$u_name'
	
	";
	
	/*
	JOIN user_
	ON orders.u_name = user_.u_name
	JOIN meal
	ON orders.m_id = meal.m_id
	WHERE orders.u_name = '����'
	*/
//ִ��sql���
$result = mysql_query($sql,$conn);

//����ִ�к�Ľ��
while ( $row = mysql_fetch_assoc ( $result ) ) {
    $response [] = $row;
}
//ʹ��Foreach�������� ͬʱʹ��urlencode���� �������ĵ��ֶ�
foreach ( $response as $key => $value ) {
    $newData[$key] = $value;
    
}
//����json����
echo urldecode ( json_encode ( $newData ) );
//�ر����ݿ�����
mysql_close ( $conn );