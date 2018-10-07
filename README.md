# Summary
This is a room occupancy optimization tool for the hotel.

The user(hotel) has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.

Users have two different categories of rooms: Premium and Economy. 

Users want their customers to be satisfied: they will not book a customer willing to pay over EUR 100 for the night into an Economy room. But they will book lower paying customers into Premium rooms if these rooms would be empty and all Economy rooms will be filled by low paying customers. Highest paying customers below EUR 100 will get preference for the “upgrade”. Customers always only have one specific price they are willing to pay for the night.

This app provides an interface for hotels to enter the numbers of Premium and Economy rooms that are available for the night and then tells them immediately how many rooms of each category will be occupied and how much money they will make in total. 

Potential guests are represented by an array of numbers that is their willingness to pay for the night.

# Instalation
To install and launch the app:
1. Download [the jar](https://github.com/shtykh/room-planner/raw/master/bin/room-planner-1.0.0.jar)
2. Make sure your _java -version_ is _1.8_ or above
3. Make sure your port 8080 is available.
3. Run the jar with _java -jar_
# Example
## Update rooms availability
### Request
```
POST /updateRooms HTTP/1.1
Host: localhost:8080
Content-Type: application/json
charset: utf-8
Cache-Control: no-cache

[ { "roomLevel": "ECONOMY", "roomsNumber": 3 }, { "roomLevel": "PREMIUM", "roomsNumber": 3 } ]
```
### Response
Ok(200) response with empty body
## Get rooms availability
### Request
```
GET /getRooms HTTP/1.1
Host: localhost:8080
Content-Type: application/json
charset: utf-8
Cache-Control: no-cache
```
### Response
```json
{
    "ECONOMY": 3,
    "PREMIUM": 3
}
```
## Plan the rooms usage
### Request
```
POST /plan HTTP/1.1
Host: localhost:8080
Content-Type: application/json
charset: utf-8
Cache-Control: no-cache

[ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]
```
### Response 
```
{
    "roomsUsages": [
        {
            "roomLevel": "PREMIUM",
            "roomsNumber": 3,
            "paymentSum": 738
        },
        {
            "roomLevel": "ECONOMY",
            "roomsNumber": 3,
            "paymentSum": 167
        }
    ]
}
```
