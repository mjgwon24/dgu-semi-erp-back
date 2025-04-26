-- 1. 유저 정보
INSERT INTO user (id, student_number, major, email, username, password, nickname, role, is_verified)
VALUES (1, 20240001, '컴퓨터공학과', 'hong@test.com', 'hong', '1234', '홍길동', 'MEMBER', true);

-- 2. 동아리 정보
INSERT INTO club (club_id, name, affiliation, status, created_at, updated_at)
VALUES (1, '개발동아리', '공과대학', 'ACTIVE', NOW(), NOW());

-- 3. 가입 정보
INSERT INTO club_member (id, club_id, user_id, role, status, registered_at)
VALUES (1, 1, 1, 1, 0, NOW());

-- 4. 알림 데이터 (created_at 3일 이내면 isNew = true)
INSERT INTO notification (notification_id, title, content, category, created_at, is_read, club_id, user_id)
VALUES
    (1, '동아리 가입 승인', '홍길동 님의 가입 요청이 승인되었습니다.', 'CLUB', NOW(), false, 1, 1),
    (2, '정기 모임 공지', '이번 주 정기 모임은 금요일입니다.', 'CLUB', NOW(), true, 1, 1),
    (3, '경고', '무단 불참 시 불이익이 있을 수 있습니다.', 'CLUB', DATE_SUB(NOW(), INTERVAL 5 DAY), false, 1, 1);