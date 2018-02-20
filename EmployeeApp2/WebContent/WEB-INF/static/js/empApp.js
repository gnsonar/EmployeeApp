	var mainApp = angular.module("empApp", ['ngRoute','ngTouch','ngAnimate','ui.bootstrap']);

	mainApp.factory('restConstants', function(){
		var endPoints = {
				EMP_VALIDATE_TOKEN_SERVICE : '/EmployeeApp2/validateToken',
				EMP_LOGIN_SERVICE :  '/EmployeeApp2/home',
				EMP_GET_ALL_SERVICE :  '/EmployeeApp2/employees/getAllEmployees',
				EMP_SAVE_SERVICE :  '/EmployeeApp2/employees/saveEmployee',
				EMP_SAVE_PHOTO_SERVICE :  '/EmployeeApp2/employees/saveEmployeePhoto',
				EMP_GET_PHOTO_SERVICE :  '/EmployeeApp2/employees/getEmployeePhoto',
				EMP_DEL_SERVICE :  '/EmployeeApp2/employees/deleteEmployee'
		};
		
		return endPoints;
	});
	mainApp.config(function($routeProvider) {
		$routeProvider
			.when('/home', {
				templateUrl: 'static/login.html',
				controller: 'loginCntrl'
			})
			.when('/employees', {
				templateUrl: 'static/Employee.html',
				controller: 'empCntrl'
			})
			.otherwise({
				redirectTo: '/home'
			});
	});
	
	/*mainApp.config(['$stateProvider','$urlRouterProvider','$locationProvider',
		function($stateProvider,$locationProvider,$urlRouterProvider) {
		//$locationProvider.html5Mode(true);
		$urlRouterProvider.otherwise('/');
		
		$stateProvider
		.state('login', {
			url : '/',
			templateUrl: '/static/login.html',
			controller: 'loginCntrl'
		})
		.state('employees', {
			url: '/employees',
			templateUrl: '/static/Employee.html',
			controller: 'empCntrl'
		});
	}]);*/

	mainApp.controller("loginCntrl", function($scope,$http,$location,restConstants){
		$scope.loginFailure = false,
		$scope.errorMessage = sessionStorage.getItem("errorMessage"),
		$scope.initData = function(){
			var token = sessionStorage.getItem("token");
			$scope.username = sessionStorage.getItem("user");
			if(token != null && token != ''){
				$http({
			        method: 'GET',
			        headers: {
			        	'Accept': 'text/plain',
			        	'Content-Type': 'application/json',
			        	'Authorization' : token
			        },
			        url: restConstants.EMP_VALIDATE_TOKEN_SERVICE
			    }).then(function (response) {
			        console.log(response.data);
			        if(response.data != null && response.data != '' && response.data == 'success'){
			        	$location.path('/employees');
			        }
			    });
			}
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
		        url: restConstants.EMP_LOGIN_SERVICE,
		        data : loginData
		    }).then(function (response) {
		        console.log(response.data);
		        if(response.data.sts == 'F'){
		        	 $scope.loginFailure = true;
		        }
		        if(response.data.sts == 'P'){
		        	sessionStorage.setItem("token", response.data.token);
		        	$location.path('/employees');
		        	//$state.path('/employees');
		        }
		    }, function (response) {
		        console.log(response);
		    });
		}
	});
	
	var matchcnt = 0;
	var matchrow = 0;

	mainApp.filter('empDataFilter', function(){
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
	
	mainApp.directive('filemodel', ['$parse', function($parse){
		return {
			restrict: 'A',
			link: function(scope,element,attrs){
				var model = $parse(attrs.filemodel);
					modelSetter = model.assign;
				
				element.bind('change',function(){
					scope.$apply(function(){
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	}]);
	
	mainApp.controller("empCntrl",function($scope,$http,$location,$window,restConstants,$uibModal){
			$scope.employees = [],
			$scope.oldEmp = '',
			$scope.validationError = false,
			$scope.validEmailId = false,
			$scope.validFirstName = false,
			$scope.validLastName = false,
			$scope.disabled = false,
			$scope.errorMsg = "",
			$scope.errList = [],
			$scope.initData = function(){
				$scope.username = sessionStorage.getItem("user");
				$http({
			        method: 'GET',
			        headers: {
			        	'Accept': 'application/json',
			        	'Content-Type': 'application/json',
			        	'Authorization' : sessionStorage.getItem("token")
			        },
			        url: restConstants.EMP_GET_ALL_SERVICE + '?user='+sessionStorage.getItem("user")
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
				$scope.errList = [];
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
						        	'Accept': 'text/plain',
						        	'Content-Type': 'application/json',
						        	'Authorization' : sessionStorage.getItem("token")
						        },
						        url: restConstants.EMP_SAVE_SERVICE,
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
						        	
						        	var file = $scope.form.photo;
									
									console.log('file is ' + file);

									var fd = new FormData();
						            fd.append('uploadfile', file);
						            
						            $http({
								        method: 'POST',
								        headers: {
								        	'Content-Type': undefined,
								        	'Authorization' : sessionStorage.getItem("token")
								        },
								        url: restConstants.EMP_SAVE_PHOTO_SERVICE + '?empId='+emp.id,
								        data: fd
								    }).then(function (response) {
								    	console.log(response);
								    }, function (response) {
								        console.log(response);
								        $scope.showErrorAndRedirect(response,$scope,$location);
								    })

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
    			        url: restConstants.EMP_DEL_SERVICE + '?empid='+emp.id,
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
    			}else if(response.data.errorCode == 401){
    				$scope.errorMsg = "You are not authorized to delete records";
    			} else if(response.status == 400){
    				$scope.errList = response.data.errorList;
    			} else{
    				sessionStorage.setItem("errorMessage","Please Login Again to Continue");
    				$location.path('/home');
    			}
    			
    		},
    		
    		$scope.openViewImagePopUp = function(emp){
    			sessionStorage.setItem("empId",emp.id);
    			$window.open('/EmployeeApp2/static/viewPhoto.html', "popup", "width=300,height=200,left=500,top=150");
    		},
    		
    		$scope.openModal = function(emp){
    			sessionStorage.setItem("empId",emp.id);
    			 $uibModal.open({
    				 ariaLabelledBy: 'modal-title',
    				 ariaDescribedBy: 'modal-body',
    				 templateUrl: '/EmployeeApp2/static/viewPhoto.html',
    				 controller :'photoAppCntrl',
    				 controllerAs: '$ctrl',
    				 size: 'lg',
    				 resolve: {
    				 
    				 } 
    			 });
    		}
	});

	mainApp.controller('photoAppCntrl', function($scope,$http,$uibModalInstance){
		
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
		$scope.cancelModal = function(){
			console.log("cancelmodal");
			$uibModalInstance.dismiss('close');
		}
		$scope.ok = function(){
			$uibModalInstance.close('save');
		}
	});