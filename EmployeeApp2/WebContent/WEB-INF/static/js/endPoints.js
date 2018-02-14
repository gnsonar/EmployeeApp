angular.module('empAppC',[]).
factory('restConstants', function(){
	var endPoints = {
			EMP_VALIDATE_TOKEN_SERVICE : 'http://localhost:8080/EmployeeApp/validateToken',
			EMP_LOGIN_SERVICE :  'http://localhost:8080/EmployeeApp/home',
			EMP_GET_ALL_SERVICE :  'http://localhost:8080/EmployeeApp/employees/getAllEmployees',
			EMP_SAVE_SERVICE :  'http://localhost:8080/EmployeeApp/employees/saveEmployee',
			EMP_DEL_SERVICE :  'http://localhost:8080/EmployeeApp/employees/deleteEmployee'
	};
	
	return endPoints;
});