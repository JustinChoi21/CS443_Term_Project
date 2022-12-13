package edu.umb.cs443termproject.data

enum class EventType (val value: String) {
    Refuel("Refuel"),
    EngineOil("Engine Oil Change"),
    Tire("Tire Change"),
    RegularService("Regular Service")
}