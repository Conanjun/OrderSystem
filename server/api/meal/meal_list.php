<?php
//�������ݿ�����
include '../connf/conn.php';
//�༭sql���
$query = "select * from meal";
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
    $newData [$key] ['m_name'] = urlencode ( $value ['m_name'] );
	$newData [$key] ['m_other'] = urlencode ( $value ['m_other'] );
    
}
//����json����
echo urldecode ( json_encode ( $newData ) );
//�ر����ݿ�����
mysql_close ( $conn );
?>