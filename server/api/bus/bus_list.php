<?php
//�������ݿ�����
include '../connf/conn.php';
//�༭sql���
$query = "select *
 from business";
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
    $newData [$key] ['b_name'] = urlencode ( $value ['b_name'] );
    $newData [$key] ['b_address'] = urlencode ( $value ['b_address'] );
    $newData [$key] ['b_other'] = urlencode ( $value ['b_other'] );
    
}
//����json����
echo urldecode ( json_encode ( $newData ) );
//�ر����ݿ�����
mysql_close ( $conn );
?>