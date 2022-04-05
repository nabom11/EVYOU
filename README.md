# JavaFx를 이용한 전기차 충전소 조회 프로그램 개발

### Loading 화면
![loading](https://user-images.githubusercontent.com/95290996/161712285-e3a7ec47-2f26-4c8e-a776-f0461b9c28b1.PNG)

### 프로그램 내부 화면
![page](https://user-images.githubusercontent.com/95290996/161712505-04b7c567-30d7-4ecb-b37e-6ffdc6c453eb.PNG)


## Oracle 사용
#### [테이블 생성 코드]

create table member(<br>
    id varchar2(50) primary key,<br>
    pwd varchar2(50),<br>
    chger_Type varchar2(100),<br>
    car_Type varchar2(50)<br>
);

---
create table search(<br>
    stat_name varchar2(100),<br>
    stat_id varchar2(100),<br>
    chger_id number,<br>
    chger_type varchar2(100),<br>
    stat varchar2(100),<br>
    addr varchar2(150),<br>
    lat number,<br>
    lng number<br>
);

---
create table favor(<br>
    id varchar2(50),<br>
    stat_name varchar2(100),<br>
    stat_id varchar2(100),<br>
    chger_id number,<br>
    chger_type varchar2(100),<br>
    stat varchar2(100),<br>
    addr varchar2(150)<br>
);

---
create table point(<br>
    id varchar2(50),<br>
    stat_name varchar2(100),<br>
    accum_date date,<br>
    save_point number<br>
);
