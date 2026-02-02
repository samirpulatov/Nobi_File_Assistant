# Nobi Bot

**Nobi Bot** is a Telegram bot written in **Java** using **Spring**, designed to convert user-uploaded image files into different formats.

The project demonstrates clean service separation, custom exception handling, and structured Telegram update processing.

---

## Features

- Receive image files from Telegram users
- Convert images between formats:
  - JPG → PNG
  - JPG → PDF
  - JPG → WEBP
- Per-user in-memory file sessions
- Inline button–based action selection
- Centralized domain-specific exception handling
- Informative logging and user-friendly error messages

---

## Tech Stack

- Java 21
- Spring Framework
- TelegramBots Java API
- iText 7
- ImageIO
- SLF4J / Logback
- Maven

---

## Architecture Overview

- **NobiBot** – Telegram entry point  
- **ResponseHandler** – processes updates and routes actions  
- **CommandRouter / FileRouter** – separates command and file logic  
- **ImageConverterService** – coordinates file conversion  
- **Format services** (e.g. `JpgService`) – handle specific conversions  
- **Custom exceptions** – propagate meaningful errors through the system

---

## Error Handling

- No `null` values returned from services  
- All domain errors are represented by custom runtime exceptions  
- Exceptions are logged with context and translated into user-friendly Telegram responses

---

## Configuration

Set the Telegram bot token in `application.properties`:

```properties
telegram.bot.token=YOUR_TELEGRAM_BOT_TOKEN
