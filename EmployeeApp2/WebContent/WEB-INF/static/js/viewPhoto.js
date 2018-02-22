var mainApp = angular.module("photoApp", []);

mainApp.controller('photoAppCntrl', function($scope,$http,$window){
	
	$scope.loadPhoho = function(){
		
		var token = sessionStorage.getItem("token");
		var empId = sessionStorage.getItem("empId");
		$http({
	        method: 'GET',
	        headers: {
	        	'Accept': 'application/json',
	        	'Content-Type': 'application/json',
	        	'Authorization' : token
	        },
	        url: '/EmployeeApp2/employees/getEmployeePhoto?empId='+empId
	    }).then(function (response) {
	        console.log(response.data);
	        $scope.image = response.data.photo;
	        /*if(response.data != null && response.data != '' && response.data == 'success'){
	        	$location.path('/employees');
	        }*/
	    },function (response) {
	        console.log(response);
	        $window.close();
	    });
	}
});