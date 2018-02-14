	var mainApp = angular.module("empApp", []);
	
	mainApp.controller("loginCntrl", function($scope,$http,$location,$state){
		$scope.loginFailure = false,
		$scope.errorMessage = sessionStorage.getItem("errorMessage"),
		$scope.initData = function(){
			
			var token = sessionStorage.getItem("token");
			$scope.username = sessionStorage.getItem("user");
			/*if(token != null && token != ''){
				$http({
			        method: 'GET',
			        headers: {
			        	'Accept': 'application/json',
			        	'Content-Type': 'application/json',
			        	'Authorization' : sessionStorage.getItem("token")
			        },
			        url: 'http://localhost:8080/EmployeeApp/employees/getAllEmployees?user='+sessionStorage.getItem("user")
			    }).then(function (response) {
			        console.log(response.data);
			        if(response.data != null && response.data != ''){
			        	$scope.employees = response.data;
			        }
			    }, function (response) {
			        console.log(response);
			    });
			}*/
		},
		$scope.login = function(){
			sessionStorage.setItem("errorMessage","");
			sessionStorage.setItem("user",$scope.form.username);
			var loginData = {
					username : $scope.form.username,
					password : $scope.form.password,
					ip : '',
					token : '',
					sts : ''
			}
			$http({
		        method: 'POST',
		        headers: {
		        	'Accept': 'application/json',
		        	'Content-Type': 'application/json'
		        },
		        url: 'http://localhost:8080/EmployeeApp/home',
		        data : loginData
		    }).then(function (response) {
		        console.log(response.data);
		        if(response.data.sts == 'F'){
		        	 $scope.loginFailure = true;
		        }
		        if(response.data.sts == 'P'){
		        	sessionStorage.setItem("token", response.data.token);
		        	//$location.path('/employees');
		        	$state.path('/employees');
		        }
		    }, function (response) {
		        console.log(response);
		    });
		}
	});