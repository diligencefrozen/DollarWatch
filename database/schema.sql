-- DollarWatch Oracle XE schema
-- Run as the application user before deploying the WAR.

begin execute immediate 'drop table dollar_board_comment cascade constraints'; exception when others then null; end;
/
begin execute immediate 'drop table dollar_board cascade constraints'; exception when others then null; end;
/
begin execute immediate 'drop table dollar_favorite cascade constraints'; exception when others then null; end;
/
begin execute immediate 'drop table dollar_search_log cascade constraints'; exception when others then null; end;
/
begin execute immediate 'drop table dollar_member cascade constraints'; exception when others then null; end;
/

begin execute immediate 'drop sequence dollar_board_comment_seq'; exception when others then null; end;
/
begin execute immediate 'drop sequence dollar_board_seq'; exception when others then null; end;
/
begin execute immediate 'drop sequence dollar_favorite_seq'; exception when others then null; end;
/
begin execute immediate 'drop sequence dollar_search_log_seq'; exception when others then null; end;
/

create table dollar_member (
    m_id varchar2(30 char) primary key,
    m_pw varchar2(100 char) not null,
    m_name varchar2(30 char) not null,
    m_email varchar2(100 char),
    m_reg_date date default sysdate
);

create table dollar_favorite (
    f_no number primary key,
    f_member_id varchar2(30 char) not null,
    f_base varchar2(10 char) not null,
    f_target varchar2(10 char) not null,
    f_memo varchar2(200 char),
    f_reg_date date default sysdate,
    constraint dollar_favorite_member_fk foreign key (f_member_id) references dollar_member(m_id) on delete cascade,
    constraint dollar_favorite_unique unique (f_member_id, f_base, f_target)
);

create sequence dollar_favorite_seq start with 1 increment by 1;

create table dollar_search_log (
    s_no number primary key,
    s_member_id varchar2(30 char),
    s_base varchar2(10 char) not null,
    s_target varchar2(10 char) not null,
    s_rate varchar2(50 char),
    s_reg_date date default sysdate
);

create sequence dollar_search_log_seq start with 1 increment by 1;

create table dollar_board (
    b_no number primary key,
    b_title varchar2(100 char) not null,
    b_content varchar2(2000 char) not null,
    b_writer varchar2(30 char) not null,
    b_img varchar2(300 char),
    b_reg_date date default sysdate,
    constraint dollar_board_member_fk foreign key (b_writer) references dollar_member(m_id) on delete cascade
);

create sequence dollar_board_seq start with 1 increment by 1;

create table dollar_board_comment (
    c_no number primary key,
    c_b_no number not null,
    c_writer varchar2(30 char) not null,
    c_content varchar2(500 char) not null,
    c_date date default sysdate,
    constraint dollar_board_comment_board_fk foreign key (c_b_no) references dollar_board(b_no) on delete cascade,
    constraint dollar_board_comment_member_fk foreign key (c_writer) references dollar_member(m_id) on delete cascade
);

create sequence dollar_board_comment_seq start with 1 increment by 1;

insert into dollar_member (
    m_id,
    m_pw,
    m_name,
    m_email
) values (
    'demo',
    '1234',
    'Demo User',
    'demo@dollarwatch.local'
);

insert into dollar_favorite (
    f_no,
    f_member_id,
    f_base,
    f_target,
    f_memo
) values (
    dollar_favorite_seq.nextval,
    'demo',
    'USD',
    'KRW',
    'Daily USD/KRW watch'
);

insert into dollar_board (
    b_no,
    b_title,
    b_content,
    b_writer
) values (
    dollar_board_seq.nextval,
    'How are you tracking USD/KRW this week?',
    'Share your notes on exchange-rate moves, favorites, and trend changes.',
    'demo'
);

commit;
