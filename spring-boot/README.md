# Curl tests

### Testing via cURL

### addRestaurant
```shell
curl -X POST localhost:8080/restaurants/add -H "Content-type: application/json" -d "{\"name\":\"Gen Oz\",\"zipcode\":95243, \"peanutScore\": 4, \"eggScore\": 3, \"dairyScore\": 4, \"overallScore\": 4}"

{"id":1,"name":"Din Tai Fung","zipcode":95421,"peanutScore":4,"eggScore":4,"daire":4,"overallScore":4}
```

### createUser
```shell
curl -X POST localhost:8080/restaurants/create-user -H "Content-type: application/json" -d "{\"username\":\"Gian\",\"city\": \"Stockton\", \"state\": \"CA\", \"zipcode\": 95210, \"isInterestedInPeanutAllergies\": true, \"isInterestedInEggAllergies\": true, \"isInterestedInDairyAllergies\": true}"

{"id":1,"username":"Gian","city":"Stockton","state":"CA","zipcode":95210,"interestedInPeanutAllergies":false,"interestedInEggAllergies":false,"interestedInDairyAllergies":false}
```

### addReview
```shell
curl -X POST localhost:8080/restaurants/1/post-review/Gian -H "Content-type: application/json" -d "{\"name\":\"Gian\",\"restaurant\": 1, \"peanutScore\": 4, \"eggScore\": 4, \"dairyScore\": 4}"

{"id":1,"username":"Gian","city":"Stockton","state":"CA","zipcode":95210,"interestedInPeanutAllergies":false,"interestedInEggAllergies":false,"interestedInDairyAllergies":false}
```