# Hệ thống kê đơn điện tử chăm sóc sức khỏe từ xa an toàn

## Mô tả Dự án

Hệ thống này là một giải pháp toàn diện cho việc quản lý đơn thuốc điện tử an toàn trong bối cảnh chuyển đổi số y tế. Nó được thiết kế để khắc phục các thách thức như giả mạo, sửa đổi trái phép và lạm dụng thông tin nhạy cảm. Dựa trên kiến trúc microservices, thiết kế hướng sự kiện (event-driven), và nguyên tắc DevSecOps, hệ thống tích hợp lớp Edge, API Gateway, các service chức năng chuyên biệt, xác thực mạnh mẽ, chữ ký số, mã QR an toàn (SQRC), nhật ký kiểm toán bất biến, và tuân thủ hoàn toàn Nghị định 04/2022/TT-BYT. Mục tiêu là đảm bảo mọi đơn thuốc điện tử được phát hành một cách an toàn, minh bạch, có khả năng truy vết và hoàn toàn hợp pháp.

## Mục lục

- [Mô tả Dự án](#mô-tả-dự-án)
- [Mục tiêu Hệ thống](#mục-tiêu-hệ-thống)
- [Tính năng Chính](#tính-năng-chính)
- [Kiến trúc Hệ thống](#kiến-trúc-hệ-thống)
    - [Lớp Edge và API Gateway](#lớp-edge-và-api-gateway)
    - [Các Microservice Chính](#các-microservice-chính)
    - [Lưu trữ Dữ liệu](#lưu-trữ-dữ-liệu)
    - [Event Bus](#event-bus)
    - [Quan sát Hệ thống (Observability)](#quan-sát-hệ-thống-observability)
    - [Điều phối Saga (Saga Choreography)](#điều-phối-saga-saga-choreography)
- [Chi tiết các chức năng an toàn](#chi-tiết-các-chức-năng-an-toàn)
    - [Xác thực & Ủy quyền](#xác-thực--ủy-quyền)
    - [Chữ ký số X.509](#chữ-ký-số-x509)
    - [Mã QR An toàn (SQRC)](#mã-qr-an-toàn-sqrc)
    - [Điểm cuối Xác minh (Verification Endpoint)](#điểm-cuối-xác-minh-verification-endpoint)
    - [Nhật ký Kiểm toán & Phiên bản](#nhật-ký-kiểm-toán--phiên-bản)
- [Công nghệ Sử dụng](#công-nghệ-sử-dụng)
- [Quy trình Nghiệp vụ](#quy-trình-nghiệp-vụ)
    - [Đăng ký & Tư vấn Ban đầu](#đăng-ký--tư-vấn-ban-đầu)
    - [Đặt lịch Hẹn](#đặt-lịch-hẹn)
    - [Khám Lâm sàng & Chẩn đoán](#khám-lâm-sàng--chẩn-đoán)
    - [Phát hành Đơn thuốc](#phát-hành-đơn-thuốc)
    - [Cập nhật Hồ sơ Bệnh án](#cập-nhật-hồ-sơ-bệnh-án)
    - [Xử lý Thanh toán & Bảo hiểm](#xử-lý-thanh-toán--bảo-hiểm)
- [Hạ tầng & CI/CD Pipeline](#hạ-tầng--cicd-pipeline)
    - [Tổng quan Hạ tầng](#tổng-quan-hạ-tầng)
    - [Các Giai đoạn Pipeline GitLab CI/CD](#các-giai-đoạn-pipeline-gitlab-cicd)
- [Lợi ích](#lợi-ích)

## Mục tiêu Hệ thống

Hệ thống tập trung vào năm mục tiêu chiến lược xuyên suốt quá trình tư vấn, kê đơn và cung cấp thuốc:

-   **Phát hành được Ủy quyền:** Chỉ bác sĩ có đủ thẩm quyền mới được tạo đơn thuốc.
-   **Tính toàn vẹn của Chữ ký số:** Mỗi đơn thuốc được đóng dấu bằng chữ ký số X.509 để đảm bảo tính toàn vẹn và xác thực nội dung, ngăn chặn giả mạo.
-   **Mã QR An toàn (SQRC):** Cho phép nhà thuốc xác minh trạng thái đơn thuốc ("đã phát hành" hoặc "đã sử dụng") mà không làm lộ nội dung.
-   **Nhật ký Kiểm toán Bất biến:** Nhật ký không thể sửa đổi ghi lại mọi hành động theo thời gian thực, hỗ trợ kiểm toán, kiểm tra quy định và điều tra sự cố.
-   **Tuân thủ Quy định:** Hệ thống tuân thủ đầy đủ các quy định tại Nghị định 04/2022/TT-BYT và các chỉ thị liên quan về định dạng mã QR và truy xuất dữ liệu.

## Tính năng Chính

Dựa trên các mục tiêu và mô tả, hệ thống cung cấp các tính năng chính sau:

-   Hỗ trợ tư vấn sơ bộ qua chat (dược sĩ).
-   Quản lý đặt và hủy lịch hẹn.
-   Ghi nhận chi tiết khám lâm sàng, kết quả xét nghiệm và chẩn đoán.
-   Phát hành đơn thuốc điện tử theo quy định.
-   Tự động tạo mã đơn thuốc tuân thủ Nghị định 04/2022/TT-BYT.
-   Áp dụng chữ ký số X.509 cho đơn thuốc.
-   Tạo mã QR an toàn (SQRC) cho đơn thuốc.
-   Cập nhật và duy trì hồ sơ bệnh án điện tử có phiên bản.
-   Xử lý thanh toán và tích hợp bảo hiểm.
-   Gửi thông báo qua email, SMS, push notification.
-   Thực hiện truy vấn phân tích và báo cáo KPI.
-   Ghi lại nhật ký kiểm toán bất biến cho mọi sự kiện.
-   Xác thực người dùng (OAuth2/OpenID Connect) và ủy quyền theo vai trò/phạm vi.
-   Bắt buộc xác thực đa yếu tố (MFA) cho bác sĩ.
-   Điểm cuối API để xác minh trạng thái đơn thuốc an toàn.

## Kiến trúc Hệ thống

<p align="center">
  <img src="document/diagrams/telehealth-v2-global-architecture.drawio.png" alt="Architecture" width="1727">
</p>

Hệ thống được xây dựng trên kiến trúc microservices và thiết kế hướng sự kiện.

### Lớp Edge và API Gateway

Tất cả các yêu cầu người dùng đến ban đầu được xử lý tại lớp Edge thông qua Spring Cloud Gateway hoặc Kong. Gateway thực hiện xác thực OAuth2/OpenID Connect qua Keycloak, bắt buộc MFA cho bác sĩ, cấp phát JWT token chứa thông tin vai trò và phạm vi, đồng thời áp dụng rate-limiting và các biện pháp bảo vệ WAF cơ bản.

### Các Microservice Chính

Hệ thống bao gồm chín microservice chính, giao tiếp thông qua Apache Kafka:

| Service                | Description                                                                                                                               |
|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| **Consultation Service** | Hỗ trợ tư vấn sơ bộ qua chat WebSocket (dược sĩ) và ghi lại tương tác.                                                  |
| **Appointment Service** | Quản lý lịch hẹn, phát sự kiện và duy trì lịch sử.                                                   |
| **Examination Service** | Ghi nhận triệu chứng, kết quả xét nghiệm, và chẩn đoán.                                                            |
| **Prescription Service** | Phát hành đơn thuốc điện tử (chỉ bác sĩ), tạo mã tuân thủ quy định, ký số, tạo SQRC, quản lý trạng thái. |
| **Medical Records Service**| Tổng hợp dữ liệu, hỗ trợ lập phiên bản, cung cấp API truy vấn kiểm soát chặt chẽ.                                             |
| **Billing Service** | Xử lý thanh toán (Stripe, PayPal), xuất hóa đơn, phát sự kiện hoàn thành thanh toán.                                                     |
| **Notification Service** | Gửi email, SMS, push notification, tích hợp scheduler và Kafka.                                                         |
| **Analytics Service** | Xử lý truy vấn phân tích phức tạp và báo cáo KPI.                                                                                      |
| **Audit Log Service** | Ghi lại nhật ký bất biến mọi sự kiện với dấu thời gian, ID tác nhân, và địa chỉ IP.                                                                     |

### Lưu trữ Dữ liệu

Cơ sở hạ tầng lưu trữ được tổ chức thành ba tầng:

-   **PostgreSQL:** Phục vụ năm service cốt lõi (Appointment, Examination, Billing, Medical Records, Prescription) với đảm bảo ACID và hỗ trợ các truy vấn quan hệ phức tạp.
-   **MongoDB:** Lưu trữ dữ liệu hướng tài liệu cho các service còn lại, cung cấp tính linh hoạt về schema.
-   **Elasticsearch:** Hoạt động như một lớp lập chỉ mục cho phân tích nâng cao và tìm kiếm log, đồng bộ hóa qua sao chép định kỳ hoặc Debezium Change Data Capture (CDC).

### Event Bus

Apache Kafka đóng vai trò là xương sống của kiến trúc hướng sự kiện, đảm bảo giao tiếp thời gian thực đáng tin cậy giữa các thành phần hệ thống.

### Quan sát Hệ thống (Observability)

OpenTelemetry thu thập metrics và distributed traces. Prometheus, Grafana, và Loki xử lý metrics, dashboards, và giám sát log.

### Điều phối Saga (Saga Choreography)

Hệ thống sử dụng mô hình Saga phân tán dùng sự kiện Kafka để điều phối các service. Trong trường hợp lỗi, sự kiện bù trừ (compensating event) đảm bảo hành vi rollback nhất quán.

## Chi tiết các chức năng an toàn

Các biện pháp bảo mật mạnh mẽ được tích hợp:

### Xác thực & Ủy quyền

Keycloak cung cấp xác thực OAuth2/OpenID Connect với MFA bắt buộc cho bác sĩ. JWT token bao gồm thông tin vai trò và phạm vi, được xác thực tại Edge Gateway.

### Chữ ký số X.509

Chứng chỉ do cơ quan y tế cấp được sử dụng. Khóa riêng được lưu trữ trong HSM, khóa công khai phân phối trong mạng. Prescription Service áp dụng chữ ký số cho mỗi tài liệu (PDF/JSON) trước khi mã hóa.

### Mã QR An toàn (SQRC)

Mô hình sign-then-encrypt được sử dụng: payload (ID Đơn thuốc, mã hash SHA-256, chữ ký số) được mã hóa; mã QR chỉ hiển thị khối ciphertext. Nhà thuốc phải gọi Prescription Service để giải mã và xác minh nội dung.

### Điểm cuối Xác minh (Verification Endpoint)

Các hệ thống bên ngoài hoặc dược sĩ có thể gọi `GET /api/prescriptions/verify/{prescriptionId}` để truy xuất phản hồi JSON chứa các trường như `valid`, `status` (issued | used | expired), `physicianDetails`, `issuedAt`, và `expiresAt`.

### Nhật ký Kiểm toán & Phiên bản

Mọi thao tác (tạo, cập nhật, xác minh, sử dụng) đều publish sự kiện lên Kafka và được ghi lại bất biến. Mỗi lần cập nhật tạo một bản ghi phiên bản mới với dấu thời gian, ID tác nhân và địa chỉ IP để hỗ trợ truy vết từ đầu đến cuối.

## Công nghệ Sử dụng

Dự án sử dụng các công nghệ chính sau:

-   **Gateway/API Management:** Spring Cloud Gateway, Kong
-   **Identity & Access Management:** Keycloak (OAuth2/OpenID Connect, JWT, MFA)
-   **Event Streaming:** Apache Kafka
-   **Database:** PostgreSQL (Relational), MongoDB (Document)
-   **Search & Analytics:** Elasticsearch
-   **Change Data Capture (CDC):** Debezium
-   **Observability:** OpenTelemetry, Prometheus, Grafana, Loki
-   **Security:** X.509 Digital Signatures, Hardware Security Module (HSM), SQRC (Sign-then-Encrypt), SAST (SonarQube), DAST (Arachni), Image Scanning (Trivy)
-   **Build & Packaging:** Maven, Docker
-   **CI/CD:** GitLab CI/CD
-   **Testing:** JUnit, Mockito, H2 (embedded DB), k6 (Performance Testing)
-   **Deployment:** Ubuntu, docker-compose, Harbor (Private Docker Registry), SSH

## Quy trình Nghiệp vụ

### Đăng ký & Tư vấn Ban đầu

Bệnh nhân đăng ký và xác minh tài khoản. Sau đó, có thể bắt đầu tư vấn sơ bộ do dược sĩ thực hiện qua Consultation Service.

### Đặt lịch Hẹn

Đối với các cuộc khám lâm sàng chi tiết, bệnh nhân sử dụng Appointment Service để đặt lịch hẹn với bác sĩ. Hệ thống xác thực thông tin và gửi thông báo xác nhận.

### Khám Lâm sàng & Chẩn đoán

Bác sĩ thực hiện khám tại cơ sở y tế. Tất cả dữ liệu thu thập được (triệu chứng, kết quả xét nghiệm, chẩn đoán) được ghi lại trong Examination Service và phát các sự kiện Kafka tương ứng.

### Phát hành Đơn thuốc

Dựa trên chẩn đoán, bác sĩ kê đơn thuốc điện tử qua Prescription Service. Hệ thống tự động tạo mã tuân thủ quy định, áp dụng chữ ký số, mã hóa payload và tạo SQRC.

### Cập nhật Hồ sơ Bệnh án

Ngay sau khi phát hành đơn thuốc, Medical Records Service tổng hợp dữ liệu từ các service liên quan để cập nhật hồ sơ bệnh án điện tử của bệnh nhân, lưu giữ lịch sử phiên bản.

### Xử lý Thanh toán & Bảo hiểm

Bệnh nhân hoàn tất thanh toán bằng Billing Service, tích hợp với các cổng thanh toán (Stripe, PayPal) và API bảo hiểm. Giao dịch thành công phát ra sự kiện cập nhật hồ sơ bệnh án.

## Hạ tầng & CI/CD Pipeline

<p align="center">
  <img src="document/diagrams/telehealth-pipeline-architecture.drawio.png" alt="Architecture" width="1727">
</p>

### Tổng quan Hạ tầng

Giải pháp được triển khai trên năm máy chủ Ubuntu: bốn máy ảo on-premises (máy chủ GitLab, build server, development server, database server) và một instance Amazon EC2 (chứa Private Docker Registry).

### Các Giai đoạn Pipeline GitLab CI/CD

Mỗi lần push code đều kích hoạt một pipeline GitLab CI/CD gồm sáu giai đoạn, chạy trên các runner chuyên dụng:

| Giai đoạn                     | Mô tả                                                                                                                               |
|---------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|
| **Build** | Máy chủ build thực thi `mvn clean package` và xây dựng Docker images gắn tag theo commit SHA.                                                               |
| **Test Source Code** | Thực hiện SAST (SonarQube), unit tests (JUnit/Mockito), và integration tests (embedded H2/PostgreSQL). SonarQube thực thi các cổng chất lượng/bảo mật. |
| **Push** | Push các Docker images đã build lên registry Harbor riêng tư. Pipeline hủy nếu push thất bại.                                                       |
| **Security Scan Image** | Trivy quét mỗi container image để tìm các CVE HIGH/CRITICAL. Pipeline fail nếu có lỗ hổng bảo mật chặn.                                             |
| **Deploy** | Pull các image mới về development server qua SSH và chạy `docker-compose down && docker-compose up -d`.                                                    |
| **Security Scan Website** | DAST với Arachni chống lại URL staging, báo cáo các lỗ hổng như XSS, CSRF, IDOR...                                            |
| **Performance Testing** | k6 mô phỏng tải (ví dụ: 50 virtual users trong 2 phút). Fail nếu p95 latency > 300 ms hoặc error rate > 3%.                         |

Logs, metrics, báo cáo kiểm thử và các artifact quét được lưu trữ tập trung để truy vết và kiểm toán.

## Lợi ích

Giải pháp này mang lại bốn lợi ích chính:

-   **An ninh Tuyệt đối:** Chữ ký số và SQRC loại bỏ rủi ro giả mạo hoặc sửa đổi đơn thuốc.
-   **Minh bạch Hoàn toàn:** Nhật ký kiểm toán bất biến đảm bảo khả năng hiển thị và truy vết từ đầu đến cuối, hỗ trợ kiểm toán và kiểm tra quy định.
-   **Tuân thủ Quy định:** Hệ thống tuân thủ nghiêm ngặt các hướng dẫn của Bộ Y tế về định dạng đơn thuốc, lưu trữ dữ liệu và thời hạn.
-   **Kiến trúc Mở rộng & Linh hoạt:** Microservices, thiết kế hướng sự kiện và thực hành DevSecOps cho phép triển khai nhanh chóng, mở rộng liền mạch và tích hợp dễ dàng với bên thứ ba.

## Thông tin Chi tiết

Để xem phân tích chi tiết hơn về dự án, bao gồm các lựa chọn thiết kế sâu sắc hơn, chi tiết triển khai và kết quả đánh giá, vui lòng tham khảo báo cáo dự án đầy đủ tại đây:

[Liên kết đến Báo cáo Dự án Chi tiết](document/report/REPORT.md)