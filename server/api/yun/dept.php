 <?php
//�������ݿ�����
include 'conn.php';
//�༭sql���
$query = "select d_no,d_name from dept";
//ִ��SQL���
$result = mysql_query ( $query ,$conn);
//ѭ�� ����ѯ�����ݴ�������
while ( $row = mysql_fetch_assoc ( $result ) ) {
    $response [] = $row;
}
//ʹ��Foreach�������� ͬʱʹ��urlencode���� �������ĵ��ֶ�
foreach ( $response as $key => $value ) {
    $newData[$key] = $value;
    //���������ĵ��ֶ�
    
	$newData [$key] ['d_name'] = urlencode ( $value ['d_name'] );
    
}
//����json����
echo urldecode ( json_encode ( $newData ) );
//�ر����ݿ�����
mysql_close ( $conn );
?>