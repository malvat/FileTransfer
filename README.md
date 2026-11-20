# Java Socket File Transfer Application

A command-line client-server file transfer application in Java built on TCP sockets and multithreading. Clients can upload and download files over a TCP connection while the server handles multiple concurrent client sessions.

## Contents

- [Overview & Technologies](#overview--technologies)
- [Project Structure](#project-structure)
- [Supported Commands](#supported-commands)
- [Walkthrough & Output Example](#walkthrough--output-example)

---

## Overview & Technologies

### Key Capabilities
- Upload files from client to server (`put`)
- Download files from server to client (`get`)
- Handle multiple concurrent clients using threads
- Interact through a lightweight command-line interface (CLI) over TCP

### Technologies Used
- **Language & Networking:** Java, TCP Sockets
- **Concurrency:** Multithreading
- **Streams & Storage:** `DataInputStream`, `DataOutputStream`, Java File I/O

---

## Project Structure

- **`Client.java`:** Manages client-side socket communication and executes file upload and download transfers.
- **`Server.java`:** Initializes the server socket, listens for incoming connections, and spawns a dedicated thread per connected client.
- **`Util.java`:** Parses and validates client commands.

---

## Supported Commands

### Download a File (`get`)

```bash
get <filename>
```

Example:

```bash
get test.txt
```

### Upload a File (`put`)

```bash
put <filename>
```

Example:

```bash
put notes.txt
```

### Close the Connection (`quit`)

```bash
quit
```

---

## Walkthrough & Output Example

### Initial State (Before Transfer)
1. `client.txt` exists on the client side.
2. `server.txt` exists on the server side.

<img width="1223" height="708" alt="image" src="https://github.com/user-attachments/assets/32fa5aa6-6c99-4d99-bf91-331dfbf71658" />

### Client & Server Sessions

#### Client Side
<img width="720" height="322" alt="image" src="https://github.com/user-attachments/assets/e1443696-b54a-4ef3-8cd6-f106e02d5db2" />

#### Server Side
<img width="767" height="228" alt="image" src="https://github.com/user-attachments/assets/2cc61fab-43cf-43a3-a9d3-bbd73758eafc" />

### Transfer Results

#### After `put client.txt`
<img width="1259" height="710" alt="image" src="https://github.com/user-attachments/assets/55fcd93c-079f-41f5-a60f-9784177bf830" />

#### After `get server.txt`
<img width="1264" height="715" alt="image" src="https://github.com/user-attachments/assets/7fbc0228-c904-4bc6-9d5b-eb12ec815508" />
