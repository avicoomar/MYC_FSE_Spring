echo "-----------------------------------Calling /signup--------------------------------------";
jwtToken=$(curl \
-X POST \
-H "Content-Type: application/json" \
-d '{"username":"avico","password":"sex123","role":"INVESTOR"}' \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/auth/signup);
echo "Response: $jwtToken";

echo "-----------------------------------Calling /signin--------------------------------------";
jwtToken=$(curl \
-X POST \
-H "Content-Type: application/json" \
-d '{"username":"avico","password":"sex123"}' \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/auth/signin);
echo "JWT Token received: $jwtToken";

echo "-----------------------------------Calling /testEntrepreneur--------------------------------------";
response=$(curl \
-X GET \
-H "Authorization:$jwtToken" \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/entrepreneur/testEntrepreneur);
echo "Response: $response";

echo "-----------------------------------Calling /testInvestor--------------------------------------";
response=$(curl \
-X GET \
-H "Authorization:$jwtToken" \
http://localhost:8080/MYC_FSE_Spring-1/frontcontroller/investor/testInvestor);
echo "Response: $response";
