# Netword_Programming

Xây chương trình Chat, gửi file (audio, video, hình ảnh…) giữa Client – Server
bằng Java với giao thức TCP mã hóa và giải mã văn bản với thuật toán mã hóa AES.
Chương trình có thể thực hiện các chức năng sau:

Client:
- Cho phép nhập văn bản và khóa để mã hóa văn bản trước khi gửi lên Server.
- Trao đổi khóa với Server.
- Nhận kết quả trả về từ Client.

Server:
- Nhận bản mã và khóa sau đó giải mã bản mã để tìm bản rõ.
- Sau khi giải mã xong thì đếm số lượng xuất hiện của các chữ cái trong bản rõ.
- Trả về số lượng xuất hiện của các chữ cái cho Client.
- Lưu trữ dữ liệu trên CSDL trong quá trình Client gửi lên.
