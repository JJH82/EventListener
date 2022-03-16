drop table if exists employee;
drop table if exists app_emp;
drop table if exists ums_send;
drop sequence if exists sq_ums_send;

CREATE TABLE IF NOT EXISTS employee(emp_id varchar(50) PRIMARY KEY, name VARCHAR(100), cellPhone varchar(15), pin_no varchar(50));
CREATE TABLE IF NOT EXISTS app_emp(app_code VARCHAR(3) , emp_id varchar(50));
CREATE TABLE IF NOT EXISTS ums_send
(
	ums_id varchar(50) primary key 
	, recv_cellphone varchar(15)
	, created_time varchar(14)
	, send_yn char(1)
	, pin_no varchar(50)
	, send_type varchar(2)
	, sender_name varchar(100)
	, msg varchar(2000)
	, appcode varchar(3)
	, program_name varchar(500) 
	, recv_origin varchar(100) 
	, sender_ip varchar(100)
);


CREATE SEQUENCE IF NOT EXISTS sq_ums_send START WITH 1 INCREMENT BY 1 MAXVALUE 1000000000;