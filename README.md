# Ôn Thi Giữa Kỳ Môn WWW - Jakarta EE REST Service

Kho lưu trữ mã nguồn ôn tập thi giữa kỳ môn **Lập trình Web Phân Tán (WWW)**.
Bao gồm 2 đề tài ôn tập chuẩn theo kiến trúc MVC, REST Service, JNDI DataSource, Tomcat 10, JDK 17, CSDL MariaDB:

---

## Cấu trúc Repository

```
gkw/
├── employee-department/   # Đề tài 1: Quản lý Nhân Viên & Phòng Ban (1 - N)
│   ├── pom.xml            # Jakarta EE 10, Jersey 3.1.10, Weld 5.1.2, MariaDB Client
│   └── src/
│       ├── main/java/     # Models, Repository (Interface & Impl), Service, Resource
│       └── main/webapp/   # META-INF/context.xml (JNDI jdbc/employee_db), beans.xml, web.xml
│
└── news-category/         # Đề tài 2: Quản lý Tin Tức & Danh Mục (1 - N)
    ├── pom.xml            # Jakarta EE 10, Jersey 3.1.10, Weld 5.1.2, MariaDB Client
    └── src/
        ├── main/java/     # Models, DTO, Repository, Service, Resource
        └── main/webapp/   # META-INF/context.xml (JNDI jdbc/tintucdb), beans.xml, web.xml
```

---

## 1. Project 1: Quản lý Nhân Viên - Phòng Ban (`employee-department`)
* **Database:** `employee_db` (MariaDB port 3306)
* **JNDI DataSource:** `jdbc/employee_db`
* **Mô hình:**
  * `departments` (Bảng Cha 1) $\leftrightarrow$ `employees` (Bảng Con N)
* **Các Endpoint REST API chính:**
  * `GET  /api/departments` - Danh sách phòng ban
  * `POST /api/departments` - Thêm mới phòng ban
  * `GET  /api/departments/{id}/employees` - Danh sách nhân viên theo phòng ban
  * `GET  /api/employee` - Danh sách nhân viên
  * `POST /api/employee` - Thêm mới nhân viên
  * `PUT  /api/employee/{id}` - Cập nhật nhân viên
  * `DELETE /api/employee/{id}` - Xóa nhân viên
  * `GET  /api/employee/avg-salary` - **Nghiệp vụ 1-N: Tính lương trung bình theo từng phòng ban**
  * `GET  /api/employee/highest-salary` - **Nghiệp vụ 1-N: Xuất nhân viên lương cao nhất ở mỗi phòng ban**

---

## 2. Project 2: Quản lý Tin Tức - Danh Mục (`news-category`)
* **Database:** `tintucdb` (MariaDB port 3306)
* **JNDI DataSource:** `jdbc/tintucdb`
* **Mô hình:**
  * `danhmuc` (Bảng Cha 1) $\leftrightarrow$ `tintuc` (Bảng Con N)
* **Các Endpoint REST API chính:**
  * `GET  /api/news` - Danh sách tin tức
  * `POST /api/news` - Thêm mới tin tức
  * `GET  /api/news/by-category/{id}` - Lấy tin theo ID danh mục
  * `GET  /api/news/by-category-name/{name}` - Lấy tin theo tên danh mục
  * `GET  /api/news/count-by-category` - Đếm số lượng tin theo danh mục (`COUNT`)
  * `GET  /api/news/count-by-news` - Danh mục có $\ge 2$ tin tức (`HAVING`)
  * `GET  /api/news/max-news-category` - Danh mục có nhiều tin nhất (`MAX Subquery`)

---

## 3. Công nghệ sử dụng
* **Java:** JDK 17 / OpenJDK 17
* **Server:** Apache Tomcat 10.1.x
* **Framework / API:** Jakarta EE 10 (Web Profile)
* **REST Engine:** GlassFish Jersey 3.1.10
* **CDI Container:** JBoss Weld 5.1.2.Final
* **Database:** MariaDB 12.x / MySQL 8.x
* **Testing:** Postman
