e# AndroidHuflit - Mobile Java Project for MOBILE SUBJECT (HUFLIT)
# Product được chạy trên
- Android Studio 2021
- Máy ảo LDPlayer9
- Language: Java
- Version: Nougat Android 7.0

# Yêu cầu trước khi thực hiện project

1. Follow @NaKMiers on GitHub
2. Download Android Studio 2021
3. Máy ảo LDPlayer9
4. Có kỹ năng Github CƠ BẢN (commit, push, pull, branch, revert, resolve conflict)
6. Có kỹ năng JAVA cơ bản
7. Có kỹ năng Anrdoid Studio cơ bản
8. Đọc OpenAI API theo các link sau
    - https://platform.openai.com/docs/overview (full)
    - https://platform.openai.com/docs/api-reference
        + https://platform.openai.com/docs/api-reference/chat
        + https://platform.openai.com/docs/api-reference/images
        + https://platform.openai.com/docs/api-reference/audio (optional)

# Phân công Project

--- Project được phân công code theo chức năng cụ thể thay vì phân công theo actity: 
    + Để tránh lặp code 
    + Tránh conflict (conflict có thể dẫn đến xóa hết làm lại)
    + Đảm bảo công bằng về khối lượng công việc

## Phân công cụ thể

### Độ phức tạp (được tính dựa trên số giờ dự đoán hoàn thành và tỉ lệ xuất hiện bug dựa trên kinh nghiệm code của @nakmiers)
* trung bình 2h <=> 1 point

- 1 🥰
- 2 😁
- 3 🤔
- 4 🙂
- 5 😣
- 6 😰
- 7 🥶
- 15 🤬

### Khoa - 20
- 15 Express Server 🤬
- 5 Chức năng gửi prompt & nhận completion & hiển thị ra view 😣

### Linh - 16
- 7 Làm giao diện web 🥶
- 6 Login, Register 😰
- 3 Change theme 🤔

### Bảo - 14
- 5 Tạo chat mới, xóa chat, rename chat, delete chat, delete all chat 😣
- 3 Điều chỉnh thuộc tính của image mode 🤔
- 4 Gửi prompt image, nhận images, hiển thị images ra view 🙂
- 2 Save images, copy image và completion vào clipboard 🙂

### Hữu - 15
- 1 Login View 🥰
- 1 Register View 🥰
- 1 Forgot Password View 🥰
- 1 Reset Password View 🥰
- 2 Profile View 😁
- 1 Security View 🥰
- 3 Theme View 🤔
- 2 Change password 😁
- 3 Forgot password & reset password 🤔

### Tuấn - 15
- 5 Chat View 😣
- 3 Image View 🤔
- 2 Dev Info View 😁
- 3 Change avatar 🤔
- 2 Update Profile 😁


# Routes (quy định đường dân, params để server trả về dữ liệu)
### AUTH
Login(post): /auth/login
(emailOrUsername, password)

Register(post): /auth/register
(email, username, password)

Forgot Password(post): /auth/forgot-password
(email)

Reset Password(post): /auth/reset-password
(email, token, newPassword)

### BOX
Get Boxes(get): /box/get-boxes/:type

Create Box(post): /box/create-box/:type

Edit Box(patch): /box/:id
(title)

Delete Box(delete): /box/:id

### USER
Get User(get): /user/:id

Change Password(patch): /user/change-password
(oldPassword, newPassword)

Update Profile(post): /user/update-profile
(firstname, lastname, birthday, job, address)

Change Theme(patch): /user/change-theme/:theme

Update Image Params(patch): /user/update-image-params
(amount, size)

### CHAT
Get Prompts(get): /chat/get-prompts

Create Prompt(post): /chat/create-prompt
(chatId, prompt)

Create Completion(post): /chat/create-completion
(chatId, prompt, model, maxTokens, temperature)

### IMAGE
Get prompts(get): /image/get-prompts

Create prompt(post): /image/create-prompt
(chatId, prompt)

Create Images(post): /image/create-images
(chatId, prompt, amount, size)



# Trình tự làm việc

## 1. Trước khi code

-  Kiểm tra code của mình đã update lại ở nhánh main chưa: git pull
-  Tạo branch mới bằng git branch: git branch Khoa_Prompt
-  Chuyển sang nhánh mới: git checkout Khoa_Prompt
-  Tạo và chuyển cùng lúc: git checkout -b Khoa_Prompt
-  **\_\_\_\_**TUYỆT ĐỐI KHÔNG ĐƯỢC CODE TRÊN NHÁNH MAIN**\_\_\_\_**

#


## 2. Sau khi code một yêu cầu cụ thể -> push lên Github

- Xác nhận các sự thay đổi ở các file: git add .
- Để lại mô tả cho sự thay đổi: git commit -m "...."
- Push cả nhánh lên Github: git push origin tên_nhánh

#

## 4. Sau khi gọp code vào main

-  Những thành viên còn lại update lại của của mình trên máy:
-  Chuyển sang nhánh main: git checkout main
-  Update lại code trên nhánh main: git pull
