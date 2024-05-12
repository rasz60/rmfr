CREATE DATABASE rmfrDB default CHARACTER SET UTF8;

/*
1. rmfrDB 데이터 베이스를 생성하고 한글을 사용할 수 있는 UTF8로 문자열을 저장
2. `데이터 베이스 목록 보기
*/

-- 데이터베이스 목록 확인
SHOW DATABASES;


/*
테이블 생성시 경고 메세지
 'utf8'은 현재 문자 세트 UTF8MB3의 별칭이지만 향후 릴리스에서는 UTF8MB4의 별칭이 될 것입니다. 명확하게 하려면 UTF8MB4 사용을 고려하십시오. 3778 'utf8mb3_bin'은 더 이상 사용되지 않는 문자 세트 UTF8MB3의 조합입니다. 대신 적절한 데이터 정렬과 함께 UTF8MB4를 사용하는 것을 고려해 보세요.
*/

drop table members;

CREATE TABLE `rmfrdb`.`members` (
  `M_ENTR_ID` varchar(100) COLLATE utf8mb3_bin NOT NULL COMMENT '고객가입번호',
  `M_ID` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '고객아이디',
  `M_PW` varchar(100) COLLATE utf8mb3_bin NOT NULL COMMENT '비밀번호',
  `M_EMAIL` varchar(100) COLLATE utf8mb3_bin NOT NULL COMMENT '고객이메일',
  `M_LEVEL` int NOT NULL DEFAULT '1' COMMENT '고객등급',
  `M_PW_UPDATE_DATE` datetime NOT NULL COMMENT '패스워드수정일자',
  `M_PHONE` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '고객핸드폰번호',
  `M_ADDR1` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '우편번호',
  `M_ADDR2` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '상세주소',
  `M_ADDR3` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '기타주소',
  `M_THUM` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '프로필사진',
  PRIMARY KEY (`M_ENTR_ID`),
  UNIQUE KEY `M_ID_UNIQUE` (`M_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='rmfr_고객가입정보테이블';

/* 고객가입테이블 */
select * from members;

drop table allnoticeboard;

CREATE TABLE `rmfrdb`.`allnoticeboard` (
  `ANB_UUID` VARCHAR(100) NOT NULL COMMENT '게시판식별UUID',
  `ANB_TITLE` VARCHAR(100) NOT NULL COMMENT '게시판제목',
  `ANB_CATEGORY` VARCHAR(10) NULL COMMENT '게시판카테고리',
  `ANB_REG_DATE` DATETIME NOT NULL COMMENT '게시판생성일자',
  `ANB_REG_ID` VARCHAR(100) NOT NULL COMMENT '생성자아이디',
  `ANB_UPDATE_DATE` DATETIME NOT NULL COMMENT '게시판수정일자',
  `ANB_UPDATER_ID` VARCHAR(100) NULL COMMENT '게시판수정자',
  `ANB_STATE` INT NOT NULL DEFAULT 1 COMMENT '게시판상태',
  PRIMARY KEY (`ANB_UUID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = '공통속성게시판';

/* 공통속성테이블 */
select * from allnoticeboard;

/* 공통속성글 */


