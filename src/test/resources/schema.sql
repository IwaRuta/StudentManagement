CREATE TABLE IF NOT EXISTS students
(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  furigana VARCHAR(50) NOT NULL,
  nickname VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  address VARCHAR(50),
  age INT,
  gender VARCHAR(10),
  remark VARCHAR(100),
  isDeleted boolean
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id INT PRIMARY KEY AUTO_INCREMENT,
  student_id VARCHAR(50) NOT NULL,
  course_name VARCHAR(50) NOT NULL,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  status ENUM("仮申込","本申込","受講中","受講終了"),NOT NULL
);
