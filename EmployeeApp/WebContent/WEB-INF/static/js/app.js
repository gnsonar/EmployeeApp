	var app = angular.module("empApp",[]);
	var matchcnt = 0;
	var matchrow = 0;

	app.filter('empDataFilter', function(){
		return function(input,searchBox){
			var output = []
			var row = 0;
			matchcnt = 0;
			angular.forEach(input, function (item) {
				if(searchBox != undefined && searchBox != ''){
					if((item.firstName.indexOf(searchBox) + item.lastName.indexOf(searchBox) + 
							item.email.indexOf(searchBox)) > -3){
						item.highlight = true;
						matchcnt++;
    					matchrow = row;
					}else{
						item.highlight = false;
					}
				}else{
					item.highlight = false;
				}
				output.push(item);
				row++;
			});
			return output;
		}
	});
	
	app.controller("empCntrl", function($scope,$http,$location,$window){
			$scope.employees = [],
			$scope.oldEmp = '',
			$scope.validationError = false,
			$scope.validEmailId = false,
			$scope.validFirstName = false,
			$scope.validLastName = false,
			$scope.disabled = false,
			$scope.errorMsg = "",
			$scope.initData = function(){
				$scope.username = sessionStorage.getItem("user");
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
			        $scope.showErrorAndRedirect(response,$scope,$location);
			    });
				
				$scope.form = [{
						fname : '',lname : '', email : ''
				}];
			},
			$scope.logout = function(){
				sessionStorage.removeItem("token");
				$location.path('/home');
			}
    		$scope.addUser = function(){
				var validData = false;
				if($scope.form == undefined){
					$scope.validationError = true;
				}
				
				if($scope.form != undefined){
									
					if($scope.form.fname == undefined || $scope.form.lname == undefined || $scope.form.email == undefined){
						$scope.validationError = true;
					} else if ($scope.form.fname == '' || $scope.form.lname == '' || $scope.form.email == '') {
						$scope.validationError = true;
					} else{
						$scope.validationError = false;
						validData = true;
					}
					
				}
				
				var EMAIL_REGEXP = /^[_a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
				if(validData && !EMAIL_REGEXP.test($scope.form.email)){
					validData = false;
					$scope.validEmailId = true;
				}else{
					$scope.validEmailId = false;
				}
				
				
				if(validData){
					var emp = {
							firstName:$scope.form.fname,
			            	lastName:$scope.form.lname,
			            	email:$scope.form.email,
			            	id : '0',
			            	highlight: false,
			            	user : sessionStorage.getItem("user")
						}
					var index = $scope.employees.indexOf($scope.oldEmp);
					var saveRecord = true;
					var dataChanged = false;
					var addEmp = true;
					if(index != -1){
						emp.id = $scope.oldEmp.id;
						if(emp.firstName != $scope.oldEmp.firstName || emp.lastName != $scope.oldEmp.lastName || emp.email != $scope.oldEmp.email){
							dataChanged = true; 
						}else{
							saveRecord = false;
							alert("No Data Changed!!");
						}
						
					}
					
					if(saveRecord){
						
						for(var i = 0; i < $scope.employees.length;i++){
	        				var emp1 = $scope.employees[i];
	        				if(emp.firstName == emp1.firstName && emp.lastName == emp1.lastName && emp.email == emp1.email){
	        					alert("Employee already exists");
	        					addEmp = false;
	        					break;
	        				}
						}
						if(addEmp){
							$http({
						        method: 'POST',
						        headers: {
						        	'Accept': 'application/json',
						        	'Content-Type': 'application/json',
						        	'Authorization' : sessionStorage.getItem("token")
						        },
						        url: 'http://localhost:8080/EmployeeApp/employees/saveEmployee',
						        data: emp
						    }).then(function (response) {
						        console.log(response.data);
						        if(response.status == 200 && response.data != 'fail'){
						        	$scope.employees.push(emp);
						        	emp.id = response.data;
						        	$scope.clearCssAndData();
						        	if(dataChanged){
					            		$scope.employees.splice(index, 1);
					            	}
						        }
						    }, function (response) {
						        console.log(response);
						        $scope.showErrorAndRedirect(response,$scope,$location);
						    });
						}
					}
				}
    		},
    		
    		$scope.deleteEmp = function(emp){
    			
    			var option = $window.confirm("Are you sure to delete this record?");
    			
    			if(option){
    				var index = $scope.employees.indexOf(emp);
    				$scope.employees.splice(index, 1);
    				
    				$http({
    			        method: 'DELETE',
    			        headers: {
		    			        	'Content-Type': 'application/text',
		    			        	'Authorization' : sessionStorage.getItem("token")
    			        		},
    			        url: 'http://localhost:8080/EmployeeApp/employees/deleteEmployee?empid='+emp.id,
    			        data: emp
    			    }).then(function (response) {
    			        console.log(response.data);
    			        $scope.clearCssAndData();
    			    }, function (response) {
    			        console.log(response);
    			        $scope.showErrorAndRedirect(response,$scope,$location);
    			    });
    			}
    		},
    		
    		$scope.editEmployee = function(emp){
    			$scope.form.fname = emp.firstName;
            	$scope.form.lname = emp.lastName;
            	$scope.form.email = emp.email;
    			$scope.oldEmp = emp;
    			
    			$scope.disabled = false;
    			$scope.fnameReadOnly = false;
    			$scope.lnameReadOnly = false;
    			$scope.emailReadOnly = false;
    		},
    		
    		$scope.editDetails = function(){
    			if($scope.searchBox == undefined || $scope.searchBox == ''){
    				alert("Enter Text to Search");
    			}else{
    				if(matchcnt == 1){
        				var emp = $scope.employees[matchrow];
        				$scope.form.fname = emp.firstName;
                    	$scope.form.lname = emp.lastName;
                    	$scope.form.email = emp.email;
                    	//$scope.searchBox = '';
            			$scope.disabled = true;
            			$scope.fnameReadOnly = true;
            			$scope.lnameReadOnly = true;
            			$scope.emailReadOnly = true;
        			}
    			}
    			matchcnt = 0;
    		},
    		
    		$scope.clearCssAndData = function(){
    			$scope.form.fname = '';
            	$scope.form.lname = '';
            	$scope.form.email = '';
            	$scope.searchBox = '';
            	$scope.disabled = false;
            	$scope.fnameReadOnly = false;
    			$scope.lnameReadOnly = false;
    			$scope.emailReadOnly = false;
            	for(var i = 0; i < $scope.employees.length;i++){
            		$scope.employees[i].highlight = false;
            	}
    		},
    		
    		$scope.showErrorAndRedirect = function(response,$scope,$location){
    			if(response.status == 601){
    				sessionStorage.setItem("errorMessage","Session Expired Please Login Again to Continue");
    				$location.path('/home');
    			}else if(response.status == 602 || response.status == 603 || response.status == 604){
    				sessionStorage.setItem("errorMessage","Error in session Please Login Again to Continue");
    				$location.path('/home');
    			}else if(response.data.errorCode == 501){
    				$scope.errorMsg = response.data.errorMessage;
    			} else{
    				sessionStorage.setItem("errorMessage","Please Login Again to Continue");
    				$location.path('/home');
    			}
    			
    		}
    		
	});
