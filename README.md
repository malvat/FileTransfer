# Java Socket File Transfer Application

A simple client-server file transfer application built in Java using TCP sockets and multithreading.

## Features

- Upload files from client to server using `put`
- Download files from server using `get`
- Multiple client support using threads
- Simple command-line interface
- File transfer over TCP sockets

---

## Technologies Used

- Java
- TCP Sockets
- Multithreading
- DataInputStream / DataOutputStream
- File I/O


### File Descriptions

#### `Client.java`
Handles client-side communication and file uploads/downloads.

#### `Server.java`
Starts the server, listens for client connections, and creates a new thread for each connected client.

#### `Util.java`
Parses and validates client commands.

## Supported Commands

### Download a file from server

```bash
get <filename>
```

Example:

```bash
get test.txt
```

---

### Upload a file to server

```bash
put <filename>
```

Example:

```bash
put notes.txt
```

---

### Close the connection

```bash
quit
```

## Output Example

Files before the transfer
1. client.txt (exists on the client side)
2. server.txt (exists on the server side) 
<img width="1223" height="708" alt="image" src="https://github.com/user-attachments/assets/32fa5aa6-6c99-4d99-bf91-331dfbf71658" />

### Client Side
<img width="720" height="322" alt="image" src="https://github.com/user-attachments/assets/e1443696-b54a-4ef3-8cd6-f106e02d5db2" />

### Server Side
<img width="767" height="228" alt="image" src="https://github.com/user-attachments/assets/2cc61fab-43cf-43a3-a9d3-bbd73758eafc" />

### Result
executed put client.txt
<img width="1259" height="710" alt="image" src="https://github.com/user-attachments/assets/55fcd93c-079f-41f5-a60f-9784177bf830" />

executed get server.txt
<img width="1264" height="715" alt="image" src="https://github.com/user-attachments/assets/7fbc0228-c904-4bc6-9d5b-eb12ec815508" />
