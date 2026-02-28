# 🖥️ Process Scheduler Simulator (Java Swing GUI)

## 📌 Overview
The Process Scheduler Simulator is a desktop-based application developed in Java using Swing that demonstrates how CPU scheduling algorithms work in an operating system. The simulator allows users to input process details and observe execution order, waiting time, and turnaround time for different scheduling strategies.

This project is designed as an educational tool to bridge the gap between theoretical OS concepts and practical visualization of scheduling behavior.

---

## 🚀 Features
- Interactive GUI built using Java Swing
- Supports multiple CPU scheduling algorithms:
  - First Come First Serve (FCFS)
  - Shortest Job First (SJF)
  - Round Robin (RR) with time quantum
- User input for process parameters (Arrival Time, Burst Time)
- Automatic calculation of:
  - Waiting Time
  - Turnaround Time
  - Average Waiting & Turnaround Time
- Logical visualization of execution sequence (Gantt-style flow)

---

## 🧠 Algorithms Implemented
### 1. FCFS (First Come First Serve)
Processes are executed in the order of their arrival time.

### 2. SJF (Shortest Job First)
The process with the smallest burst time is selected for execution.

### 3. Round Robin (RR)
Processes are executed in cyclic order using a fixed time quantum, simulating time-sharing in modern operating systems.

Each scheduling algorithm is implemented in a separate class to ensure modularity and maintainability.

---

## 🏗️ Project Architecture
The project follows an object-oriented and modular design:

- GUI Layer: Handles user input and result display using Java Swing components (JFrame, JPanel, JTable, Buttons).
- Scheduling Layer: Contains separate classes for FCFS, SJF, and Round Robin algorithms.
- Data Handling: Uses arrays and queue structures to manage process execution order and timing calculations.

This separation of concerns improves readability, scalability, and testing of individual scheduling modules.

---

## ⚙️ Working Flow
1. User enters process details (Process ID, Arrival Time, Burst Time).
2. Selects the desired scheduling algorithm.
3. The selected algorithm processes the input data.
4. Execution order is determined based on scheduling logic.
5. Waiting time and turnaround time are calculated.
6. Results are displayed in the GUI in a structured format.

---

## 🛠️ Technologies Used
- Java (Core Java, OOP Concepts)
- Java Swing (GUI Development)
- Event Handling & Action Listeners
- Data Structures (Arrays, Queues)

---

## ▶️ How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/Visavadiya-Niraj/process-scheduler-simulator.git
