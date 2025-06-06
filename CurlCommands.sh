echo "-----------------------------------Calling /auth/signup--------------------------------------";
jwtToken=$(curl \
-X POST \
-H "Content-Type: application/json" \
-d '{"username":"avico","password":"sex123","role":"INVESTOR"}' \
-v \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/auth/signup);
echo "Response: $jwtToken";

echo "-----------------------------------Calling /auth/signin--------------------------------------";
jwtToken=$(curl \
-X POST \
-H "Content-Type: application/json" \
-d '{"username":"avico","password":"sex123"}' \
-v \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/auth/signin);
echo "JWT Token received: $jwtToken";

# /auth/refresh-token cannot be tested as it requires cookie
# /auth/signout cannot be tested as it returns setcookie which curl cannot set (only browser can)

echo "-----------------------------------Calling /entrepreneur/testEntrepreneur--------------------------------------";
response=$(curl \
-X GET \
-H "Authorization:$jwtToken" \
-v \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/entrepreneur/testEntrepreneur);
echo "Response: $response";

echo "-----------------------------------Calling /investor/testInvestor--------------------------------------";
response=$(curl \
-X GET \
-H "Authorization:$jwtToken" \
-v \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/investor/testInvestor);
echo "Response: $response";
