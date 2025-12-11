# Smart Traffic Simulator

A Java-based traffic simulation system that combines **Artificial Intelligence** and **Machine Learning** to create an intelligent traffic management solution with real-time visualization.

## 🚗 Features

- **AI-Driven Vehicle Agents**: Intelligent vehicle behavior using decision-making algorithms
- **Traffic Prediction**: Machine learning model for traffic flow prediction and congestion analysis
- **Real-Time Visualization**: Interactive JavaFX-based traffic simulation display
- **Traffic Light Management**: Smart traffic light control system
- **Road Network Modeling**: Flexible road network architecture

## 🏗️ Project Structure

```
demo/
├── src/main/java/com/example/trafficSim/
│   ├── ai/
│   │   └── VehicleAgent.java          # AI agent for vehicle behavior
│   ├── ml/
│   │   └── TrafficPredictor.java      # ML model for traffic prediction
│   ├── model/
│   │   ├── RoadNetwork.java           # Road network representation
│   │   ├── TrafficLight.java          # Traffic light control
│   │   └── Vehicle.java               # Vehicle model
│   └── ui/
│       ├── MainApp.java               # JavaFX application entry point
│       └── TrafficVisualizer.java     # Traffic visualization component
└── pom.xml                            # Maven configuration
```

## 🛠️ Technologies Used

- **Java** - Core programming language
- **JavaFX** - UI framework for visualization
- **Maven** - Build and dependency management
- **AI/ML Algorithms** - Traffic prediction and intelligent decision-making

## 🚀 Getting Started

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- JavaFX SDK

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Gokule7/-Smart-Traffic-Simulator.git
   cd -Smart-Traffic-Simulator
   ```

2. Navigate to the project directory:
   ```bash
   cd demo
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn javafx:run
   ```

## 📊 How It Works

1. **Vehicle Agents**: AI-powered agents make decisions based on traffic conditions, speed, and nearby vehicles
2. **Traffic Prediction**: ML model analyzes traffic patterns and predicts congestion
3. **Traffic Light Control**: Smart traffic lights adapt to traffic flow
4. **Visualization**: Real-time display of vehicles, roads, and traffic conditions

## 🎯 Future Enhancements

- [ ] Advanced ML models for better traffic prediction
- [ ] Multi-intersection coordination
- [ ] Weather and time-of-day factors
- [ ] Performance metrics and analytics dashboard
- [ ] Export simulation data for analysis

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Gokule7**

---

⭐ If you find this project useful, please consider giving it a star!