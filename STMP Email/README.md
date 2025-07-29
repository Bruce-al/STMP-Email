# 📧 Java SMTP Mail Client

A simple Java-based GUI SMTP mail client that allows users to send email messages by manually implementing the **SMTP protocol** (without using the `javax.mail` package).

> ⚠️ **Note:** This project was built without the use of any external libraries like JavaMail. The SMTP protocol is implemented from scratch.

---

## 🚀 Features

- Connect to any SMTP server using hostname and port
- GUI with:
  - Sender address input
  - Recipient address input
  - Email body text area
  - Hostname and port configuration
- Status display for success and errors
- Optional bonus:
  - 📎 **Attachment Support** – Send files as email attachments

---

## 🖼️ Interface Overview

The user interface includes:
- Input fields for:
  - Hostname
  - Port
  - Sender name
  - Recipient name
- A text area to compose your message
- A **Send** button to initiate the email transmission
- An **Attach** button (bonus) to select a file and include it in the email

---

## 🔧 Technologies Used

- Java SE
- Java Swing (GUI)
- Socket Programming
- SMTP Protocol

---

## 🗃️ Files Included

- `SMTPClient.java` – Main logic for handling the SMTP protocol
- `EmailGUI.java` – Swing GUI interface
- `AttachmentHandler.java` *(optional)* – For encoding attachments in base64
- `run.bat` – A batch file to compile and run the application
- `README.md` – This documentation

---

## 💡 How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/java-smtp-client.git
   cd java-smtp-client
   ```

2. Run the batch file:
   ```bash
   run.bat
   ```

> Make sure you have **Java JDK** installed and added to your system PATH.

---

## 🧪 Testing

For local testing, you can use:
- [Papercut SMTP](https://github.com/ChangemakerStudios/Papercut-SMTP) – Simulates an SMTP server locally
- [SMTP Bucket](https://smtpbucket.com/) – Free temporary email inbox via SMTP

---

## ⚠️ Restrictions

- ❌ No external mail libraries like `javax.mail` are allowed.
- ✅ All SMTP commands and responses are handled manually via sockets.

---

## 📄 License

This project is for educational use only and not intended for production usage.